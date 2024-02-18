package com.example.thoughts_socialnetwork.adpters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thoughts_socialnetwork.R
import com.example.thoughts_socialnetwork.databinding.ReelDesignBinding
import com.example.thoughts_socialnetwork.models.videoModel
import com.squareup.picasso.Picasso

class ReelsAdapter(var context:Context,var reelList:ArrayList<videoModel>):RecyclerView.Adapter<ReelsAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ReelDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ReelDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return  ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.profile).into(holder.binding.profileImage)
        holder.binding.txtreelsCaption.setText(reelList.get(position).caption)
        holder.binding.videoView.setVideoPath(reelList.get(position).videourl)
        holder.binding.videoView.setOnPreparedListener {
            holder.binding.progressBar.visibility = View.GONE
        holder.binding.videoView.start()

        }
    }
}