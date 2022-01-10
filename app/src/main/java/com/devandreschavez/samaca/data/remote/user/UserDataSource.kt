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
}