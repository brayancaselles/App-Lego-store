package com.example.applicationalternova.modules.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationalternova.modules.common.retrofit.RetrofitManager
import com.example.applicationalternova.modules.detail.data.DetailDataSource
import com.example.applicationalternova.modules.detail.data.DetailRepository
import com.example.applicationalternova.modules.detail.domain.DetailInteractor
import com.example.applicationalternova.modules.detail.domain.IDetailInteractor
import com.example.applicationalternova.modules.detail.model.DetailProductModel
import com.example.applicationalternova.modules.home.ui.Error
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailProduct(val detailProduct: DetailProductModel? = null)
class DetailProductViewModel() : ViewModel() {

    private val interactor: IDetailInteractor =
        DetailInteractor(repository = DetailRepository(dataSource = DetailDataSource(network = RetrofitManager.apiService)))

    private var productId: String? = null

    private val _error: MutableStateFlow<Error> by lazy { MutableStateFlow(Error()) }
    val error: StateFlow<Error> get() = _error

    private val _product: MutableStateFlow<DetailProduct> by lazy { MutableStateFlow(DetailProduct()).also { loadProduct() } }
    val product: StateFlow<DetailProduct> get() = _product

    private val _validateStock = MutableLiveData<Int>()
    val validateStock: LiveData<Int> get() = _validateStock

    fun setProductId(productId: String) {
        this.productId = productId
    }

    private fun loadProduct() {
        viewModelScope.launch {
            try {
                _product.update { currentState ->
                    currentState.copy(
                        detailProduct = productId?.let { interactor.getDetailProduct(it) },
                    )
                }
            } catch (e: Exception) {
                _error.update { currentState -> currentState.copy(error = e) }
            }
        }
    }

    fun validateStock(stock: Int) {
        _validateStock.postValue(interactor.validateStock(stock))
    }
}
