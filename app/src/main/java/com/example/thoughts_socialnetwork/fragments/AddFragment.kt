package com.example.thoughts_socialnetwork.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.databinding.FragmentAddBinding
import com.example.thoughts_socialnetwork.post.AddVideoPostActivity
import com.example.thoughts_socialnetwork.post.ImagePostActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.txtAddImagePost.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), ImagePostActivity::class.java))
            activity?.finish()
        }
        binding.txtAddVideoPost.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),AddVideoPostActivity::class.java))
        }
        return binding.root
    }

    companion object {

    }
}