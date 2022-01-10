package com.devandreschavez.samaca.repository.user

import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.User

interface UserRepository {
    suspend fun getUser(): Resource<User>
    suspend fun updateUser(phone: String, address: String, urb: String):Resource.Success<Unit>
}