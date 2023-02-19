package com.example.problemapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.problemapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val auth by lazy { FirebaseAuth.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogIn.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            if (email.isNotBlank() && password.length > 5){
                binding.btnLogIn.isVisible = false
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Log in successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                        binding.btnLogIn.isVisible = true
                    }
            }else{
                Toast.makeText(this, "Pleas enter values", Toast.LENGTH_SHORT).show()
            }
        }

        binding.textRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}