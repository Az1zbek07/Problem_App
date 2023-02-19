package com.example.problemapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import com.example.problemapp.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        object : CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                if (auth.currentUser == null){
                    startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }
            }
        }.start()
    }
}