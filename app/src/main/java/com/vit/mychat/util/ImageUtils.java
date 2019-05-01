package com.vit.mychat.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class ImageUtils {

    public static void openPickImage(Activity activity) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);
    }

    public static void openPickImageForFragment(Context context, Fragment fragment) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(context, fragment);
    }

    public static File getAvatarImage(Uri imageUri, Context context) {
        File actualFile = new File(imageUri.getPath());

        try {
            return new Compressor(context)
                    .setMaxHeight(200)
                    .setMaxWidth(200)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(actualFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualFile;
    }

    public static File getImage(Uri imageUri, Context context) {
        File actualFile = new File(imageUri.getPath());

        try {
            return new Compressor(context)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(actualFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualFile;
    }
}
