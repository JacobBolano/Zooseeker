package com.example.cse110_team45;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DisplayExhibitsTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void displayExhibitsTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.newButton), withText("New Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("e"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.search_button), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.searchItem), withText("Crocodiles"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.searchItem), withText("Capuchin Monkeys"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        1),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.searchItem), withText("Blue Capped Motmot"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        2),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.plan_button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.plan_item_text), withText("Blue Capped Motmot, Orangutan Trail, 7400 ft"),
                        withParent(withParent(withId(R.id.orderedVisits))),
                        isDisplayed()));
        textView.check(matches(withText("Blue Capped Motmot, Orangutan Trail, 7400 ft")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.plan_item_text), withText("Crocodiles, Hippo Trail, 12700 ft"),
                        withParent(withParent(withId(R.id.orderedVisits))),
                        isDisplayed()));
        textView2.check(matches(withText("Crocodiles, Hippo Trail, 12700 ft")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.plan_item_text), withText("Capuchin Monkeys, Monkey Trail, 16500 ft"),
                        withParent(withParent(withId(R.id.orderedVisits))),
                        isDisplayed()));
        textView3.check(matches(withText("Capuchin Monkeys, Monkey Trail, 16500 ft")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.plan_item_text), withText("Entrance and Exit Gate, Gate Path, 24900 ft"),
                        withParent(withParent(withId(R.id.orderedVisits))),
                        isDisplayed()));
        textView4.check(matches(withText("Entrance and Exit Gate, Gate Path, 24900 ft")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.routePlanSummaryTitle), withText("Route Plan"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView5.check(matches(withText("Route Plan")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
