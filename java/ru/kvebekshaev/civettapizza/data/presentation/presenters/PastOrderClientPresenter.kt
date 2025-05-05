package ru.kvebekshaev.civettapizza.presentation.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetLocalUserIdUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.GetOrdersByUserIdUseCase
import ru.kvebekshaev.civettapizza.presentation.views.PastOrderClientView

class PastOrderClientPresenter(
    private val getOrdersByUserIdUseCase: GetOrdersByUserIdUseCase,
    private val getLocalUserIdUseCase: GetLocalUserIdUseCase,
    private val view: PastOrderClientView
) {
    fun getOrders(){
        CoroutineScope(Dispatchers.Main).launch {
            val id: Int = getLocalUserIdUseCase.execute()
            val orders: Result<List<OrderEntity>> = getOrdersByUserIdUseCase.execute(id)
            orders.fold(
                onSuccess = { data: List<OrderEntity> ->
                    view.fillRecyclerView(data)
                },
                onFailure = { e: Throwable ->
                    view.showError(e)
                }
            )
        }
    }
}