package ch.epfl.sweng.qeeqbii;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;



@RunWith(AndroidJUnit4.class)
public class TwitterTest {
    @Rule
    public final IntentsTestRule<TwitterLoginActivity> mActivityRule =
            new IntentsTestRule<>(TwitterLoginActivity.class);

    @Test
    public void useAppContext() throws Exception {
        TwitterLoginActivity activity = mActivityRule.getActivity();
        Thread.sleep(200);
        Espresso.closeSoftKeyboard();
        Thread.sleep(200);
        onView(withId(R.id.button_twitter_login)).perform(click());
    }
}
/*
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
@LargeTest
@RunWith(AndroidJUnit4.class)
public class TwitterTest {
    private IdlingResource mActivityResource;
    @Rule
    public ActivityTestRule<AnonymousAuthActivity> mActivityTestRule =
            new ActivityTestRule<>(AnonymousAuthActivity.class);
    @Test
    public void TwitterSignInTest_() {
        // Sign out if possible
        signOutIfPossible();
        //Continue activity
        SaveIfPossible();
        // Click sign in
        //onView(allOf(withId(R.id.button_facebook_login), isDisplayed())).perform(click());
    }
    private void signOutIfPossible() {
        try {
            onView(allOf(withId(R.id.button_facebook_signout), isDisplayed()))
                    .perform(click());
        } catch (NoMatchingViewException e) {
            // Ignore
        }
    }
    /*private void signInIfPossible() {
        try {
            onView(allOf(withId(R.id.button_twitter_login), isDisplayed()))
                    .perform(click());
        } catch (NoMatchingViewException e) {
            // Ignore
        }
    }*/

   /* private void SaveIfPossible() {
        try {
            onView(allOf(withId(R.id.buttonSave), isDisplayed()))
                    .perform(click());
        } catch (NoMatchingViewException e) {
            // Ignore
        }
    }
}*/