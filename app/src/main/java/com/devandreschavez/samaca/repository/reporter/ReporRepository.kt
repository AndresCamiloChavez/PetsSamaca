package com.devandreschavez.samaca.repository.reporter

import android.net.Uri
import com.devandreschavez.samaca.data.model.Pet

interface ReporRepository {
    suspend fun uploadReport(img: Uri?, pet: Pet)
}