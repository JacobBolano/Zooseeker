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
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DirectionDetailsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void arcticFoxesDirectionDetailsTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("e"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_button), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.searchItem), withText("Arctic Foxes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Entrance Way for 10.0m to Entrance Plaza"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed along Entrance Way for 10.0m to Entrance Plaza")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Arctic Avenue for 300.0m to Arctic Foxes"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView2.check(matches(withText("Proceed along Arctic Avenue for 300.0m to Arctic Foxes")));
    }

    @Test
    public void nextExhibitDirectionsTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("e"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_button), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.searchItem), withText("Arctic Foxes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.searchItem), withText("Elephant Odyssey"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        1),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Arctic Avenue for 300.0m to Entrance Plaza"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed along Arctic Avenue for 300.0m to Entrance Plaza")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Reptile Road for 100.0m to Alligators"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView2.check(matches(withText("Proceed along Reptile Road for 100.0m to Alligators")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Sharp Teeth Shortcut for 200.0m to Lions"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView3.check(matches(withText("Proceed along Sharp Teeth Shortcut for 200.0m to Lions")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Africa Rocks Street for 200.0m to Elephant Odyssey"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView4.check(matches(withText("Proceed along Africa Rocks Street for 200.0m to Elephant Odyssey")));
    }

    @Test
    public void exitDirectionsTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("e"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_button), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.searchItem), withText("Arctic Foxes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.directionDetails), withText("Entrance and Exit Gate"),
                        withParent(allOf(withId(R.id.directionList),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        textView.check(matches(withText("Entrance and Exit Gate")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Arctic Avenue for 300.0m to Entrance Plaza"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView2.check(matches(withText("Proceed along Arctic Avenue for 300.0m to Entrance Plaza")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Entrance Way for 10.0m to Entrance and Exit Gate"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView3.check(matches(withText("Proceed along Entrance Way for 10.0m to Entrance and Exit Gate")));
    }

    @Test
    public void lotsOfPlacesTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_button), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.searchItem), withText("Arctic Foxes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.searchItem), withText("Elephant Odyssey"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        1),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.searchItem), withText("Gorillas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        2),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.searchItem), withText("Alligators"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        3),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton7.perform(click());
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
