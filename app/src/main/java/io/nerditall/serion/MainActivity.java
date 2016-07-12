package io.nerditall.serion;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import io.nerditall.serion.Adapter.ImageAdapter;

public class MainActivity extends AppCompatActivity
                            implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private RecyclerView mRecyclerview;
    private RecyclerView.ItemDecoration decoration;
    private GridLayoutManager gridLayoutManager;
    private ImageAdapter mImageAdapter;
    private FloatingActionButton fab;

    private static final int MEDIASTORE_LOADER = 0;
    private static final int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundDrawable();

        mRecyclerview = (RecyclerView) findViewById(R.id.image_grid);

        gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerview.setLayoutManager(gridLayoutManager);

//        decoration = new GridSize();

        mImageAdapter = new ImageAdapter(this);
        mRecyclerview.setAdapter(mImageAdapter);

        getLoaderManager().initLoader(MEDIASTORE_LOADER, null, this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,TAKE_PICTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(MainActivity.this, "Yet to be able to save", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.refresh:{
                getLoaderManager().initLoader(MEDIASTORE_LOADER, null, this);
                return true;
            }
            case R.id.settings:{
                Intent i = new Intent(this,Settings.class);
                startActivity(i);
                return true;
            }
            case R.id.donate:{
                Toast.makeText(MainActivity.this, "Adding Donate option", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.exit:{
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    @Override
//    public void getRecyclerViewAdapterPosition(int position){
//
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projections = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE
        };

        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

        Log.d(TAG, MediaStore.Files.getContentUri("external").toString());

        return new CursorLoader(
               this,
               MediaStore.Files.getContentUri("external"),
               projections,
               selection,
               null,
               MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mImageAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mImageAdapter.changeCursor(null);
    }
}
