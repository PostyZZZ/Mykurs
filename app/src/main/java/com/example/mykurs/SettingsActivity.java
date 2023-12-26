package com.example.mykurs;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
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
        Button btnFeedback = findViewById(R.id.feedbackButton); // Добавленная кнопка

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFeedbackDialog();
            }
        });
    }

    private void showFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Обратная связь");
        builder.setMessage("Оставьте свой отзыв:");

        final EditText feedbackEditText = new EditText(this);
        builder.setView(feedbackEditText);

        builder.setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String feedback = feedbackEditText.getText().toString();
// Здесь можно отправить обратную связь на указанную почту или как-то её обработать.
// Например, использовать Intent для отправки электронного письма.

// Пример использования Intent:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"адрес_почты@example.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Обратная связь");
                emailIntent.putExtra(Intent.EXTRA_TEXT, feedback);
                startActivity(Intent.createChooser(emailIntent, "Отправить по почте"));
            }
        });

        builder.setNegativeButton("Отмена", null);

        builder.show();
    }
}