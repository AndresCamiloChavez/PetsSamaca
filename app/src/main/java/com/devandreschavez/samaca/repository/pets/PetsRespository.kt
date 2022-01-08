package com.devandreschavez.samaca.repository.pets

import android.net.Uri
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.data.model.PetUser
import com.devandreschavez.samaca.data.model.User

interface PetsRespository {
    suspend fun getPets(): Resource<List<PetUser>>
    suspend fun generateDynamicLink(
        namePet: String,
        descriptionPet: String,
        imgPet: String): Resource<Uri?>
}