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
    //NUEVO ALSO
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

        //DATABASE
        DBGameBuilder dbGameBuilder = new DBGameBuilder(this);
        insertInitialScore(dbGameBuilder);
        manageDatabase(dbGameBuilder);
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

    private void insertInitialScore(DBGameBuilder dbGameBuilder) {
        SQLiteDatabase db = dbGameBuilder.getWritableDatabase();
        Log.i("MainActivity", "Database ON: " + db.isOpen());
        ContentValues values = new ContentValues();
        values.put(DBStructure.DataEntry.COLUMN_SCORE, 0); // puntaje inicial
        long newRowId = db.insert(DBStructure.DataEntry.TABLE_NAME, null, values);
        Log.i("MainActivity", "New Row ID: " + newRowId);
        db.close();
    }

    private void manageDatabase(DBGameBuilder dbGameBuilder) {
        SQLiteDatabase db = dbGameBuilder.getReadableDatabase();
        String[] projectionScore = {
                DBStructure.DataEntry._ID,
                DBStructure.DataEntry.COLUMN_SCORE,
                DBStructure.DataEntry.COLUMN_TIMESTAMP
        };

        Cursor cursor = db.query(
                DBStructure.DataEntry.TABLE_NAME,
                projectionScore,
                null, null, null, null,
                DBStructure.DataEntry.COLUMN_TIMESTAMP
        );
        try {
            scoreList.clear(); //Clear the list before adding anything
            while (cursor.moveToNext()) {
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(DBStructure.DataEntry.COLUMN_SCORE));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(DBStructure.DataEntry.COLUMN_TIMESTAMP));
                String playTime = "N/A";

                ScoreItem scoreItem = new ScoreItem(score, timestamp, playTime);
                scoreList.add(scoreItem);
            }
                ScoreAdapter adapter = new ScoreAdapter(scoreList);
                scRecyclerView.setAdapter(adapter);
        } finally {
            cursor.close();
            db.close();
        }
    }
}