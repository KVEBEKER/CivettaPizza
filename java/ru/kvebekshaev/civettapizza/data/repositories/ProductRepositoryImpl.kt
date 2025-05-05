package ru.kvebekshaev.civettapizza.data.repositories

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kvebekshaev.civettapizza.data.db.api.ApiService
import ru.kvebekshaev.civettapizza.data.db.local.OrderItemDao
import ru.kvebekshaev.civettapizza.data.dto.ProductDto
import ru.kvebekshaev.civettapizza.domain.entities.ProductCategory
import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.repositories.ProductRepository

class ProductRepositoryImpl(private val apiService: ApiService,private val orderItemDao: OrderItemDao): ProductRepository {

    override suspend fun getProducts(): Result<List<ProductEntity>> {
        return try {
            val products = apiService.getProducts()
            Result.success(products.map { it.toDomain() })
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: Int): Result<ProductEntity> {
        return try {
            val products = apiService.getProducts().map {it.toDomain()}
            val product = products.find { it.productId == id } // Используем find
                ?: return Result.failure(Throwable("Продукция $id не найдена"))
            Result.success(product)
        } catch (e: Exception){
            Result.failure(e)
        }
    }


}
private fun ProductDto.toDomain() = ProductEntity(
    productId = this.productId,
    productName = this.productName,
    price = this.price,
    imageLink = this.imageLink,
    description = this.description,
    category = when(this.category){
        1 -> ProductCategory.Combo
        2 -> ProductCategory.Pizza
        3 -> ProductCategory.Drink
        4 -> ProductCategory.Snack
        5 -> ProductCategory.Desert
        else -> ProductCategory.Pizza
    },
    isAvailable = this.isAvailable
)
