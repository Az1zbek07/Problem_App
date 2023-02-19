package com.example.problemapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

data class HouseOwner(
    val fullName: String = "",
    val age: String = "",
    val gender: String = "",
    val number: String = "",
    val problem: Problem = Problem()
): Serializable