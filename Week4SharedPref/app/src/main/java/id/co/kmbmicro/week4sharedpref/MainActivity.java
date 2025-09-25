package id.co.kmbmicro.week4sharedpref;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button plus, minus;
    int number;

    TextView textView;

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

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        number = sharedPreferences.getInt("number", 0);

        Log.d("WEEK4", "number="+number);

        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        textView = findViewById(R.id.textView);
        textView.setText(""+number);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = number + 1;
                storeNumber(number);
                textView.setText(""+number);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = number - 1;
                storeNumber(number);
                textView.setText(""+number);
            }
        });
    }

    private void storeNumber(int number) {
        Log.d("WEEK4", "Storing the value into shared preferences "+number);
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();

        editor.putInt("number", number);

        editor.apply();
    }
}