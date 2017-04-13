package com.example.a3mpe.imageupload;

import android.graphics.Bitmap;

/**
 * Created by Gaaraj on 13.04.2017.
 */

public interface ResponseImageFile {

    void onSuccess(Bitmap bitmap);
    void onError(String message);
}
