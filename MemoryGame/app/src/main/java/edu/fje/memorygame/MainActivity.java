package edu.fje.memorygame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ToolbarHelper toolbarHelper;
    private RecyclerView scRecyclerView;
    private List <ScoreItem> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        toolbarHelper = new ToolbarHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarHelper.setupToolbar(toolbar);

        findViewById(R.id.btPlay).setOnClickListener(view ->  {
            startActivity(new Intent(MainActivity.this, GameActivity.class));
            finish();
        });

        // Initialize score list
        scRecyclerView = findViewById(R.id.recyclerView);
        scRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoreList = new ArrayList<>();
    }

    //TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return toolbarHelper.handleCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
       if(toolbarHelper.handleOptionsItemSelected(item)) {
            return true;
       }else{
           return super.onOptionsItemSelected(item);
       }
    }

}