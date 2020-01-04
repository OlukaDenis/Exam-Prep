package com.mcdenny.examprep.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mcdenny.examprep.data.db.AppDatabase;
import com.mcdenny.examprep.data.db.UserDao;
import com.mcdenny.examprep.model.User;

import java.util.List;

public class UserRepository {
    private AppDatabase database;
    private UserDao userDao;

    public UserRepository(Application application){
        database = AppDatabase.getDatabase(application);
        userDao = database.userDao();
    }


    public void addUser(User user){
        userDao.addUser(user);
    }

    public void updateUser(User user){
        userDao.updateUser(user);
    }


    public LiveData<List<User>> getAllUsers(){
        defaultUsers();
        return userDao.getAllUsers();
    }

    private void defaultUsers() {
        User user = new User();
        user.setEmail("olukadeno@gmail.com");
        user.setName("Oluka Denis");

        userDao.addUser(user);
    }

    public void deleteUser(User user){
        userDao.deleteUser(user);
    }
}
