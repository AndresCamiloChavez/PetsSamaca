package com.devandreschavez.samaca.data.remote.user

import com.devandreschavez.samaca.application.AppConstants
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserDataSource {
    private val userFirebase = FirebaseAuth.getInstance().currentUser
    suspend fun getUser(): Resource<User> {
        var usuario = User()
        withContext(Dispatchers.IO) {
            usuario = FirebaseFirestore.getInstance().collection(AppConstants.collectionUser)
                .document("${userFirebase?.uid}").get().await().toObject(User::class.java)!!
        }
        return Resource.Success(usuario)
    }

    suspend fun updateUser(phone: String, address: String, urb: String): Resource.Success<Unit> {
        withContext(Dispatchers.IO) {
            FirebaseFirestore.getInstance().collection(AppConstants.collectionUser)
                .document(userFirebase?.uid.toString()).update("phone", phone, "urb", urb, "address", address).await()
        }
        return Resource.Success(Unit)
    }
}