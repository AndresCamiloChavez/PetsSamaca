package com.devandreschavez.samaca.data.remote.home

import android.net.Uri
import com.devandreschavez.samaca.application.AppConstants
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class HomeDataSource {
    suspend fun getPetsHome(): Resource<List<Pet>> {
        val petsList = mutableListOf<Pet>()
        withContext(Dispatchers.IO) {
            val querySnapshot =
                FirebaseFirestore.getInstance().collection(AppConstants.collectionPets).get()
                    .await()
            for (pet in querySnapshot.documents) {
                pet.toObject(Pet::class.java).let { resultPet ->
                    resultPet?.let {
                        petsList.add(it)
                    }
                }
            }
        }
        return Resource.Success(petsList)
    }

    suspend fun generateDynamicLink(
        namePet: String,
        descriptionPet: String,
        imgPet: String
    ): Resource<
            Uri?> {
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                //mejorar esta parte porque debemos identificar de alguna manera el intent para redirigir
            .setLink(Uri.parse("https://samaca.page.link/Sam?pet=${namePet}"))
            .setDomainUriPrefix("https://samaca.page.link").setAndroidParameters(
                DynamicLink.AndroidParameters.Builder("com.devandreschavez.samaca")
                    .setMinimumVersion(1).build()
            ).setIosParameters(
                DynamicLink.IosParameters.Builder("com.devandreschavez.samaca")
                    .setMinimumVersion("1.0.1").build()
            ).setSocialMetaTagParameters(
                DynamicLink.SocialMetaTagParameters.Builder()
                    .setTitle("Ayúdame a buscar a $namePet")
                    .setDescription(descriptionPet)
                    .setImageUrl(Uri.parse(imgPet))
                    .build()
            ).buildShortDynamicLink().await()
        return Resource.Success(dynamicLink.shortLink)
    }
}