package com.example.chocotest.orders.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.chocotest.products.domain.ProductEntity

@Entity(
    tableName = OrderProductEntity.TABLE_NAME,
    indices = [
        Index(
            value = ["product_id", "order_id"],
            unique = true
        ),
        Index(
            value = ["product_id"],
            unique = false
        ),
        Index(
            value = ["order_id"],
            unique = false
        ),
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["product_id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.NO_ACTION,
        ),
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["order_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class OrderProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "order_id")
    val order_id: Long,
    @ColumnInfo(name = "product_id")
    val product_id: String,
) {
    companion object {
        const val TABLE_NAME = "orders_products"
    }
}