package com.devandreschavez.samaca.view.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.util.PatternsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.devandreschavez.samaca.core.Resource
import com.devandreschavez.samaca.data.remote.auth.AuthDataSource
import com.devandreschavez.samaca.databinding.ActivityLoginBinding
import com.devandreschavez.samaca.repository.auth.AuthRepoImpl
import com.devandreschavez.samaca.viewmodel.auth.AuthViewModel
import com.devandreschavez.samaca.viewmodel.auth.FactoryAuthModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel: AuthViewModel by viewModels {
        FactoryAuthModel(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (firebaseAuth.currentUser != null){
            goHomeActivity()
        }

        binding.btnGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        binding.btnLogin.setOnClickListener {
            if (validateEmail()) {
                isUserLogin()
            }
        }
    }
    private fun isUserLogin() {
        binding.btnLogin.isEnabled = false
        binding.btnGoRegister.isEnabled = false
        viewModel.signInUser(binding.etEmail.text.toString(), binding.etPassword.text.toString()).observe(this, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.progressLoginBar.visibility = View.VISIBLE
                    Log.d("Login", "cargando...")
                }
                is Resource.Success -> {
                    binding.progressLoginBar.visibility = View.GONE
                    goHomeActivity()
                }
                is Resource.Failure -> {
                    binding.progressLoginBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    binding.btnGoRegister.isEnabled = true
                    Log.d("Login", "Error")
                    Toast.makeText(this, "Ocurrió un error intente de nuevo", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateEmail(): Boolean {
        val email = binding.etEmail.text.toString()

        return when {
            email.isEmpty() -> {
                binding.etEmail.error = "Campo vacío"
                false
            }
            !PatternsCompat.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.etEmail.error = "Formato de correo no válido"
                false
            }
            else -> {
                binding.etEmail.error = null
                true
            }
        }
    }

    private fun goHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}