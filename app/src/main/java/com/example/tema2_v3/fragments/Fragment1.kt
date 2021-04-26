package com.example.tema2_v3.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tema2_v3.R;
import com.example.tema2_v3.adapters.MyAdapter;
import com.example.tema2_v3.interfaces.ActivityFragmentCommunication;
import com.example.tema2_v3.interfaces.OnUserItemClick;
import com.example.tema2_v3.models.Post;
import com.example.tema2_v3.models.User;
import com.example.tema2_v3.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

import static com.example.tema2_v3.utils.Constants.BODY;
import static com.example.tema2_v3.utils.Constants.ID;
import static com.example.tema2_v3.utils.Constants.POSTS_URL;
import static com.example.tema2_v3.utils.Constants.TITLE;
import static com.example.tema2_v3.utils.Constants.USER_ID;
import static com.example.tema2_v3.utils.Constants.USER_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActivityFragmentCommunication activityFragmentCommunication;
    private RecyclerView recyclerView;
    ArrayList<User> users = new ArrayList<User>();
    ArrayList<Post> posts = new ArrayList<Post>();
    public static String userNameClicked;

    public static ArrayList<Pair<String, String>> userAndId = new ArrayList<Pair<String, String>>();

    public Fragment1() {

    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_1, container, false);
        //Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.recyclerlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        getUsers();
        //setUpRecyclerView();
        return view;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {
        super.dump(prefix, fd, writer, args);
    }


    public void getUsers() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constants.BASE_URL + "/users";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            handleUserResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR: get users failed with error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

    }


    private void handleUserResponse(String responseJson) throws JSONException {

        JSONArray usersJSONArray = new JSONArray(responseJson);
        for (int i = 0; i < usersJSONArray.length(); i++) {
            JSONObject obj = usersJSONArray.getJSONObject(i);
            String name = obj.getString(USER_NAME);
            String id = obj.getString(ID);

            getPosts(id);
            User newUser = new User(name, posts);
            userAndId.add(new Pair<String, String>(id, name));
            users.add(newUser);
        }
        MyAdapter adapter = new MyAdapter(users, new OnUserItemClick() {
            @Override
            public void onClick(User user) {

                //  Toast.makeText(getContext(), "Ai apasat pe ceva", Toast.LENGTH_SHORT).show();
                if (activityFragmentCommunication != null) {
                    activityFragmentCommunication.replaceWithF2();

                }
            }

        });
        recyclerView.setAdapter(adapter);


    }

    public void getPosts(String userId) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = POSTS_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // ArrayList<Post> posts = new ArrayList<>();

                        try {

                            handlePostResponse(response, userId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR: get posts failed with error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void handlePostResponse(String responseJson, String userId) throws JSONException {
        posts.clear();

        JSONArray usersJSONArray = new JSONArray(responseJson);

        for (int i = 0; i < usersJSONArray.length(); i++) {
            JSONObject obj = usersJSONArray.getJSONObject(i);
            String id = obj.getString(ID);
            String title = obj.getString(TITLE);
            String body = obj.getString(BODY);
            if (obj.getString(USER_ID).equals(userId)) {
                Post post = new Post(id, title, body);
                posts.add(post);
            }


        }

    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        if (context instanceof ActivityFragmentCommunication) {
            activityFragmentCommunication = (ActivityFragmentCommunication) context;
        }
    }

}