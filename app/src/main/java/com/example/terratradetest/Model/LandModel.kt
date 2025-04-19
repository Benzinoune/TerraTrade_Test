package com.example.landrenting.Model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LandModel(
    val id: String,
    val imageRes: Int,
    val location: String,
    val price: String,
    val userName: String,
    var isFavorite: Boolean = false
) : Parcelable

