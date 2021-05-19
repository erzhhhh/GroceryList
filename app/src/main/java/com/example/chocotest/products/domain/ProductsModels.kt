package com.example.chocotest.products.domain

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductResponse(
    @Expose
    @SerializedName("Id")
    val id: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("Description")
    val description: String,
    @Expose
    @SerializedName("price")
    val price: Int,
    @Expose
    @SerializedName("photo")
    val photo: String
) : Parcelable

fun List<ProductResponse>.mapToProductEntity(): List<ProductEntity> {
    return map {
        ProductEntity(
            productId = it.id,
            name = it.name,
            description = it.description,
            price = it.price.toDouble(),
            photo = it.photo,
            deleted = false
        )
    }
}