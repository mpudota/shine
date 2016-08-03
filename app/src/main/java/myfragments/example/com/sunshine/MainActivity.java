package myfragments.example.com.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity {



    String TAG ="Shine";
    MenuItem x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new Forecast()).commit();
        }
    }

    }


