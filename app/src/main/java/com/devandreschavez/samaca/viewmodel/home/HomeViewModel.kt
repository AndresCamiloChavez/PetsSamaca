package com.devandreschavez.samaca.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.repository.home.HomeRepositoryImpl
import com.devandreschavez.samaca.repository.home.HomeRespository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repositoryHome: HomeRespository) : ViewModel() {

    fun fetchPets() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repositoryHome.getPets())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class FactoryHomeViewModel(private val repositoryHome: HomeRespository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRespository::class.java).newInstance(repositoryHome)
    }

}