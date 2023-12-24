package com.example.mykurs;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteActivity extends AppCompatActivity {

    private TranslationDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.parseColor("#000000"));

        dbHelper = new TranslationDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewFavorites);

        setupRecyclerView();
        loadFavorites();

        // Обработчики для кнопок Home, Favorite и Settings
        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnFavorite = findViewById(R.id.btnFavorite);
        ImageButton btnSettings = findViewById(R.id.btnSettings);

        btnHome.setOnClickListener(view -> {
            Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnFavorite.setOnClickListener(view -> {
            // Действие, когда пользователь уже находится в разделе "Избранное"
            // Можете добавить свой код, если необходимо
        });

        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(FavoriteActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoriteAdapter = new FavoriteAdapter(this, getFavorites());
        recyclerView.setAdapter(favoriteAdapter);
    }

    private Cursor getFavorites() {
        // Получаем данные из базы данных
        return dbHelper.getReadableDatabase().query(
                TranslationDatabaseHelper.TABLE_TRANSLATIONS,
                new String[]{TranslationDatabaseHelper.COLUMN_ID, TranslationDatabaseHelper.COLUMN_QUERY, TranslationDatabaseHelper.COLUMN_TRANSLATION},
                null,
                null,
                null,
                null,
                null
        );
    }

    private void loadFavorites() {
        Cursor cursor = getFavorites();
        favoriteAdapter.swapCursor(cursor);
    }
}
