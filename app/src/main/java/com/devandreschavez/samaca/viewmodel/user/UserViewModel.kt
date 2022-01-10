package com.devandreschavez.samaca.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.repository.user.UserRepository
import java.lang.Exception

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    fun fetchUser() = liveData {
        emit(Resource.Loading())
        try {
            emit(userRepository.getUser())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}
class FactoryUserViewModel(private val userRepository: UserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(userRepository)
    }

}