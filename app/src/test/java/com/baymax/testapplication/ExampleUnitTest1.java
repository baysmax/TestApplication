package com.baymax.testapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.fonts.Font;
import android.os.Environment;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest1 {

    @Test
    public void addition_isCorrect() {
        File file = new File("C:\\Users\\lenovo\\Desktop\\jk\\a.jpg");

        //这边直接用canvas画，然后保存

        Paint paint = new Paint();
        ColorMatrix matrix = new ColorMatrix(new float[]{
                1.25f,0,0,0,0,
                0,1.25f,0,0,0,
                0,0,1.25f,0,0,
                0,0,0,1f,0,
        });

        paint.setColorFilter(new ColorMatrixColorFilter(matrix));

        Bitmap bitmap = BitmapFactory.decodeFile("C:\\Users\\lenovo\\Desktop\\jk\\a.jpg",getBitmapOption(2));

        Canvas canvas = new Canvas(bitmap);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap,null,rect, paint);

        canvas.save(); //保存
        canvas.restore(); // 存储

        saveImage(bitmap);


    }


    private BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }
    private void saveImage(Bitmap bitmap) {
        String save_path = "C:\\Users\\lenovo\\Desktop\\jk1\\"+System.currentTimeMillis()+".jpg";
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(save_path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}