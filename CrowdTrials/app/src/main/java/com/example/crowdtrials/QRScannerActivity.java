/*
package com.example.crowdtrials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class QRScannerActivity extends AppCompatActivity {

    Experiment selectedExperiment;
    private CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_scanner);

        selectedExperiment = (Experiment) getIntent().getSerializableExtra("experiment");

        if(ContextCompat.checkSelfPermission(QRScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(QRScannerActivity.this,new String[] {Manifest.permission.CAMERA},1);
        }
        else {
            scan();
        }
    }

    private void scan() {
        CodeScannerView scannerView = findViewById(R.id.);
        codeScanner = new CodeScanner(QRScannerActivity.this, scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addTrial(result.getText());
                    }
                });
            }
        });
    }

    public void addTrial(String result) {
        Intent intent;

        switch(result) {
            case "pass":
                if(selectedExperiment.getType() == "Binomial Exp") {
                    intent = new Intent(QRScannerActivity.this, BinomialActivity.class);
                    intent.putExtra("Pass", selectedExperiment);
                    startActivityForResult(intent, 101);
                }
                else {
                    Toast.makeText(QRScannerActivity.this,"Invalid Entry!",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;

            case "fail":
                if(selectedExperiment.getType() == "Binomial Exp") {
                    intent = new Intent(QRScannerActivity.this, BinomialActivity.class);
                    intent.putExtra("Fail", selectedExperiment);
                    startActivityForResult(intent, 101);
                }
                else {
                    Toast.makeText(QRScannerActivity.this,"Invalid Entry!",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                if(selectedExperiment.getType() == "CountExp" || selectedExperiment.getType() == "NonNegativeCountExp") {
                    try {
                        int count = Integer.parseInt(result);
                        if (count < 0 && selectedExperiment.getType() == "CountExp" ) {
                            intent = new Intent(QRScannerActivity.this,CountActivity.class);
                            intent.putExtra("CountExp",selectedExperiment);
                            intent.putExtra("Count",count);
                            startActivityForResult(intent,101);
                            break;
                        }
                        else if (selectedExperiment.getType() == "CountExp") {
                            intent = new Intent(QRScannerActivity.this,CountActivity.class);
                            intent.putExtra("CountExp",selectedExperiment);
                            intent.putExtra("Count",count);
                            startActivityForResult(intent,101);
                            break;
                        }
                        else {
                            intent = new Intent(QRScannerActivity.this,NonNegativeCountActivity.class);
                            intent.putExtra("NonNegativeCountExp",selectedExperiment);
                            intent.putExtra("Count",count);
                            startActivityForResult(intent,101);
                            break;
                        }
                    }
                    catch (Exception e) {
                        Toast.makeText(QRScannerActivity.this,"Invalid Entry!",Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }
                }
                else {
                    Toast.makeText(QRScannerActivity.this,"Invalid Entry!",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                scan();
            }
            else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK) {
            selectedExperiment = (Experiment) data.getSerializableExtra("Experiment");
            Intent intent = new Intent();
            intent.putExtra("Experiment",selectedExperiment);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            System.out.println("No Data to be sent");
        }
    }

}*/
