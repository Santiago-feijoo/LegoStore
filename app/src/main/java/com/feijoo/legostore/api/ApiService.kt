package com.feijoo.legostore.api

import com.feijoo.legostore.common.Constants
import com.feijoo.legostore.common.models.response.ResponseGetProducts
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.SERVICE_GET_PRODUCTS)
    suspend fun getProducts(): Response<ResponseGetProducts>

}