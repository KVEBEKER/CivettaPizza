package ru.kvebekshaev.civettapizza.presentation.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetPendingOrdersUseCase
import ru.kvebekshaev.civettapizza.presentation.views.OrderEmployeeView

class OrderEmployeePresenter(
    private val getPendingUseCase: GetPendingOrdersUseCase,
    private val view: OrderEmployeeView
) {
    fun getOrders(){
        CoroutineScope(Dispatchers.Main).launch {
            val orders: Result<List<OrderEntity>> = getPendingUseCase.execute()
            orders.fold(
                onSuccess = { data: List<OrderEntity> ->
                    view.fillRecyclerView(data.toMutableList())
                },
                onFailure = { e: Throwable ->
                    view.showError(e)
                }
            )
        }
    }
}