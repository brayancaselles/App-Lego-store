package com.example.applicationalternova.modules.common.retrofit

import com.example.applicationalternova.modules.detail.model.DetailProductModel
import com.example.applicationalternova.modules.home.model.ProductsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface ApiService {
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("allProducts")
    suspend fun getAllProducts(): Response<ProductsModel>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET
    suspend fun getDetailProduct(@Url url: String): Response<DetailProductModel>
}
