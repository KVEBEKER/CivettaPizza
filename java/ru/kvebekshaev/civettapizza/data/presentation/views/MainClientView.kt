package ru.kvebekshaev.civettapizza.presentation.views

import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity

interface MainClientView {
    fun fillRecyclerView(mutableData: MutableList<ProductEntity>)
    fun showError(e: Throwable)
}