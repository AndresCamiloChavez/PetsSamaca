package com.devandreschavez.samaca.repository.petsuser

import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet

interface PetsUserRepository {
    suspend fun getPetsByUser(userId: String): Resource<List<Pet>>
    suspend fun findPetReport(userId: String, petId: String,  img: String)

}