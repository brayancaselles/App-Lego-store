package com.example.applicationalternova.modules.shoppingCart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationalternova.databinding.ShoppingCartFragmentBinding
import com.example.applicationalternova.modules.detail.model.DetailProductModel

class ShoppingCartFragment : Fragment() {

    private var _binding: ShoppingCartFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var shoppingAdapter: AdapterShoppingCart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ShoppingCartFragmentBinding.inflate(inflater, container, false)
        with(binding) {
            val list = arguments?.getSerializable("list") as ArrayList<DetailProductModel>
            Log.d("TAg-------Z", "list ----> $list")
            setAdapter()
            setDataAdpater(list)
        }
        return binding.root
    }

    private fun setDataAdpater(list: ArrayList<DetailProductModel>) {
        shoppingAdapter.setList(list)
        shoppingAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapter() {
        shoppingAdapter = AdapterShoppingCart()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = shoppingAdapter
        }
    }
}
