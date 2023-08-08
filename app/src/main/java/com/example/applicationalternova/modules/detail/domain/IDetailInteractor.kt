package com.example.applicationalternova.modules.detail.domain

import com.example.applicationalternova.modules.detail.model.DetailProductModel

interface IDetailInteractor {

    suspend fun getDetailProduct(productId: String): DetailProductModel?

    fun validateStock(stock: Int): Int
}
