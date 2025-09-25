package id.co.kmbmicro.nameandhobby;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button save, load;
    EditText name, hobby;

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

        save = findViewById(R.id.save);
        load = findViewById(R.id.load);
        name = findViewById(R.id.name);
        hobby = findViewById(R.id.hobby);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("Settings", MODE_PRIVATE);
                String nameSp = sp.getString("name", "");
                String hobbySp = sp.getString("hobby", "");
                name.setText(nameSp);
                hobby.setText(hobbySp);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
                String nameEditText = name.getText().toString();
                String hobbyEditText = hobby.getText().toString();
                editor.putString("name", nameEditText);
                editor.putString("hobby", hobbyEditText);
                editor.apply();
            }
        });
    }
}