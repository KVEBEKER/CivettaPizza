package ru.kvebekshaev.civettapizza.data.db.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.kvebekshaev.civettapizza.data.dto.OrderItemDto

@Database(entities = [OrderItemDto::class], version = 1)
abstract class OrderItemDatabase : RoomDatabase() {
    abstract fun orderItemDao(): OrderItemDao

    companion object {
        private var INSTANCE: OrderItemDatabase? = null
        fun getInstance(context: Context): OrderItemDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OrderItemDatabase::class.java,
                        "OrderItem_database"
                    ).fallbackToDestructiveMigration(false).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}