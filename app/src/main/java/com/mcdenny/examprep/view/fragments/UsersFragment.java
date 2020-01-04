package com.mcdenny.examprep.view.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mcdenny.examprep.R;
import com.mcdenny.examprep.model.User;
import com.mcdenny.examprep.view.adapters.UserAdapter;
import com.mcdenny.examprep.viewmodel.MovieViewModel;
import com.mcdenny.examprep.viewmodel.UserViewModel;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
    private AddUserFragment addUserFragment;
    private FloatingActionButton floatingActionButton;
    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    private Context context;
    private UserViewModel userViewModel;
    private UserAdapter adapter;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.users_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter = new UserAdapter(context, userViewModel.getAllUsers().getValue());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        floatingActionButton = view.findViewById(R.id.add_user_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddUserFragment();
            }
        });
        return view;
    }

    private void openAddUserFragment() {
        addUserFragment = new AddUserFragment();
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(UsersFragment.this);
        transaction.replace(((ViewGroup)getView().getParent()).getId(), addUserFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
