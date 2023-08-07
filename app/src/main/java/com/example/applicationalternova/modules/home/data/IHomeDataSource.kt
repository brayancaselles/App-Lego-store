package com.example.applicationalternova.modules.home.data

import com.example.applicationalternova.modules.home.model.ProductsModel

interface IHomeDataSource {

    suspend fun getProducts(): ProductsModel?

    suspend fun getNameUser(): String?
}
