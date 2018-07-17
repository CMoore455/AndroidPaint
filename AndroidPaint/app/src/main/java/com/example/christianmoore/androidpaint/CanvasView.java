package com.example.christianmoore.androidpaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.BoringLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

// Code from https://examples.javacodegeeks.com/android/core/graphics/canvas-graphics/android-canvas-example/

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private boolean makeCircle = false;
    private Canvas mCanvas;
    private Path mPath;;
    private Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private float brushSize = 10f;
    private Paint.Style style;
    private int color = Color.RED;
    private boolean addCircle = false;

    private ArrayList<Paint> paints = new ArrayList<>();
    private ArrayList<Path> paths = new ArrayList<>();


    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        // we set a new Path
        mPath = new Path();
        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        style = Paint.Style.STROKE;
        mPaint.setStyle(style);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(brushSize);

    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw
        for(int i = 0; i < paints.size(); i++){
            canvas.drawPath(paths.get(i), paints.get(i));
        }

    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void undo(){
        if (paints.size() > 0) {
            paints.remove(paints.size()-1);
            paths.remove(paths.size()-1);
            artificialTouch();
        }
    }

    public void clearCanvas() {
        paths.clear();
        paints.clear();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);
        resetPaint();
    }

    public void ChangeBrushSize(float f){
        mPaint.setStrokeWidth(f);
        setBrushSize(f);
    }

    public void ChangeBrushColor(int color){
        mPaint.setAntiAlias(true);
        mPaint.setStyle(style);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.color = color;
        mPaint.setColor(color);
    }

    public void ChangePathEffect(String effect){
        if(effect.equals("dashed")){
            float[] f = new float[]{6f, 3f};
            DashPathEffect dashPathEffect = new DashPathEffect(f, 10f);
            mPaint.setPathEffect(dashPathEffect);

        }
        else{
            mPaint.setPathEffect(null);
        }
    }


    public void fillStroke(boolean fill)
    {
        if(fill){
            mPaint.setStyle(Paint.Style.FILL);
        }
        else{
            mPaint.setStyle(Paint.Style.STROKE);
        }
    }

    public void resetPaint(){
        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getColor());
        mPaint.setStyle(getStyle());
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(getBrushSize());
    }
    //override the onTouchEvent

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();

                if (makeCircle) {
                    mX = x;
                    mY = y;
                    mPath.addCircle(mX, mY, 120f, Path.Direction.CCW);
                    makeCircle = false;
                }

                startTouch(x, y);
                paints.add(mPaint);
                paths.add(mPath);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }

    private void artificialTouch() {
       //mPath = new Path();
        startTouch(mX, mY);
        //paints.add(mPaint);
        //paths.add(mPath);
        moveTouch(mX, mY);
        upTouch();
        invalidate();
    }

    public void createCircle(){
        makeCircle = true;
    }

    public float getBrushSize() {
        return brushSize;
    }

    public void setBrushSize(float brushSize) {
        this.brushSize = brushSize;
        mPaint.setStrokeWidth(brushSize);

    }

    public Paint.Style getStyle() {
        return style;
    }

    public void setStyle(Paint.Style style) {
        this.style = style;
        mPaint.setStyle(this.style);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(this.color);
    }
}