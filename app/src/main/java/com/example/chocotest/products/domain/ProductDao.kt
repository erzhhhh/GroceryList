package com.example.chocotest.products.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProducts(products: List<ProductEntity>)

    @Query("Update products SET deleted = 1 WHERE product_id not in (:ids)")
    fun setDeletedForNonExist(ids: List<String>)

    @Query("SELECT products.* \nFROM orders_products\nINNER JOIN products ON orders_products.product_id = products.product_id\nWHERE orders_products.order_id = :orderId")
    fun getOrderProducts(orderId: Long): Single<List<ProductEntity>>

    @Transaction
    fun updateBase(products: List<ProductEntity>) {
        insertAllProducts(products)
        setDeletedForNonExist(products.map(ProductEntity::productId))
    }
}