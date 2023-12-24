package com.example.mykurs;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Context context;
    private Cursor cursor;

    public FavoriteAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        long id = cursor.getLong(cursor.getColumnIndex(TranslationDatabaseHelper.COLUMN_ID));
        String query = cursor.getString(cursor.getColumnIndex(TranslationDatabaseHelper.COLUMN_QUERY));
        String translation = cursor.getString(cursor.getColumnIndex(TranslationDatabaseHelper.COLUMN_TRANSLATION));

        holder.textQuery.setText(query);
        holder.textTranslation.setText(translation);

        // Обработчик для кнопки удаления
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Вызываем метод удаления при нажатии на кнопку удаления
                deleteItem(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }

        cursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    // Метод для удаления элемента из базы данных
    private void deleteItem(long id) {
        TranslationDatabaseHelper dbHelper = new TranslationDatabaseHelper(context);
        dbHelper.getWritableDatabase().delete(
                TranslationDatabaseHelper.TABLE_TRANSLATIONS,
                TranslationDatabaseHelper.COLUMN_ID + "=" + id,
                null);

        // Обновляем список после удаления
        Cursor newCursor = dbHelper.getReadableDatabase().query(
                TranslationDatabaseHelper.TABLE_TRANSLATIONS,
                new String[]{TranslationDatabaseHelper.COLUMN_ID, TranslationDatabaseHelper.COLUMN_QUERY, TranslationDatabaseHelper.COLUMN_TRANSLATION},
                null,
                null,
                null,
                null,
                null
        );

        swapCursor(newCursor);
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView textQuery, textTranslation;
        ImageButton btnDelete;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            textQuery = itemView.findViewById(R.id.textQuery);
            textTranslation = itemView.findViewById(R.id.textTranslation);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
