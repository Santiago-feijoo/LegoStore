package com.feijoo.legostore.common.interfaces

import com.feijoo.legostore.common.models.Product

interface ProductInterface {
    fun showDetail(product: Product)
    fun addProduct(product: Product)

}