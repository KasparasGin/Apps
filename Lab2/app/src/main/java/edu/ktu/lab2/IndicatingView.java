package edu.ktu.lab2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class IndicatingView extends View {
        public static final int NOTEXECUTED = 0;
        public static final int SUCCESS = 1;
        public static final int FAILED = 2;
        public static final int INPROGRESS = 3;

        int state = NOTEXECUTED;

    private Path mPath;

        public IndicatingView(Context context) {super(context);}

        public IndicatingView(Context context, AttributeSet attrs) {super(context, attrs);}

        public IndicatingView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public int getState(){
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            int width = getWidth();
            int height = getHeight();
            Paint paint;
            switch (state){
                case SUCCESS:
                    paint = new Paint();
                    paint.setColor(Color.GREEN);
                    paint.setStrokeWidth(20f);
                    canvas.drawLine(0,0, width/2, height, paint);
                    canvas.drawLine(width/2, height, width, height/2, paint);
                    break;

                case FAILED:
                    paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setStrokeWidth(20f);
                    canvas.drawLine(0,0, width, height, paint);
                    canvas.drawLine(0, height, width, 0, paint);
                    break;

                case INPROGRESS:
                    paint = new Paint();
                    paint.setColor(Color.WHITE);
                    paint.setStrokeWidth(20f);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setPathEffect(new DashPathEffect(new float[]{40, 40,}, 0));
                    mPath = new Path();
                    mPath.moveTo(width/2, 5);
                    mPath.quadTo(5, height-5, 5, height-5);
                    mPath.quadTo(width -5, height-5, width-5, height-5);
                    mPath.quadTo(width/2, 5, width/2, 5);
                    canvas.drawPath(mPath, paint);
                    break;

                default:
                    break;
            }
        }

}
