package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageButton imageButton = view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Telephone.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton2 = view.findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), prof.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton4 = view.findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Selfintroduction.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton5 = view.findViewById(R.id.imageButton10);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Birthday.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton6 = view.findViewById(R.id.imageButton9);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), gender.class);
                startActivity(intent);
            }
        });
        Button button = view.findViewById(R.id.edit_profile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), editprofile.class);
                startActivity(intent);
            }
        });

        Button button1 = view.findViewById(R.id.bt1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),linechart.class);
                startActivity(intent);
            }
        });

        TextView mUsernameTextView = view.findViewById(R.id.text_view_username);
        TextView mEmailTextView = view.findViewById(R.id.text_view_email);
        TextView mPhoneTextView = view.findViewById(R.id.text_view_phone);
        TextView mselfintrod= view.findViewById(R.id.textView19);
        TextView mbirthday= view.findViewById(R.id.textView14);
        TextView mgender= view.findViewById(R.id.textView20);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");

        // 建立要求的欄位字符串
        String fields = "username,phone,email,Introduction,birthday,gender";
        String URL = "http://0w0chen3060-2.ddns.net:7777/app_login/userData.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("res", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String username = jsonObject.getString("username");
                    String phone = jsonObject.getString("phone");
                    String email = jsonObject.getString("email");
                    String selfintrod = jsonObject.getString("Introduction");
                    String birthday = jsonObject.getString("birthday");
                    String gender = jsonObject.getString("gender");

                    mUsernameTextView.setText(username);
                    mEmailTextView.setText(email);
                    mPhoneTextView.setText(phone);
                    mselfintrod.setText(selfintrod);
                    mbirthday.setText(birthday);
                    mgender.setText(gender);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "解析 JSON 響應時出錯", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ProfileFragment.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "檢索用戶數據時出錯", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password", password);
                data.put("fields", fields);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        return view;
    }
}