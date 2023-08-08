package com.example.applicationalternova.modules.detail.data

import com.example.applicationalternova.modules.common.retrofit.ApiService
import com.example.applicationalternova.modules.detail.model.DetailProductModel

class DetailDataSource(private val network: ApiService) : IDetailDataSource {

    override suspend fun getDetailProduct(productId: String): DetailProductModel? {
        val baseUrl = "https://us-central1-api-back-admission-test.cloudfunctions.net/detail?id=%s"
        val urlComplete = String.format(baseUrl, productId)

        val call = network.getDetailProduct(urlComplete)

        return if (call.isSuccessful) {
            call.body()
        } else {
            null
        }
    }
}
