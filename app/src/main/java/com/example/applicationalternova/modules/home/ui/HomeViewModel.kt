package com.example.applicationalternova.modules.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationalternova.modules.common.firebase.FirebaseAuthManager
import com.example.applicationalternova.modules.common.retrofit.RetrofitManager
import com.example.applicationalternova.modules.home.data.HomeDataSource
import com.example.applicationalternova.modules.home.data.HomeRepository
import com.example.applicationalternova.modules.home.domain.HomeInteractor
import com.example.applicationalternova.modules.home.domain.IHomeInteractor
import com.example.applicationalternova.modules.home.model.ProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ListProducts(val list: List<ProductModel>? = null)
data class Error(val error: Any? = null)
data class UserName(val userName: String? = null)
class HomeViewModel : ViewModel() {

    private val interactor: IHomeInteractor =
        HomeInteractor(
            repository = HomeRepository(
                dataSource = HomeDataSource(
                    RetrofitManager.apiService,
                    FirebaseAuthManager(),
                ),
            ),
        )

    private val _error: MutableStateFlow<Error> by lazy { MutableStateFlow(Error()) }
    val error: StateFlow<Error> get() = _error

    private val _products: MutableStateFlow<ListProducts> by lazy { MutableStateFlow(ListProducts()).also { loadProducts() } }
    val products: StateFlow<ListProducts> get() = _products

    private val _userName: MutableStateFlow<UserName> by lazy { MutableStateFlow(UserName()).also { loadName() } }
    val userName: StateFlow<UserName> get() = _userName

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                _products.update { currentState -> currentState.copy(list = interactor.getProducts()) }
            } catch (e: Exception) {
                _error.update { currentState -> currentState.copy(error = e) }
            }
        }
    }

    private fun loadName() {
        viewModelScope.launch {
            _userName.update { currentState -> currentState.copy(userName = interactor.getNameUser()) }
        }
    }
}
