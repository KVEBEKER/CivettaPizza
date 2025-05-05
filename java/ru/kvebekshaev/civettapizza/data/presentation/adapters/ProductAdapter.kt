package ru.kvebekshaev.civettapizza.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.databinding.ItemProductBigBinding
import ru.kvebekshaev.civettapizza.databinding.ItemProductSmallBinding
import ru.kvebekshaev.civettapizza.domain.entities.ProductCategory
import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.usecases.AddItemUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.ChangeQualityByIdItemUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetItemByIdUseCase

class ProductAdapter(
    private val products: List<ProductEntity>,
    private val addItemUseCase: AddItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val changeQualityByIdItemUseCase: ChangeQualityByIdItemUseCase)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
       const val TYPE_BIG = 0
       const val TYPE_SMALL = 1
    }

    class ProductBigViewHolder(val binding: ItemProductBigBinding): ViewHolder(binding.root){
    }
    class ProductSmallViewHolder(val binding: ItemProductSmallBinding): ViewHolder(binding.root){
    }

    override fun getItemViewType(position: Int): Int {
        return when(products[position].category){
            ProductCategory.Combo ->{
                TYPE_BIG
            }

            ProductCategory.Pizza ->{
                TYPE_BIG
            }

            else -> {
                TYPE_SMALL
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
            TYPE_BIG ->{
                val view = ProductBigViewHolder(ItemProductBigBinding.inflate(LayoutInflater.from(parent.context),parent,false))
                return view
            }
            TYPE_SMALL ->{
                val  view = ProductSmallViewHolder(ItemProductSmallBinding.inflate(LayoutInflater.from(parent.context),parent,false))
                return view
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        when(holder){
            is ProductBigViewHolder -> {
                holder.binding.productNameText.setText(product.productName)
                holder.binding.productDescriptionText.setText(product.description)
                holder.binding.buttonBuy.text = product.price.toString()+" ₽"
                holder.binding.buttonBuy.setOnClickListener{
                    order(product)
                }
            }
            is ProductSmallViewHolder -> {
                holder.binding.productNameText.setText(product.productName)
                holder.binding.buttonBuy.text = product.price.toString()+" ₽"
                holder.binding.buttonBuy.setOnClickListener{
                    order(product)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun order(product: ProductEntity){
        CoroutineScope(Dispatchers.IO).launch {
            getItemByIdUseCase.execute(product.productId).fold(
                onSuccess = {changeQualityByIdItemUseCase.execute(product.productId, 1)},
                onFailure = {addItemUseCase.execute(product)}
            )
        }
    }

}