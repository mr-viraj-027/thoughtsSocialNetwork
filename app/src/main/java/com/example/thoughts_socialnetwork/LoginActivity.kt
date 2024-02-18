package com.example.thoughts_socialnetwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.thoughts_socialnetwork.databinding.ActivityLoginBinding
import com.example.thoughts_socialnetwork.databinding.ActivitySignUpBinding
import com.example.thoughts_socialnetwork.models.AuthUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //val User = AuthUser()
        binding.btnLogin.setOnClickListener {
            if (binding.email.editText?.text.toString()
                    .equals("") or binding.password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(this@LoginActivity, "please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var user = AuthUser(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                )
                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
        binding.btnNewUser.setOnClickListener{
            startActivity(Intent(this,SignUP::class.java))

        }
    }
}