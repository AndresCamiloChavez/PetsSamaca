package com.devandreschavez.samaca.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val fullName: String? = null,
    val document: String? = "0",
    val phone: String? = null,
    val address: String? = null,
    val email: String? = null,
    val password: String? = null,
    val urb: String? = null
): Parcelable
