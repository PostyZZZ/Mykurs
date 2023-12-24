package com.example.mykurs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.parseColor("#000000"));

        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnFavorite = findViewById(R.id.btnFavorite);
        ImageButton btnSettings = findViewById(R.id.btnSettings);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Обработка нажатия на кнопку Home
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Обработка нажатия на кнопку Favorite
                Intent intent = new Intent(SettingsActivity.this, FavoriteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Обработка нажатия на кнопку Settings
                // Можете добавить код для перехода к другим активити
            }
        });

        // Можете добавить дополнительную логику для ActivitySettings
    }
}
