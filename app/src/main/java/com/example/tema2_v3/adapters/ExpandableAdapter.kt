package com.example.tema2_v3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.tema2_v3.MainActivity
import com.example.tema2_v3.R
import com.example.tema2_v3.fragments.Fragment2
import com.example.tema2_v3.interfaces.IActivityFragmentCommunication
import com.example.tema2_v3.interfaces.IExpandable
import com.example.tema2_v3.models.ExpandableType
import com.example.tema2_v3.models.Post
import com.example.tema2_v3.models.User
import com.example.tema2_v3.utils.Constants.BASE_URL
import com.example.tema2_v3.utils.Constants.POST_BODY
import com.example.tema2_v3.utils.Constants.POST_TITLE
import com.example.tema2_v3.utils.VolleySingleton
import org.json.JSONArray


class ExpandableAdapter(
    private var items: ArrayList<IExpandable>,
    private val activity: IActivityFragmentCommunication?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class TopViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var userId = -1
        private val nameView: TextView = itemView.findViewById(R.id.item_text_view)
        val btnNextView: ImageButton = itemView.findViewById(R.id.btn_posts)

        fun bind(user: User) {
            nameView.text = user.name
            userId = user.id
        }
    }

    class UnderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.underitem_title)
        private val bodyView: TextView = itemView.findViewById(R.id.underitem_body)

        fun bind(post: Post) {
            titleView.text = post.title
            bodyView.text = post.body
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position].getExpandableType() == ExpandableType.USER) {
            return 0
        } else if (items[position].getExpandableType() == ExpandableType.POST) {
            return 1
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == ExpandableType.USER.ordinal) {
            val topItemView = inflater.inflate(R.layout.expandable_recycleview_user, parent, false)
            TopViewHolder(topItemView)
        } else {
            val underItemView =
                inflater.inflate(R.layout.expandable_recycleview_post, parent, false)
            UnderViewHolder(underItemView)
        }
    }

    private fun cleanItems(from: Int, delSize: Int) {

        for (i in 0 until delSize) {
            items.removeAt(from)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = items[position]

        if (holder is TopViewHolder) {
            val user = currentItem as User
            holder.itemView.setOnClickListener {
                val myFragment: Fragment = Fragment2(holder.userId)
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, myFragment).addToBackStack(null).commit()
            }
            holder.btnNextView.setOnClickListener {
                if (!user.isExpanded) {

                    val url = "$BASE_URL/users/${holder.userId}/posts/"
                    val requestQ = VolleySingleton.getInstance(holder.itemView.context).requestQueue
                    if ((items[position] as User).posts.size == 0) {
                        val postsRequest = StringRequest(
                            Request.Method.GET,
                            url,
                            { responseString ->
                                val posts = getPostsFromRequestResponse(JSONArray(responseString))
                                for (i in 0 until posts.size) {
                                    items.add(holder.layoutPosition + 1 + i, posts[i])
                                }
                                (items[position] as User).posts = posts
                                notifyItemRangeInserted(holder.layoutPosition + 1, posts.size)
                            },
                            {
                                items.add(
                                    holder.layoutPosition + 1,
                                    Post("ERROR GETTING TITLE AND BODY", "")
                                )
                                notifyItemRangeInserted(holder.layoutPosition + 1, 1)
                            }
                        )
                        requestQ.add(postsRequest)
                    } else {
                        for (i in 0 until (items[position] as User).posts.size) {
                            items.add(
                                holder.layoutPosition + 1 + i,
                                (items[position] as User).posts[i]
                            )
                        }
                        notifyItemRangeInserted(
                            holder.layoutPosition + 1,
                            (items[position] as User).posts.size
                        )
                    }
                    user.isExpanded = true
                } else {

                    cleanItems(holder.layoutPosition + 1, (items[position] as User).posts.size)

                    notifyItemRangeRemoved(
                        holder.layoutPosition + 1,
                        (items[position] as User).posts.size
                    )
                    user.isExpanded = false
                }


            }
            holder.bind(user)

        } else if (holder is UnderViewHolder) {

            val post = currentItem as Post
            holder.bind(post)
        }
    }

    private fun getPostsFromRequestResponse(jsonArray: JSONArray): ArrayList<Post> {
        val posts = ArrayList<Post>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val title = jsonObject.optString(POST_TITLE)
            val body = jsonObject.optString(POST_BODY)
            posts.add(Post(title, body))
        }

        return posts
    }

    override fun getItemCount() = items.size
}