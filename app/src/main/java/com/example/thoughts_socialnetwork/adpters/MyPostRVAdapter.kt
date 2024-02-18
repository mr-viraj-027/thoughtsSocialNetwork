package com.example.thoughts_socialnetwork.adpters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thoughts_socialnetwork.databinding.MyPostRvDesignBinding
import com.example.thoughts_socialnetwork.models.postModel
import com.squareup.picasso.Picasso

class MyPostRVAdapter( var context:Context,var postlist:ArrayList<postModel>): RecyclerView.Adapter<MyPostRVAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: MyPostRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var  binding = MyPostRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(postlist.get(position).posturl).into(holder.binding.postImgId)
    }
}