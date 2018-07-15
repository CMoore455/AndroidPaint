package com.example.christianmoore.androidpaint;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();

    }

    public void btnBrushSizeClicked(View view) {
        if(view.getId() == R.id.SizeUp){
            customCanvas.increaseBrushSize();
        }
        else{
            customCanvas.decreaseBrushSize();
        }
    }

    public void saveSettingsClicked(View view) {
        RadioGroup rg = findViewById(R.id.ColorBrushRG);
        int rdId = rg.getCheckedRadioButtonId();
        RadioButton rd = findViewById(rdId);
        ColorStateList lst = rd.getTextColors();

        if (lst != null) {
            customCanvas.ChangeBrushColor(lst.getDefaultColor());
        }
        LinearLayout l = findViewById(R.id.SettingsMenu);
        l.setVisibility(View.INVISIBLE);

    }

    public void openSettings(View view) {
        LinearLayout l = (LinearLayout) findViewById(R.id.SettingsMenu);
        l.setVisibility(View.VISIBLE);
    }
}
