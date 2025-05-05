package ru.kvebekshaev.civettapizza.presentation.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetPassedOrdersUseCase
import ru.kvebekshaev.civettapizza.presentation.views.PastOrderEmployeeView

class PastOrderEmployeePresenter(
    private val getPassedOrdersUseCase: GetPassedOrdersUseCase,
    private val view: PastOrderEmployeeView
) {
    fun getOrders(){
        CoroutineScope(Dispatchers.Main).launch {
            val orders: Result<List<OrderEntity>> = getPassedOrdersUseCase.execute()
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