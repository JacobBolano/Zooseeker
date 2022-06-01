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
import androidx.test.filters.LargeTest;
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
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void capuchinMonkeysDirectionDetailsTest() {
        ViewInteraction newPlan = onView(
                allOf(withId(R.id.newButton), withText("New Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        newPlan.perform(click());

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
                allOf(withId(R.id.searchItem), withText("Capuchin Monkeys"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.plan_button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.directionsButton), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Gate Path for 1100.0m to Front Street / Treetops Way"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed along Gate Path for 1100.0m to Front Street / Treetops Way")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Front Street for 2700.0m to Front Street / Monkey Trail"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView2.check(matches(withText("Proceed along Front Street for 2700.0m to Front Street / Monkey Trail")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Monkey Trail for 4600.0m to Capuchin Monkeys"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView3.check(matches(withText("Proceed along Monkey Trail for 4600.0m to Capuchin Monkeys")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.directionDetails), withText("Capuchin Monkeys"),
                        withParent(allOf(withId(R.id.directionList),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        textView4.check(matches(withText("Capuchin Monkeys")));
    }

    @Test
    public void nextExhibitTest() {
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
        appCompatEditText.perform(replaceText("c"), closeSoftKeyboard());

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
                allOf(withId(R.id.searchItem), withText("Capuchin Monkeys"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        1),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.searchItem), withText("Crocodiles"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.plan_button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.directionsButton), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
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

        ViewInteraction textView = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Monkey Trail for 2300.0m to Monkey Trail / Hippo Trail"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed along Monkey Trail for 2300.0m to Monkey Trail / Hippo Trail")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Hippo Trail for 1500.0m to Crocodiles"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView2.check(matches(withText("Proceed along Hippo Trail for 1500.0m to Crocodiles")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.directionDetails), withText("Crocodiles"),
                        withParent(allOf(withId(R.id.directionList),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        textView3.check(matches(withText("Crocodiles")));
    }

    @Test
    public void exitDirectionsTest() {
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
        appCompatEditText.perform(replaceText("r"), closeSoftKeyboard());

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
                allOf(withId(R.id.searchItem), withText("Gorillas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        1),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.plan_button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.directionsButton), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
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

        ViewInteraction textView = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Monkey Trail for 2400.0m to Monkey Trail / Hippo Trail"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed along Monkey Trail for 2400.0m to Monkey Trail / Hippo Trail")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Hippo Trail for 4500.0m to Treetops Way / Hippo Trail"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView2.check(matches(withText("Proceed along Hippo Trail for 4500.0m to Treetops Way / Hippo Trail")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Treetops Way for 4400.0m to Front Street / Treetops Way"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView3.check(matches(withText("Proceed along Treetops Way for 4400.0m to Front Street / Treetops Way")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.individualDirectionDetail), withText("Proceed along Gate Path for 1100.0m to Entrance and Exit Gate"),
                        withParent(withParent(withId(R.id.direction_lists))),
                        isDisplayed()));
        textView4.check(matches(withText("Proceed along Gate Path for 1100.0m to Entrance and Exit Gate")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.directionDetails), withText("Entrance and Exit Gate"),
                        withParent(allOf(withId(R.id.directionList),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        textView5.check(matches(withText("Entrance and Exit Gate")));
    }

    @Test
    public void lotsOfPlacesTest() {
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

        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.searchItem), withText("Fern Canyon"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        3),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction materialTextView5 = onView(
                allOf(withId(R.id.searchItem), withText("Emerald Dove"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_id),
                                        4),
                                0),
                        isDisplayed()));
        materialTextView5.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.plan_button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.directionsButton), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
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

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton9.perform(click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.nextButton), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.directionList),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton10.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.routePlanSummaryTitle), withText("Route Plan"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Route Plan")));
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
