package ch.epfl.sweng.qeeqbii;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by guillaume on 14/11/17.
 * Test for all the SavedProducts pipeline.
 */

@RunWith(AndroidJUnit4.class)
public class SavedProductsDatabaseTest {

    @Rule
    public final ActivityTestRule<SavedProductsDatesActivity> mActivityRule =
            new ActivityTestRule<>(SavedProductsDatesActivity.class);

    @Before
    public void loadTestDatabase() {
        Intent intent = new Intent(mActivityRule.getActivity(), SavedProductsDatesActivity.class);
        intent.putExtra("test",R.raw.saved_products_database_test);
        mActivityRule.launchActivity(intent);
    }

    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    @Test
    public void loadDatabaseTest()
    {
        try
        {
            Date[] dates = SavedProductsDatabase.getDates();
            assertEquals(formatter.parse("13/11/2017"),dates[0]);

        } catch( Exception e)
        {
            fail(e.getMessage());
        }

    }

    @Test
    public void getProductTest()
    {
        try
        {
            Product[] products = SavedProductsDatabase.getProductsFromDate(formatter.parse("13/11/2017"));

            assertEquals(2,products.length);
            assertEquals("Tartiflette", products[0].getName());
            assertEquals("Rebloch, Rebloch, Rebloch et Reblochh", products[1].getIngredients());

        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void addProductTest()
    {
        Product product = new Product("Raclette", "1000g", "Cheese," +
                " Cheese ,Cheese", "Sel: 0.200g", "00055232323", ClusterType.CHEESE);

        Product product2 = new Product("Fondue", "1500g", "Cheese," +
                " Cheese ,Cheese", "Sel: 0.300g", "00055237323", ClusterType.CHEESE);

        try
        {
            SavedProductsDatabase.addProduct(product);
            SavedProductsDatabase.addProduct(product2);
            Date[] dates = SavedProductsDatabase.getDates();
            Date today_date = formatter.parse(formatter.format(Calendar.getInstance().getTime()));
            assertEquals(today_date, dates[dates.length-1]);

            Product entered_product = SavedProductsDatabase.getProductsFromDate(today_date)[0];

            assertEquals(product.getName(),entered_product.getName());
            assertEquals(product.getIngredients(),entered_product.getIngredients());
            assertEquals(product.getNutrients(),entered_product.getNutrients());
            assertEquals(product.getQuantity(),entered_product.getQuantity());
            assertEquals(product.getBarcode(),entered_product.getBarcode());
            assertEquals(product.getCluster(),entered_product.getCluster());

            Product entered_product2 = SavedProductsDatabase.getProductsFromDate(today_date)[1];

            assertEquals(product2.getName(),entered_product2.getName());


        } catch (Exception e)
        {
            fail(e.getMessage());
        }


    }

    @Test
    public void showProduct()
    {
        try
        {
            onView(withText(formatter.parse("13/11/2017").toString())).perform(click());
            onView(withText("Tartiflette")).perform(click());
            Product product = SavedProductsDatabase.getProductsFromDate(formatter.parse("13/11/2017"))[0];
            onView(withText(product.toString())).check(matches(isDisplayed()));

        } catch (Exception e)
        {
            fail(e.getMessage());
        }

    }


}
