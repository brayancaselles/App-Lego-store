package com.example.applicationalternova.modules.home.model

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("id") val idProduct: Int? = null,
    @SerializedName("name") val nameProduct: String? = null,
    @SerializedName("unit_price") val priceProduct: Int? = null,
    @SerializedName("stock") val stock: Int? = null,
    @SerializedName("image") val imageProduct: String? = null,
)
