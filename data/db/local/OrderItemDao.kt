package ru.kvebekshaev.civettapizza.data.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kvebekshaev.civettapizza.data.dto.OrderItemDto

@Dao
interface OrderItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrderItem(product: OrderItemDto)

    @Query("Update order_item Set quality = quality+:changing where product_id = :id")
    fun ChangeQualityById(id: Int, changing: Int)

    @Query("Delete from order_item where product_id = :id")
    fun deleteOrderItem(id: Int)

    @Query("Delete from order_item")
    fun clearOrderItem()

    @Query("Select * from order_item")
    fun getAllOrderItem(): Flow<List<OrderItemDto>>

    @Query("Select * from order_item where product_id = :id")
    fun getOrderItemById(id: Int): Flow<OrderItemDto>
}