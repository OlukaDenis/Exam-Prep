package com.mcdenny.examprep.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mcdenny.examprep.data.repository.UserRepository;
import com.mcdenny.examprep.model.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private LiveData<List<User>> users;
    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getAllUsers();
    }


    public LiveData<List<User>> getAllUsers(){
        return users;
    }

    public void addUser(User user){
        userRepository.addUser(user);
    }

    public void update(User user){
        userRepository.updateUser(user);
    }

    public void deleteUser(User user){
        userRepository.deleteUser(user);
    }






//    public void onLoginClicked() {
//        if (isInputDataValid())
//            setToastMessage(successMessage);
//        else
//            setToastMessage(errorMessage);
//    }
//
//    public boolean isInputDataValid() {
//        return !TextUtils.isEmpty(getUserEmail()) && Patterns.EMAIL_ADDRESS.matcher(getUserEmail()).matches() && getUserPassword().length() > 5;
//    }



}