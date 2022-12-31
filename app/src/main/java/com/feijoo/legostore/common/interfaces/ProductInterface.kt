package com.feijoo.legostore.common.interfaces

import com.feijoo.legostore.common.models.Product

interface ProductInterface {
    fun addProduct(product: Product, position: Int)
    fun productRemove(product: Product, position: Int)
    fun showDetail(product: Product, position: Int)
    fun listUpdate(position: Int)

}