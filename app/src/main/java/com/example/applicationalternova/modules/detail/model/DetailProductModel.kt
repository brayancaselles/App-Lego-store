package com.example.applicationalternova.modules.detail.model

import com.google.gson.annotations.SerializedName

data class DetailProductModel(
    @SerializedName("id") val idProduct: Int? = null,
    @SerializedName("name") val nameProduct: String? = null,
    @SerializedName("unit_price") val priceProduct: Int? = null,
    @SerializedName("stock") val stock: Int? = null,
    @SerializedName("image") val imageProduct: String? = null,
    @SerializedName("description") val descriptionProduct: String? = null,
)
