package com.example.problemapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.problemapp.R
import com.example.problemapp.databinding.FragmentAddBinding
import com.example.problemapp.model.HouseOwner

class AddFragment : Fragment(R.layout.fragment_add) {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddBinding.bind(view)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnContinue.setOnClickListener {
            val fullName = binding.editFullName.text.toString().trim()
            val number = binding.editNumber.text.toString().trim()
            val gender = binding.editGender.text.toString().trim()
            val age = binding.editAge.text.toString().trim()

            if (fullName.isNotBlank() && number.isNotBlank() && gender.isNotBlank() && age.isNotBlank()){
                val houseOwner = HouseOwner(fullName, age, gender, number)
                val bundle = bundleOf("houseOwner" to houseOwner)
                findNavController().navigate(R.id.action_addFragment_to_addImageFragment, bundle)
                binding.editAge.setText("")
                binding.editGender.setText("")
                binding.editNumber.setText("")
                binding.editFullName.setText("")
            }else{
                Toast.makeText(requireContext(), "Please enter value", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}