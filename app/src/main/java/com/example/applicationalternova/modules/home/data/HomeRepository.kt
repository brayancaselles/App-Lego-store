package com.example.applicationalternova.modules.home.data

import com.example.applicationalternova.modules.home.domain.IHomeRepository
import com.example.applicationalternova.modules.home.model.ProductModel

class HomeRepository(private val dataSource: IHomeDataSource) : IHomeRepository {
    override suspend fun getProducts(): List<ProductModel>? {
        val response = dataSource.getProducts()
        return response?.listProducts
    }

    override suspend fun getNameUser(): String? {
        return dataSource.getNameUser()
    }
}
