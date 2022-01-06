package com.devandreschavez.samaca.repository.pets

import android.net.Uri
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet

interface PetsRespository {
    suspend fun getPets(): Resource<List<Pet>>
    suspend fun generateDynamicLink(
        namePet: String,
        descriptionPet: String,
        imgPet: String): Resource<Uri?>

}