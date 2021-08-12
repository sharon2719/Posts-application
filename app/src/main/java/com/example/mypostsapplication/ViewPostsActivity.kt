package com.example.mypostsapplication

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPostsActivity : AppCompatActivity() {
    var postId = 0
    lateinit var tvPostTitle: TextView
    lateinit var tvPostBody: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_posts)


        postId = intent.getIntExtra("POST_ID", 0)

        castViews()
        getPost()
        getComments()
    }

    fun castViews() {
        tvPostTitle = findViewById(R.id.tvPostTitle)
        tvPostBody = findViewById(R.id.tvPostBody)

    }

    fun getPost() {
        if (postId == 0) {
            Toast.makeText(baseContext, "Post not found", Toast.LENGTH_LONG).show()
            finish()
        }

        var apiClient = ApiClient.buildApiClient(ApiInterface::class.java)
        var request = apiClient.getPost(postId)

        request.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    var post = response.body()!!
                    tvPostTitle.text = post.title
                    tvPostBody.text = post.body
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getComments() {
        var rvComments = findViewById<RecyclerView>(R.id.rvComments)
        val retrofit = ApiClient.buildApiClient(ApiInterface::class.java)
        val request = retrofit.getComments(postId)
        request.enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
                if (response.isSuccessful) {
//                    rvPosts.findViewById<RecyclerView>(R.id.rvPosts)

                    var comments= response.body()!!
                    var commentsAdapter =PostRvAdapter (comments)
                    rvComments.adapter = commentsAdapter
                    rvComments.layoutManager = LinearLayoutManager(baseContext)
//                    Toast.makeText(baseContext, "${posts!!.size} posts", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}