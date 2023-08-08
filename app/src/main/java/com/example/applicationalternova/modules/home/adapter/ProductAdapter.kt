package com.example.applicationalternova.modules.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applicationalternova.R
import com.example.applicationalternova.databinding.ProductItemBinding
import com.example.applicationalternova.modules.home.model.ClickOptional
import com.example.applicationalternova.modules.home.model.ProductModel

class ProductAdapter(private val onclick: (Triple<ProductModel, Boolean, ClickOptional>) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productsList: List<ProductModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onclick, parent.context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = productsList[position]
        holder.bindItem(item)
    }

    override fun getItemCount(): Int = productsList.size

    class ProductViewHolder(
        private val binding: ProductItemBinding,
        private val onclick: (Triple<ProductModel, Boolean, ClickOptional>) -> Unit,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: ProductModel

        init {
            binding.containerProduct.setOnClickListener {
                onclick(Triple(item, binding.checkboxAddToCar.isChecked, ClickOptional.IS_CLICK))
            }
            binding.checkboxAddToCar.setOnCheckedChangeListener { _, _ ->
                onclick(
                    Triple(
                        item,
                        binding.checkboxAddToCar.isChecked,
                        ClickOptional.IS_CHECK_ADD_CAR,
                    ),
                )
            }
        }

        fun bindItem(item: ProductModel) {
            this.item = item
            with(binding) {
                if (item.nameProduct != null) {
                    textViewNameProduct.text = item.nameProduct
                }

                if (item.priceProduct != null) {
                    textViewPrice.text =
                        context.getString(R.string.home_price, item.priceProduct.toString())
                }

                if (item.imageProduct != null) {
                    Glide.with(context).load(item.imageProduct).into(imageViewProduct)
                }
            }
        }
    }

    fun setListProducts(list: List<ProductModel>) {
        this.productsList = list
    }
}
