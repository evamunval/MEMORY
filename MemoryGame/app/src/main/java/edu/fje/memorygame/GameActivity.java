package edu.fje.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GameActivity extends AppCompatActivity {
    private static final Map<Integer, Integer> imageIdToServerId = new HashMap<>();
    static {
        imageIdToServerId.put(R.drawable.img1, 1);
        imageIdToServerId.put(R.drawable.img2, 2);
        imageIdToServerId.put(R.drawable.img3, 3);
        imageIdToServerId.put(R.drawable.img4, 4);
        imageIdToServerId.put(R.drawable.img5, 5);
        imageIdToServerId.put(R.drawable.img6, 6);
        imageIdToServerId.put(R.drawable.img7, 7);
        imageIdToServerId.put(R.drawable.img8, 8);
        imageIdToServerId.put(R.drawable.img9, 9);
        imageIdToServerId.put(R.drawable.img10, 10);
    }

    private Chronometer chronometer;
    private ToolbarHelper toolbarHelper;
    private int scoreCount = 0;
    private int attemps = 10;
    private int currentDelay = 3000;
    private static final String URL = "http://192.168.1.68:8001/?num_imatge=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        StrictMode.ThreadPolicy politiques = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(politiques);

        setContentView(R.layout.activity_game);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        toolbarHelper = new ToolbarHelper(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarHelper.setupToolbar(toolbar);

        final int[] arrayImg = {
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
                R.drawable.img5,
                R.drawable.img6,
                R.drawable.img7,
                R.drawable.img8,
                R.drawable.img9,
                R.drawable.img10
        };
        updateScore();
        updateAttemps();
        generateOptions(arrayImg);
    }

    private void generateOptions(final int[] arrayImg) {
        final int[] optIds = {R.id.opt1, R.id.opt2, R.id.opt3};
        final List<Integer> remainingImg = new ArrayList<>();
        final Random random = new Random();

        for (int img : arrayImg) {
            remainingImg.add(img);
        }

        ImageView prev = findViewById(R.id.preview);
        int newRandomInt = random.nextInt(arrayImg.length);
        int previewImageRes = arrayImg[newRandomInt];
        prev.setImageResource(previewImageRes);
        prev.setTag(arrayImg[newRandomInt]);
        prev.setVisibility(View.VISIBLE);

        int correctOptionIndex = random.nextInt(optIds.length);
        ImageView correctOption = findViewById(optIds[correctOptionIndex]);
        correctOption.setImageResource(previewImageRes);
        correctOption.setTag(previewImageRes);

        remainingImg.remove((Integer) previewImageRes);
        new Handler().postDelayed(()-> prev.setVisibility(View.INVISIBLE), currentDelay);

        for (int i = 0; i < optIds.length; i++) {
            ImageView imageView = findViewById(optIds[i]);
            if(i == correctOptionIndex){
                imageView.setOnClickListener(v -> handleOptionClick(v, arrayImg, optIds));
                continue;
            }
            int randomIndex = random.nextInt(remainingImg.size());
            int imgRes = remainingImg.remove(randomIndex);

            imageView.setImageResource(imgRes);
            imageView.setTag(imgRes);
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

            int previewImgId = (int) findViewById(R.id.preview).getTag();
            Integer serverImgId = imageIdToServerId.get(previewImgId);
            if (serverImgId != null) {
                try {
                    URL urlObj = new URL(URL + serverImgId);
                    String jsonResponse = llegirJSON(urlObj);
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    String pista = jsonObject.getString("pista");
                    showToast(pista);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }else {
                Log.e("GameActivity", "No se pudo encontrar el ID del servidor para el recurso: " + previewImgId);
            }

            if(attemps <= 0){
                chronometer.stop();
                long elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();

                Intent gameOverIntent = new Intent(this, GameOverActivity.class);
                gameOverIntent.putExtra("SCORE", scoreCount);
                gameOverIntent.putExtra("ELAPSED_TIME", elapsedTime);
                startActivity(gameOverIntent);
                finish();
                return;
            }
        }
        updateScore();
        updateDelay();

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

    private void updateDelay(){
        if(scoreCount % 50 == 0){
            currentDelay -= 250;
        }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toolbarHelper.handleOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public String llegirJSON(URL urlObj) throws IOException {
        StringBuilder builder = new StringBuilder();
        HttpURLConnection clientHTTP = (HttpURLConnection) urlObj.openConnection();
        try {
            if (clientHTTP.getResponseCode() == 200) {
                InputStream contingut = clientHTTP.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(contingut));
                String linia;
                while ((linia = reader.readLine()) != null) {
                    builder.append(linia);
                }
            } else {
                Log.e("DAM2", "Problemes HTTP");
            }
            return builder.toString();
        }finally {
            if(clientHTTP != null){
                clientHTTP.disconnect();
            }
        }
    }
}

