package io.nerditall.serion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();
    private final int READ_EXTERNAL_STORAGE_ID = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        checkExternalReadStoragePermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_ID:
                boolean granted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (granted) //call cursor loader
                    startActivity(new Intent(BaseActivity.this, MainActivity.class));
                else
                    onDestroy();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void checkExternalReadStoragePermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                //start cursor loader
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
            }else {
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Toast.makeText(BaseActivity.this, "Needs to read Storage for images", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    READ_EXTERNAL_STORAGE_ID);
            }
        } else {
            //start cursor loader
        }
    }
}
