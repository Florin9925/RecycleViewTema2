package com.example.tema2_v3.models

import android.os.Parcel
import com.example.tema2_v3.interfaces.IExpandable
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

data class User(val id: Int, val name: String): IExpandable {

    override fun getExpandableType(): ExpandableType = ExpandableType.USER
    var posts: ArrayList<Post> = ArrayList<Post>()
    var isExpanded = false
}