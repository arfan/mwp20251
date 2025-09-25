package com.example.week3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button aboutButton, contactButton;
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

        aboutButton = findViewById(R.id.aboutButtonLayout);
        contactButton = findViewById(R.id.contactButtonLayout);

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAbout = new Intent(getApplicationContext(), About.class);
                startActivities(new Intent[]{openAbout});
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openContact = new Intent(getApplicationContext(), ContactActivity.class);
                startActivities(new Intent[]{openContact});
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("WMP", "Main activity call onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("WMP", "Main activity call onPause");
    }
}