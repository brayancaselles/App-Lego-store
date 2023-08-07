package com.example.applicationalternova.modules.home.domain

import com.example.applicationalternova.modules.home.model.ProductModel

class HomeInteractor(private val repository: IHomeRepository) : IHomeInteractor {

    override suspend fun getProducts(): List<ProductModel>? {
        return repository.getProducts()
    }

    override suspend fun getNameUser(): String? {
        return repository.getNameUser()
    }
}
