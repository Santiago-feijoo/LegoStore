package com.feijoo.legostore.common.models

data class Product(
    /** Product id **/
    val id: Long,
    /** Product name **/
    val name: String,
    /** Product description **/
    var description: String,
    /** Product price **/
    val unit_price: Double,
    /** Product stock **/
    var stock: Int,
    /** Purchased product quantity **/
    var purchasedQuantity: Int,
    /** Image url **/
    val image: String
)
