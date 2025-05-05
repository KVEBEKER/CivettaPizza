package ru.kvebekshaev.civettapizza.presentation.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.usecases.ClearItemsUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetItemsUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetLocalUserIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.SendOrderUseCase
import ru.kvebekshaev.civettapizza.presentation.views.OrderClientView

class OrderClientPresenter(
    private val getItemsUseCase: GetItemsUseCase,
    private val sendOrderUseCase: SendOrderUseCase,
    private val getLocalUserIdUseCase: GetLocalUserIdUseCase,
    private val clearItemsUseCase: ClearItemsUseCase,
    private val view: OrderClientView
) {
    fun getItems(){
        CoroutineScope(Dispatchers.Main).launch {
            val items: Result<List<OrderItemEntity>> = getItemsUseCase.execute()
            items.fold(
                onSuccess = { data: List<OrderItemEntity> ->
                    val mutableData : MutableList<OrderItemEntity> = arrayListOf()
                    for(item in data){
                        mutableData.add(item)
                    }
                    view.fillRecyclerView(mutableData)
                },
                onFailure = { e: Throwable ->
                    view.showError(e)
                }
            )
        }
    }
    fun sendOrder(){
        val userId = getLocalUserIdUseCase.execute()
        CoroutineScope(Dispatchers.Main).launch {
            val items: Result<List<OrderItemEntity>> = getItemsUseCase.execute()
            items.fold(
                onSuccess = { data: List<OrderItemEntity> ->
                    val message: Result<String> = sendOrderUseCase.execute(userId,data)
                    message.fold(
                        onSuccess = { data: String ->
                            view.showResultOrder(data)
                        },
                        onFailure = { e: Throwable ->
                            view.showError(e)
                        }
                    )
                },
                onFailure = { e: Throwable ->
                    view.showError(e)
                }
            )
        }
    }
    fun clearItems(){
        clearItemsUseCase.execute()
    }
    fun refreshCostAndOwlcoin() {
        CoroutineScope(Dispatchers.Main).launch {
            val items: Result<List<OrderItemEntity>> = getItemsUseCase.execute()
            items.fold(
                onSuccess = { data: List<OrderItemEntity> ->
                    val mutableData : MutableList<OrderItemEntity> = arrayListOf()
                    for(item in data){
                        mutableData.add(item)
                    }
                    view.refreshCostAndOwlcoins(mutableData)
                },
                onFailure = { e: Throwable ->
                    view.showError(e)
                }
            )
        }
    }
}