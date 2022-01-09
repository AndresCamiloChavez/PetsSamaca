package com.devandreschavez.samaca.repository.petsuser

import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.data.remote.petsuser.PetsUserDataSource

class PetsUserRepositoryImpl(private val dataPetsUserSource: PetsUserDataSource): PetsUserRepository {
    override suspend fun getPetsByUser(userId: String): Resource<List<Pet>> = dataPetsUserSource.getPetsByUser(userId)
    override suspend fun findPetReport(userId: String, petId: String,  img: String) {
        dataPetsUserSource.reportFindPet(userId, petId,  img)
    }
}