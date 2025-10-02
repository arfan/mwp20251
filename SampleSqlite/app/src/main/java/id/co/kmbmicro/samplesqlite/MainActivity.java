package id.co.kmbmicro.samplesqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

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

        SQLiteDatabase myDatabase = openOrCreateDatabase("sample", MODE_PRIVATE, null);
        myDatabase.execSQL("create table if not exists employee(username varchar, password varchar)");
        myDatabase.execSQL("insert into employee values('admin', 'admin')");

        Cursor resultSet = myDatabase.rawQuery("select * from employee", null);
        resultSet.moveToFirst();
        String username = resultSet.getString(0);
        String password = resultSet.getString(1);

        Log.i("WMP20251", "username="+username);
        Log.i("WMP20251", "password="+password);

        Log.i("WMP20251", "getColumnCount:"+resultSet.getColumnCount());
        Log.i("WMP20251", "getColumnIndex:"+resultSet.getColumnIndex("password"));
        Log.i("WMP20251", "getColumnName:"+resultSet.getColumnName(0));
        Log.i("WMP20251", "getColumnNames:"+ Arrays.toString(resultSet.getColumnNames()));
        Log.i("WMP20251", "getCount:"+ resultSet.getCount());
    }
}