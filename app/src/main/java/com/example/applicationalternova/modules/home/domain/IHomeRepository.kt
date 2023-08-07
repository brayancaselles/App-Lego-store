package com.example.applicationalternova.modules.home.domain

import com.example.applicationalternova.modules.home.model.ProductModel

interface IHomeRepository {

    suspend fun getProducts(): List<ProductModel>?

    suspend fun getNameUser(): String?
}
