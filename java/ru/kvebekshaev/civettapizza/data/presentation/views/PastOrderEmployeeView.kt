package ru.kvebekshaev.civettapizza.presentation.views

import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity

interface PastOrderEmployeeView {
    fun fillRecyclerView(orders: List<OrderEntity>)
    fun showError(e: Throwable)
}