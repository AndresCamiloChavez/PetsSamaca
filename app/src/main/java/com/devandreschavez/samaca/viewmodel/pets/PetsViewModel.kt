package com.devandreschavez.samaca.viewmodel.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.repository.pets.PetsRespository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repositoryPets: PetsRespository) : ViewModel() {


    val requestPets = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repositoryPets.getPets())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun fetchPets() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repositoryPets.getPets())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun fetchDynamicLink(
        namePet: String,
        descriptionPet: String,
        imgPet: String
    ) = liveData {
        emit(Resource.Loading())
        try{
            emit(repositoryPets.generateDynamicLink(namePet, descriptionPet, imgPet))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }


}

class FactoryPetsViewModel(private val repositoryPets: PetsRespository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PetsRespository::class.java).newInstance(repositoryPets)
    }

}