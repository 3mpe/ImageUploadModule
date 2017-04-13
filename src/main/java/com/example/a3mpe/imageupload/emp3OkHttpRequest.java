package com.example.a3mpe.imageupload;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class emp3OkHttpRequest {

    private String url;
    private File imageFile = null;
    private String imageName = "";
    private String imagePostParam = "";
    private Map<String, String> headers;
    private Map<String, String> postParams;
    private OnResponse onResponse;
    private OkHttpClient client;
    private int requestMethod = Enums.POST.getValue();

    public emp3OkHttpRequest(String url) {
        this.url = url;
        client = new OkHttpClient();
    }
    public void setHeader(String key, String value) {
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }

        headers.put(key, value);
    }
    public void setPostParams(String key, String value) {
        if (postParams == null || postParams.equals(Collections.emptyMap())) {
            postParams = new HashMap<>();
        }

        postParams.put(key, value);
    }
    public void setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
    }
    public void setImageFile(File imageFile, String imageName, String paramName) {
        this.imageFile = imageFile;
        this.imageName = imageName;
        this.imagePostParam = paramName;
    }
    public void setOnResponse(OnResponse onResponse) {
        this.onResponse = onResponse;
    }

    public void SendFile(final String url) {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            for (Map.Entry<String, String> entry : postParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.addFormDataPart(key, value);
            }

            if (imageFile != null) {
                // imageName = asd.png
                builder.addFormDataPart(imagePostParam, imageName,
                        RequestBody.create(MEDIA_TYPE_PNG, imageFile));
            }

            RequestBody requestBody = builder.build();
            Request.Builder requestBuilder = new Request.Builder().url(url);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                requestBuilder.addHeader(key, value);
            }

            Request request = applyRequestMethod(requestMethod, requestBuilder, requestBody).build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                onResponse.onError(response.message());
            } else {
                onResponse.onSuccess(response.message());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            onResponse.onError(ex.getMessage());
        }
    }
    private Request.Builder applyRequestMethod(int method, Request.Builder requestBuilder, RequestBody requestBody) {
        if (method == Enums.GET.getValue()) {
            requestBuilder.get();
        } else if (method == Enums.POST.getValue()) {
            requestBuilder.post(requestBody);
        } else if (method == Enums.PUT.getValue()) {
            requestBuilder.put(requestBody);
        } else if (method == Enums.DELETE.getValue()) {
            requestBuilder.delete(requestBody);
        }
        return requestBuilder;
    }


}
