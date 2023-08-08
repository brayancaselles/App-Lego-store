package com.example.applicationalternova.modules.detail.domain

import com.example.applicationalternova.modules.detail.model.DetailProductModel

class DetailInteractor(private val repository: IDetailRepository) : IDetailInteractor {

    private var counter: Int = 1

    override suspend fun getDetailProduct(productId: String): DetailProductModel? {
        return repository.getDetailProduct(productId)
    }

    override fun validateStock(stock: Int): Int {
        return if (counter <= stock) {
            counter++
        } else {
            counter
        }
    }
}
