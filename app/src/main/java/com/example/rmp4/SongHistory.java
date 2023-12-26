package com.example.rmp4;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
public class SongHistory extends AppCompatActivity {

    ListView userList;
    TextView header;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_history);

        dbHandler = new DatabaseHandler(getApplicationContext());

        header = findViewById(R.id.header);
        userList = findViewById(R.id.list);
    }

    @Override
    public void onResume() {
        super.onResume();
        db = dbHandler.getReadableDatabase();

        userCursor =  db.rawQuery("SELECT * FROM "+ DatabaseHandler.TABLE, null);
        String[] headers = new String[] {DatabaseHandler.COLUMN_NAME, DatabaseHandler.COLUMN_MUSICIAN};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        header.setText("Прослушанные песни: " +  userCursor.getCount());
        userList.setAdapter(userAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        userCursor.close();
    }
}