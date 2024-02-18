package com.example.thoughts_socialnetwork.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.adpters.SearchAdapter
import com.example.thoughts_socialnetwork.databinding.FragmentSearchBinding
import com.example.thoughts_socialnetwork.models.AuthUser
import com.example.thoughts_socialnetwork.utils.USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext.Auth

class searchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    var userList = ArrayList<AuthUser>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(requireContext(), userList)
        binding.rv.adapter = adapter


        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList = ArrayList<AuthUser>()
            userList.clear()

                for (i in it.documents) {
                        val user: AuthUser = i.toObject<AuthUser>()!!
                        tempList.add(user)



                }
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()


        }
        binding.btnSearch.setOnClickListener {
            var text = binding.searchView.text.toString()
            Firebase.firestore.collection(USER_NODE).whereEqualTo("username", text).get()
                .addOnSuccessListener {
                    var tempList = ArrayList<AuthUser>()
                    userList.clear()
                    if (it.isEmpty) {

                    } else {
                        for (i in it.documents) {
                            if (i.id.toString() == Firebase.auth.currentUser!!.uid) {

                            } else {
                                val user: AuthUser = i.toObject<AuthUser>()!!
                                tempList.add(user)
                            }


                        }
                        userList.addAll(tempList)
                        adapter.notifyDataSetChanged()
                    }
                }
        }

        return binding.root
    }

    companion object {

    }
}