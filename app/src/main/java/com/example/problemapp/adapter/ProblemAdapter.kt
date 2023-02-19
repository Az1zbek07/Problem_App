package com.example.problemapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.problemapp.databinding.ItemLayoutBinding
import com.example.problemapp.model.HouseOwner
import com.example.problemapp.model.Problem

class ProblemAdapter: RecyclerView.Adapter<ProblemAdapter.VHolder> (){
    lateinit var onClick: (HouseOwner) -> Unit

    private var problemList = mutableListOf<HouseOwner>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(problemList[position])
    }

    override fun getItemCount(): Int = problemList.size

    inner class VHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(houseOwner: HouseOwner){
            binding.textProblem.text = houseOwner.problem.problem
            binding.textName.text = houseOwner.fullName
            itemView.setOnClickListener {
                onClick(houseOwner)
            }
        }
    }

    fun setList(problemList: MutableList<HouseOwner>){
        this.problemList = problemList
        notifyDataSetChanged()
    }
}