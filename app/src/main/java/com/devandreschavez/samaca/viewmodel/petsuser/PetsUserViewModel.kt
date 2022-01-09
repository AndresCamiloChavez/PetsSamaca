package com.devandreschavez.samaca.viewmodel.petsuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.repository.petsuser.PetsUserRepository

class PetsUserViewModel(private val petsUserRepository: PetsUserRepository): ViewModel() {
    fun getPetsByUser(userId: String) = liveData {
        emit(Resource.Loading())
        try{
            emit(petsUserRepository.getPetsByUser(userId))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    fun findPetReport(userId: String, petId: String,  img: String) = liveData {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(petsUserRepository.findPetReport(userId, petId, img)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class FactoryPetsUserViewModel(private val petsUserRepository: PetsUserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PetsUserRepository::class.java).newInstance(petsUserRepository)
    }

}