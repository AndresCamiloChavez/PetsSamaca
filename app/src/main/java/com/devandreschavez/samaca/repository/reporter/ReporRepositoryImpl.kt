package com.devandreschavez.samaca.repository.reporter

import android.net.Uri
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.data.remote.reporter.ReporterDataSource

class ReporRepositoryImpl(private val reporterDataSource: ReporterDataSource): ReporRepository {
    override suspend fun uploadReport(img: Uri?, pet: Pet) {
        reporterDataSource.uploadReporter(img, pet)
    }
}