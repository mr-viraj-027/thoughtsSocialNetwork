package com.example.thoughts_socialnetwork.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thoughts_socialnetwork.LoginActivity
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.SignUP
import com.example.thoughts_socialnetwork.adpters.ViewPagerAdapter
import com.example.thoughts_socialnetwork.databinding.FragmentAddBinding
import com.example.thoughts_socialnetwork.databinding.FragmentProfileBinding
import com.example.thoughts_socialnetwork.models.AuthUser
import com.example.thoughts_socialnetwork.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private  lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.btnEditProfile.setOnClickListener{
            val intent = Intent(activity,SignUP::class.java)
            intent.putExtra("MODE",1)
            activity?.startActivity(intent)
            activity?.finish()
        }

        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(myPost(),"My posts")
        viewPagerAdapter.addFragments(myreels(),"My reels")
        binding.viewpager.adapter=viewPagerAdapter
        binding.tablayout.setupWithViewPager(binding.viewpager)

        auth = FirebaseAuth.getInstance()
        binding.btnLogout.setOnClickListener {
          auth.signOut()
          val intent = Intent(activity, LoginActivity::class.java)
          startActivity(intent)
        //  finish()


        }

        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user: AuthUser = it.toObject<AuthUser>()!!
                binding.userName.text = user.username
                binding.bio.text = user.bio
                if (!user.image.isNullOrEmpty()) {
                    Picasso.get().load(user.image).into(binding.profileImage)
                }
            }
    }

}
