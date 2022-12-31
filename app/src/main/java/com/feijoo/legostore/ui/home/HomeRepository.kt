package com.feijoo.legostore.ui.home

import android.content.Context
import com.feijoo.legostore.R
import com.feijoo.legostore.api.ApiService
import com.feijoo.legostore.common.Constants
import com.feijoo.legostore.common.models.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val apiService: ApiService, @ApplicationContext private val context: Context) {
    /** Methods **/
    suspend fun getAllProducts(): HomeResponse {
        val api = apiService.getAllProducts()

        when(api.code()) {
            200 -> {
                api.body()?.let { responseContent ->
                    val productList = responseContent.products

                    if(productList.isNotEmpty()) {
                        return HomeResponse.ProductList(productList)

                    } else {
                        return HomeResponse.Error(context.getString(R.string.service_response_error))

                    }

                } ?: kotlin.run {
                    return HomeResponse.Error(context.getString(R.string.service_response_error))

                }

            }
            else -> {
                return HomeResponse.Error(context.getString(R.string.type_of_error_in_the_service_response, api.code().toString(), api.raw().request().url().toString().replace(Constants.CONNECTION_STRING, "")))

            }

        }

    }

    suspend fun getProductDetail(product: Product): HomeResponse {
        val api = apiService.getProductDetail(product.id)

        when(api.code()) {
            200 -> {
                api.body()?.let { responseContent ->
                    product.description = responseContent.description
                    return HomeResponse.ProductWithDetail(product)

                } ?: kotlin.run {
                    return HomeResponse.Error(context.getString(R.string.service_response_error))

                }

            }
            else -> {
                return HomeResponse.Error(context.getString(R.string.type_of_error_in_the_service_response, api.code().toString(), api.raw().request().url().toString().replace(Constants.CONNECTION_STRING, "")))

            }

        }

    }

}