package ru.kvebekshaev.civettapizza.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kvebekshaev.civettapizza.databinding.ItemOrderClientBinding
import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductByIdUseCase

class PastOrderEmployeeAdapter (private val orders: List<OrderEntity>,
private val getProductByIdUseCase: GetProductByIdUseCase):
RecyclerView.Adapter<PastOrderEmployeeAdapter.OrderEmployeeViewHolder>() {

    class OrderEmployeeViewHolder(val binding: ItemOrderClientBinding) : ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderEmployeeViewHolder {
        val view = OrderEmployeeViewHolder(ItemOrderClientBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
    }
}