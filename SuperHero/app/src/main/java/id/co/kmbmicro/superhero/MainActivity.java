package id.co.kmbmicro.superhero;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    Button buttonDc, buttonMarvel, buttonBumiLangit;

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

        buttonDc = findViewById(R.id.button_dc);
        buttonMarvel = findViewById(R.id.button_marvel);
        buttonBumiLangit = findViewById(R.id.button_bumilangit);

        // Load default fragment when activity starts
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, DcFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }

        buttonDc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, DcFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        buttonMarvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, MarvelFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        buttonBumiLangit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, BumiLangitFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });
    }
}