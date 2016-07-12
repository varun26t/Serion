package io.nerditall.serion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.io.File;

import io.nerditall.serion.workers.SingleImageWorker;

/**
 * Created by a0_ on 2/7/16.
 */
public class SingleImage extends Activity {

    private final String IMAGE_FILE_TAG = "img";
    private static final String TAG = SingleImage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        ImageView imageView = new ImageView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);

        imageView.setLayoutParams(params);

        setContentView(imageView);

        byte[] array = getIntent().getByteArrayExtra(IMAGE_FILE_TAG);
        Bitmap bmp = BitmapFactory.decodeByteArray(array,0,array.length);

        imageView.setImageBitmap(bmp);
    }
}
