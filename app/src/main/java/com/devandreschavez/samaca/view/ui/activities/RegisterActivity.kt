package com.devandreschavez.samaca.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.model.User
import com.devandreschavez.samaca.data.remote.auth.AuthDataSource
import com.devandreschavez.samaca.databinding.ActivityRegisterBinding
import com.devandreschavez.samaca.repository.auth.AuthRepoImpl
import com.devandreschavez.samaca.viewmodel.auth.AuthViewModel
import com.devandreschavez.samaca.viewmodel.auth.FactoryAuthModel


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewmodel by viewModels<AuthViewModel> {
        FactoryAuthModel(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }
    private val TAG = "REGISTER"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGoLogin.setOnClickListener {
            goToLogin()
        }
        binding.btnRegister.setOnClickListener {
            createUser()
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun createUser() {
        binding.apply {
            when {
                etEmailRegister.text.isNullOrEmpty() -> {
                    etEmailRegister.error = "Ingrese un correo electrónico"
                }
                etAddress.text.isNullOrEmpty() -> {
                    etAddress.error = "Ingrese una dirección"
                }
                etFullName.text.isNullOrEmpty() -> {
                    etFullName.error = "Ingrese un nombre y apellido"
                }
                etPhone.text.toString().length < 7 -> {
                    etPhone.error = "Ingrese un número de celular"
                }
                etPasswordRegister.text.toString().length < 7 -> {
                    etPasswordRegister.error = "Ingrese mayor a 6 caracteres"
                }
                etUrb.text.isNullOrEmpty() -> {
                    etPasswordRegister.error = "Ingrese una urbanización o sector"
                }
                else -> {
                    val user = User(
                        etFullName.text.toString(),
                        etDocument.text.toString(),
                        etPhone.text.toString(),
                        etAddress.text.toString(),
                        etEmailRegister.text.toString(),
                        etPasswordRegister.text.toString(),
                        etUrb.text.toString()
                    )
                    viewmodel.signUpUser(user).observe(this@RegisterActivity, Observer {
                        when (it) {
                            is Resource.Loading -> {
                                binding.progressRegister.visibility = View.VISIBLE
                                Log.d(TAG, "Cargando registro ${it}")
                            }
                            is Resource.Success -> {
                                goToLogin()
                                Log.d(TAG, "Cargando registro ${it}")

                            }
                            is Resource.Failure -> {
                                binding.progressRegister.visibility = View.GONE
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Ocurrió un error, intente de nuevo",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }
        }
    }
}