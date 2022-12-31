package com.feijoo.legostore.api

import com.feijoo.legostore.common.Constants
import com.feijoo.legostore.common.models.Product
import com.feijoo.legostore.common.models.response.ResponseGetProducts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(Constants.SERVICE_GET_ALL_PRODUCTS)
    suspend fun getAllProducts(): Response<ResponseGetProducts>

    @GET(Constants.SERVICE_GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(@Path("productId") productId: Long): Response<Product>

}