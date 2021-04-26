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
import com.example.tema2_v3.adapters.AlbumsAdapter
import com.example.tema2_v3.interfaces.IActivityFragmentCommunication
import com.example.tema2_v3.models.Album
import com.example.tema2_v3.utils.Constants.ALBUMS_URL
import com.example.tema2_v3.utils.Constants.ALBUM_TITLE
import com.example.tema2_v3.utils.Constants.BASE_URL
import com.example.tema2_v3.utils.Constants.USERS_URL
import com.example.tema2_v3.utils.VolleySingleton
import org.json.JSONArray
import java.util.*

class Fragment2(private val userId: Int) : Fragment() {

    private var activity: IActivityFragmentCommunication? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (userId != -1) {

            val url = "$BASE_URL/$USERS_URL/$userId/$ALBUMS_URL/"

            val requestQ = VolleySingleton.getInstance(context!!).requestQueue

            val albumsRequest = StringRequest(
                Request.Method.GET,
                url,
                { response: String? ->
                    val albums = getAlbumsFromJSON(JSONArray(response))

                    view.findViewById<RecyclerView>(R.id.albums_recyclerview).adapter = AlbumsAdapter(albums)
                    view.findViewById<RecyclerView>(R.id.albums_recyclerview).layoutManager = LinearLayoutManager(this.context)
                },
                { volleyError ->

                    val albums = ArrayList<Album>()
                    albums.add(Album("$volleyError", ""))

                    view.findViewById<RecyclerView>(R.id.albums_recyclerview).adapter = AlbumsAdapter(albums)
                    view.findViewById<RecyclerView>(R.id.albums_recyclerview).layoutManager = LinearLayoutManager(this.context)
                }
            )

            requestQ.add(albumsRequest)
        }
    }

    private fun getAlbumsFromJSON(jsonArray: JSONArray): ArrayList<Album> {
        val albums = ArrayList<Album>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val name = jsonObject.optString(ALBUM_TITLE)
            albums.add(Album(name, ""))
        }

        return albums
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IActivityFragmentCommunication) {
            this.activity = context
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Fragment2(-1)
    }
}