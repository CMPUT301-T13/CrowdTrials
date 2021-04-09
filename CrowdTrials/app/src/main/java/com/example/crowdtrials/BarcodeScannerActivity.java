package com.example.crowdtrials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class BarcodeScannerActivity extends AppCompatActivity {
    private CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_scanner);

        if(ContextCompat.checkSelfPermission(BarcodeScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(BarcodeScannerActivity.this,new String[] {Manifest.permission.CAMERA},1);
        }
        else {
            scan();
        }
    }

    private void scan() {
        CodeScannerView scannerView = findViewById(R.id.scan_view);
        codeScanner = new CodeScanner(BarcodeScannerActivity.this,scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.putExtra("scannedCode", result.getText());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Camera permission granted",Toast.LENGTH_LONG).show();
                scan();
            }
            else {
                Toast.makeText(this,"Camera permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(codeScanner != null) {
            codeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if(codeScanner != null) {
            codeScanner.releaseResources();
        }
        super.onPause();
    }
}