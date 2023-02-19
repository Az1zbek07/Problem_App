package com.example.problemapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.problemapp.R
import com.example.problemapp.adapter.ImageAdapter
import com.example.problemapp.databinding.FragmentDetailBinding
import com.example.problemapp.model.HouseOwner

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var houseOwner: HouseOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        houseOwner = arguments?.getParcelable("data")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageAdapter = ImageAdapter()
        _binding = FragmentDetailBinding.bind(view)

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = imageAdapter
        }
        imageAdapter.submitList(houseOwner.problem.imageList)
        binding.textAge.text = houseOwner.age
        binding.textGender.text = houseOwner.gender
        binding.textName.text = houseOwner.fullName
        binding.textNumber.text = houseOwner.number
        binding.textProblem.text = houseOwner.problem.problem
        binding.textHouseOwner.text = houseOwner.fullName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}