package ch.epfl.sweng.qeeqbii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static ch.epfl.sweng.qeeqbii.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    public static final String BARCODE_READER = "ch.epfl.sweng.qeeqbii.mainBarcode";

    //private CancerDataBase cancer_data_base = new CancerDataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            CancerDataBase.readCSVFile(getApplicationContext());
        }
        catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        setContentView(activity_main);
    }

    @SuppressWarnings("UnusedParameters")
    public void cancerDataBaseShow(View view)
    {
        Intent intent = new Intent(this, CancerDataShowActivity.class);
        startActivity(intent);
    }

    public void readBarcode(View view) {
        Intent intent = new Intent(this, BarcodeActivity.class);
        startActivity(intent);
    }

    public void searchProductFromBarcode(View view) {
        Intent intent = new Intent(this, BarcodeToProductActivity.class);
        EditText editText = (EditText) findViewById(R.id.mainName);
        String barcode = editText.getText().toString();
        intent.putExtra(BARCODE_READER, barcode);
        startActivity(intent);
    }

    public void showGraphs(View view) {
        Intent intent = new Intent(this, Graphs.class);
        startActivity(intent);
    }

    public void cancerDataQuery(View view) {
        Intent intent = new Intent(this, CancerDataQueryActivity.class);

        startActivity(intent);
    }
}