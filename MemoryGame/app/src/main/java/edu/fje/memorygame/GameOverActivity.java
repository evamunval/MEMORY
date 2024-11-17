package edu.fje.memorygame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GameOverActivity extends AppCompatActivity {
    private ToolbarHelper toolbarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_over);

        toolbarHelper = new ToolbarHelper(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarHelper.setupToolbar(toolbar);

        //DATABASE
        int score = getIntent().getIntExtra("SCORE",0);
        TextView scoreTextView = findViewById(R.id.finalScore);
        scoreTextView.setText(String.valueOf(score));

        int scoreToInsert = Integer.parseInt(scoreTextView.getText().toString());

        try {
            DBGameBuilder dbHelper = new DBGameBuilder(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DBStructure.DataEntry.COLUMN_SCORE, scoreToInsert);
            long newRowId = db.insert(DBStructure.DataEntry.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("GameOverActivity", "Error inserting score: " + e.getMessage());
        }

        //Initialize id's
        Button replayButton = findViewById(R.id.btReplay);

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameIntent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(gameIntent);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return toolbarHelper.handleCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toolbarHelper.handleOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}