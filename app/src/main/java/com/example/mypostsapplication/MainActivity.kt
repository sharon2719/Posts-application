package com.example.mypostsapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var rvPosts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPosts()
    }

    fun getPosts() {
        var rvPosts = findViewById<RecyclerView>(R.id.rvPosts)
        val retrofit = ApiClient.buildApiClient(ApiInterface::class.java)
        val request = retrofit.getPosts()
        request.enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>?>, response: Response<List<Post>?>) {
                if (response.isSuccessful) {
                  rvPosts.findViewById<RecyclerView>(R.id.rvPosts)
                    var posts = response.body()!!
                    var myAdapter = MyPosts(baseContext, posts)
                    rvPosts.adapter = myAdapter
                    rvPosts.layoutManager = LinearLayoutManager(baseContext)
//                    Toast.makeText(baseContext, "${posts!!.size} posts", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Post>?>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}