package com.example.applicationalternova.modules.detail.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.applicationalternova.R
import com.example.applicationalternova.databinding.DetailProductFragmentBinding
import com.example.applicationalternova.modules.detail.model.DetailProductModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class DetailProductFragment : Fragment() {

    private var _binding: DetailProductFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailProductViewModel by viewModels()

    private var listProducts: ArrayList<DetailProductModel> = arrayListOf()
    private lateinit var product: DetailProductModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DetailProductFragmentBinding.inflate(inflater, container, false)

        with(binding) {
            showLoading(true)
            val idProduct = arguments?.getString("id_product")

            idProduct?.let { viewModel.setProductId(it) }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.product.collect { valueProduct ->
                        if (valueProduct.detailProduct != null) {
                            product = valueProduct.detailProduct
                            showDataDetail(valueProduct.detailProduct)
                        }
                    }
                }
            }

            buttonAddToCart.setOnClickListener {
                listProducts.add(product)
                product.stock?.let { stock -> viewModel.validateStock(stock) }
            }

            buttonShoppingCar.setOnClickListener {
                savePreferences()
                findNavController().navigate(
                    R.id.action_detailProductFragment_to_shoppingCartFragment,
                    Bundle().apply {
                        putSerializable("list", listProducts)
                    },
                )
            }

            buttonBuy.setOnClickListener { }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.validateStock.observe(viewLifecycleOwner) { stockButton ->
            if (stockButton <= product.stock!!) {
                binding.textViewNumberStock.text = "$stockButton"
            } else {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.detail_stock_product_title))
            .setMessage(getString(R.string.detail_stock_product_message))
            .setPositiveButton(android.R.string.ok) { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun showDataDetail(product: DetailProductModel) {
        with(binding) {
            textViewNameProduct.text = product.nameProduct
            Glide.with(requireContext()).load(product.imageProduct).into(imageViewProduct)
            textViewPrice.text = getString(R.string.home_price, product.priceProduct.toString())
            textViewStock.text = getString(R.string.detail_stock_product, product.stock.toString())
            textViewDescription.text = product.descriptionProduct
        }
        showLoading(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun savePreferences() {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("shopping_car", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear().apply()

        val json = Gson().toJson(listProducts)

        editor.putString("product_select", json)
        editor.apply()
        Log.d("SAVE", "SAVE data complete")
    }
}
