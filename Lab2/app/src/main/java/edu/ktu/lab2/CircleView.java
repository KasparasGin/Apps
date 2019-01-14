package edu.ktu.lab2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {

    String text = "";

    public CircleView(Context context) {super(context);}

    public CircleView(Context context, AttributeSet attrs) {super(context, attrs);}

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text){
        this.text = text;
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint paint = new Paint();

        float width = getWidth();

        float textWidth = paint.measureText(text);
        System.out.println(textWidth);

        paint.setColor(Color.WHITE);
        paint.setTextSize(300);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, (width-textWidth)/2f, 350, paint);
    }
}
