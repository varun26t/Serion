package io.nerditall.serion.workers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by a0_ on 2/7/16.
 */
public class SingleImageWorker extends AsyncTask<File,Void,Bitmap> {

    private ImageView imageView;
    private int TARGET_IMAGE_VIEW_WIDTH;
    private int TARGET_IMAGE_VIEW_HEIGHT;
    private File mImageFile;
    private Context context;

    public SingleImageWorker(Context context,ImageView imageView, int TARGET_IMAGE_VIEW_HEIGHT, int TARGET_IMAGE_VIEW_WIDTH) {
        this.context = context;
        this.imageView = imageView;
        this.TARGET_IMAGE_VIEW_HEIGHT = TARGET_IMAGE_VIEW_HEIGHT;
        this.TARGET_IMAGE_VIEW_WIDTH = TARGET_IMAGE_VIEW_WIDTH;
    }

    @Override
    protected Bitmap doInBackground(File... params) {
        mImageFile = params[0];
        Bitmap bitmap = getBitmap(mImageFile.toString());
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap !=null){
            imageView.setImageBitmap(bitmap);
        }
    }
    private Bitmap getBitmap(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }
}

