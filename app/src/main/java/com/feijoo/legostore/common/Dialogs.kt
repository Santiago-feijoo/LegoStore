package com.feijoo.legostore.common

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.feijoo.legostore.R
import com.feijoo.legostore.common.interfaces.ProductInterface
import com.feijoo.legostore.common.models.Product
import com.feijoo.legostore.databinding.DialogProductDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Dialogs @Inject constructor() {
    /** Methods **/
    private fun getView(activity: Activity, layoutId: Int): View {
        return LayoutInflater.from(activity).inflate(layoutId, activity.findViewById(android.R.id.content), false)

    }

    private fun getAlertDialog(activity: Activity, view: View): AlertDialog {
        return MaterialAlertDialogBuilder(activity).setView(view).create()

    }

    fun dialogProductDetail(activity: Activity, product: Product, productInterface: ProductInterface): AlertDialog {
        val view = getView(activity, R.layout.dialog_product_detail)
        val binding = DialogProductDetailBinding.bind(view)
        val dialog = getAlertDialog(activity, binding.root)

        binding.buttonClose.setOnClickListener { button ->
            button.isEnabled = false
            dialog.cancel()

        }

        Glide.with(activity)
            .load(product.image)
            .placeholder(R.drawable.ic_bag)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .error(R.drawable.ic_bag)
            .into(binding.imageViewProduct)

        binding.textViewProductName.text = product.name

        val productDescription = product.description.ifEmpty {
            activity.getString(R.string.product_without_description)

        }

        binding.textViewProductDescription.text = HtmlCompat.fromHtml(activity.getString(R.string.form_product_description, productDescription), HtmlCompat.FROM_HTML_MODE_LEGACY)

        val productPrice = "$${product.unit_price}"
        binding.textViewProductPrice.text = productPrice

        binding.textViewAvailableStock.text = "${product.stock}"
        binding.textViewProductQuantity.text = "${product.purchasedQuantity}"

        if(product.purchasedQuantity > 0) {
            binding.buttonAdd.isVisible = false
            binding.buttonMinus.isVisible = true
            binding.textViewProductQuantity.isVisible = true
            binding.buttonMore.isVisible = true

        } else {
            binding.buttonMinus.isVisible = false
            binding.textViewProductQuantity.isVisible = false
            binding.buttonMore.isVisible = false
            binding.buttonAdd.isVisible = true

        }

        binding.buttonAdd.setOnClickListener {
            if(product.purchasedQuantity < product.stock) {
                product.purchasedQuantity++

                binding.textViewProductQuantity.text = "${product.purchasedQuantity}"

                binding.buttonAdd.isVisible = false
                binding.buttonMinus.isVisible = true
                binding.textViewProductQuantity.isVisible = true
                binding.buttonMore.isVisible = true

                productInterface.listUpdate()

            } else {
                Snackbar.make(binding.root, activity.getString(R.string.product_out_of_stock), Snackbar.LENGTH_SHORT).setBackgroundTint(
                    ContextCompat.getColor(activity, R.color.red)).show()

            }

        }

        binding.buttonMinus.setOnClickListener {
            if(product.purchasedQuantity >= 1) {
                product.purchasedQuantity--

                binding.textViewProductQuantity.text = "${product.purchasedQuantity}"

                if(product.purchasedQuantity == 0) {
                    binding.buttonMinus.isVisible = false
                    binding.textViewProductQuantity.isVisible = false
                    binding.buttonMore.isVisible = false
                    binding.buttonAdd.isVisible = true

                }

                productInterface.listUpdate()

            }

        }

        binding.buttonMore.setOnClickListener {
            if(product.purchasedQuantity < product.stock) {
                product.purchasedQuantity++

                binding.textViewProductQuantity.text = "${product.purchasedQuantity}"

                productInterface.listUpdate()

            } else {
                Snackbar.make(binding.root, activity.getString(R.string.product_out_of_stock), Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(activity, R.color.red)).show()

            }

        }

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window?.attributes = layoutParams
        dialog.create()
        dialog.show()

        return dialog

    }

}