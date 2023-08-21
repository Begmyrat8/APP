package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText user_field;
    TextView W;
    Button btnW;
    ImageButton button;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        user_field = findViewById(R.id.edit);
        W = findViewById(R.id.W);
        btnW = findViewById(R.id.btnW);
        button = findViewById(R.id.button);


        btnW.setOnClickListener(v -> {
            if (user_field.getText().toString().trim().equals("")) {
                Toast.makeText(MainActivity.this, R.string.city, Toast.LENGTH_SHORT).show();
            } else {
                String city = user_field.getText().toString();
                String key = "ba7ed180275dd7923a7f4b1315f69735";
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=metric&lang=ru";

                new GetUrlData().execute(url);
            }
        });
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, Dictionary.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main4, menu);
        return true;
    }



    @SuppressLint("StaticFieldLeak")
    private class GetUrlData extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            W.setText("Ожидайте...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();

                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                W.setText("" + jsonObject.getJSONObject("main").getDouble("temp"));
            } catch (Exception e) {
                e.printStackTrace();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Ошибка")
                        .setMessage("Нет подключения к интернету или вы ввели город не правилно.")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> dialog.cancel());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    public void GoCalculatorActivity(View view) {
        Intent intent = new Intent(this, Calculator.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}