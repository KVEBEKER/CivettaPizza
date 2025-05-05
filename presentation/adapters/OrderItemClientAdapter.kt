package ru.kvebekshaev.civettapizza.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.databinding.ItemOrderItemClientBinding
import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.usecases.ChangeQualityByIdItemUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.DeleteItemByIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetItemsUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductByIdUseCase
import ru.kvebekshaev.civettapizza.presentation.presenters.OrderClientPresenter

class OrderItemClientAdapter(
    private var items: MutableList<OrderItemEntity>,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val deleteItemByIdUseCase: DeleteItemByIdUseCase,
    private val changeQualityByIdItemUseCase: ChangeQualityByIdItemUseCase,
    private val getItemsUseCase: GetItemsUseCase,
    private val presenter: OrderClientPresenter
    ):
    RecyclerView.Adapter<OrderItemClientAdapter.OrderItemClientViewHolder>() {

    class OrderItemClientViewHolder(val binding: ItemOrderItemClientBinding): ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemClientViewHolder {
        val  view = OrderItemClientViewHolder(ItemOrderItemClientBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return view
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OrderItemClientViewHolder, position: Int) {
        val item = items[position]
        refreshData(item, holder)
        holder.binding.plusButton.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                changeQualityByIdItemUseCase.execute(item.productId, 1)
                val newItems = getItemsUseCase.execute()
                newItems.fold(
                    onSuccess = {data: List<OrderItemEntity> ->
                        items = data.toMutableList()
                        notifyDataSetChanged()
                        presenter.refreshCostAndOwlcoin()
                    },
                    onFailure = {
                        refreshData(item, holder)
                    }
                )
            }
        }
        holder.binding.minusButton.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                if (holder.binding.qualityCount.text.toString().toInt()-1 > 0){
                    changeQualityByIdItemUseCase.execute(item.productId, -1)
                    val newItems = getItemsUseCase.execute()
                    newItems.fold(
                        onSuccess = {data: List<OrderItemEntity> ->
                            items = data.toMutableList()
                            notifyDataSetChanged()
                            presenter.refreshCostAndOwlcoin()
                        },
                        onFailure = {
                            refreshData(item, holder)
                        }
                    )
                } else{
                    CoroutineScope(Dispatchers.Main).launch {
                        deleteItemByIdUseCase.execute(item.productId)
                        presenter.refreshCostAndOwlcoin()
                        removeItem(position)
                    }
                }
            }
        }
    }

    private fun removeItem(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    private fun refreshData(item: OrderItemEntity, holder: OrderItemClientViewHolder){
        holder.binding.qualityCount.text = item.quality.toString()
        holder.binding.priceOrderItemText.setText((item.price*item.quality).toString()+" ₽")
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

    fun clearItems(){
        items = emptyList<OrderItemEntity>().toMutableList()
        notifyDataSetChanged()
    }
}