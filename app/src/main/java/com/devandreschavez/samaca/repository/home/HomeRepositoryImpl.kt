package com.devandreschavez.samaca.repository.home

import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.data.remote.home.HomeDataSource

class HomeRepositoryImpl(private val dataHomeSource: HomeDataSource): HomeRespository {

    //futuro yo debe implementar room :) thanks

    override suspend fun getPets(): Resource<List<Pet>>  = dataHomeSource.getPetsHome()
}