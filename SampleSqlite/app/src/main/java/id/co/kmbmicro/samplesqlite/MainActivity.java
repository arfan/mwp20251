package id.co.kmbmicro.samplesqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText name;
    TextView age;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String dbName = "sample.sqlite";
        String outPath = getDatabasePath(dbName).getAbsolutePath();

        SQLiteDatabase db = SQLiteDatabase.openDatabase(outPath, null, SQLiteDatabase.OPEN_READONLY);

        Cursor cursor = db.rawQuery("SELECT * FROM employee", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                int age = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
                Log.d("DB", "Name: " + name + " - Age: " + age);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


//        SQLiteDatabase myDatabase = openOrCreateDatabase("sample", MODE_PRIVATE, null);
//        myDatabase.execSQL("create table if not exists employee(username varchar, password varchar)");
//        myDatabase.execSQL("insert into employee values('admin', 'admin')");
//
//        Cursor resultSet = myDatabase.rawQuery("select * from employee", null);
//        resultSet.moveToFirst();
//        String username = resultSet.getString(0);
//        String password = resultSet.getString(1);
//
//        Log.i("WMP20251", "username="+username);
//        Log.i("WMP20251", "password="+password);
//
//        Log.i("WMP20251", "getColumnCount:"+resultSet.getColumnCount());
//        Log.i("WMP20251", "getColumnIndex:"+resultSet.getColumnIndex("password"));
//        Log.i("WMP20251", "getColumnName:"+resultSet.getColumnName(0));
//        Log.i("WMP20251", "getColumnNames:"+ Arrays.toString(resultSet.getColumnNames()));
//        Log.i("WMP20251", "getCount:"+ resultSet.getCount());

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbName = "sample.sqlite";
                String outPath = getDatabasePath(dbName).getAbsolutePath();
                SQLiteDatabase db = SQLiteDatabase.openDatabase(outPath, null, SQLiteDatabase.OPEN_READONLY);

                String nameStr = name.getText().toString();
                Cursor cursor = db.rawQuery("SELECT * FROM EMPLOYEE WHERE name='"+nameStr+"'", null);

                if(cursor.getCount() >= 1) {
                    cursor.moveToFirst();
                    String ageStr = Integer.toString(cursor.getInt(1));
                    age.setText(ageStr);
                } else {
                    age.setText("not found");
                }
            }
        });
    }
}