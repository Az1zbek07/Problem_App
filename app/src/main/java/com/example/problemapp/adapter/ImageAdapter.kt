package com.example.problemapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.problemapp.databinding.ImageLayoutBinding
import com.example.problemapp.model.HouseOwner
import com.example.problemapp.model.ImageData

class ImageAdapter: ListAdapter<ImageData, ImageAdapter.VHolder>(DiffCallback()) {
    private class DiffCallback: DiffUtil.ItemCallback<ImageData>(){
        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(ImageLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VHolder(private val binding: ImageLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(imageData: ImageData){
            with(binding){
                Glide.with(imageView).load(imageData.imageUrl).into(imageView)
            }
        }
    }
}