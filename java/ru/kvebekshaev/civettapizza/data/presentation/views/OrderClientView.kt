package ru.kvebekshaev.civettapizza.presentation.views

import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity

interface OrderClientView {
    fun showResultOrder(message: String)
    fun fillRecyclerView(items: MutableList<OrderItemEntity>)
    fun showError(e: Throwable)
    fun refreshCostAndOwlcoins(items: MutableList<OrderItemEntity>)
}