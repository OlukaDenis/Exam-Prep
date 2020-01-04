package com.mcdenny.examprep.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.view.fragments.AddUserFragment;
import com.mcdenny.examprep.view.fragments.UsersFragment;

public class MainActivity extends AppCompatActivity {
    private UsersFragment usersFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            openFragment();
        }
    }

    private void openFragment() {
        usersFragment = new UsersFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, usersFragment);
        transaction.commit();
    }
}

