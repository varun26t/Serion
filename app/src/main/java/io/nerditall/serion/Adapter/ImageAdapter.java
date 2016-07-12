package io.nerditall.serion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import io.nerditall.serion.R;
import io.nerditall.serion.SingleImage;


/**
 * Created by a0_ on 22/6/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private Cursor mCursor;
    private Context context;
    private final String IMAGE_FILE_TAG = "img";

    public ImageAdapter(Activity activity) {
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.indi_img, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bitmap mBitmap = getBitmapFromMediaStore(position);
        if(mBitmap != null){
            holder.mImageView.setImageBitmap(mBitmap);
        }
    }

    @Override
    public int getItemCount() {
        return (mCursor == null)? 0:mCursor.getCount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;

        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = (ImageView) itemView.findViewById(R.id.imgView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("ImageAdapter", "onClick: " + position);
            Bitmap bitmap = getBitmapFromMediaStore(position);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray = stream.toByteArray();

            Intent intent = new Intent(context, SingleImage.class);
            intent.putExtra(IMAGE_FILE_TAG, byteArray);
            context.startActivity(intent);
        }
    }


    private Cursor swapCursor(Cursor cursor){
        if(cursor == mCursor){
            return null;
        }
        Cursor oCursor = mCursor;
        this.mCursor = cursor;
        if(cursor != null){
            this.notifyDataSetChanged();
        }
        return oCursor;
    }

    public void changeCursor(Cursor cursor){
        Cursor oCursor = swapCursor(cursor);
        if(oCursor != null){
            oCursor.close();
        }
    }

    private String getRealPath(Uri contentUri){
        Cursor cursor = null;
        try{
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri,projection,null,null,null);
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(index);
        }finally {
            if(cursor != null)
                cursor.close();
        }
    }

    public Bitmap getBitmapFromMediaStore(int position){
        int idIndex = mCursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
        int mediaTypeIndex = mCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);

        mCursor.moveToPosition(position);
        switch (mCursor.getInt(mediaTypeIndex)){
            case MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE:
                return MediaStore.Images.Thumbnails.getThumbnail(
                        context.getContentResolver(),
                        mCursor.getLong(idIndex),
                        MediaStore.Images.Thumbnails.MICRO_KIND,
                        null
                );
            case MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO:
                return MediaStore.Video.Thumbnails.getThumbnail(
                        context.getContentResolver(),
                        mCursor.getLong(idIndex),
                        MediaStore.Video.Thumbnails.MICRO_KIND,
                        null
                );
            default:
                return null;
        }

    }
}
