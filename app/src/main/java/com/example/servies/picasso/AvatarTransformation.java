package com.example.servies.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.media.ExifInterface;

import com.squareup.picasso.Transformation;

public class AvatarTransformation implements Transformation {

    public AvatarTransformation() {}

    @Override
    public Bitmap transform(Bitmap source) {
        final Paint paint = new Paint();
//        final Matrix matrix = new Matrix();

        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//        matrix.setRotate(90);

//        final Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        final Bitmap bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(source.getWidth() / 2, source.getHeight() / 2, source.getWidth() / 2, paint);

        if (source != bitmap)
            source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "avatar";
    }
}
