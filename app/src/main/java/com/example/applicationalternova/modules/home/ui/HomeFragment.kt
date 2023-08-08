package com.example.applicationalternova.modules.home.ui

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationalternova.R
import com.example.applicationalternova.databinding.HomeFragmentBinding
import com.example.applicationalternova.modules.detail.model.DetailProductModel
import com.example.applicationalternova.modules.home.adapter.ProductAdapter
import com.example.applicationalternova.modules.home.model.ClickOptional
import com.example.applicationalternova.modules.home.model.ProductModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private var listProducts: ArrayList<DetailProductModel> = arrayListOf()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        with(binding) {
            showLoading(true)
            setAdapter()
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.products.collect { list ->
                        list.list?.let { productAdapter.setListProducts(it) }
                        productAdapter.notifyDataSetChanged()
                        showLoading(false)
                    }
                }
            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.userName.collect { userName ->
                        textViewWelcome.text =
                            getString(R.string.home_message_welcome, userName.userName)
                    }
                }
            }

            buttonShoppingCar.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_shoppingCartFragment,
                    Bundle().apply {
                        putSerializable("list", listProducts)
                    },
                )
            }

            buttonLogOut.setOnClickListener {
                clearPreferences()
                clearPreferencesProduct()
                findNavController().navigate(R.id.action_homeFragment_to_initFragment)
            }

            /*lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.error.collect() {
                        showErrorDialog()
                    }
                }
            }*/
        }
        return binding.root
    }

    private fun setAdapter() {
        productAdapter = ProductAdapter {
            when (it.third) {
                ClickOptional.IS_CLICK -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_detailProductFragment,
                        Bundle().apply {
                            putString("id_product", it.first.idProduct.toString())
                        },
                    )
                }

                ClickOptional.IS_CHECK_ADD_CAR -> {
                    if (it.second) {
                        binding.textViewNumberStock.text =
                            "${
                                Integer.parseInt(
                                    binding.textViewNumberStock.text.toString().trim(),
                                ) + 1
                            }"
                        savePreferences(it.first)
                    } else {
                        binding.textViewNumberStock.text =
                            "${
                                Integer.parseInt(
                                    binding.textViewNumberStock.text.toString().trim(),
                                ) - 1
                            }"
                        savePreferences(it.first)
                    }
                }
            }
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
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

    private fun showErrorDialog() {
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.sign_in_error_dialog_title))
            .setMessage(getString(R.string.sign_in_error_general))
            .setPositiveButton(getString(R.string.sign_in_error_dialog_positive_action)) { view, _ ->
                findNavController().popBackStack()
                view.dismiss()
            }.setNegativeButton(android.R.string.cancel) { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun savePreferences(productModel: ProductModel) {
        val product = DetailProductModel(
            productModel.idProduct,
            productModel.nameProduct,
            productModel.priceProduct,
            productModel.stock,
            productModel.imageProduct,
        )
        listProducts.add(product)
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("shopping_car", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear().apply()

        val json = Gson().toJson(listProducts)

        editor.putString("product_select", json)
        editor.apply()
        Log.d("SAVE", "SAVE data complete")
    }

    private fun clearPreferences() {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.clear().apply()
        Log.d("CLEAR", "Preferences cleared")
    }

    private fun clearPreferencesProduct() {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("shopping_car", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.clear().apply()
        Log.d("CLEAR", "Preferences cleared")
    }
}
