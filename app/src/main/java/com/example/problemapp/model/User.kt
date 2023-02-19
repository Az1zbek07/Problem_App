package com.example.problemapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val uid: String = ""
): Parcelable
