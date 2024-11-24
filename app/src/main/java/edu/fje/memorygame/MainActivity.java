package edu.fje.memorygame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
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
        loadScores();
    }

    private void loadScores() {
        // Initialize database helper
        DBHelper dbUtil = new DBHelper(this);
        SQLiteDatabase db = dbUtil.getReadableDatabase();

        String[] projection = {
                "puntuacion", "fecha", "tiempo"
        };

        Cursor cursor = db.query(
                "puntuacion",
                projection,
                null, null, null, null,
                "fecha DESC"
        );

        scoreList.clear();

        while (cursor.moveToNext()) {
            int score = cursor.getInt(cursor.getColumnIndexOrThrow("puntuacion"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("tiempo"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
            scoreList.add(new ScoreItem(score, time, date));
        }
        cursor.close();

        // Set the adapter to RecyclerView
        ScoreAdapter adapter = new ScoreAdapter(scoreList);
        scRecyclerView.setAdapter(adapter);
    }

    //TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return toolbarHelper.handleCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
       if(toolbarHelper.handleOptionsItemSelected(item)) {
            return true;
       }else{
           return super.onOptionsItemSelected(item);
       }
    }

}
