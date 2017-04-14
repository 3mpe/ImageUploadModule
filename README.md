

# 3mpe Image File Upload Manager

----
## What is Image File Upload Manager?
> A service for sending images or parameters with simple http request


## How to install 

```Groovy
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
  compile 'com.github.3mpe:ImageFileUploader:1.0.1'
}
```

#### Example 
````Groovy
        // import project.
        import com.example.a3mpe.imageupload.ImageFileUpload;
        import com.example.a3mpe.imageupload.OnResponse;
        import com.example.a3mpe.imageupload.ResponseImageFile;

        ImageFileUpload imageFileUpload;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            imageFileUpload = new ImageFileUpload(MainActivity.this);
            imageFileUpload.galleryIntent(); // Choose a picture from the gallery or Take a photo


            // To see the selected drawable item
            imageFileUpload.getResponseImageFile(new ResponseImageFile() {
            @Override
            public void onSuccess(Bitmap bitmap) {

                // For Http request parameters
                Map<String, String> headers = new ArrayMap<String, String>();
                headers.put("key", "sadlas12312ajfş");

                Map<String, String> params = new ArrayMap<String, String>();
                params.put("name", "Emre");

                // url
                imageFileUpload.setUrl("");

                // returning Answers
                imageFileUpload.getOnResponse(new OnResponse() {
                    @Override
                    public void onSuccess(String message) {
                        String succesMessage = message;
                    }

                    @Override
                    public void onError(String message) {
                        String errorMessage = message;
                    }
                });

                // Get the selected image as a bitmap and post it to the url as post
                imageFileUpload.sendFileAll("POST", bitmap, "logo.png", "image", headers, params);
            }

            @Override
            public void onError(String message) {
                // hata varsa yakala
                Toast.makeText(MainActivity.this,"Resim Okunamıyor",Toast.LENGTH_LONG).show();
            }
        });
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageFileUpload.onActivityResult(requestCode,resultCode,data);
    }


````
