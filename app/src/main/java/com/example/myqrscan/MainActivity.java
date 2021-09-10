package com.example.myqrscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private CodeScannerView scannerViewObj;
    private CodeScanner scannerObj;

    public String resultStrng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        qrcamera();

    }



    public void init(){

        // ADDING REFERENCES :
        scannerViewObj = findViewById(R.id.scannerView);
        scannerObj = new CodeScanner(MainActivity.this,scannerViewObj);
    }




    public void qrcamera() {

        scannerObj.startPreview();
        scannerObj.setDecodeCallback(new callbackMethod());

    }




    public class callbackMethod implements DecodeCallback{
        @Override
        public void onDecoded(@NonNull @NotNull Result result) {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    resultStrng = result.getText();

                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);

                    alertdialog.setTitle("Your Scanned Result is :");
                    alertdialog.setMessage(resultStrng);
                    alertdialog.setPositiveButton("SCAN AGAIN",new AlertdialogListener());
                    alertdialog.setNegativeButton("CANCEL",new AlertdialogListener());

                    alertdialog.show();

                }

            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerObj.startPreview();
    }





    public class AlertdialogListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which==DialogInterface.BUTTON_POSITIVE){
                scannerObj.startPreview();
            }
            else if (which == DialogInterface.BUTTON_NEGATIVE){
                dialog.cancel();
                scannerObj.stopPreview();
                finish();
            }
        }
    }





}