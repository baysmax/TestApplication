package com.baymax.testapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Created by  Baymax on 2021/6/8.
 * @e-mail 761241917@qq.com
 */
public class DrawableView extends View {
    private int radius=20;
    private Paint mRadiusPaint=null;
    private int centerX,centerY;
    public DrawableView(Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX=w/2;
        centerY=h/2;
    }

    private void init(Context context) {
        mRadiusPaint=new Paint();
        mRadiusPaint.setAntiAlias(true);
        mRadiusPaint.setColor(Color.RED);
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mRadiusPaint.setColor(Color.argb(255,200,100,100));
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.a);
        Canvas canvas1 = new Canvas();
        canvas.drawBitmap(bitmap,null,new Rect(0,0,600,600), null);

//        canvas.translate(bitmap.getWidth(),bitmap.getHeight());
        //五阶矩阵，R、G为0，A、B为1，第五列为分量，不需要进行平移为0
        //曝光效果
//        ColorMatrix matrix = new ColorMatrix(new float[]{
//                -1,0,0,0,255,
//                0,-1,0,0,255,
//                0,0,-1,0,255,
//                0,0,0,1,0,
//        });
//        美白
        ColorMatrix matrix = new ColorMatrix(new float[]{
                1.25f,0,0,0,0,
                0,1.25f,0,0,0,
                0,0,1.25f,0,0,
                0,0,0,1f,0,
        });
        //黑白图片
//        ColorMatrix matrix = new ColorMatrix(new float[]{
//                0.213f, 0.715f,0.072f,0,0,
//                0.213f, 0.715f,0.072f,0,0,
//                0.213f, 0.715f,0.072f,0,0,
//                0,      0,      0,    1f,0,
//        });
        //发色效果（比如红色和绿色交换----把第一行和第二行交换）
//        ColorMatrix matrix = new ColorMatrix(new float[]{
//                0,1f,0,0,0,
//                1f,0,0,0,0,
//                0,0,1f,0,0,
//                0,0,0,1f,0,
//        });
        //复古风格
//        ColorMatrix matrix = new ColorMatrix(new float[]{
//                1/2f,1/2f,1/2f,0,0,
//                1/3f,1/3f,1/3f,0,0,
//                1/4f,1/4f,1/4f,0,0,
//                0,0,0,1f,0,
//        });
        //设置颜色过滤器
        mRadiusPaint.setColorFilter(new ColorMatrixColorFilter(matrix));

        canvas.drawBitmap(bitmap,null,new Rect(0,650,600,1200), mRadiusPaint);
    }
}
