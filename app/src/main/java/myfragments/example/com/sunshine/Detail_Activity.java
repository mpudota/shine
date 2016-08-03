package myfragments.example.com.sunshine;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by mpudota on 8/2/16.
 */
public class Detail_Activity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceHolderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        getMenuInflater().inflate(R.menu.detail,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.action_setting_detail) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
