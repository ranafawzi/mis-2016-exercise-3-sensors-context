package com.example.alexander.sensorproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by rana on 5/19/2016.
 */
public class FFTView extends View{

    private FFT Fft;
    public int points = 64;
    double magnitude;
    ArrayList<Double> Magnitude;
    double[] ima;
    double[] real;
    Double[] Magnitude1;
    double [] FFTMag;
    int size,i;
    Paint paint;

    public FFTView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        Fft =new FFT(points);
        Magnitude  = new ArrayList<>(points);
        ima = new double[points];
        real= new double [points];
        FFTMag = new double [points];

    }

    public void addSensorData(float x, float yy, float z){
      ;
        if ( Magnitude.size() == points) {

            Magnitude.remove(0);
        }

        magnitude= Math.sqrt(Math.pow(x,2)+Math.pow(yy,2)+Math.pow(z,2));
        Magnitude.add(magnitude);
        invalidate();

    }



    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        paint.setStrokeWidth(3);
        paint.setColor(Color.MAGENTA);

        Magnitude1 = Magnitude.toArray(new Double[Magnitude.size()]);
        for(int i=0; i < Magnitude.size() ;i++)
        {
            ima[i]=0.0d;
            Magnitude1[i]= Magnitude.get(i);
            real[i] = Magnitude1[i];
        }
         Fft.fft(real,ima);
        for(int i=0; i < Magnitude.size();i++){

            FFTMag[i] = Math.sqrt(Math.pow(real[i], 2) + Math.pow(ima[i], 2));
        }
        for (i = 0; i < (Magnitude.size()/2) - 1; ++i) {
            float PosX1 = i * width / (points/2);
            float PosX2 = (i + 1) * width /(points/2);
            float PosY1 = (float)((FFTMag[i] - (-30.0f)))/ 60.0f;
            float PosY2 = (float)((FFTMag[i+1] - (-30.0f)))/ 60.0f;
            canvas.drawLine(PosX1, height * (1.0f - PosY1), PosX2, height * (1.0f - PosY2), paint);
        }





}
}