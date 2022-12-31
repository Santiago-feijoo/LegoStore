package com.feijoo.legostore.common

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.feijoo.legostore.R
import com.feijoo.legostore.common.models.Product
import com.feijoo.legostore.databinding.DialogProductDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    fun dialogProductDetail(activity: Activity, product: Product): AlertDialog {
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