package com.devandreschavez.samaca.view.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devandreschavez.samaca.R
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.remote.petsuser.PetsUserDataSource
import com.devandreschavez.samaca.databinding.FragmentUserReportsBinding
import com.devandreschavez.samaca.repository.petsuser.PetsUserRepositoryImpl
import com.devandreschavez.samaca.view.adapter.PetsByUserAdapter
import com.devandreschavez.samaca.viewmodel.petsuser.FactoryPetsUserViewModel
import com.devandreschavez.samaca.viewmodel.petsuser.PetsUserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class UserReportsFragment : Fragment(R.layout.fragment_user_reports), PetsByUserAdapter.onPetUserClickListener {
    private lateinit var binding: FragmentUserReportsBinding
    private val user = FirebaseAuth.getInstance().currentUser
    private val viewModel: PetsUserViewModel by viewModels {
        FactoryPetsUserViewModel(
            PetsUserRepositoryImpl(
                PetsUserDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserReportsBinding.bind(view)

        user?.let {
            viewModel.getPetsByUser(it.uid).observe(viewLifecycleOwner, Observer { listPetsUser ->
                when(listPetsUser){
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Cargando...", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        binding.rvPetsUser.adapter = PetsByUserAdapter(listPetsUser.data, this)
                    }
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onReportPetUser(userId: String, petId: String, img:String) {
        viewModel.findPetReport(userId, petId, img).observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Cargando...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Se elimino", Toast.LENGTH_SHORT).show()

                }
                is Resource.Failure -> {
                    Log.d("report", "${it.e}")
                    Toast.makeText(requireContext(), "Ocurrió un error al eliminar", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}