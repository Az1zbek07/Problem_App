package com.example.problemapp.model

import java.io.Serializable

data class Problem(
    val problem: String = "",
    val imageList: MutableList<ImageData> = mutableListOf()
): Serializable