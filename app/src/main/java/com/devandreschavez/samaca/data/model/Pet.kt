package com.devandreschavez.samaca.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val namePet: String = "",
    val user: User? = User(),
    val date: String = "",
    val typeAnimal: String = "",
    val pictureAnimal: String = "",
    val sector: String = "",
    val sex: String = "",
    val description: String = "",
    val status: Boolean = true,
    val publicationDate: String = ""
): Parcelable
