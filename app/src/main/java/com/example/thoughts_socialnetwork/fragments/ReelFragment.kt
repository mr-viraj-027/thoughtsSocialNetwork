package com.example.thoughts_socialnetwork.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.adpters.ReelsAdapter
import com.example.thoughts_socialnetwork.databinding.FragmentReelBinding
import com.example.thoughts_socialnetwork.models.videoModel
import com.example.thoughts_socialnetwork.utils.VIDEO
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class ReelFragment : Fragment() {

    private  lateinit var binding: FragmentReelBinding
     lateinit var adapter:ReelsAdapter
    var reelList =ArrayList<videoModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentReelBinding.inflate(inflater, container, false)
        adapter = ReelsAdapter(requireContext(),reelList)
        binding.viewPager.adapter= adapter
        Firebase.firestore.collection(VIDEO).get().addOnSuccessListener {
            var tempList = ArrayList<videoModel>()
            reelList.clear()
            for (i in it.documents)
            {
                var reel = i.toObject<videoModel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }
     
     
      return  binding.root
    }

    companion object {

    }
}