package com.localtrip.jejulocaltrip.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by hong on 2017. 5. 31..
 */

public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private Context context;
    private ImageView imageView;
    private String image;

    public ImageAsyncTask(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Bitmap doInBackground(String... image) {

        this.image = image[0];
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

        Glide.with(this.context).load(URLDefine.IMAGE_URL + this.image).into(imageView);
        super.onPostExecute(result);
    }
}