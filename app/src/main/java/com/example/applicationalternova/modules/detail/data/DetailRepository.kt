package com.example.applicationalternova.modules.detail.data

import com.example.applicationalternova.modules.detail.domain.IDetailRepository
import com.example.applicationalternova.modules.detail.model.DetailProductModel

class DetailRepository(private val dataSource: IDetailDataSource) : IDetailRepository {

    override suspend fun getDetailProduct(productId: String): DetailProductModel? {
        return dataSource.getDetailProduct(productId)
    }
}
