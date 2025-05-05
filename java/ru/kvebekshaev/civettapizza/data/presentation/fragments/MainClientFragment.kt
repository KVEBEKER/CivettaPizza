package ru.kvebekshaev.civettapizza.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.kvebekshaev.civettapizza.R
import ru.kvebekshaev.civettapizza.data.db.api.RetrofitClient
import ru.kvebekshaev.civettapizza.data.db.local.OrderItemDatabase
import ru.kvebekshaev.civettapizza.data.repositories.OrderItemRepositoryImpl
import ru.kvebekshaev.civettapizza.data.repositories.ProductRepositoryImpl
import ru.kvebekshaev.civettapizza.databinding.FragmentMainClientBinding
import ru.kvebekshaev.civettapizza.domain.entities.ProductCategory
import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.usecases.AddItemUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.ChangeQualityByIdItemUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.DeleteItemByIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetItemByIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetItemsUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductByIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductsUseCase
import ru.kvebekshaev.civettapizza.presentation.Interfaces.NavigationVisibilityController
import ru.kvebekshaev.civettapizza.presentation.adapters.ProductAdapter
import ru.kvebekshaev.civettapizza.presentation.presenters.MainClientPresenter
import ru.kvebekshaev.civettapizza.presentation.views.MainClientView


class MainClientFragment : Fragment(), MainClientView {
    private lateinit var binding: FragmentMainClientBinding
    private var navigationController: NavigationVisibilityController? = null
    private lateinit var presenter: MainClientPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderItemDatabase = OrderItemDatabase.getInstance(requireContext())
        val productRepository = ProductRepositoryImpl(RetrofitClient.userApiService,orderItemDatabase.orderItemDao())
        presenter = MainClientPresenter(GetProductsUseCase(productRepository),this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainClientBinding.inflate(inflater,container,false)
        presenter.getItems()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationController = context as? NavigationVisibilityController
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController?.setNavigationVisibility(true)
        binding.pizzaCategoryButton.setOnClickListener{
            val position = findNextPositionOfType(ProductAdapter.TYPE_BIG, 0, binding.productRecyclerView.adapter as ProductAdapter)
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationController = null
    }

    fun findNextPositionOfType(type: Int, startFrom: Int, adapter: ProductAdapter): Int{
        for(i in startFrom until adapter.itemCount){
            if(adapter.getItemViewType(i) == type){
                return i
            }
        }
        return  -1
    }

    val productComparator: Comparator<ProductEntity> = object : Comparator<ProductEntity>{
        override fun compare(product1: ProductEntity, product2: ProductEntity): Int {
            val categoryCompare = product1.category.priority.compareTo(product2.category.priority)
            if(categoryCompare != 0){
                return  categoryCompare
            } else{
                return product2.productId.compareTo(product1.productId)
            }
        }
    }

    override fun fillRecyclerView(products: MutableList<ProductEntity>) {
        val sortedProducts = products.sortedWith(productComparator)
        val orderItemDatabase = OrderItemDatabase.getInstance(requireContext())
        val orderItemRepository = OrderItemRepositoryImpl(RetrofitClient.userApiService,orderItemDatabase.orderItemDao())
        val adapter = ProductAdapter(
            products,
            AddItemUseCase(orderItemRepository),
            GetItemByIdUseCase(orderItemRepository),
            ChangeQualityByIdItemUseCase(orderItemRepository)
        )
        val layoutManager = GridLayoutManager(this.context,2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return when(adapter.getItemViewType(position)){
                    ProductAdapter.TYPE_BIG -> 2
                    ProductAdapter.TYPE_SMALL -> 1
                    else -> 1
                }
            }
        }
        binding.productRecyclerView.layoutManager = layoutManager
        binding.productRecyclerView.adapter = adapter
    }

    override fun showError(e: Throwable) {
        MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
            .setTitle("Ошибка")
            .setMessage(e.toString())
            .setPositiveButton("Ок",null)
            .show()
        e.printStackTrace()
    }
}