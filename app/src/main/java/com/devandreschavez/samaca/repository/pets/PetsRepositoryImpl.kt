package com.devandreschavez.samaca.repository.pets

import android.net.Uri
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.data.remote.pets.PetsDataSource

class PetsRepositoryImpl(private val dataPetsSource: PetsDataSource) : PetsRespository {

    //futuro yo debe implementar room :) thanks

    override suspend fun getPets(): Resource<List<Pet>> = dataPetsSource.getPetsHome()
    override suspend fun generateDynamicLink(
        namePet: String,
        descriptionPet: String,
        imgPet: String
    ): Resource<Uri?> = dataPetsSource.generateDynamicLink(namePet, descriptionPet, imgPet)
}