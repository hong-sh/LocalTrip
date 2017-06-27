package com.localtrip.jejulocaltrip.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

/**
 * Created by hong on 2017. 5. 31..
 */

public class CircleImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private Context context;
    private CircleImageView imageView;
    private String image;

    public CircleImageAsyncTask(Context context, CircleImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Bitmap doInBackground(String... image) {
//
//        Bitmap bitmap = null;
//        try {
//            InputStream is = new java.net.URL(URLDefine.IMAGE_URL + image[0]).openStream();
//            bitmap = BitmapFactory.decodeStream(is);
//        } catch (IOException e) {
//            Log.e("ImageLoaderTask", e.toString());
//            Log.e("ImageLoaderTask", "Cannot load image from ");
//        }
//        return bitmap;

        this.image = image[0];
        return null;

    }

    @Override
    protected void onPostExecute(Bitmap result) {

        Glide.with(this.context).load(URLDefine.IMAGE_URL + this.image).into(imageView);

        super.onPostExecute(result);
    }
}