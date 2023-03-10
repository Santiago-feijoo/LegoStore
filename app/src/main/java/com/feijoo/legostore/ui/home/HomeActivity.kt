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

    @Inject lateinit var dialogs: Dialogs

    /** Methods **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterProducts = AdapterProducts(this)

        initComponents()
        observe()

        viewModel.getAllProducts()

    }

    private fun initComponents() {
        binding.recyclerViewProductList.adapter = adapterProducts

        initListener()

    }

    private fun initListener() {
        binding.buttonBuy.setOnClickListener { button ->
            button.isEnabled = false
            viewModel.buyProducts(adapterProducts.productList)

        }

    }

    private fun observe() {
        viewModel.getAllProducts.observe(this) { newProductList ->
            adapterProducts.productList = newProductList

        }

        viewModel.getProductDetail.observe(this) { response ->
            val productWithDetail = response.first
            val position = response.second

            dialogs.dialogProductDetail(this, productWithDetail, position,this)

        }

        viewModel.updatedStock.observe(this) { newProductList ->
            adapterProducts.productList = newProductList

            adapterProducts.notifyItemRangeChanged(0, newProductList.size)
            updateView()

            Snackbar.make(binding.root, getString(R.string.successful_purchase), Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(this, R.color.green)).show()

        }

        viewModel.error.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(this, R.color.red)).show()

        }

    }

    override fun listUpdate(position: Int) {
        adapterProducts.notifyItemChanged(position)
        updateView()

    }

    private fun updateView() {
        val purchasedProductList = adapterProducts.productList.filter { it.purchasedQuantity > 0 }
        binding.buttonShoppingCart.textViewCounter.text = "${purchasedProductList.size}"

        binding.buttonBuy.isEnabled = purchasedProductList.isNotEmpty()

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
        viewModel.getProductDetail(product, position)

    }

}