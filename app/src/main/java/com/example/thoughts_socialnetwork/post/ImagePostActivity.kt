package com.example.thoughts_socialnetwork.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.thoughts_socialnetwork.HomeActivity
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.databinding.ActivityImagePostBinding
import com.example.thoughts_socialnetwork.fragments.AddFragment
import com.example.thoughts_socialnetwork.fragments.ProfileFragment
import com.example.thoughts_socialnetwork.models.AuthUser
import com.example.thoughts_socialnetwork.models.postModel
import com.example.thoughts_socialnetwork.utils.POST
import com.example.thoughts_socialnetwork.utils.POST_FOLDER
import com.example.thoughts_socialnetwork.utils.USER_NODE
import com.example.thoughts_socialnetwork.utils.USER_PROFILE_FOLDER
import com.example.thoughts_socialnetwork.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ImagePostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityImagePostBinding.inflate(layoutInflater)
    }
    var imageurl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageurl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.closeImg.setOnClickListener {
            startActivity(Intent(this@ImagePostActivity, HomeActivity::class.java))
            finish()
        }
        binding.btnPost.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user = it.toObject<AuthUser>()!!
                    val post: postModel =
                        postModel(
                           posturl =  imageurl!!,
                          caption =   binding.txtCaption.editText?.text.toString(),
                            uid = Firebase.auth.currentUser!!.uid,
                           time = System.currentTimeMillis().toString()
                        )

                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                            .set(post)
                            .addOnSuccessListener {
                                startActivity(
                                    Intent(
                                        this@ImagePostActivity,
                                        HomeActivity::class.java
                                    )
                                )
                                finish()
                            }
                        Toast.makeText(this, "Posted!!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

        }
        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            Toast.makeText(this, "Post Has Canceled!!", Toast.LENGTH_SHORT).show()
        }
    }
}