package com.example.mypostsapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyPosts(var context: Context, var userList: List<Post>): RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var currentPosts = userList.get(position)
        holder.tvUserId.text = currentPosts.userId.toString()
        holder.tvId.text = currentPosts.id.toString()
        holder.tvTitle.text = currentPosts.title
        holder.tvBody.text = currentPosts.body
        holder.cvPosts.setOnClickListener {
            var intent = Intent(context, ViewPostsActivity::class.java)
            intent.putExtra("POST_ID", currentPosts.id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}

class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var tvUserId = itemView.findViewById<TextView>(R.id.tvUserId)!!
    var tvId = itemView.findViewById<TextView>(R.id.tvId)!!
    var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)!!
    var tvBody = itemView.findViewById<TextView>(R.id.tvBody)!!
    var cvPosts = itemView.findViewById<CardView>(R.id.cvPosts)!!
}
