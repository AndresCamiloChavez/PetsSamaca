package com.devandreschavez.samaca.view.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: UserViewModel by viewModels {
        FactoryUserViewModel(UserRepositoryImpl(UserDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding.btnEditProfile.setOnClickListener {
            editData()
        }
        loadData()
    }
    private fun loadData(){
        viewModel.fetchUserL.observe(viewLifecycleOwner, Observer { resultUser ->
            when (resultUser) {
                is Resource.Loading -> {
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
    private fun loadData2(){
        viewModel.fetchUser().observe(viewLifecycleOwner, Observer { resultUser ->
            when (resultUser) {
                is Resource.Loading -> {
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
        binding.tvNameUserProfile.text = user.fullName
        binding.tvEmailUserProfile.text = user.email
        binding.tvPhoneUserProfile.text = user.phone
        binding.tvUrbUserProfile.text = user.urb
        binding.tvAddressUserProfile.text = user.address
    }

    private fun editData() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialogedit, null)
        builder.setView(view)
        val dialog = builder.show()

        val inputPhone = view.findViewById<EditText>(R.id.etEditPhone)
        val inputAddress = view.findViewById<EditText>(R.id.etEditAdrress)
        val inputUrb = view.findViewById<EditText>(R.id.etEditBr)
        val btnAcept = view.findViewById<Button>(R.id.btnAceptar)
        val btnCanel = view.findViewById<Button>(R.id.btnCancelEdit)
        btnCanel.setOnClickListener {
            dialog.cancel()
        }
        btnAcept.setOnClickListener {
            val valid = when {
                inputPhone.text?.trim().isNullOrEmpty() -> {
                    inputPhone.error = "Ingrese un número válido"
                    false
                }
                inputAddress.text.isNullOrEmpty() -> {
                    inputAddress.error = "Ingrese un valor"
                    false
                }
                inputUrb.text.isNullOrEmpty() -> {
                    inputUrb.error = "Ingrese un valor"
                    false
                }
                else -> {
                    true
                }
            }
            if(valid){
                viewModel.updateUser(inputPhone.text.toString(), inputAddress.text.toString(), inputUrb.text.toString()).observe(viewLifecycleOwner, Observer {
                    when(it){
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            Toast.makeText(requireContext(), "¡Se actualizo la información exitosamente!", Toast.LENGTH_SHORT).show()
                            loadData()
                            dialog.dismiss()
                        }
                        is Resource.Failure -> {
                            Toast.makeText(requireContext(), "Ocurrió un error, intente de nuevo", Toast.LENGTH_SHORT).show()
                            dialog.cancel()
                        }
                    }
                })
            }
        }
    }
}