package com.devandreschavez.samaca.data.remote.home

import com.devandreschavez.samaca.application.AppConstants
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeDataSource {
    suspend fun getPetsHome(): Resource<List<Pet>> {
        val petsList = mutableListOf<Pet>()
        withContext(Dispatchers.IO) {
            val querySnapshot =
                FirebaseFirestore.getInstance().collection(AppConstants.collectionPets).get()
                    .await()
            for (pet in querySnapshot.documents){
                pet.toObject(Pet::class.java).let { resultPet ->
                    resultPet?.let {
                        petsList.add(it)
                    }
                }
            }
        }
        return Resource.Success(petsList)
    }
}