package com.devandreschavez.samaca.repository.auth

import com.devandreschavez.samaca.data.model.User
import com.devandreschavez.samaca.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val authDataSource: AuthDataSource): AuthRepository {
    override suspend fun signUp(user: User): FirebaseUser? = authDataSource.signUp(user)
    override suspend fun signIn(email: String, password: String): FirebaseUser? = authDataSource.signIn(email,password)
}