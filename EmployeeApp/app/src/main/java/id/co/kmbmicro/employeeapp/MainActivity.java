package id.co.kmbmicro.employeeapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnAdd, btnView;
    EditText editTextName, editTextEmail;

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

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        btnAdd.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();

            if (name.length() <= 0 || email.length() <= 0) {
                Toast.makeText(MainActivity.this, "Enter data", Toast.LENGTH_SHORT).show();
            } else {
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                EmployeeModel employeeModel = new EmployeeModel(name, email);
                dbHelper.addEmployee(employeeModel);
                Toast.makeText(MainActivity.this, "Add Successful", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        });

        btnView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ViewEmployee.class);
            startActivity(intent);
        });

//        SQLiteDatabase myDb = openOrCreateDatabase("employee_db", MODE_PRIVATE, null);
//
//        Cursor cursor = myDb.rawQuery("select * from employee", null);
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                String name = cursor.getString(1);
//                String email = cursor.getString(2);
//
//                Log.d("DB_RESULT", "ID: " + id + ", Name: " + name + " Email: "+email);
//            } while (cursor.moveToNext());
//        }

//        DBHelper dbHelper = new DBHelper(MainActivity.this);
//        List<EmployeeModel> employeeModelList = dbHelper.getEmployeeList();
//        for(EmployeeModel em: employeeModelList) {
//            Log.d("DB_RESULT", "ID: " + em.id + ", Name: " + em.name + " Email: "+em.email);
//        }
    }
}