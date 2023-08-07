package com.example.applicationalternova.modules.home.domain

import com.example.applicationalternova.modules.home.model.ProductModel

interface IHomeInteractor {

    suspend fun getProducts(): List<ProductModel>?

    suspend fun getNameUser(): String?
}
