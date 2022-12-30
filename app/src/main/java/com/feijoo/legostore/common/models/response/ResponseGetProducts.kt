package com.feijoo.legostore.common.models.response

import com.feijoo.legostore.common.models.Product

data class ResponseGetProducts (
    val products: ArrayList<Product>
)