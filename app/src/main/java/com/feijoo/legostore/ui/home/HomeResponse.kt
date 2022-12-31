package com.feijoo.legostore.ui.home

import com.feijoo.legostore.common.models.Product
import javax.inject.Singleton

@Singleton
sealed class HomeResponse {
    data class ProductList(val productList: ArrayList<Product>): HomeResponse()
    data class ProductWithDetail(val product: Product, val position: Int): HomeResponse()
    data class UpdatedStock(val productList: List<Product>): HomeResponse()
    data class Error(val message: String): HomeResponse()

}