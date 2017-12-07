package ch.epfl.sweng.qeeqbii.slider;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.MenuItem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.qeeqbii.R;
import ch.epfl.sweng.qeeqbii.activities.BarcodeScannerActivity;
import ch.epfl.sweng.qeeqbii.activities.CancerDataQueryActivity;
import ch.epfl.sweng.qeeqbii.activities.CancerDataShowActivity;
import ch.epfl.sweng.qeeqbii.activities.GraphsActivity;
import ch.epfl.sweng.qeeqbii.activities.MainActivity;
import ch.epfl.sweng.qeeqbii.activities.ShoppingListActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by antoine on 07/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class CancerDataSliderTest {

    @Rule
    public final ActivityTestRule<CancerDataShowActivity> mActivityRule =
            new ActivityTestRule<>(CancerDataShowActivity.class);

    @Test
    public void testBackToMain() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

        // open current activity.
        CancerDataShowActivity myActivity = mActivityRule.getActivity();
        final MenuItem button = (MenuItem) myActivity.findViewById(R.id.nav_main);
        myActivity.backToMain(button);

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        MainActivity nextActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testCancerDataBaseShow() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CancerDataShowActivity.class.getName(), null, false);

        // open current activity.
        CancerDataShowActivity myActivity = mActivityRule.getActivity();
        final MenuItem button = (MenuItem) myActivity.findViewById(R.id.nav_cancerdb);
        myActivity.cancerDataBaseShow(button);

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        CancerDataShowActivity nextActivity = (CancerDataShowActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }


    @Test
    public void testOpenGraphs() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(GraphsActivity.class.getName(), null, false);

        // open current activity.
        CancerDataShowActivity myActivity = mActivityRule.getActivity();
        final MenuItem button = (MenuItem) myActivity.findViewById(R.id.nav_graphs);
        myActivity.showGraphs(button);

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        GraphsActivity nextActivity = (GraphsActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testCancerDataQuery() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CancerDataQueryActivity.class.getName(), null, false);

        // open current activity.
        CancerDataShowActivity myActivity = mActivityRule.getActivity();
        final MenuItem button = (MenuItem) myActivity.findViewById(R.id.nav_dataquery);
        myActivity.cancerDataQuery(button);

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        CancerDataQueryActivity nextActivity = (CancerDataQueryActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testCanOpenQRCodesScanner() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(BarcodeScannerActivity.class.getName(), null, false);

        // open current activity.
        CancerDataShowActivity myActivity = mActivityRule.getActivity();
        final MenuItem button = (MenuItem) myActivity.findViewById(R.id.nav_scan);
        myActivity.readBarcode(button);

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        BarcodeScannerActivity nextActivity = (BarcodeScannerActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testShoppingCart() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ShoppingListActivity.class.getName(), null, false);

        // open current activity.
        CancerDataShowActivity myActivity = mActivityRule.getActivity();
        final MenuItem button = (MenuItem) myActivity.findViewById(R.id.nav_shopping_cart);
        myActivity.showShoppingList(button);

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        ShoppingListActivity nextActivity = (ShoppingListActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

}