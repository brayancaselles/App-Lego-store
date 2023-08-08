package com.example.applicationalternova.modules.detail.domain

import com.example.applicationalternova.modules.detail.model.DetailProductModel

interface IDetailRepository {

    suspend fun getDetailProduct(productId: String): DetailProductModel?
}
