package com.example.shapes;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Draw extends AppCompatActivity {

    private PaintView paintView;
    private Button btnClear;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        paintView = findViewById(R.id.paintView);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.clear();
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        Spiral();
    }

    /*public void Sinus() {
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
    }*/

    public void Spiral() {          // vykresli spiralu
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