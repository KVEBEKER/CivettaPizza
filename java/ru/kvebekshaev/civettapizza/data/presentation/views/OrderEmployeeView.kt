package ru.kvebekshaev.civettapizza.presentation.views

import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity

interface OrderEmployeeView {
    fun fillRecyclerView(orders: MutableList<OrderEntity>)
    fun showError(e: Throwable)
}