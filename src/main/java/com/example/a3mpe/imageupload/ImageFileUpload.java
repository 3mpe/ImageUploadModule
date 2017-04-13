package com.example.a3mpe.imageupload;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class ImageFileUpload{

    private String url;
    private Bitmap imgBitmap;
    private ResponseImageFile file;
    private Activity activity;
    private BaseAppCompatActivity baseAppCompatActivity;
    private OnResponse httpOnResponse;

    public ImageFileUpload(Activity activity){
        this.activity = activity;
        baseAppCompatActivity = new BaseAppCompatActivity();
    }
    public void getResponseImageFile(ResponseImageFile responseImageFile){
        this.file = responseImageFile;
    }
    public void getOnResponse(OnResponse onResponse) {
        this.httpOnResponse = onResponse;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void sendFile(String method, Map<String, String> headers, Map<String, String> postParams) {
        request(method, null, null, null, headers, postParams);
    }
    public void sendFileJustPostParams(String method, Map<String, String> postParams) {
        request(method, null, null, null, null, postParams);
    }
    public void sendFileJustPostHeaders(String method, Map<String, String> headers) {
        request(method, null, null, null, headers, null);
    }
    public void sendFileAll(String method, Bitmap imgBitmap, String imageName, String imagePostParam, Map<String, String> headers, Map<String, String> postParams) {
        request(method, imgBitmap, imageName, imagePostParam, headers, postParams);
    }
    public void sendFileImgAndHeaders(String method, Bitmap imgBitmap, String imageName, String imagePostParam, Map<String, String> headers) {
        request(method, imgBitmap, imageName, imagePostParam, headers, null);
    }
    public void sendFileImgAndPostParams(String method, Bitmap imgBitmap, String imageName, String imagePostParam, Map<String, String> postParams) {
        request(method, imgBitmap, imageName, imagePostParam, null, postParams);
    }


    // Galeriyi Startla
    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), Enums.SELECT_FILE.getValue());
    }
    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, Enums.REQUEST_CAMERA.getValue());
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == Enums.SELECT_FILE.getValue()) {
                Uri filePath = data.getData();
                try {
                    imgBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), filePath);
                    file.onSuccess(imgBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    file.onError(e.getMessage());
                }
            } else if (requestCode == Enums.REQUEST_CAMERA.getValue()) {
                imgBitmap = (Bitmap) data.getExtras().get("data");
                file.onSuccess(imgBitmap);
            }

    }

    private File getImageFile(Bitmap bitmap, String imageName) throws Exception {
        File imageFile;
        try {
            imageFile = new File(activity.getCacheDir(), imageName);

            if (imageFile.exists()) {
                imageFile.delete();
            }

            if (imageFile.createNewFile()) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(imageFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return imageFile;
    }

    // imageName = "Logo.png';
    // String method = "GET", "POST" .. vs
    private void request(String method, Bitmap imgBitmap, String imageName, String imagePostParam, Map<String, String> headers, Map<String, String> postParams) {
        baseAppCompatActivity.ShowProgressBar();
        final JSONObject jsonObject = null;
        try {
            emp3OkHttpRequest request = new emp3OkHttpRequest(url);
            request.setRequestMethod(selectionMethod(method));

            if(httpOnResponse != null)
                request.setOnResponse(httpOnResponse);

            if (imgBitmap != null)
                request.setImageFile(getImageFile(imgBitmap, imageName), imageName, imagePostParam);

            if (headers != null && !headers.equals(Collections.emptyMap())){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    request.setHeader(key, value);
                }
            }
            if (headers != null && !postParams.equals(Collections.emptyMap())){
                for (Map.Entry<String, String> entry : postParams.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    request.setPostParams(key, value);
                }
            }

            request.setOnResponse(new OnResponse() {
                @Override
                public void onSuccess(String message) {
                    baseAppCompatActivity.HideProgressBar();
                    httpOnResponse.onSuccess(message);
                }

                @Override
                public void onError(final String errorMessage) {
                    baseAppCompatActivity.HideProgressBar();
                    Toast.makeText(activity.getBaseContext(), errorMessage, Toast.LENGTH_LONG);
                    httpOnResponse.onError(errorMessage);
                }
            });

            request.SendFile(url);
        } catch (Exception ex) {
            baseAppCompatActivity.HideProgressBar();
            ex.printStackTrace();
            Toast.makeText(activity.getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private int selectionMethod(String method) {
        if (method == "GET") {
            return Enums.GET.getValue();
        } else if (method == "POST") {
            return Enums.POST.getValue();
        } else if (method == "PUT") {
            return Enums.PUT.getValue();
        } else if (method == "DELETE") {
            return Enums.DELETE.getValue();
        } else {
            return Enums.GET.getValue();
        }


    }

}
