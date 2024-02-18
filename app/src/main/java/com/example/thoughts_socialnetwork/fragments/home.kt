package com.example.thoughts_socialnetwork.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.adpters.FollowAdapter
import com.example.thoughts_socialnetwork.adpters.PostAdapter
import com.example.thoughts_socialnetwork.databinding.FragmentHomeBinding
import com.example.thoughts_socialnetwork.models.AuthUser
import com.example.thoughts_socialnetwork.models.postModel
import com.example.thoughts_socialnetwork.utils.FOLLOW
import com.example.thoughts_socialnetwork.utils.POST
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext.Auth

class home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var postList = ArrayList<postModel>()
    private lateinit var adapter: PostAdapter
    private var followList = ArrayList<AuthUser>()
    private lateinit var followAdapter: FollowAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initializing layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = PostAdapter(requireContext(), postList)
        binding.postRv.layoutManager = LinearLayoutManager(requireContext())
        binding.postRv.adapter = adapter

        // Initialize followAdapter and set its adapter for followRv
        followAdapter = FollowAdapter(requireContext(), followList)
        binding.followRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.followRv.adapter = followAdapter

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar)

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).get()
            .addOnSuccessListener {
                var tempList = ArrayList<AuthUser>()
                for(i in it.documents)
                {
                    var user = i.toObject<AuthUser>()!!
                    tempList.add(user)

                }
                followList.addAll(tempList)
                followAdapter.notifyDataSetChanged()
            }


        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList = ArrayList<postModel>()
            for (i in it.documents) {
                var post: postModel = i.toObject<postModel>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}