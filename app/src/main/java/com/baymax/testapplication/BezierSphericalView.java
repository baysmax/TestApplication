package com.baymax.testapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.Nullable;

/**
 * @Created by  Baymax on 2021/6/9.
 * @e-mail 761241917@qq.com
 */
public class BezierSphericalView extends View {
    /**
     * 中心点
     */
    private float centerX,centerY;
    /**
     * 圆环半径
     */
    private int radius=300;

    private int mStrokeWidth=10;
    /**
     * 控制点所在圆环半径
     */
    private int flagRadius= (int) (radius*1.17);
    private Paint mPaint;
    /**
     * 0 开始点坐标
     * 1 结束点坐标
     * 2 控制点坐标
     */
    private PointF[][] pointFS;
    /**
     * 贝塞尔路径
     */
    private Path[] mPath;
    /**
     * 初始位置
     */
    float[] startPosition ;
    Handler handler=new Handler();
    Runnable[] runnables;
    /**
     * 贝塞尔曲线的个数
     */
    private int bezierCount=12;

    private void calculationPosition(ValueAnimator animation,float startPosition,PointF[] pointFS,int flag) {
        float v = startPosition +(float)animation.getAnimatedValue();
        pointFS[0].x=(float) (radius*Math.sin(v)+centerX);
        pointFS[0].y=(float) (radius*Math.cos(v)+centerY);

        pointFS[1].x=(float) (radius*Math.sin(v+Math.PI/flag)+centerX);
        pointFS[1].y=(float) (radius*Math.cos(v+Math.PI/flag)+centerY);

        pointFS[2].x=(float) (flagRadius*Math.sin(v+Math.PI/(flag*2))+centerX);
        pointFS[2].y=(float) (flagRadius*Math.cos(v+Math.PI/(flag*2))+centerY);
    }

    public BezierSphericalView(Context context) {
        super(context);
        init();
    }



    public BezierSphericalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierSphericalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX= getWidth() >> 1;
        centerY= getHeight() >> 1;

        for (int i = 0; i < mPath.length; i++) {
            mPath[i]=new Path();
        }

        for (int i = 0; i < pointFS.length; i++) {
            for (int j = 0; j < pointFS[i].length; j++) {
                pointFS[i][j]=new PointF();
            }
        }

        startPosition= new float[mPath.length];

        for (int i = 0; i < startPosition.length; i++) {
            startPosition[i]= (float) (2*Math.PI-2*Math.PI*i/startPosition.length);
        }

        for (int i = 0; i < runnables.length; i++) {
            int finalI = i;
            runnables[i]= () -> {
                ValueAnimator animator = ValueAnimator.ofFloat(0,(float)(2*Math.PI-2*Math.PI*finalI/startPosition.length),0);
                animator.setDuration(3000*(4+finalI));
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.addUpdateListener(animation -> {
                    calculationPosition(animation,startPosition[finalI],pointFS[finalI],4);
                    postInvalidate();
                });
                animator.setInterpolator((Interpolator) input -> (float) (finalI%2==0?((Math.sin((input + 1) * Math.PI) / 2.0f) + 0.5f):((Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f)));
                animator.start();
            };
        }
        for (Runnable r : runnables) {
            handler.postDelayed(r,1000);
        }
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPath=new Path[bezierCount];
        pointFS=new PointF[mPath.length][3];
        runnables=new Runnable[mPath.length];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawColor(Color.WHITE);

        mPaint.setAlpha(100);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX,centerY,radius,mPaint);

        for (int i = 0; i < pointFS.length; i++) {
            mPaint.setColor(0xaaF98584-(i*0x10));
            drawPath(canvas,pointFS[i],mPaint,mPath[i]);
        }

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(centerX,centerY,radius-mStrokeWidth>>1,mPaint);



    }

    private void drawPath(Canvas canvas,PointF[] pointFS,Paint mPaint,Path mPath) {
        mPath.reset();
        mPath.moveTo(pointFS[0].x,pointFS[0].y);
        mPath.quadTo(pointFS[2].x,pointFS[2].y,pointFS[1].x,pointFS[1].y);
//        mPath.cubicTo(pointFS[2].x,pointFS[2].y,pointFS[3].x,pointFS[3].y,pointFS[1].x,pointFS[1].y);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }
}
