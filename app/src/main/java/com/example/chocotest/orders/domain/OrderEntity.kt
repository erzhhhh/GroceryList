package com.example.chocotest.orders.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = OrderEntity.TABLE_NAME)
data class OrderEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "name") val name: String,
) {
    companion object {
        const val TABLE_NAME = "orders"
    }
}