package com.example.tema2_v3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tema2_v3.fragments.Fragment1
import com.example.tema2_v3.fragments.Fragment2
import com.example.tema2_v3.fragments.Fragment3
import com.example.tema2_v3.interfaces.ActivityFragmentCommunication
import com.example.tema2_v3.models.Post
import com.example.tema2_v3.models.User

class MainActivity : AppCompatActivity(), ActivityFragmentCommunication {
    val users: ArrayList<User> = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFirstFragmentFragment()

    }

    fun setUpRecyclerView() {

        val recyclerView: RecyclerView = findViewById(R.id.recyclerlist)
        val layoutManager: LinearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        // val layoutManager: GridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        users.clear()
        val posts: ArrayList<Post> = ArrayList<Post>();
        posts.add(Post("1", "FirstPost", "test1"))
        posts.add(Post("2", "SecondPost", "test2"))
        posts.add(Post("3", "ThirdPost", "test3"))

        users.add(User( "Tralala", posts))
        // var adapter: MyAdapter = MyAdapter(users, )

        // recyclerView.adapter = adapter

    }
    private fun addFirstFragmentFragment() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val firstFragment = Fragment1.newInstance("", "")
        val tag = Fragment1::class.java.name

        val addTransaction = transaction.add(
            R.id.frame_layout, firstFragment, tag
        )

        //addTransaction.addToBackStack(tag)
        addTransaction.commit()

    }
    override fun replaceWithF2() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = Fragment1::class.java.name

        val replaceTransaction = transaction.add(
            R.id.frame_layout, Fragment2.newInstance("", ""), tag
        )
        replaceTransaction.addToBackStack(tag)

        replaceTransaction.commit()
    }
    override fun replaceWithF3() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = Fragment2::class.java.name

        val replaceTransaction = transaction.add(
            R.id.frame_layout, Fragment3.newInstance("", ""), tag
        )
        replaceTransaction.addToBackStack(tag)

        replaceTransaction.commit()
    }
}