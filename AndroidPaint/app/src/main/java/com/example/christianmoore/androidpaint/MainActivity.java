package com.example.christianmoore.androidpaint;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.SimpleDateFormat;
import android.opengl.Visibility;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CanvasView customCanvas;
    private Random rand = new Random();
    private float size = 10f;


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
        if (view.getId() == R.id.SizeUp) {
            size += 5f;
        } else {
            size -= 5f;
        }
    }

    public void saveSettingsClicked(View view) {
//        if (this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        } else {
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
//        }
        RadioGroup rg = findViewById(R.id.ColorBrushRG);
        int rdId = rg.getCheckedRadioButtonId();
        RadioButton rd = findViewById(rdId);
        ColorStateList lst = rd.getTextColors();
        RadioGroup rg2 = findViewById(R.id.pathEffectRadioButtonGroup);

        if (rg2.getCheckedRadioButtonId() == R.id.dashedButton) {
            customCanvas.ChangePathEffect("dashed");
        } else {
            customCanvas.ChangePathEffect("normal");
        }
        customCanvas.ChangeBrushSize(size);

        if (lst != null) {
            customCanvas.ChangeBrushColor(lst.getDefaultColor());
        }
//        RadioButton fillRb = findViewById(R.id.fillButton);
//        if(fillRb.isChecked()){
//            customCanvas.fillStroke(true);
//        }
//        else{
//            customCanvas.fillStroke(false);
//        }
        setCanvasColor(view);

        LinearLayout l = findViewById(R.id.SettingsMenu);
        l.setVisibility(View.INVISIBLE);
    }

    private void setCanvasColor(View view) {
        RadioGroup rgCanvasColor = findViewById(R.id.ColorCanvasRG);
        int canvasColorId = rgCanvasColor.getCheckedRadioButtonId();
        RadioButton rBtn = findViewById(canvasColorId);

        customCanvas.setBackground(rBtn.getBackground());
    }

    public void openSettings(View view) {
        LinearLayout l = (LinearLayout) findViewById(R.id.SettingsMenu);
        l.setVisibility(View.VISIBLE);
    }

    public Bitmap getBitmap(CanvasView layout) {
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(layout.getDrawingCache());
        layout.setDrawingCacheEnabled(true);
        return bmp;
    }

    public void saveChart(Bitmap getbitmap, float height, float width) {
        try{
           FileOutputStream fos =  openFileOutput("NewImage.png", Context.MODE_PRIVATE);
           getbitmap.compress(Bitmap.CompressFormat.PNG,100, fos);
           fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
//        File folder = new File(Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myfolder");
//        boolean success = false;
//        if (!folder.exists()) {
//            success = folder.mkdirs();
//        }
//        File file = new File(folder.getPath() + File.separator + ".png");
//        if (!file.exists()) {
//            try {
//                Toast.makeText(this, folder.getPath(), Toast.LENGTH_SHORT).show();
//                success = file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        FileOutputStream ostream = null;
//        try {
//            ostream = new FileOutputStream(file);
//            System.out.println(ostream);
//            Bitmap well = getbitmap;
//            Bitmap save = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
//            Paint paint = new Paint();
//            paint.setColor(Color.WHITE);
//            Canvas now = new Canvas(save);
//            now.drawRect(new Rect(0, 0, (int) width, (int) height), paint);
//            now.drawBitmap(well,
//                    new Rect(0, 0, well.getWidth(), well.getHeight()),
//                    new Rect(0, 0, (int) width, (int) height), null);
//            if (save == null) {
//                System.out.println(";NULL bitmap save\n");
//            }
//            save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            //Toast.makeText(getApplicationContext(), ";Null error";, Toast.LENGTH_SHORT).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            // Toast.makeText(getApplicationContext(), ";File error";, Toast.LENGTH_SHORT).show();
//        }
    }

    public void saveImage(View view) {
        Bitmap bitmap = getBitmap(customCanvas);
        saveChart(bitmap, customCanvas.getMeasuredHeight(), customCanvas.getMeasuredWidth());

    }

    public void makeCircle(View view) {
        customCanvas.createCircle();
    }

    public void undo(View view) {
        customCanvas.undo();
    }

    public void randomizeColors(View view) {
        int paint = rand.nextInt(6);
        int canvas = rand.nextInt(3);
        switch (paint) {
            case 0:
                customCanvas.ChangeBrushColor(Color.RED);
                break;
            case 1:
                customCanvas.ChangeBrushColor(Color.BLUE);
                break;
            case 2:
                customCanvas.ChangeBrushColor(Color.GREEN);
                break;
            case 3:
                customCanvas.ChangeBrushColor(Color.YELLOW);
                break;
            case 4:
                customCanvas.ChangeBrushColor(Color.BLACK);
                break;
            case 5:
                customCanvas.ChangeBrushColor(Color.MAGENTA);
                break;
        }

        int color = 0;
        switch (canvas) {
            case 0:
               color = Color.RED;
                break;
            case 1:
                color = Color.BLUE;
                break;
            case 2:
                color = Color.GREEN;
                break;
        }

        customCanvas.setBackground(new ColorDrawable(color));
    }
}
