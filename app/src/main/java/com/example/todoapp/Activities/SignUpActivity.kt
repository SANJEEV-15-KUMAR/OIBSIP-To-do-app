package com.example.todoapp.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth


    private var binding:ActivitySignUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        setSupportActionBar(binding?.toolbarSignUpActivity)


        auth = Firebase.auth

        binding?.btnSignUp!!.setOnClickListener {
             SignUpUser()
        }

        binding?.tvGoToSignIn!!.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }



    }

    private fun SignUpUser(){
        val email = binding?.etEmail?.text.toString()
        val password = binding?.etPassword?.text.toString()


        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            if(it.isSuccessful){
                Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                Toast.makeText(this, "Sign up Failed!", Toast.LENGTH_SHORT).show()
            }
        }

    }



}