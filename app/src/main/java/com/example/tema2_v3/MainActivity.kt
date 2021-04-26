package com.example.tema2_v3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tema2_v3.fragments.Fragment1
import com.example.tema2_v3.fragments.Fragment2
import com.example.tema2_v3.fragments.Fragment3
import com.example.tema2_v3.interfaces.IActivityFragmentCommunication

class MainActivity : AppCompatActivity(), IActivityFragmentCommunication {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(Fragment1::class.java.name)
    }

    override fun openNextActivity() {
        // EMPTY
    }

    override fun replaceFragment(tag: String) = when (tag) {
        Fragment1::class.java.name -> {
            addMainFragment()
        }
        Fragment2::class.java.name ->{
            addSecondFragment()
        }
//        Fragment3::class.java.name ->{
//            addThirdFragment()
//        }
        else -> println("Invalid tag!")
    }

    private fun addSecondFragment() {
        val fragmentManager = this.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = Fragment2::class.java.name
        val addTransaction = transaction.add(
            R.id.fragment_container, Fragment2.newInstance(), tag
        )
        addTransaction.commit()
    }

    private fun addThirdFragment() {
        val fragmentManager = this.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = Fragment2::class.java.name
        val addTransaction = transaction.add(
            R.id.fragment_container, Fragment3.newInstance(), tag
        )
        addTransaction.commit()
    }



    private fun addMainFragment() {
        val fragmentManager = this.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = Fragment1::class.java.name
        val addTransaction = transaction.add(
            R.id.fragment_container, Fragment1.newInstance(), tag
        )
        addTransaction.commit()
    }
}