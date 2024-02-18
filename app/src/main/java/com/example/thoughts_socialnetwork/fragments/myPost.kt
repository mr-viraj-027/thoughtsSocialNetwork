package com.example.thoughts_socialnetwork.fragments

import android.annotation.SuppressLint
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.adpters.MyPostRVAdapter
import com.example.thoughts_socialnetwork.databinding.FragmentMyPostBinding
import com.example.thoughts_socialnetwork.models.postModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class myPost : Fragment() {
    private lateinit var binding: FragmentMyPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPostBinding.inflate(inflater, container, false)
        var postList = ArrayList<postModel>()
        var adapter = MyPostRVAdapter(requireContext(), postList)
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            var   tempList = arrayListOf<postModel>()
            for(i in it.documents)
            {
                var post:postModel =i.toObject<postModel>()!!
                tempList.add(post)

            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}