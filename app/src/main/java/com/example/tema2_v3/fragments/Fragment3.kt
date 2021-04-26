package com.example.tema2_v3.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.tema2_v3.R
import com.example.tema2_v3.adapters.ImagesAdapter
import com.example.tema2_v3.interfaces.IActivityFragmentCommunication
import com.example.tema2_v3.interfaces.IExpandable
import com.example.tema2_v3.models.Image
import com.example.tema2_v3.utils.Constants.ALBUMS_URL
import com.example.tema2_v3.utils.Constants.BASE_URL
import com.example.tema2_v3.utils.Constants.PHOTOS_URL
import com.example.tema2_v3.utils.Constants.URL
import com.example.tema2_v3.utils.VolleySingleton
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
        if (albumId != -1) {
            var url = "$BASE_URL/$PHOTOS_URL/"

            val requestQ = VolleySingleton.getInstance(context!!).requestQueue

            val imagesRequest = StringRequest(
                Request.Method.GET,
                url,
                { response: String? ->

                    val photos = getImagesFromJSON(JSONArray(response))
                    view.findViewById<RecyclerView>(R.id.photos_recyclerview).adapter =
                        ImagesAdapter(photos)
                    view.findViewById<RecyclerView>(R.id.photos_recyclerview).layoutManager =
                        GridLayoutManager(this.context, 2, RecyclerView.VERTICAL, false)
                },
                { _ ->

                    val photos = ArrayList<Image>()
                    view.findViewById<RecyclerView>(R.id.photos_recyclerview).adapter =
                        ImagesAdapter(photos)
                    view.findViewById<RecyclerView>(R.id.photos_recyclerview).layoutManager =
                        GridLayoutManager(this.context, 2, RecyclerView.VERTICAL, false)
                }
            )

            requestQ.add(imagesRequest)
        }

    }
    private fun getImagesFromJSON(jsonArray: JSONArray): ArrayList<Image> {
        val images = ArrayList<Image>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val url = jsonObject.optString(URL)
            images.add(Image(url))
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