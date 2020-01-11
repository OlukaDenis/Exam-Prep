package com.mcdenny.examprep;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mcdenny.examprep.view.activity.MainActivity;
import com.mcdenny.examprep.view.fragments.AddUserFragment;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddUserTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class
    );


}
