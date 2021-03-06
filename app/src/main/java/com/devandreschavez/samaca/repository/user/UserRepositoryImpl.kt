package com.devandreschavez.samaca.repository.user

import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.User
import com.devandreschavez.samaca.data.remote.user.UserDataSource

class UserRepositoryImpl(private val dataUserSource: UserDataSource) :UserRepository {
    override suspend fun getUser(): Resource<User> = dataUserSource.getUser()
    override suspend fun updateUser(phone: String, address: String, urb: String): Resource.Success<Unit> = dataUserSource.updateUser(phone,address,urb)

}