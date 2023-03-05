package com.example.shapes;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Draw extends AppCompatActivity {

    private PaintView paintView;
    private Button btnClear;
    private Button btnComp;
    private TextView txtMatch;
    private TextView txtMiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        paintView = findViewById(R.id.paintView);
        btnClear = findViewById(R.id.btnClear);
        Button btnComp = findViewById(R.id.btnComp);
        txtMatch = findViewById(R.id.txtMatch);
        txtMiss  = findViewById(R.id.txtMiss);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.clear();
                paintView.clearSpiral();
            }
        });
        btnComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.evaluateImageMatch();
                txtMatch.setTextSize(20);
                txtMiss.setTextSize(20);
                txtMatch.setText("Zhoda: " + (float)Math.round(paintView.precision * 100) + "%");
                txtMiss.setText("Odchylka: " + (float)Math.round(paintView.missRatio * 100) + "%");
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        Spiral();
    }

    // Menu
    @Override
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Sinus() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        int X = 0;
        int Y = height;
        paintView.touchStart(X, Y);
        for (double x = 0; x <= width; x = x + 0.5) {
            double y = 200 * Math.sin(x * (Math.PI / 270));
            Y = height / 3 - (int) y;
            X = (int) x;
            paintView.touchMove(X, Y);
        }
        paintView.touchUp();
    }

    public void Spiral() {          // vykresli spiralu
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float x = width / 2, y = height / 2, angle;
        int i;
        float a = 20;
        float b = 20;
        int maxSteps = 190;
        int offset = height / 2;
        int offset2 = width / 2;

        paintView.touchStartSpiral(x, y);
        for (i = 0; i <= maxSteps; i = i + 1) {
            angle = (float) (0.1 * i);
            x = (float) (offset2 + (a + b * angle) * Math.cos(angle));
            y = (float) (offset + (a + b * angle) * Math.sin(angle));
            paintView.touchMoveSpiral(x, y);
        }
        paintView.touchUpSpiral();
    }
}