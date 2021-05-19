package com.example.chocotest.products.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = ProductEntity.TABLE_NAME,
    indices = [
        Index(
            value = ["product_id"],
            unique = true
        )
    ]
)
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "photo") val photo: String,
    @ColumnInfo(name = "deleted") val deleted: Boolean,
) {
    companion object {
        const val TABLE_NAME = "products"
    }
}