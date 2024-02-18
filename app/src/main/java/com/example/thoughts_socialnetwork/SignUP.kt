package com.example.thoughts_socialnetwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.thoughts_socialnetwork.databinding.ActivitySignUpBinding
import com.example.thoughts_socialnetwork.models.AuthUser
import com.example.thoughts_socialnetwork.utils.USER_NODE
import com.example.thoughts_socialnetwork.utils.USER_PROFILE_FOLDER
import com.example.thoughts_socialnetwork.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class SignUP : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: AuthUser
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER){
                if(it!=null)
                {
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text = "<font color=#FF000000> Already have an Account ?</font>  <font color=#1E88E5> Login</font>"
        binding.txtLogin.setText(Html.fromHtml(text))
        user = AuthUser()
        //for editing user profile
            if(intent.hasExtra("MODE")){
                if(intent.getIntExtra("MODE",-1)==1)
                {
                    binding.btnSignup.text = "update profile"
                    Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                        .addOnSuccessListener {
                            user = it.toObject<AuthUser>()!!
                            if (!user.image.isNullOrEmpty()) {
                                Picasso.get().load(user.image).into(binding.profileImage)
                            }
                            binding.username.editText?.setText(user.username)
                            binding.email.editText?.setText(user.email)
                            binding.bio.editText?.setText(user.bio)
                           // binding.password.editText?.setText(user.password)



                        }
                }
            }

        //firebase create user using email & password
        binding.btnSignup.setOnClickListener {
            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {
                    val db = FirebaseFirestore.getInstance()
                    db.collection(USER_NODE)
                        .document(FirebaseAuth.getInstance().currentUser!!.uid)
                        .set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Updated!!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignUP, HomeActivity::class.java))
                            finish()

                        }
                }
            } else {
                if (binding.username.editText?.text.toString()
                        .equals("") or binding.email.editText?.text.toString()
                        .equals("") or binding.bio.editText?.text.toString()
                        .equals("") or binding.password.editText?.text.toString().equals("")
                ) {

                    Toast.makeText(this@SignUP, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(

                        binding.email.editText?.text.toString(),
                        binding.password.editText?.text.toString()

                    ).addOnCompleteListener { result ->
                        if (result.isSuccessful) {

                            user.username = binding.username.editText?.text.toString()
                            user.email = binding.email.editText?.text.toString()
                            user.bio = binding.bio.editText?.text.toString()
                            user.password = binding.password.editText?.text.toString()

                            val db = FirebaseFirestore.getInstance()
                            db.collection(USER_NODE)
                                .document(FirebaseAuth.getInstance().currentUser!!.uid)

                                .set(user).addOnSuccessListener {
                                    Toast.makeText(this, "Welcome!!", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@SignUP, HomeActivity::class.java))
                                    finish()

                                }.addOnFailureListener { e ->
                                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT)
                                        .show()
                                }
                        } else {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        //store user profile to firebase cloud store
        binding.profileImage.setOnClickListener{
            launcher.launch("image/*")
        }
        //navigate to login activity when clicked
        binding.txtLogin.setOnClickListener{
            startActivity(Intent(this@SignUP,LoginActivity::class.java))
           // finish()
        }
    }
}