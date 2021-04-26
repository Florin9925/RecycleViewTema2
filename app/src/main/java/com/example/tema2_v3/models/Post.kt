package com.example.tema2_v3.models

import com.example.tema2_v3.interfaces.IExpandable

class Post(val title: String, val body: String): IExpandable {
    override fun getExpandableType(): ExpandableType = ExpandableType.POST
}