package com.example.tema2_v3.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tema2_v3.R;
import com.example.tema2_v3.adapters.ImagesAdapter;
import com.example.tema2_v3.interfaces.ActivityFragmentCommunication;
import com.example.tema2_v3.models.Image;
import com.example.tema2_v3.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ActivityFragmentCommunication activityFragmentCommunication;
    private RecyclerView recyclerView ;
    ArrayList<Image> images = new ArrayList<>();

    public Fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment F3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        //Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.photos_recyclerview);
        GridLayoutManager test = new GridLayoutManager( getContext(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(test);
        // Inflate the layout for this fragment
        //  setUpRecyclerView();
        getImages();
        return view;
    }

    public void setUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        GridLayoutManager test = new GridLayoutManager( getContext(), 2, RecyclerView.HORIZONTAL, false);

        recyclerView.findViewById(R.id.photos_recyclerview);
        recyclerView.setLayoutManager(test);
        images.add(new Image("https://via.placeholder.com/600/92c952"));
        images.add(new Image("https://via.placeholder.com/600/92c952"));
        images.add(new Image("https://via.placeholder.com/600/92c952"));

        ImagesAdapter adapter = new ImagesAdapter(images);
        recyclerView.setAdapter(adapter);


    }

    public void getImages() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constants.BASE_URL + "/photos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            handleImageResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR: get photos failed with error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    private void handleImageResponse(String responseJson) throws JSONException {

        JSONArray usersJSONArray = new JSONArray(responseJson);
        for (int i = 0; i < usersJSONArray.length(); i++) {
            JSONObject obj = usersJSONArray.getJSONObject(i);
            String url = obj.getString(Constants.URL);
            if(obj.getString(Constants.ALBUM_ID).equals(Fragment2.albumId)){
                Image image = new Image(url);
                images.add(image);
            }



        }
        ImagesAdapter adapter = new ImagesAdapter(getContext(), images);
        recyclerView.setAdapter(adapter);




    }
    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        if (context instanceof ActivityFragmentCommunication) {
            activityFragmentCommunication = (ActivityFragmentCommunication) context;
        }
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}