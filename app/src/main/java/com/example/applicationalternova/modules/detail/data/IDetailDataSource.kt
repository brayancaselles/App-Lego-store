package com.example.applicationalternova.modules.detail.data

import com.example.applicationalternova.modules.detail.model.DetailProductModel

interface IDetailDataSource {

    suspend fun getDetailProduct(productId: String): DetailProductModel?
}
