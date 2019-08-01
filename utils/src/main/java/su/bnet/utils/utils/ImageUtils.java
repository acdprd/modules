package su.bnet.utils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

public class ImageUtils {


    /**
     * @param url       - url to previewImage
     * @param imageView - imageView
     */
    public static void loadImage(String url, ImageView imageView) {
        if (url == null || url.length() == 0) {
            return;
        }
        Picasso.get()
                .load(url)
                .into(imageView);
    }

    public static void loadImage(Uri url, ImageView imageView, long size) {
        Picasso.get()
                .load(url)
                .resize((int) size, 0)
                .into(imageView);
    }

    /**
     * @param url       - url to previewImage
     * @param imageView - imageView
     */
    public static void loadImageCenterInside(String url, ImageView imageView, int width, int height) {
        if (url == null || url.length() == 0) {
            return;
        }
        Picasso.get()
                .load(url)
                .centerInside()
                .resize(width, height)
                .into(imageView);
    }

    /**
     * @param url         - url to previewImage
     * @param imageView   - imageView
     * @param placeholder - placeHolder
     */
    public static void loadImage(String url, ImageView imageView, Drawable placeholder) {
        if (url == null || url.length() == 0) {
            imageView.setImageDrawable(placeholder);
            return;
        }
        Picasso.get()
                .load(url)
                .placeholder(placeholder)
                .into(imageView);
    }

    /**
     * @param context     - context
     * @param url         - url to previewImage
     * @param imageView   - imageView
     * @param placeholder - placeHolder
     */
    public static void loadImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder) {
        if (url == null || url.length() == 0) {
            imageView.setImageDrawable(context.getResources().getDrawable(placeholder));
            return;
        }
        Picasso.get()
                .load(url)
                .placeholder(context.getResources().getDrawable(placeholder))
                .into(imageView);
    }

    /**
     * picasso debug
     */
    public static Picasso picassoDebug(Context context) {
        Picasso p = new Picasso.Builder(context).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();
        p.setIndicatorsEnabled(true);
        p.setLoggingEnabled(true);
        return p;
    }

    public static Bitmap getCroppedAndResizedBitmap(Bitmap bitmap, int viewWidth, int viewHeight) {
        Bitmap croppedBitmap = getCroppedBitmap(bitmap, viewWidth, viewHeight);
        int cropppedWidth = croppedBitmap.getWidth();
        if (cropppedWidth == viewWidth) {
            return croppedBitmap;
        }
        return getResizedBitmap(croppedBitmap, viewWidth, viewHeight);
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap, int viewWidth, int viewHeight) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        float originalRatio = (float) originalWidth / (float) originalHeight;
        float viewRatio = (float) viewWidth / (float) viewHeight;
        int startX = 0;
        int startY = 0;
        int targetWidth = 0;
        int targetHeight = 0;
        if (originalRatio == viewRatio) {
            return bitmap;
        } else if (originalRatio < viewRatio) {
            targetWidth = originalWidth;
            startX = 0;
            targetHeight = (int) ((float) targetWidth / viewRatio);
            startY = (originalHeight - targetHeight) / 2;
        } else {
            targetHeight = originalHeight;
            startY = 0;
            targetWidth = (int) (targetHeight * viewRatio);
            startX = (originalWidth - targetWidth) / 2;
        }
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, startX, startY, targetWidth, targetHeight);
        return croppedBitmap;
    }

    public static Bitmap createBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.TRANSPARENT);
        // draw the view on the canvas
        view.draw(canvas);
        return returnedBitmap;
    }


    public static void setBitmapToFile(Bitmap bitmap, File file) {
        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.close();
        } catch (Exception e) {
            //  e.printStackTrace();
        }
    }

}
