package com.example.shapes;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DrawTolerance extends AppCompatActivity {

    private Tolerance tolerance;
    private TextView txtMatch;
    private TextView txtMiss;
    private Button btnClear;
    private Button btnComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tolerance);
        tolerance = findViewById(R.id.tolerance);
        btnClear = findViewById(R.id.btnClear);
        btnComp = findViewById(R.id.btnComp);
        txtMatch = findViewById(R.id.txtMatch);
        txtMiss  = findViewById(R.id.txtMiss);
        txtMatch.setTextSize(20);
        txtMiss.setTextSize(20);
        txtMatch.setText("Zhoda: ");
        txtMiss.setText("Odchylka: ");
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tolerance.clear();
                txtMatch.setText("Zhoda: ");
                txtMiss.setText("Odchylka: ");
            }
        });
        btnComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tolerance.evaluateImageMatch();

                float precis = (float)Math.round(tolerance.precision * 100);
                //if (precis > 100){ precis = 100;}
                txtMatch.setText("Zhoda: " + precis + "%");
                txtMiss.setText("Odchylka: " + (float)Math.round(tolerance.missRatio * 100) + "%");
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        tolerance.init(metrics);
        Spiral();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.spiral:
                Spiral();
                return true;
            case R.id.sinus:
                Sinus();
                return true;
            case R.id.back:
                Intent intent = new Intent(DrawTolerance.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

// Spirala
    public void Spiral() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float x = width / 2, y = height / 3, angle;
        int i;
        float a = 20;
        float b = 20;
        int maxSteps = 190;
        int offset = height / 3;
        int offset2 = width / 2;

        tolerance.touchStartSpiral(x, y);
        for (i = 0; i <= maxSteps; i = i + 1) {
            angle = (float) (0.1 * i);
            x = (float) (offset2 + (a + b * angle) * Math.cos(angle));
            y = (float) (offset + (a + b * angle) * Math.sin(angle));
            tolerance.touchMoveSpiral(x, y);
        }
        tolerance.touchUpSpiral();
    }
    public void Sinus() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        int X = width/5;
        int Y = height/3;
        tolerance.touchStartSpiral(X, Y);
        for (double x = X; x <= width*3/4; x = x + 0.5) {
            double y = 200 * Math.sin(x * (Math.PI / 270));
            Y = height / 3 - (int) y;
            X = (int) x;
            tolerance.touchMoveSpiral(X, Y);
        }
        tolerance.touchUpSpiral();
    }
}
