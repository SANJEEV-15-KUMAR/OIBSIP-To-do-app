package com.example.todoapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoapp.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SigninActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    private var binding:ActivitySigninBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        setSupportActionBar(binding?.toolbarSignInActivity)



        auth = Firebase.auth
        binding?.btnSignIn?.setOnClickListener {
            login()
        }

        binding?.tvGoToSignUp!!.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun login(){

        val email = binding?.etEmailSignIn?.text.toString()
        val password = binding?.etPasswordSignIn?.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
          }
        }

    }

