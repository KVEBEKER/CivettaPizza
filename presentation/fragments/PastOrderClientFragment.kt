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
import ru.kvebekshaev.civettapizza.data.repositories.SessionRepositoryImpl
import ru.kvebekshaev.civettapizza.databinding.FragmentPastOrderClientBinding
import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetLocalUserIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetOrdersByUserIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductByIdUseCase
import ru.kvebekshaev.civettapizza.presentation.Interfaces.NavigationVisibilityController
import ru.kvebekshaev.civettapizza.presentation.adapters.PastOrderClientAdapter
import ru.kvebekshaev.civettapizza.presentation.presenters.PastOrderClientPresenter
import ru.kvebekshaev.civettapizza.presentation.views.PastOrderClientView

class PastOrderClientFragment : Fragment(), PastOrderClientView {
    private lateinit var binding: FragmentPastOrderClientBinding
    private lateinit var presenter: PastOrderClientPresenter

    private var navigationController: NavigationVisibilityController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PastOrderClientPresenter(GetOrdersByUserIdUseCase(OrderRepositoryImpl(RetrofitClient.userApiService)),
            GetLocalUserIdUseCase(SessionRepositoryImpl(requireContext())),
            this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPastOrderClientBinding.inflate(inflater,container,false)
        presenter.getOrders()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController?.setNavigationVisibility(true)
        binding.backOrdersButton.setOnClickListener{
            findNavController().navigate(R.id.action_pastOrderClientFragment_to_orderClientFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationController = context as? NavigationVisibilityController
    }

    override fun onDetach() {
        super.onDetach()
        navigationController = null
    }

    override fun showError(e: Throwable) {
        MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
            .setTitle("Ошибка")
            .setMessage(e.toString())
            .setPositiveButton("Ок",null)
            .show()
        e.printStackTrace()
    }

    override fun fillRecyclerView(orderList: List<OrderEntity>) {
        val orderItemDatabase = OrderItemDatabase.getInstance(requireContext())
        val productRepository = ProductRepositoryImpl(RetrofitClient.userApiService,orderItemDatabase.orderItemDao())
        val layoutManager = LinearLayoutManager(this.context)
        val adapter = PastOrderClientAdapter(orderList, GetProductByIdUseCase(productRepository))
        binding.recyclerOrderView.layoutManager = layoutManager
        binding.recyclerOrderView.adapter = adapter
    }

}