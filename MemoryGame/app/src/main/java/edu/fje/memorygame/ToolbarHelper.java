package edu.fje.memorygame;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ToolbarHelper {

    private final Activity activity;

    public ToolbarHelper(Activity activity) {
        this.activity = activity;
    }

    public void setupToolbar(Toolbar toolbar) {
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            if (((AppCompatActivity) activity).getSupportActionBar() != null) {
                ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public boolean handleOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_instructions) {
            Toast.makeText(activity, "Instructions clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean handleCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

