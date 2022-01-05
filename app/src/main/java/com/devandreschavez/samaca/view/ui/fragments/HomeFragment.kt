package com.devandreschavez.samaca.view.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devandreschavez.samaca.R
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.data.remote.home.HomeDataSource
import com.devandreschavez.samaca.databinding.FragmentHomeBinding
import com.devandreschavez.samaca.repository.home.HomeRepositoryImpl
import com.devandreschavez.samaca.view.adapter.PetsAdapter
import com.devandreschavez.samaca.viewmodel.home.FactoryHomeViewModel
import com.devandreschavez.samaca.viewmodel.home.HomeViewModel
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import kotlinx.coroutines.tasks.await
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home), PetsAdapter.onPetClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        FactoryHomeViewModel(HomeRepositoryImpl(HomeDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        viewModel.fetchPets().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressHome.visibility = View.VISIBLE
                    Log.d("Home", "Cargando la info")
                }
                is Resource.Success -> {
                    binding.progressHome.visibility = View.GONE
                    binding.rvPetsHome.adapter = PetsAdapter(result.data, this)
                    Log.d("Home", "INFORMACIÓN : ${result.data}")
                }
                is Resource.Failure -> {
                    binding.progressHome.visibility = View.GONE
                    Log.d("Home", "Ocurrió un error ${result.e}")
                    Toast.makeText(requireContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show()
                }
            }
        })
        tryToGetDynamicLink()
    }

    override fun onItemClick(item: Pet) {
        Toast.makeText(requireContext(), "Selecciono a: ${item.user?.fullName}", Toast.LENGTH_SHORT).show()

    }

    override fun onSharePostPet(name: String, description: String, img: String) {
        viewModel.fetchDynamicLink(name, description, img).observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    Log.d("DynamicLink", "Cargando...")
                }
                is Resource.Success -> {
                    Log.d("DynamicLink", "Exito")
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Más información en: ${it.data}")
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(intent, "Compartir con:"))
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun tryToGetDynamicLink(){
        FirebaseDynamicLinks.getInstance().getDynamicLink(Intent.getIntentOld("")).addOnSuccessListener {
            if(it != null){
                val deepLink = it.link
                if(deepLink?.getQueryParameter("pet") != null){
                    Toast.makeText(requireContext(), "Hola ${deepLink.getQueryParameter("pet")}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Error no se encontro la mascota ${deepLink?.getQueryParameter("pet")}", Toast.LENGTH_SHORT).show()
                    Log.d("Failed", "ERROR WITH DYNAMIC LINK OR NO LINK AT ALL");
                }
            }
        }
    }


}