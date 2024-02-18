package com.example.thoughts_socialnetwork.adpters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.databinding.FollowRvBinding
import com.example.thoughts_socialnetwork.models.AuthUser
import com.example.thoughts_socialnetwork.models.videoModel

class FollowAdapter(var context: Context,var followList:ArrayList<AuthUser>):RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    inner class ViewHolder(var binding:FollowRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding= FollowRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return  ViewHolder(binding)
    }

    override fun getItemCount(): Int {
    return followList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(followList.get(position).image).placeholder(R.drawable.user).into(holder.binding.profileImage)
        holder.binding.username.text = followList.get(position).username
    }

}