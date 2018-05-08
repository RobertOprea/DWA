package com.dwa.rybridge.ryebridgedwa.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {


    private Context context;

    public ImageUtil(Context context) {
        this.context = context;
    }

    public void orientPhoto(Uri photoUri, String photoPath) {
        Bitmap oldBitMap = null;
        try {
            oldBitMap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
            Bitmap newBitMap = transformOldBitMap(oldBitMap, photoPath);
            saveBitMap(newBitMap, photoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap transformOldBitMap(Bitmap oldBitMap, String photoPath) throws IOException {
        ExifInterface ei = new ExifInterface(photoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(oldBitMap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(oldBitMap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(oldBitMap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = oldBitMap;
        }

        return rotatedBitmap;
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void saveBitMap(Bitmap bitmap, String photoPath) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(photoPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
