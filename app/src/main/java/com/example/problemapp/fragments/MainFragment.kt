package com.example.problemapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.problemapp.LoginActivity
import com.example.problemapp.MainActivity
import com.example.problemapp.R
import com.example.problemapp.adapter.ProblemAdapter
import com.example.problemapp.databinding.FragmentMainBinding
import com.example.problemapp.model.HouseOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val uid by lazy { FirebaseAuth.getInstance().currentUser?.uid }
    private val db by lazy { FirebaseDatabase.getInstance().getReference("users/problems") }
    private val problemList = mutableListOf<HouseOwner>()
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val problemAdapter by lazy { ProblemAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        binding.rv.apply {
            adapter = problemAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        }

        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                problemList.clear()
                for (sn in snapshot.children){
                    val problem = sn.getValue(HouseOwner::class.java)
                    problemList.add(problem!!)
                }
                problemAdapter.setList(problemList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        problemAdapter.onClick = {
            val bundle = bundleOf("data" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }

        binding.textName.apply {
            text = auth.currentUser?.email!!
            setOnClickListener {
                auth.signOut()
                (activity as MainActivity).startActivity(Intent(requireContext(), LoginActivity::class.java))
                (activity as MainActivity).finish()
            }
        }
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}