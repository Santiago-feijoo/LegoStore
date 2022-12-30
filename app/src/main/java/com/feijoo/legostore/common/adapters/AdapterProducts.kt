package com.feijoo.legostore.common.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class AdapterProducts(private val productInterface: ProductInterface) : RecyclerView.Adapter<ViewHolder>() {
    private var productList: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product, parent, false
        )

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(productList[position], productInterface)

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        if(payloads.isNotEmpty()) {
            val bundle = payloads[0] as Bundle

            if(bundle.getString("update") != null) {
                (holder as ViewHolder).bind(productList[position], productInterface)

            }

        }

    }

    override fun getItemCount(): Int {
        return productList.size

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        /** Attributes **/
        private val binding = ItemProductBinding.bind(view)

        /** Methods **/
        fun bind(product: Product, productInterface: ProductInterface) {
            Glide.with(binding.root)
                .load(product.pImageUrl)
                .placeholder(R.drawable.ic_bag)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .error(R.drawable.ic_bag)
                .into(binding.imageViewProduct)

            binding.textViewProductName.text = product.pName

            val productPrice = "$${product.pPrice}"
            binding.textViewProductPrice.text = productPrice

            if(product.pQuantity > 0) {
                binding.textViewProductQuantity.text = "${product.pQuantity}"

                binding.buttonAdd.isVisible = false
                binding.buttonMinus.isVisible = true
                binding.textViewProductQuantity.isVisible = true
                binding.buttonMore.isVisible = true

            } else {
                binding.textViewProductQuantity.text = "0"

                binding.buttonMinus.isVisible = false
                binding.textViewProductQuantity.isVisible = false
                binding.buttonMore.isVisible = false
                binding.buttonAdd.isVisible = true

            }

            binding.cardViewProduct.setOnClickListener {
                productInterface.showDetail(product)

            }

            binding.buttonAdd.setOnClickListener {
                productInterface.addProduct(product)

            }

        }

    }

    fun setNewProductList(newList: ArrayList<Product>) {
        val diffUtil = ProductsDiffUtils(productList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        productList = newList

        diffResult.dispatchUpdatesTo(this)

    }

    class ProductsDiffUtils(
        private val oldList: ArrayList<Product>,
        private val newList: ArrayList<Product>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size

        }

        override fun getNewListSize(): Int {
            return newList.size

        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].pId == newList[newItemPosition].pId

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]

        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
            val bundle = Bundle()
            bundle.putString("update", "update")

            return bundle

        }

    }

}