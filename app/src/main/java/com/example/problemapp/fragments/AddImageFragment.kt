package com.example.problemapp.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.problemapp.R
import com.example.problemapp.databinding.FragmentAddImageBinding
import com.example.problemapp.model.HouseOwner
import com.example.problemapp.model.ImageData
import com.example.problemapp.model.Problem
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddImageFragment : Fragment(R.layout.fragment_add_image) {
    private var _binding: FragmentAddImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var houseOwner: HouseOwner
    private val imageList = mutableListOf<ImageData>()
    private val db by lazy { FirebaseDatabase.getInstance() }
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        houseOwner = arguments?.getSerializable("houseOwner") as HouseOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddImageBinding.bind(view)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {
            val problem = binding.editProblem.text.toString()

            if (problem.isNotBlank() && imageList.size > 2){
                saveProblem(problem)
            }
        }

        binding.imageView.setOnClickListener {
            photoUri = null
            photoLauncher.launch("image/*")
        }
    }

    private val photoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        it?.let {uri ->
            photoUri = uri
        }
        val imageData = ImageData(photoUri)
        imageList.add(imageData)
    }

    private fun saveProblem(problem: String){
        val problemData = Problem(problem, imageList)
        val houseOwnerNew = HouseOwner(houseOwner.fullName, houseOwner.age, houseOwner.gender, houseOwner.number, problemData)
        for (imageUriNew in imageList){
            val fileName = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("image/${houseOwner.fullName}/$fileName")
            ref.putFile(imageUriNew.imageUrl!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {

                    }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                }
        }

        db.getReference("users").child("problems").child(houseOwnerNew.fullName)
            .setValue(houseOwnerNew)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Problem saved", Toast.LENGTH_SHORT).show()
                binding.editProblem.setText("")
                binding.editMessage.setText("")
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}