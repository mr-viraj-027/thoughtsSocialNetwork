package com.example.thoughts_socialnetwork.adpters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.thoughts_socialnetwork.databinding.MyPostRvDesignBinding
import com.example.thoughts_socialnetwork.models.postModel
import com.example.thoughts_socialnetwork.models.videoModel
import com.squareup.picasso.Picasso


class MyvideoAdapter(var context: Context, var videolist:ArrayList<videoModel>): RecyclerView.Adapter<MyvideoAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: MyPostRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var  binding = MyPostRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return videolist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(videolist.get(position).videourl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.postImgId)
    }
}