package com.devandreschavez.samaca.data.remote.auth

import com.devandreschavez.samaca.application.AppConstants
import com.devandreschavez.samaca.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await
class AuthDataSource {

    suspend fun signUp(user: User): FirebaseUser? {
        return withContext(Dispatchers.IO){
            val authResult = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(user.email.toString(), user.password.toString()).await()
            authResult.user?.uid?.let {id ->
                FirebaseFirestore.getInstance().collection(AppConstants.collectionUser).document(id).set(user)
            }
            authResult.user
        }
    }
    suspend fun signIn(email: String, password: String): FirebaseUser?{
        return  withContext(Dispatchers.IO){
            val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            authResult.user
        }
    }

}