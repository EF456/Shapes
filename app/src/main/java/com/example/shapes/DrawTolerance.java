package com.example.shapes;

import android.os.Bundle;
import android.util.DisplayMetrics;
import androidx.appcompat.app.AppCompatActivity;

public class DrawTolerance extends AppCompatActivity {

    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tolerance);
        paintView = findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        Spiral();
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
