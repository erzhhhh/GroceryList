package com.example.chocotest.orders.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import kotlinx.parcelize.Parcelize

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders")
    fun getAll(): Observable<List<OrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(orderEntity: OrderEntity): Long

    @Insert
    fun insertOrders(products: List<OrderProductEntity>)

    @Query("SELECT orders.id, orders.name, SUM(products.price) as sum\nFROM orders\nINNER JOIN orders_products ON orders.id = orders_products.order_id\nINNER JOIN products ON orders_products.product_id = products.product_id\nGROUP BY orders.id")
    fun fetchOrders(): Observable<List<OrderModel>>

    fun createOrder(order: Order) {
        val orderId = insertOrder(
            OrderEntity(
                name = order.name
            )
        )
        insertOrders(
            order.products.map {
                OrderProductEntity(
                    order_id = orderId,
                    product_id = it
                )
            }
        )
    }
}

data class Order(
    val name: String,
    val products: List<String>,
)

@Parcelize
data class OrderModel(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sum") val sum: Double,
) : Parcelable
