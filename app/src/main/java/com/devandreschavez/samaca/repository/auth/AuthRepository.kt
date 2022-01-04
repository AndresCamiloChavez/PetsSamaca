package com.devandreschavez.samaca.repository.auth

import com.devandreschavez.samaca.data.model.User
import com.google.firebase.auth.FirebaseUser


interface AuthRepository {
    suspend fun signUp(user: User): FirebaseUser?
    suspend fun signIn(email: String, password: String): FirebaseUser?
}