package com.devandreschavez.samaca.data.model

import com.google.firebase.Timestamp

data class Pet(
    val namePet: String = "",
    val user: User? = User(),
    val date: String = "",
    val typeAnimal: String = "",
    val pictureAnimal: String = "",
    val sector: String = "",
    val sex: String = "",
    val description: String = ""
)
