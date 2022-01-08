package com.devandreschavez.samaca.viewmodel.reporter

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.repository.reporter.ReporRepository
import kotlinx.coroutines.Dispatchers

class ReporterViewModel(private val repo: ReporRepository): ViewModel() {

    fun uploadReporter(img: Uri, pet: Pet) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.uploadReport(img, pet)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}
class FactoryReporterViewModel(private val repo: ReporRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ReporRepository::class.java).newInstance(repo)
    }

}