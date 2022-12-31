package com.feijoo.legostore.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.feijoo.legostore.R
import com.feijoo.legostore.common.Dialogs
import com.feijoo.legostore.common.adapters.AdapterProducts
import com.feijoo.legostore.common.interfaces.ProductInterface
import com.feijoo.legostore.common.models.Product
import com.feijoo.legostore.databinding.ActivityHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity: AppCompatActivity(), ProductInterface {
    /** Attributes **/
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapterProducts: AdapterProducts
    private lateinit var productList: ArrayList<Product>

    @Inject lateinit var dialogs: Dialogs

    /** Methods **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterProducts = AdapterProducts(this)
        productList = ArrayList()

        initComponents()
        observe()

        viewModel.getAllProducts()

    }

    private fun initComponents() {
        binding.recyclerViewProductList.adapter = adapterProducts

        initListener()

    }

    private fun initListener() {
        binding.buttonShoppingCart.constraintLayoutShoppingCart.setOnClickListener {


        }

    }

    private fun observe() {
        viewModel.getAllProducts.observe(this) { newProductList ->
            productList = newProductList
            adapterProducts.productList = productList

        }

        viewModel.getProductDetail.observe(this) { response ->
            val productWithDetail = response.first
            val position = response.second

            dialogs.dialogProductDetail(this, productWithDetail, position,this)

        }

        viewModel.error.observe(this) { message ->
            val product = Product(
                4,
                "Postal de Nueva York",
                "",
                500.0,
                1,
                0,
                "https://www.lego.com/cdn/cs/set/assets/bltae0305908b9ef97a/40519.png"
            )

            val productTwo = Product(
                1,
                "Base Gris",
                "",
                400.0,
                2,
                0,
                "https://www.lego.com/cdn/cs/set/assets/blt3baed37200b0845a/11024.png"
            )

            productList.add(product)
            productList.add(productTwo)
            adapterProducts.productList = productList

            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(this, R.color.red)).show()

        }

    }

    override fun listUpdate(position: Int) {
        val purchasedProductList = adapterProducts.productList.filter { it.purchasedQuantity > 0 }
        binding.buttonShoppingCart.textViewCounter.text = "${purchasedProductList.size}"

        adapterProducts.notifyItemChanged(position)

    }

    override fun addProduct(product: Product, position: Int) {
        if(product.purchasedQuantity < product.stock) {
            product.purchasedQuantity++

            listUpdate(position)

        } else {
            Snackbar.make(binding.root, getString(R.string.product_out_of_stock), Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(this, R.color.red)).show()

        }

    }

    override fun productRemove(product: Product, position: Int) {
        if(product.purchasedQuantity >= 1) {
            product.purchasedQuantity--

            listUpdate(position)

        }

    }

    override fun showDetail(product: Product, position: Int) {
        dialogs.dialogProductDetail(this, product, position, this)
//        viewModel.getProductDetail(product, position)

    }

}