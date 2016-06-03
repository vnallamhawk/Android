package com.hawklist;

import helper.SQLiteHandler;
import helper.SessionManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import activity.LoginActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


public class Account extends Fragment{

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private SQLiteHandler db;
    private SessionManager session;

    public Account() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_account, container, false);


        //return inflater.inflate(R.layout.activity_account, container, false);

        txtName = (TextView) v.findViewById(R.id.name);
        txtEmail = (TextView) v.findViewById(R.id.email);
        btnLogout = (Button) v.findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getActivity().getApplicationContext());

        // session manager
        session = new SessionManager(getActivity().getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        return v;
    }
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
