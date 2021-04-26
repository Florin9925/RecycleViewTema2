package com.example.tema2_v3.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tema2_v3.R
import com.example.tema2_v3.interfaces.IActivityFragmentCommunication
import com.example.tema2_v3.models.Image
import org.json.JSONArray

class Fragment3(private val albumId: Int) : Fragment() {

    private var activity: IActivityFragmentCommunication? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (userId != -1) {
//
//            val url = "${Constants.BASE_URL}/${Constants.USERS_URL}/$userId/${Constants.ALBUMS_URL}/"
//
//            val requestQ = VolleySingleton.getInstance(context!!).requestQueue
//
//            val albumsRequest = StringRequest(
//                Request.Method.GET,
//                url,
//                { response: String? ->
//                    val albums = getAlbumsFromJSON(JSONArray(response))
//
//                    view.findViewById<RecyclerView>(R.id.albums_recyclerview).adapter = AlbumsAdapter(albums)
//                    view.findViewById<RecyclerView>(R.id.albums_recyclerview).layoutManager = LinearLayoutManager(this.context)
//                },
//                { volleyError ->
//
//                    val albums = ArrayList<Album>()
//                    albums.add(Album("$volleyError", ""))
//
//                    view.findViewById<RecyclerView>(R.id.albums_recyclerview).adapter = AlbumsAdapter(albums)
//                    view.findViewById<RecyclerView>(R.id.albums_recyclerview).layoutManager = LinearLayoutManager(this.context)
//                }
//            )
//
//            requestQ.add(albumsRequest)
    }


    private fun getImagesFromJSON(jsonArray: JSONArray): ArrayList<Image> {
        val images = ArrayList<Image>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            // val name = jsonObject.optString(Constants.ALBUM_TITLE)
            // albums.add(Album(name, ""))
        }

        return images
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IActivityFragmentCommunication) {
            this.activity = context
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Fragment3(-1)
    }
}