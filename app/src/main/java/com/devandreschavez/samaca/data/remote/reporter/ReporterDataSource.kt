package com.devandreschavez.samaca.data.remote.reporter

import android.net.Uri
import com.devandreschavez.samaca.application.AppConstants
import com.devandreschavez.samaca.data.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*

class ReporterDataSource {
    suspend fun uploadReporter(img: Uri, pet: Pet){
        val user = FirebaseAuth.getInstance().currentUser?.uid
        val randomName = UUID.randomUUID().toString()
        val imageRef = FirebaseStorage.getInstance().reference.child("reportpets/$user/$randomName")
        val downloadUrl = imageRef.putFile(img).await().storage.downloadUrl.await().toString()
        FirebaseFirestore.getInstance().collection(AppConstants.collectionPets).add(pet.apply { pictureAnimal = downloadUrl }).await()
    }
}