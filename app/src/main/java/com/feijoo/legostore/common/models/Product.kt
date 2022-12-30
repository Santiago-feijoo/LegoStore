package com.feijoo.legostore.common.models

data class Product(
    /** Product id **/
    val pId: Long,
    /** Product name **/
    val pName: String,
    /** Product price **/
    val pPrice: Double,
    /** Product stock **/
    var pStock: Int,
    /** Product quantity **/
    var pQuantity: Int,
    /** Image url **/
    val pImageUrl: String
)
