package com.devandreschavez.samaca.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val namePet: String = "",
    val userId: String = "",
    val date: String = "",
    val typeAnimal: String = "",
    var pictureAnimal: String = "",
    val sector: String = "",
    val sex: String = "",
    val description: String = "",
    val status: Boolean = true,
    val publicationDate: String = "",
): Parcelable

@Parcelize
data class PetUser(
    val pet: Pet = Pet(),
    val user: User = User()
):Parcelable
