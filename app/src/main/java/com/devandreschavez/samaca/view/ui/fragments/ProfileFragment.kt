package com.devandreschavez.samaca.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devandreschavez.samaca.R
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.User
import com.devandreschavez.samaca.data.remote.user.UserDataSource
import com.devandreschavez.samaca.databinding.FragmentProfileBinding
import com.devandreschavez.samaca.repository.user.UserRepositoryImpl
import com.devandreschavez.samaca.viewmodel.user.FactoryUserViewModel
import com.devandreschavez.samaca.viewmodel.user.UserViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: UserViewModel by viewModels {
        FactoryUserViewModel(UserRepositoryImpl(UserDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        viewModel.fetchUser().observe(viewLifecycleOwner, Observer { resultUser ->
            when(resultUser){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Cargandor", Toast.LENGTH_SHORT).show()

                }
                is Resource.Success -> {
                    setData(resultUser.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setData(user: User) {
        binding.tvAddressUserProfile.text = user.email
        binding.tvNameUserProfile.text = user.fullName
        binding.tvPhoneUserProfile.text = user.phone
        binding.tvUrbUserProfile.text = user.urb
        binding.tvAddressUserProfile.text = user.address
    }
}