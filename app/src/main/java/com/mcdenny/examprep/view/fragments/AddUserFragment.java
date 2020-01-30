package com.mcdenny.examprep.view.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mcdenny.examprep.R;
import com.mcdenny.examprep.databinding.FragmentAddUserBinding;
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
    private FragmentAddUserBinding binding;

    public AddUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        binding = FragmentAddUserBinding.inflate(inflater);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        binding.setUserViewModel(userViewModel);
        binding.setLifecycleOwner(this);

        binding.btnAddUser.setOnClickListener(v -> {

            if (binding.etEmail.getText().toString().isEmpty()){
                binding.etEmail.setError("Email must not be empty!");
            }
            else if (binding.etUsername.getText().toString().isEmpty()){
                binding.etUsername.setError("Username must not be empty!");
            } else {
                userViewModel.addUser();
                openUsersFragment();
            }
        });


        return binding.getRoot();
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
