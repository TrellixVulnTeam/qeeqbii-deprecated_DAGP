package ch.epfl.sweng.qeeqbii.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;

import java.util.Arrays;

import ch.epfl.sweng.qeeqbii.R;
import ch.epfl.sweng.qeeqbii.RecyclerViewAdapter;
import ch.epfl.sweng.qeeqbii.cancer.LevenshteinQueryCancerDB;
import ch.epfl.sweng.qeeqbii.custom_exceptions.ProductException;
import ch.epfl.sweng.qeeqbii.open_food.ClusterType;
import ch.epfl.sweng.qeeqbii.open_food.ClusterTypeFirstLevel;
import ch.epfl.sweng.qeeqbii.open_food.Product;
import ch.epfl.sweng.qeeqbii.shopping_cart.ClusterProductList;

/**
 * Created by guillaume on 10/10/17.
 * Activity that prints product details relative to the barcode entered
 * in the barcode text field in the MainActivity.
 */

public class ShowProductActivity extends AppCompatActivity {

    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_details);

        Intent intent = getIntent();
        mProduct = (Product) intent.getSerializableExtra("product");

        final TextView txt = (TextView) findViewById(R.id.product_details);
        txt.setTextSize(20);
        txt.setTextColor(Color.rgb(0, 0, 0));
        txt.setText(mProduct.toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // go back to main activity after BACK button was pressed
            //Intent intent = new Intent(this, MainActivity.class);
            //startActivity(intent);
            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    // Search for harmful ingredients contained in the product, making a query to the Cancer database.
    public void searchHarmfulIngredients(View view) {

        TextView txt_harmfull_ing = (TextView) findViewById(R.id.harmful_ingredients);

        String parsed_ingredients[];

        try {
            parsed_ingredients = mProduct.getParsedIngredients();
        } catch (ProductException e) {
            txt_harmfull_ing.setText(e.getMessage());
            return;
        }


        String str = "";
        LevenshteinQueryCancerDB levQuery = new LevenshteinQueryCancerDB();
        try {
            for (String ingredient : parsed_ingredients) {
                str += levQuery.query(ingredient).toString() + "\n";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        txt_harmfull_ing.setText(str);

    }

    // Call the Graphs activity to make generate plots from the nutrients of the product.
    public void showNutrientsGraphs(View view) {
        Intent intent = new Intent(this, GraphsActivity.class);
        intent.putExtra("product", mProduct);
        startActivity(intent);
    }

    public void buyProduct(View view)
    {
        ShoppingListActivity.addProduct(mProduct);
    }
}