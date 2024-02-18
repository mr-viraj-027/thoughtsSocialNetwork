package com.example.thoughts_socialnetwork.post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.thoughts_socialnetwork.HomeActivity
import com.example.thoughts_socialnetwork.databinding.ActivityAddVideoPostBinding
import com.example.thoughts_socialnetwork.models.AuthUser
import com.example.thoughts_socialnetwork.models.videoModel
import com.example.thoughts_socialnetwork.utils.POST_FOLDER
import com.example.thoughts_socialnetwork.utils.USER_NODE
import com.example.thoughts_socialnetwork.utils.VIDEO
import com.example.thoughts_socialnetwork.utils.VIDEO_FOLDER
import com.example.thoughts_socialnetwork.utils.uploadImage
import com.example.thoughts_socialnetwork.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class AddVideoPostActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityAddVideoPostBinding.inflate(layoutInflater)
    }
    private lateinit var videourl: String
    private lateinit var progressDialog :ProgressDialog
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, VIDEO_FOLDER,this) { url ->
                if (url != null) {
                    videourl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.closeImg.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        binding.selectVideo.setOnClickListener {
            launcher.launch("video/*")
        }
        binding.btnpostVideo.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user: AuthUser = it.toObject<AuthUser>()!!
                    val video: videoModel =
                        videoModel(videourl!!, binding.txtCaption.editText?.text.toString(),user.image!!)

                    Firebase.firestore.collection(VIDEO).document().set(video).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + VIDEO).document()
                            .set(video)
                            .addOnSuccessListener {
                                startActivity(Intent(this@AddVideoPostActivity, HomeActivity::class.java))
                                finish()
                            }
                        Toast.makeText(this, "Posted!!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
        }
    }
}