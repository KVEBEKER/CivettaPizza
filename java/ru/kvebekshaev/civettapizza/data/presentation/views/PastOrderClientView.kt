package ru.kvebekshaev.civettapizza.presentation.views

import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity

interface PastOrderClientView {
    fun showError(e: Throwable)
    fun fillRecyclerView(orderList: List<OrderEntity>)
}