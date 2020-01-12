package com.mcdenny.examprep.viewmodel;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mcdenny.examprep.BR;
import com.mcdenny.examprep.model.User;



public class AddUserViewModel extends ViewModel {
    public MutableLiveData<String> errorEmail = new MutableLiveData<>();
    public MutableLiveData<String> errorUsername = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData;

    public AddUserViewModel(){}

    public void onAddBtnClick() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = new User(email.getValue(), username.getValue());
                if(!user.isEmailValid()){
                    errorEmail.setValue("Enter a valid email address!");
                } else {
                    errorEmail.setValue(null);
                }

                if (username == null){
                    errorUsername.setValue("Username must not be empty!");
                } else {
                    errorUsername.setValue(null);
                }

                userMutableLiveData.setValue(user);
            }
        }, 3000);
    }
}
