package ru.kvebekshaev.civettapizza.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.R
import ru.kvebekshaev.civettapizza.databinding.ItemOrderEmployeeBinding
import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.entities.UserEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductByIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.PassOrderUseCase

class OrderEmployeeAdapter (
    private val orders: MutableList<OrderEntity>,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val passOrderUseCase: PassOrderUseCase):
    RecyclerView.Adapter<OrderEmployeeAdapter.OrderEmployeeViewHolder>() {

    class OrderEmployeeViewHolder(val binding: ItemOrderEmployeeBinding) : ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderEmployeeViewHolder {
        val view = OrderEmployeeViewHolder(ItemOrderEmployeeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return view
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrderEmployeeViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.orderNumberText.setText("№${order.orderId}")
        holder.binding.orderPriceText.setText("${order.totalAmount} ₽")
        holder.binding.itemsRecyclerView.layoutManager = LinearLayoutManager(holder.binding.root.context)
        holder.binding.itemsRecyclerView.adapter = ItemsInOrderEmployeeAdapter(order.orderItems,getProductByIdUseCase)
        holder.binding.expandButton.setOnClickListener(){
            holder.binding.itemsRecyclerView.visibility = if (holder.binding.expandButton.isChecked) View.VISIBLE else View.GONE
        }
        holder.binding.passOrderButton.setOnClickListener(){
            CoroutineScope(Dispatchers.Main).launch {
                val result = passOrderUseCase.execute(order.orderId)
                result.fold(
                    onSuccess = {
                        orders.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, orders.size)
                    },
                    onFailure = { e: Throwable ->
                        MaterialAlertDialogBuilder(holder.binding.root.context, R.style.CivettaAlertDialog)
                            .setTitle("Ошибка")
                            .setMessage(e.toString())
                            .setPositiveButton("Ок",null)
                            .show()
                        e.printStackTrace()
                    }
                )
            }
        }
    }
}