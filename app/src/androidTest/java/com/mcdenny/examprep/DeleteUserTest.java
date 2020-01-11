package com.mcdenny.examprep;

import androidx.test.rule.ActivityTestRule;

import com.mcdenny.examprep.testUtils.RecyclerViewMatcher;
import com.mcdenny.examprep.view.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DeleteUserTest {
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class
    );
    // Convenience helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Test
    public void actionDeleteUser(){
        onView(withId(R.id.users_recycler_view)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.users_recycler_view).atPosition(1)).perform(click());

        //User details fragment
        onView(withId(R.id.user_details_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.et_detail_name)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_delete_user)).perform(click());
        onView(withId(R.id.all_users_layout)).check(matches(isDisplayed()));
    }
}
