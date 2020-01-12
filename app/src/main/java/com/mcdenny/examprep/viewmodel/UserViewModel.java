package com.mcdenny.examprep.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mcdenny.examprep.data.repository.UserRepository;
import com.mcdenny.examprep.model.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    public MutableLiveData<String> errorEmail = new MutableLiveData<>();
    public MutableLiveData<String> errorUsername = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData;
    private LiveData<List<User>> users;
    private UserRepository userRepository;
    private static final String TAG = "UserViewModel";

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getAllUsers();
    }


    public LiveData<List<User>> getAllUsers(){
        return users;
    }

    public LiveData<User> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }



    public void update(User user){
        userRepository.updateUser(user);
    }

    public void deleteUser(User user){
        userRepository.deleteUser(user);
    }

    public void addUser() {
        Log.d(TAG, "Adding user.....");
        new Handler().postDelayed(() -> {
            User user = new User(email.getValue(), username.getValue());
            userRepository.addUser(user);

        }, 0);
    }

}