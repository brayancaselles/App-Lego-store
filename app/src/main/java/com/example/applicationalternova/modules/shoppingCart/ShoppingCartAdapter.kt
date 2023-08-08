package com.example.applicationalternova.modules.shoppingCart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationalternova.R
import com.example.applicationalternova.databinding.ItemShoppingCartBinding
import com.example.applicationalternova.modules.detail.model.DetailProductModel

class AdapterShoppingCart() : RecyclerView.Adapter<AdapterShoppingCart.ViewHolder>() {

    private var list: List<DetailProductModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AdapterShoppingCart.ViewHolder {
        val binding =
            ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: AdapterShoppingCart.ViewHolder, position: Int) {
        val item = list[position]
        holder.bindItem(item)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(private val binding: ItemShoppingCartBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: DetailProductModel) {
            with(binding) {
                if (item.nameProduct != null) {
                    textViewNameProduct.text = item.nameProduct
                }
                if (item.priceProduct != null) {
                    textViewPrice.text =
                        context.getString(R.string.detail_price, item.priceProduct.toString())
                }
                if (item.stock != null) {
                    textViewStock.text =
                        context.getString(R.string.detail_stock_product, item.stock.toString())
                }
            }
        }
    }

    fun setList(list: List<DetailProductModel>) {
        this.list = list
    }
}
