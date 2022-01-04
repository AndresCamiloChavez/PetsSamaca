package com.devandreschavez.samaca.repository.home

import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet

interface HomeRespository {
    suspend fun getPets(): Resource<List<Pet>>
}