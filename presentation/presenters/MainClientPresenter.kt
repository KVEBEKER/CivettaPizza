package ru.kvebekshaev.civettapizza.presentation.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetProductsUseCase
import ru.kvebekshaev.civettapizza.presentation.views.MainClientView

class MainClientPresenter (
    private val getProductsUseCase: GetProductsUseCase,
    private val view: MainClientView) {
    fun getItems() {
        CoroutineScope(Dispatchers.Main).launch {
            val items: Result<List<ProductEntity>> = getProductsUseCase.execute()
            items.fold(
                onSuccess = { data: List<ProductEntity> ->
                    val mutableData: MutableList<ProductEntity> = arrayListOf()
                    for (item in data) {
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
}