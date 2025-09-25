package com.example.week3sampleintent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    TextView productName;
    TextView productQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("product_name");
        String quantity = intent.getStringExtra("product_quantity");

        Log.d("DETAIL", "product name = "+name);
        Log.d("DETAIL", "product quantity = "+quantity);

        productName = findViewById(R.id.product_name);
        productQuantity = findViewById(R.id.product_quantity);

        productName.setText(name);
        productQuantity.setText(quantity);
    }
}