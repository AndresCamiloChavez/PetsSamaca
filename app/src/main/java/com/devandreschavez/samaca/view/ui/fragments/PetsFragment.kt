package com.devandreschavez.samaca.view.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.devandreschavez.samaca.R
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.Pet
import com.devandreschavez.samaca.data.remote.pets.PetsDataSource
import com.devandreschavez.samaca.databinding.FragmentPetsBinding
import com.devandreschavez.samaca.repository.pets.PetsRepositoryImpl
import com.devandreschavez.samaca.view.adapter.PetsAdapter
import com.devandreschavez.samaca.viewmodel.pets.FactoryPetsViewModel
import com.devandreschavez.samaca.viewmodel.pets.HomeViewModel
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks


class PetsFragment : Fragment(R.layout.fragment_pets), PetsAdapter.onPetClickListener{
    private lateinit var binding: FragmentPetsBinding
    private val viewModel: HomeViewModel by viewModels {
        FactoryPetsViewModel(PetsRepositoryImpl(PetsDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetsBinding.bind(view)
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

    override fun onItemClick(pet: Pet) {
        val action = PetsFragmentDirections.actionPetsFragmentToDetailPetFragment(pet)
        findNavController().navigate(action)
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