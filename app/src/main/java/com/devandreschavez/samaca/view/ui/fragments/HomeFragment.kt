package com.devandreschavez.samaca.view.ui.fragments

import android.os.Bundle
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
    }

    override fun onItemClick(item: Pet) {
        Toast.makeText(requireContext(), "Selecciono a: ${item.user?.fullName}", Toast.LENGTH_SHORT).show()

    }

}