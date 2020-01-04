package com.mcdenny.examprep.view.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mcdenny.examprep.R;
import com.mcdenny.examprep.model.User;
import com.mcdenny.examprep.viewmodel.UserViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserFragment extends Fragment {
    private Context context;
    private FloatingActionButton addUser;
    private UsersFragment usersFragment;
    private FragmentManager fragmentManager;
    private UserViewModel userViewModel;
    private EditText email, username;

    public AddUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        email = view.findViewById(R.id.et_email);
        username = view.findViewById(R.id.et_username);

        addUser = view.findViewById(R.id.btn_add_user);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToDatabase();
            }
        });

        return view;
    }

    private void addUserToDatabase() {
        final String mEmail = email.getText().toString();
        final String mUsername = username.getText().toString();

        if(mEmail.isEmpty()){
            email.setError("Email can't be empty");
        } else if(mUsername.isEmpty()){
            username.setError("Username can't be empty");
        } else {
            User mUser = new User();
            mUser.setName(mUsername);
            mUser.setEmail(mEmail);

            userViewModel.addUser(mUser);
            openUsersFragment();
        }


    }

    private void openUsersFragment() {
        usersFragment = new UsersFragment();
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(AddUserFragment.this);
        transaction.replace(((ViewGroup)getView().getParent()).getId(), usersFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
