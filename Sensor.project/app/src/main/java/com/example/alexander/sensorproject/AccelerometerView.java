package com.example.alexander.sensorproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

/**
 * Created by Alexander on 5/18/2016.
 */
public class AccelerometerView extends View {


    float x,y,z,m;
    ArrayList<float[]> SensorData;
    ArrayList<Float> Magnitude;
    public int points = 64;
    float magnitude=0.0f;

    Paint paint;

    public AccelerometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        SensorData = new ArrayList<float[]>();
        Magnitude  = new ArrayList<>();

    }


     public void addSensorData(float x, float yy, float z)
    {

        float[] DataXYZ = {x, yy,z};


        if ( SensorData.size() == points) {
            SensorData.remove(0);
           Magnitude.remove(0);
        }
        magnitude= (float)Math.sqrt(Math.pow(x,2)+Math.pow(yy,2)+Math.pow(z,2));
        SensorData.add(DataXYZ);
        Magnitude.add(magnitude);
        invalidate();
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);
        int i;
        int width = getWidth();
        int height = getHeight();
        paint.setColor(Color.RED);

        for (int k = 0; k < 4; ++k) {
            switch (k){
                case 0:
                    paint.setColor(Color.RED);
                    break;
                case 1:
                    paint.setColor(Color.GREEN);
                   break;
                case 2:
                    paint.setColor(Color.BLUE);
                    break;
                case 3:
                    paint.setColor(Color.WHITE);
                    break;
            }

            if(k<3) {
                for (i = 0; i < SensorData.size() - 1; ++i) {
                    float PosX1 = i * width / points;
                    float PosX2 = (i + 1) * width / points;
                    float PosY1 = (SensorData.get(i)[k] - (-30.0f)) / 60.0f;
                    float PosY2 = (SensorData.get(i + 1)[k] - (-30.0f)) / 60.0f;
                    canvas.drawLine(PosX1, height * (1.0f - PosY1), PosX2, height * (1.0f - PosY2), paint);
                }
            }
            else
                for (i = 0; i < Magnitude.size() - 1; ++i) {
                    float PosX1 = i * width / points;
                    float PosX2 = (i + 1) * width / points;
                    float PosY1 = ((Magnitude.get(i) - (-30.0f)))/ 60.0f;
                    float PosY2 = ((Magnitude.get(i + 1) - (-30.0f)))/ 60.0f;
                    canvas.drawLine(PosX1, height * (1.0f - PosY1), PosX2, height * (1.0f - PosY2), paint);
                }
        }

        }
  }
