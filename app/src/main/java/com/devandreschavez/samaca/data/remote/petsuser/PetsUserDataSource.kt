package com.devandreschavez.samaca.data.remote.petsuser

import android.util.Log
import com.devandreschavez.samaca.application.AppConstants
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PetsUserDataSource {
    suspend fun getPetsByUser(userId: String): Resource<List<Pet>> {
        val petsByUser = mutableListOf<Pet>()
        withContext(Dispatchers.IO) {
            val querySnapshot =
                FirebaseFirestore.getInstance().collection(AppConstants.collectionPets).whereEqualTo("userId", userId).get()
                    .await()
            for (pet in querySnapshot.documents) {
                pet.toObject(Pet::class.java).let { resultPet ->
                    resultPet?.let { petResult ->
                        resultPet.id = pet.id
                        petsByUser.add(resultPet)
                    }
                }
            }
        }
        return Resource.Success(petsByUser)
    }
    suspend fun reportFindPet(user: String , pet :String, img: String){
        withContext(Dispatchers.IO){
            FirebaseFirestore.getInstance().collection(AppConstants.collectionPets).document(pet).delete()
            if(img != "https://firebasestorage.googleapis.com/v0/b/samacacuida.appspot.com/o/assets%2FnoImage.jpg?alt=media&token=776b9d1a-4ccf-419a-8326-84a68525fa1a"){
                FirebaseStorage.getInstance().reference.child("reportpets/$user/$pet").delete()
            }
        }
    }

}