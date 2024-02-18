package com.example.thoughts_socialnetwork.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.adpters.MyPostRVAdapter
import com.example.thoughts_socialnetwork.adpters.MyvideoAdapter
import com.example.thoughts_socialnetwork.databinding.ActivityAddVideoPostBinding
import com.example.thoughts_socialnetwork.databinding.FragmentMyreelsBinding
import com.example.thoughts_socialnetwork.models.postModel
import com.example.thoughts_socialnetwork.models.videoModel
import com.example.thoughts_socialnetwork.utils.VIDEO
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class myreels : Fragment() {
private lateinit var binding:FragmentMyreelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMyreelsBinding.inflate(inflater, container, false)

        var videoList = ArrayList<videoModel>()
        var adapter = MyvideoAdapter(requireContext(), videoList)

        binding.rv.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ VIDEO).get().addOnSuccessListener {
            var   tempList = arrayListOf<videoModel>()
            for(i in it.documents)
            {
                var video: videoModel =i.toObject<videoModel>()!!
                tempList.add(video)

            }
            videoList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}