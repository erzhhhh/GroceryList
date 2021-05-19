package com.example.chocotest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chocotest.base.DATABASE_NAME
import com.example.chocotest.orders.domain.OrderDao
import com.example.chocotest.orders.domain.OrderEntity
import com.example.chocotest.orders.domain.OrderProductEntity
import com.example.chocotest.products.domain.ProductDao
import com.example.chocotest.products.domain.ProductEntity

@Database(
    entities = [OrderEntity::class, ProductEntity::class, OrderProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao
    abstract fun productDao(): ProductDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}