package com.mcdenny.examprep;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mcdenny.examprep.testUtils.RecyclerViewMatcher;
import com.mcdenny.examprep.view.activity.MainActivity;
import com.mcdenny.examprep.view.fragments.AddUserFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AddUserTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class
    );
    // Convenience helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Test
    public void actionAddUser(){
        onView(withId(R.id.all_users_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.add_user_fab)).perform(click());
        onView(withId(R.id.add_user_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.et_email)).perform(typeText("test@email.com"));
        onView(withId(R.id.et_username)).perform(typeText("Denis Oluka"));
        onView(withId(R.id.btn_add_user)).perform(click());
        onView(withId(R.id.all_users_layout)).check(matches(isDisplayed()));

        // Check item at position 1 has been added
        onView(withRecyclerView(R.id.users_recycler_view).atPosition(1))
                .check(matches(hasDescendant(withText("Denis Oluka"))));
    }

}
