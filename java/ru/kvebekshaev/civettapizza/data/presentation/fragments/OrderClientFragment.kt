package ru.kvebekshaev.civettapizza.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.kvebekshaev.civettapizza.R
import ru.kvebekshaev.civettapizza.data.db.api.RetrofitClient
import ru.kvebekshaev.civettapizza.data.db.local.OrderItemDatabase
import ru.kvebekshaev.civettapizza.data.repositories.OrderItemRepositoryImpl
import ru.kvebekshaev.civettapizza.data.repositories.ProductRepositoryImpl
import ru.kvebekshaev.civettapizza.data.repositories.SessionRepositoryImpl
import ru.kvebekshaev.civettapizza.databinding.FragmentOrderClientBinding
import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.usecases.ChangeQualityByIdItemUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.ClearItemsUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.DeleteItemByIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetItemsUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetLocalUserIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductByIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.SendOrderUseCase
import ru.kvebekshaev.civettapizza.presentation.Interfaces.NavigationVisibilityController
import ru.kvebekshaev.civettapizza.presentation.adapters.OrderItemClientAdapter
import ru.kvebekshaev.civettapizza.presentation.presenters.OrderClientPresenter
import ru.kvebekshaev.civettapizza.presentation.views.OrderClientView
import kotlin.math.roundToInt


class OrderClientFragment : Fragment(), OrderClientView {
    private lateinit var binding: FragmentOrderClientBinding
    private lateinit var presenter: OrderClientPresenter

    private var navigationController: NavigationVisibilityController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderItemDatabase = OrderItemDatabase.getInstance(requireContext())
        val orderItemRepository = OrderItemRepositoryImpl(RetrofitClient.userApiService,orderItemDatabase.orderItemDao())
        presenter = OrderClientPresenter(GetItemsUseCase(orderItemRepository),
            SendOrderUseCase(orderItemRepository), GetLocalUserIdUseCase(SessionRepositoryImpl(requireContext())),
            ClearItemsUseCase(orderItemRepository),this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationController = context as? NavigationVisibilityController
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderClientBinding.inflate(inflater,container,false)
        presenter.getItems()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController?.setNavigationVisibility(true)
        binding.makeOrderButton.setOnClickListener{
            presenter.sendOrder()
        }
        binding.seePastOrdersButton.setOnClickListener{
            parentFragment?.findNavController()?.navigate(R.id.action_orderClientFragment_to_pastOrderClientFragment)
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationController = null
    }

    override fun showResultOrder(message: String) {
        MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
            .setTitle("Результат")
            .setMessage("$message У нас оплата не реализована, так что заказ оформлен бесплатно")
            .setPositiveButton("Ок",null)
            .show()
        presenter.clearItems()
        val adapter = binding.recyclerOrderItemView.adapter as OrderItemClientAdapter
        adapter.clearItems()
    }

    override fun fillRecyclerView(items: MutableList<OrderItemEntity>) {
        val orderItemDatabase = OrderItemDatabase.getInstance(requireContext())
        val orderItemRepository = OrderItemRepositoryImpl(RetrofitClient.userApiService,orderItemDatabase.orderItemDao())
        val productRepository = ProductRepositoryImpl(RetrofitClient.userApiService,orderItemDatabase.orderItemDao())
        val layoutManager = GridLayoutManager(this.context,1)
        val adapter = OrderItemClientAdapter(
            items,
            GetProductByIdUseCase(productRepository),
            DeleteItemByIdUseCase(orderItemRepository),
            ChangeQualityByIdItemUseCase(orderItemRepository),
            GetItemsUseCase(orderItemRepository),
            presenter
        )
        binding.recyclerOrderItemView.layoutManager = layoutManager
        binding.recyclerOrderItemView.adapter = adapter
        presenter.refreshCostAndOwlcoin()
    }

    override fun showError(e: Throwable) {
        MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
            .setTitle("Ошибка")
            .setMessage(e.toString())
            .setPositiveButton("Ок",null)
            .show()
        e.printStackTrace()
    }

    override fun refreshCostAndOwlcoins(items: MutableList<OrderItemEntity>) {
        var totalAmount: Float = 0F
        for (item in items){
            totalAmount += item.price * item.quality
        }
        binding.labelNumericPrice.setText("${totalAmount} ₽")
        binding.labelNumericOwlCoin.setText("+${(totalAmount/20).roundToInt()}")
    }
}