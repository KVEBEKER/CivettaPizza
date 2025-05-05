package ru.kvebekshaev.civettapizza.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.databinding.ItemInOrderClientBinding
import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductByIdUseCase

class ItemsInOrderClientAdapter(private var items: List<OrderItemEntity>, private val getProductByIdUseCase: GetProductByIdUseCase):
    RecyclerView.Adapter<ItemsInOrderClientAdapter.ItemInOrderViewHolder>() {


    class ItemInOrderViewHolder(val binding: ItemInOrderClientBinding) : ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemInOrderViewHolder {
        val view = ItemInOrderViewHolder(
            ItemInOrderClientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return view
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemInOrderViewHolder, position: Int) {
        val item = items[position]
        refreshData(item, holder)
    }

    private fun refreshData(item: OrderItemEntity, holder: ItemInOrderViewHolder) {
        holder.binding.qualityCount.text = item.quality.toString()
        holder.binding.priceOrderItemText.setText((item.price * item.quality).toString() + " ₽")
        CoroutineScope(Dispatchers.Main).launch {
            val name: Result<ProductEntity> = getProductByIdUseCase.execute(item.productId)
            name.fold(
                onSuccess = { data: ProductEntity ->
                    holder.binding.productNameOrderItemText.setText(data.productName)
                },
                onFailure = { e: Throwable ->
                    holder.binding.productNameOrderItemText.setText(e.toString())
                }
            )
        }
    }
}