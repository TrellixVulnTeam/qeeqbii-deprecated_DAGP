package ch.epfl.sweng.qeeqbii;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * Created by guillaume on 16.10.17.
 *
 */

@RunWith(AndroidJUnit4.class)
public class OpenFoodQueryTest {

    private String string_nutrients = "Sel: 0.0g\nProtéines: 0.5g\nFibres alimentaires: 1.5g\nSucres: 15.0g\n" +
            "Glucides: 15.0g\nAcides gras saturées: 0.0g\nMatières grasses: 0.0g\nÉnergie (kCal): 67.0kCal\nÉnergie: 280.0kJ\n";

    private String string_ingredients = "mangue (Thaïlande), eau, sucre, acidifiant (E330)";

    private String string_name = "Mangue : en tranches";

    private String string_quantity = "245.0g";

    @Rule
    public final ActivityTestRule<BarcodeToProductActivity> mActivityRule =
            new ActivityTestRule<>(BarcodeToProductActivity.class);

    @Test
    public void QueryOfExistingProduct() {
        final CountDownLatch signal = new CountDownLatch(1);
        String[] barcode = new String[1];
        barcode[0] = "7610848337010";

        new OpenFoodQuery() {
            public void onPostExecute(Product product) {

                try {
                    assertEquals(product.GetQuantity(), string_quantity);
                    assertEquals(product.GetName(), string_name);
                    assertEquals(product.GetIngredients(), string_ingredients);
                    assertEquals(product.GetNutrients(), string_nutrients);
                    Map<String, Double> parsed_nutrients = product.GetParsedNutrients();

                    //Set<Map.Entry<String,Double>> set = parsed_nutrients.entrySet();
                    //Iterator<Map.Entry<String,Double>> it = set.iterator();
                    //Map.Entry<String,Double> e = it.next();
                    //assertEquals(e.getValue(),new Double(0.0));

                    assertEquals(parsed_nutrients.get("Sel (g)"), Double.valueOf(0.0));
                    assertEquals(parsed_nutrients.get("Énergie (kCal)"), Double.valueOf(67.0));
                    assertEquals(parsed_nutrients.get("Énergie (kJ)"), Double.valueOf(280.0));

                } catch (Exception e) {

                    fail(e.getMessage());
                } finally {
                    signal.countDown();
                }


            }
        }.execute(barcode);

        try {
            signal.await(); //wait for callback
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void QueryOfNonExistingProduct() {
        {
            final CountDownLatch signal = new CountDownLatch(1);
            String[] barcode = new String[1];
            barcode[0] = "";

            final String bc = barcode[0];

            new OpenFoodQuery() {
                public void onPostExecute(Product product) {
                    assertEquals(null,product);

                    assertEquals(OpenFoodQuery.error_cache.get(bc), "ERROR: (openfood) Barcode not found in the database.");

                    signal.countDown();

                }
            }.execute(barcode);

            try {
                signal.await(); //wait for callback
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }


    }

    @Test
    public void CacheQueryTest()
    {
        String barcode = "7610848337010";
        try {
            Product product = OpenFoodQuery.GetOrCreateProduct(barcode);
            assertEquals(product.GetQuantity(), string_quantity);
            assertEquals(product.GetName(), string_name);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        if (OpenFoodQuery.isCached(barcode))
        {
            try {
                Product product = OpenFoodQuery.get(barcode);
                assertEquals(product.GetIngredients(), string_ingredients);
                assertEquals(product.GetNutrients(), string_nutrients);
            } catch (Exception e) {
                fail(e.getMessage());
            }
        } else {
            fail("barcode should be cached");
        }
    }

}
