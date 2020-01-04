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
import android.widget.TextView;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.model.User;
import com.mcdenny.examprep.viewmodel.UserViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment {
    private TextView name, email;
    private Button deleteUser;
    private UserViewModel userViewModel;
    private UsersFragment usersFragment;
    private FragmentManager fragmentManager;
    private Context context;
    private User user;


    public UserDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        name = view.findViewById(R.id.et_detail_name);
        email = view.findViewById(R.id.et_detail_email);
        deleteUser = view.findViewById(R.id.btn_delete_user);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserFromDatabase();
            }
        });

        ///receive user from the bundle
        Bundle bundle = getArguments();
        assert bundle != null;
        user = bundle.getParcelable("user_info");

        populateUserDetails();

        return view;
    }


    private void populateUserDetails() {
        name.setText(user.getName());
        email.setText(user.getEmail());
    }

    private void deleteUserFromDatabase() {
        userViewModel.deleteUser(user);
        openUsersFragment();
    }

    private void openUsersFragment() {
        usersFragment = new UsersFragment();
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(UserDetailFragment.this);
        transaction.replace(((ViewGroup)getView().getParent()).getId(), usersFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
