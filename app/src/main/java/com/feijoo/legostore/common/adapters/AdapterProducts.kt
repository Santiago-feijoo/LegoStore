package com.feijoo.legostore.common.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.feijoo.legostore.R
import com.feijoo.legostore.common.interfaces.ProductInterface
import com.feijoo.legostore.common.models.Product
import com.feijoo.legostore.databinding.ItemProductBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class AdapterProducts(private val activity: Activity, private val productInterface: ProductInterface) : RecyclerView.Adapter<ViewHolder>() {
    var productList: List<Product> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return old.size

            }

            override fun getNewListSize(): Int {
                return new.size

            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition].pId == new[newItemPosition].pId

            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition] == new[newItemPosition]

            }

        }).dispatchUpdatesTo(this)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product, parent, false
        )

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(productList[position], activity, productInterface)

    }

    override fun getItemCount(): Int {
        return productList.size

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        /** Attributes **/
        private val binding = ItemProductBinding.bind(view)

        /** Methods **/
        fun bind(product: Product, activity: Activity, productInterface: ProductInterface) {
            Glide.with(binding.root)
                .load(product.pImageUrl)
                .placeholder(R.drawable.ic_bag)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .error(R.drawable.ic_bag)
                .into(binding.imageViewProduct)

            binding.textViewProductName.text = product.pName

            val productPrice = "$${product.pPrice}"
            binding.textViewProductPrice.text = productPrice

            binding.textViewProductQuantity.text = "${product.pQuantity}"

            if(product.pQuantity > 0) {
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

            binding.cardViewProduct.setOnClickListener {
                productInterface.showDetail(product)

            }

            binding.buttonAdd.setOnClickListener {
                if(product.pQuantity < product.pStock) {
                    product.pQuantity++

                    binding.textViewProductQuantity.text = "${product.pQuantity}"

                    binding.buttonAdd.isVisible = false
                    binding.buttonMinus.isVisible = true
                    binding.textViewProductQuantity.isVisible = true
                    binding.buttonMore.isVisible = true

                    productInterface.listUpdate()

                } else {
                    Snackbar.make(binding.root, activity.getString(R.string.product_out_of_stock), Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(activity, R.color.red)).show()

                }

            }

            binding.buttonMinus.setOnClickListener {
                if(product.pQuantity >= 1) {
                    product.pQuantity--

                    binding.textViewProductQuantity.text = "${product.pQuantity}"

                    if(product.pQuantity == 0) {
                        binding.buttonMinus.isVisible = false
                        binding.textViewProductQuantity.isVisible = false
                        binding.buttonMore.isVisible = false
                        binding.buttonAdd.isVisible = true

                    }

                    productInterface.listUpdate()

                }

            }

            binding.buttonMore.setOnClickListener {
                if(product.pQuantity < product.pStock) {
                    product.pQuantity++

                    binding.textViewProductQuantity.text = "${product.pQuantity}"

                    productInterface.listUpdate()

                } else {
                    Snackbar.make(binding.root, activity.getString(R.string.product_out_of_stock), Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(activity, R.color.red)).show()

                }

            }

        }

    }

}