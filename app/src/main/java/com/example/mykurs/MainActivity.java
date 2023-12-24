package com.example.mykurs;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView translationResult;
    private Spinner languageSpinner;
    private Button saveButton;  //
    private TranslationDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveButton = findViewById(R.id.saveButton);  // Инициализируем кнопку "Сохранить"

        dbHelper = new TranslationDatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        // Добавляем обработчик нажатия кнопки "Сохранить"
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTranslation();
            }
        });


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.parseColor("#000000"));

        editText = findViewById(R.id.editText);
        translationResult = findViewById(R.id.translationResult);
        languageSpinner = findViewById(R.id.languageSpinner);

        // Заполнение выпадающего списка языковыми парами
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.language_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);



        Button translateButton = findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateText();
            }
        });

        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnFavorite = findViewById(R.id.btnFavorite);
        ImageButton btnSettings = findViewById(R.id.btnSettings);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Обработка нажатия на кнопку Home
                // Добавьте код для перехода к основному содержимому MainActivity
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Обработка нажатия на кнопку Favorite
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Обработка нажатия на кнопку Settings
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void translateText() {
        String textToTranslate = editText.getText().toString();
        String selectedLanguagePair = languageSpinner.getSelectedItem().toString();
        new TranslateTask().execute(textToTranslate, selectedLanguagePair);
    }
    private void saveTranslation() {
        String query = editText.getText().toString();
        String translation = translationResult.getText().toString();

        // Добавим данные в базу данных
        if (insertData(query, translation)) {
            Toast.makeText(this, "Перевод сохранен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ошибка при сохранении перевода", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean insertData(String query, String translation) {
        ContentValues values = new ContentValues();
        values.put(TranslationDatabaseHelper.COLUMN_QUERY, query);
        values.put(TranslationDatabaseHelper.COLUMN_TRANSLATION, translation);

        try {
            long result = database.insert(TranslationDatabaseHelper.TABLE_TRANSLATIONS, null, values);
            return result != -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }


    private class TranslateTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String textToTranslate = params[0];
            String apiUrl = "https://api.mymemory.translated.net/get?q=";
            String languagePair = params[1]; // Используем выбранную языковую пару

            try {
                String encodedText = URLEncoder.encode(textToTranslate, "UTF-8");
                URL url = new URL(apiUrl + encodedText + "&langpair=" + languagePair);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String translatedText = jsonObject.getJSONObject("responseData").getString("translatedText");
                    translationResult.setText(translatedText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}