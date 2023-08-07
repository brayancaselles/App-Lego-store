package com.example.applicationalternova.modules.home.model

import com.google.gson.annotations.SerializedName

data class ProductsModel(@SerializedName("products") var listProducts: List<ProductModel>)
