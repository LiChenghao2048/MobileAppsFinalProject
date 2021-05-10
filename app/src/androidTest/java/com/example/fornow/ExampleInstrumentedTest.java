package com.example.fornow;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule mainTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test_main_user_flow() {

        onView(withId(R.id.recyclerView_todo)).check(matches(hasChildCount(0)));

        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Coffee Break"));
        onView(withId(R.id.editText_description)).perform(typeText("drink coffee."));
        onView(withId(R.id.button_save)).perform(click());

        onView(withId(R.id.recyclerView_todo)).check(matches(hasChildCount(1)));

        onView(withParent(withId(R.id.recyclerView_todo))).perform(click());
        onView(withId(R.id.button_update)).perform(click());
        onView(withId(R.id.editText_description)).perform(typeText(" a lot of coffee."));
        onView(withId(R.id.button_save)).perform(click());

        onView(withId(R.id.recyclerView_todo)).check(matches(hasChildCount(1)));

        onView(withParent(withId(R.id.recyclerView_todo))).perform(click());
        onView(withId(R.id.button_done)).perform(click());

        onView(withId(R.id.recyclerView_todo)).check(matches(hasChildCount(0)));
    }

    @Test
    public void test_check_history() {

        onView(withId(R.id.button_history)).perform(click());
        onView(withText("Past Tasks")).check(matches(isDisplayed()));
        pressBack();

    }
}