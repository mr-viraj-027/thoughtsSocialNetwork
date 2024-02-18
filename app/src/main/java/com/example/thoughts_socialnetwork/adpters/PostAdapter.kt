package com.example.thoughts_socialnetwork.adpters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thoughts_socialnetwork.databinding.PostRvBinding
import com.example.thoughts_socialnetwork.models.AuthUser
import com.example.thoughts_socialnetwork.models.postModel
import com.example.thoughts_socialnetwork.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class PostAdapter(var context: Context, var postList:ArrayList<postModel>): RecyclerView.Adapter<PostAdapter.MyHolder>(){



    inner  class MyHolder( var binding: PostRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {
                var user = it.toObject<AuthUser>()
                Glide.with(context).load(user!!.image).placeholder(com.example.thoughts_socialnetwork.R.drawable.profile).into(holder.binding.profileImage)
                holder.binding.userName.text = user.username
            }
        }catch (e:Exception){

        }

        Glide.with(context).load(postList.get(position).posturl).placeholder(com.example.thoughts_socialnetwork.R.drawable.loading).into(holder.binding.postImage)
        try {
            val text = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.time.text = text
        }
        catch (e:Exception)
        {
            holder.binding.time.text=""

        }
        holder.binding.send.setOnClickListener{
            val i = Intent(android.content.Intent.ACTION_SEND)
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList.get(position).posturl)
            context.startActivity(i)
        }
        holder.binding.caption.text = postList.get(position).caption
        holder.binding.like.setOnClickListener{
            holder.binding.like.setImageResource(com.example.thoughts_socialnetwork.R.drawable.liked)
        }
    }
}