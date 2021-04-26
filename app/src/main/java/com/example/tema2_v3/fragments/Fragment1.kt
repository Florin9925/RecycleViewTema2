package com.example.tema2_v3.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.tema2_v3.R
import com.example.tema2_v3.adapters.ExpandableAdapter
import com.example.tema2_v3.interfaces.IActivityFragmentCommunication
import com.example.tema2_v3.interfaces.IExpandable
import com.example.tema2_v3.models.User
import com.example.tema2_v3.utils.Constants.BASE_URL
import com.example.tema2_v3.utils.Constants.USER_ID
import com.example.tema2_v3.utils.Constants.USER_NAME
import com.example.tema2_v3.utils.VolleySingleton
import org.json.JSONArray
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment1 : Fragment() {

    private var activity: IActivityFragmentCommunication? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IActivityFragmentCommunication) {
            this.activity = context
        }
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val url = "$BASE_URL/users"

        val requestQ = VolleySingleton.getInstance(context!!).requestQueue

        val usersRequest = StringRequest(
            Request.Method.GET,
            url,
            { responseString ->
                view.findViewById<RecyclerView>(R.id.expanded_recycler_view).adapter =
                    ExpandableAdapter(
                        getUsersFromRequestResponse(JSONArray(responseString)),
                        this.activity
                    )
                view.findViewById<RecyclerView>(R.id.expanded_recycler_view).layoutManager =
                    LinearLayoutManager(this.context)
            },
            { volleyError ->
                val users = ArrayList<IExpandable>()

                users.add(User(1, "$volleyError"))

                view.findViewById<RecyclerView>(R.id.expanded_recycler_view).adapter =
                    ExpandableAdapter(users, this.activity)
                view.findViewById<RecyclerView>(R.id.expanded_recycler_view).layoutManager =
                    LinearLayoutManager(this.context)
            }
        )

        requestQ.add(usersRequest)
    }

    private fun getUsersFromRequestResponse(jsonArray: JSONArray): ArrayList<IExpandable> {
        val users = ArrayList<IExpandable>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.optString(USER_ID).toInt()
            val name = jsonObject.optString(USER_NAME)
            users.add(User(id, name))
        }

        return users
    }


    companion object {
        @JvmStatic
        fun newInstance() = Fragment1()
    }


}