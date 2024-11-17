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

        Intent intent = getIntent();
        int score = getIntent().getIntExtra("SCORE",0);

        //Initialize id's
        Button replayButton = findViewById(R.id.btReplay);
        TextView timeTextView = findViewById(R.id.timeTextView);
        TextView scoreTextView = findViewById(R.id.finalScore);
        scoreTextView.setText(String.valueOf(score));

        // Receive elapsed time
        long elapsedTime = getIntent().getLongExtra("ELAPSED_TIME", 0);
        String formattedTime = formatTime(elapsedTime);

        // Set time text (optional)
        if (timeTextView !=  null) {
            timeTextView.setText("Tiempo: " + formattedTime);
        } else {
            Log.w("GameOverActivity", "Time TextView not found. Add a TextView with ID 'timeTextView'");
        }

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameIntent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(gameIntent);
                finish();
            }
        });
    }

    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        return String.format("%02d:%02d", minutes, seconds);
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