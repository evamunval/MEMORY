package edu.fje.memorygame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private long startTime;
    private ToolbarHelper toolbarHelper;
    private int scoreCount = 0;
    private int attemps = 10;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        startTime = System.currentTimeMillis();

        toolbarHelper = new ToolbarHelper(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarHelper.setupToolbar(toolbar);

        final int[] arrayImg = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
        updateScore();
        updateAttemps();
        updatePreview(arrayImg);
        generateOptions(arrayImg);
    }

    private void generateOptions(final int[] arrayImg) {
        final int[] optIds = {R.id.opt1, R.id.opt2, R.id.opt3};
        final List<Integer> remainingImg = new ArrayList<>();
        final Random random = new Random();

        for (int img : arrayImg) {
            remainingImg.add(img);
        }

        for (int i = 0; i < optIds.length; i++) {
            int randomIndex = random.nextInt(remainingImg.size());
            int imgRes = remainingImg.remove(randomIndex);

            ImageView imageView = findViewById(optIds[i]);
            imageView.setImageResource(imgRes);
            imageView.setTag(imgRes);
            imageView.invalidate();

            imageView.setOnClickListener(v -> handleOptionClick(v, arrayImg, optIds));
        }
    }

    private void handleOptionClick(View v, final int[] arrayImg, final int[] optIds) {
        setOptionsEnabled(optIds, false);

        boolean correct = v.getTag().equals(findViewById(R.id.preview).getTag());
        if (correct) {
            scoreCount += 10;
        } else {
            scoreCount -= 5;
            attemps--;
            updateAttemps();

            if(attemps <= 8){
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;

                Intent gameOverIntent = new Intent(this, GameOverActivity.class);
                gameOverIntent.putExtra("SCORE", scoreCount);
                startActivity(gameOverIntent);
                finish();
                return;
            }
        }
        updateScore();

        updatePreview(arrayImg);
        new Handler().postDelayed(()->{
        generateOptions(arrayImg);
        setOptionsEnabled(optIds, true);
        }, 3000);
    }

    private void setOptionsEnabled(int[] optIds, boolean enabled){
        for (int id:optIds) {
        findViewById(id).setEnabled(enabled);
    }
}

    private void updatePreview(int[] arrayImg){
        ImageView prev = findViewById(R.id.preview);
        Random random = new Random();
        int newRandomInt = random.nextInt(arrayImg.length);
        prev.setImageResource(arrayImg[newRandomInt]);
        prev.setTag(arrayImg[newRandomInt]);
        prev.setVisibility(View.VISIBLE);

        new Handler().postDelayed(()-> prev.setVisibility(View.INVISIBLE), 3000);
    }

    private void updateScore() {
        TextView scoreView = findViewById(R.id.scoreText);
        scoreView.setText(getString(R.string.score, scoreCount));
    }

    private void updateAttemps() {
        TextView attempsView = findViewById(R.id.attemps);
        attempsView.setText(getString(R.string.attemps, attemps));
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

    private void insertScore(int score, DBGameBuilder dbGameBuilder) {
        SQLiteDatabase db = dbGameBuilder.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBStructure.DataEntry.COLUMN_SCORE, score);
        long newRowId = db.insert(DBStructure.DataEntry.TABLE_NAME, null, values);
        Log.i("MainActivity", "New Row Id inserted: " + newRowId);
        db.close();
    }
}