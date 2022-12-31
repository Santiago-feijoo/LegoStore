package com.feijoo.legostore.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feijoo.legostore.common.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {
    /** Attributes **/
    val getAllProducts: LiveData<ArrayList<Product>> get() = _getAllProducts
    private val _getAllProducts = MutableLiveData<ArrayList<Product>>()

    val getProductDetail: LiveData<Pair<Product, Int>> get() = _getProductDetail
    private val _getProductDetail = MutableLiveData<Pair<Product, Int>>()

    val updatedStock: LiveData<List<Product>> get() = _updatedStock
    private val _updatedStock = MutableLiveData<List<Product>>()

    val error: LiveData<String> get() = _error
    private val _error = MutableLiveData<String>()

    /** Methods **/
    fun getAllProducts() {
        try {
             viewModelScope.launch(Dispatchers.IO) {
                 when(val response = repository.getAllProducts()) {
                     is HomeResponse.ProductList -> {
                         _getAllProducts.postValue(response.productList)

                     }
                     is HomeResponse.Error -> {
                         _error.postValue(response.message)

                     }
                     else -> {

                     }

                 }

             }

        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    fun getProductDetail(product: Product, position: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                when(val response = repository.getProductDetail(product, position)) {
                    is HomeResponse.ProductWithDetail -> {
                        _getProductDetail.postValue(Pair(response.product, response.position))

                    }
                    is HomeResponse.Error -> {
                        _error.postValue(response.message)

                    }
                    else -> {

                    }

                }

            }

        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    fun buyProducts(productList: List<Product>) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                when(val response = repository.buyProducts(productList)) {
                    is HomeResponse.UpdatedStock -> {
                        _updatedStock.postValue(response.productList)

                    }
                    is HomeResponse.Error -> {
                        _error.postValue(response.message)

                    }
                    else -> {

                    }

                }

            }

        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

}