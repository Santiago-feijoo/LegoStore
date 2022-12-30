package com.feijoo.legostore.common.interfaces

import com.feijoo.legostore.common.models.Product

interface ProductInterface {
    fun listUpdate()
    fun showDetail(product: Product)

}