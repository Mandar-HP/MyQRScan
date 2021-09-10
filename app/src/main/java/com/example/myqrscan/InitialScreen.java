package com.example.myqrscan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class InitialScreen extends AppCompatActivity {

    private ImageButton cameraBtn,gallaryBtn;

    private final int gallaryReqCode = 1001;
    private ImageView imgViewFromGall;
    private int camerapermiss = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);

        permissionReq();
        init();


    }

    public void init(){

        // ADDING REFERENCES :
        cameraBtn = findViewById(R.id.cameraBtn);
        gallaryBtn = findViewById(R.id.gallaryBtn);

        cameraBtn.setOnClickListener(new ClickListener());
        gallaryBtn.setOnClickListener(new ClickListener());

        imgViewFromGall = findViewById(R.id.capturedImage);

    }

    public class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            if (v == cameraBtn){

                if (ContextCompat.checkSelfPermission(InitialScreen.this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

                    Intent mainActInt = new Intent(InitialScreen.this,MainActivity.class);
                    startActivity(mainActInt);

                }

                else {
                    permissionReq();
                }

            }
            else if ( v == gallaryBtn){

                if (ContextCompat.checkSelfPermission(InitialScreen.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    Intent imgPickrInt = new Intent(Intent.ACTION_PICK);
                    imgPickrInt.setType("image/*");
                    startActivityForResult(imgPickrInt,gallaryReqCode);

                }

                else {
                    permissionReq();
                }
            }




        }
    }

   public void permissionReq(){
       ActivityCompat.requestPermissions(InitialScreen.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},camerapermiss);

   }











   // PROCESSING OF SELECTED IMAGE :



   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == gallaryReqCode && data != null) {
           Uri imageSrcUri = data.getData();
           //Log.e("tag", String.valueOf(imageSrcUri));
           imgViewFromGall.setImageURI(imageSrcUri);
       }


   }



}