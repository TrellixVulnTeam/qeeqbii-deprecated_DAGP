package ch.epfl.sweng.qeeqbii;

/**
 * Created by sergei on 11/30/17.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.widget.ListView;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import ch.epfl.sweng.qeeqbii.activities.BarcodeScannerActivity;
import ch.epfl.sweng.qeeqbii.activities.ProductComparisonActivity;
import ch.epfl.sweng.qeeqbii.open_food.Product;
import ch.epfl.sweng.qeeqbii.open_food.RecentlyScannedProducts;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.VerificationModes.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.qeeqbii.clustering.ClusterTypeFirstLevel.CHOCOLAT;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductComparisonActivityTest {

    @Rule
    public final IntentsTestRule<ProductComparisonActivity> mActivityRule =
            new IntentsTestRule<>(ProductComparisonActivity.class);

    @BeforeClass
    public static void clearProducts() {
        RecentlyScannedProducts.clear();
    }

    @AfterClass
    public static void finish_all_activities() {
        ActivityFinisher.finishOpenActivities();
    }

    @Test
    public void t01_testCanShowInsufficientProducts() {
        onView(withId(R.id.product_name_1)).check(matches(withText("Name 1")));
        onView(withId(R.id.product_name_2)).check(matches(withText("Name 2")));
        ListView ls = (ListView) mActivityRule.getActivity().findViewById(R.id.graphs);
        assertTrue(ls.getCount() == 0);
    }

    public Activity getActivityInstance(final String className) {
        final Activity[] currentActivity = new Activity[1];

        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                ActivityLifecycleMonitor activityLifecycleMonitor = ActivityLifecycleMonitorRegistry.getInstance();

                for (final Stage stage : EnumSet.of(Stage.CREATED, Stage.RESUMED, Stage.STARTED)) {
                    final Collection<Activity> activities_in_stage = activityLifecycleMonitor.getActivitiesInStage(stage);
                    for (final Activity activity : activities_in_stage) {
                        if (Objects.equals(activity.getClass().getName(), className)) {
                            currentActivity[0] = activity;
                        }
                    }
                }
            }
        });

        return currentActivity[0];
    }

    public BarcodeScannerActivity getCurrentScanner() {
        return ((BarcodeScannerActivity) getActivityInstance(BarcodeScannerActivity.class.getName()));
    }

    @Test
    public void t02_testCanPressScan() {
        onView(withId(R.id.scan_button)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), BarcodeScannerActivity.class)), times(1));
        getCurrentScanner().finish();
    }

    @Test
    public void t03_testCanCompareProducts() throws Throwable {
        RecentlyScannedProducts.add("12346", new Product("Cba", "0.1g", "Sel (g) 200\nXXX 600", "Sel (g) 200\nXXX 700", "12346", CHOCOLAT));
        RecentlyScannedProducts.add("12345", new Product("Aba", "0.1g", "Sel (g) 100\nXXX 500", "Sel (g) 100\nXXX 500", "12345", CHOCOLAT));

        final CountDownLatch latch = new CountDownLatch(1);
        mActivityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityRule.getActivity().updateData();
                latch.countDown();
            }
        });
        latch.await();

        // checking if the view displays names and charts
        onView(withId(R.id.product_name_1)).check(matches(withText(startsWith("Aba"))));
        onView(withId(R.id.product_name_2)).check(matches(withText(startsWith("Cba"))));
        ListView ls = (ListView) mActivityRule.getActivity().findViewById(R.id.graphs);

        // check if button is visible
        onView(withId(R.id.scan_button)).check(matches(isDisplayed()));

        //assertTrue(ls.getAdapter().getCount() > 0);
    }
}