package ru.kvebekshaev.civettapizza.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.kvebekshaev.civettapizza.R
import ru.kvebekshaev.civettapizza.data.db.api.RetrofitClient
import ru.kvebekshaev.civettapizza.data.db.local.OrderItemDatabase
import ru.kvebekshaev.civettapizza.data.repositories.OrderRepositoryImpl
import ru.kvebekshaev.civettapizza.data.repositories.ProductRepositoryImpl
import ru.kvebekshaev.civettapizza.databinding.FragmentMainEmployeeBinding
import ru.kvebekshaev.civettapizza.databinding.FragmentOrderEmployeeBinding
import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetPendingOrdersUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductByIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.PassOrderUseCase
import ru.kvebekshaev.civettapizza.presentation.Interfaces.NavigationVisibilityController
import ru.kvebekshaev.civettapizza.presentation.adapters.OrderEmployeeAdapter
import ru.kvebekshaev.civettapizza.presentation.adapters.PastOrderClientAdapter
import ru.kvebekshaev.civettapizza.presentation.presenters.OrderEmployeePresenter
import ru.kvebekshaev.civettapizza.presentation.views.OrderEmployeeView

class OrderEmployeeFragment : Fragment(), OrderEmployeeView {
    lateinit var binding: FragmentOrderEmployeeBinding
    lateinit var presenter: OrderEmployeePresenter

    private var navigationController: NavigationVisibilityController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationController = context as? NavigationVisibilityController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = OrderRepositoryImpl(RetrofitClient.userApiService)
        presenter = OrderEmployeePresenter(GetPendingOrdersUseCase(repository),this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderEmployeeBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController?.setNavigationVisibility(false)
        presenter.getOrders()
        binding.seePastOrdersButton.setOnClickListener(){
            findNavController().navigate(R.id.action_orderEmployeeFragment_to_mainEmployeeFragment)
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationController = null
    }

    override fun fillRecyclerView(orders: MutableList<OrderEntity>) {
        val orderItemDatabase = OrderItemDatabase.getInstance(requireContext())
        val productRepository = ProductRepositoryImpl(RetrofitClient.userApiService,orderItemDatabase.orderItemDao())
        val orderRepository = OrderRepositoryImpl(RetrofitClient.userApiService)
        val layoutManager = LinearLayoutManager(this.context)
        val adapter = OrderEmployeeAdapter(
            orders,
            GetProductByIdUseCase(productRepository),
            PassOrderUseCase(orderRepository)
        )
        binding.recyclerOrderEmployeeView.layoutManager = layoutManager
        binding.recyclerOrderEmployeeView.adapter = adapter
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