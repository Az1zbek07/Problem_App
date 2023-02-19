package com.example.problemapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.problemapp.databinding.ActivityRegisterBinding
import com.example.problemapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { Firebase.database.reference }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            if (name.isNotBlank() && email.isNotBlank() && password.length > 5){
                binding.btnRegister.isVisible = false
                register(name, email, password)
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                addToDatabase(name, email, password, auth.currentUser?.uid!!)
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                binding.btnRegister.isVisible = true
            }
    }

    private fun addToDatabase(name: String, email: String, password: String, uid: String) {
        database.child("users").child(uid)
            .setValue(User(name, email, password, uid))
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                binding.btnRegister.isVisible = true
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                binding.btnRegister.isVisible = true
            }
    }
}