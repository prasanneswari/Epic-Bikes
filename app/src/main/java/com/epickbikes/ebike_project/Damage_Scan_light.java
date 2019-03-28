package com.epickbikes.ebike_project;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.epickbikes.ebike_project.List_places.profileposval;

public class Damage_Scan_light extends AppCompatActivity
        implements ZXingScannerView.ResultHandler {
    Button okbtn,backbtn,scanid,flashon,flashoff;
    static String bikeidendtripval,bikeidunlockval,bikeidreportval;
    ImageButton lightbtn;
    EditText bikeidE;
    private ZXingScannerView mScannerView;
    private ViewGroup contentFrame;
    int light=0;
    private int CAMERA_PERMISSION_CODE = 23;

    ImageView profileimg;
    String url = profileposval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_damage__scan_light);

       // lightbtn=(ImageButton) findViewById(R.id.light);
        bikeidE=(EditText) findViewById(R.id.bikeid);
        //backbtn=(Button)findViewById(R.id.backbtn);
        flashon=(Button) findViewById(R.id.flashon);
        flashoff=(Button) findViewById(R.id.flashoff);

        okbtn=(Button)findViewById(R.id.okbtn);
       // bikeidE.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
       // bikeidE.setTransformationMethod(new NumericKeyBoardTransformationMethod());

       /* backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Damage_Scan_light.this, Report_damages.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    bikeidreportval = bikeidE.getText().toString().trim();
                    if(bikeidreportval.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Damage_Scan_light.this);
                        LayoutInflater inflater = (LayoutInflater) Damage_Scan_light.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
                        View dialogLayout = inflater.inflate(R.layout.bikemap_popup2,
                                null);
                        final AlertDialog dialog = builder.create();
                        dialog.getWindow().setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                        dialog.setView(dialogLayout, 0, 0, 0, 0);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.setCancelable(true);
                        WindowManager.LayoutParams wlmp = dialog.getWindow()
                                .getAttributes();
                        wlmp.gravity = Gravity.CENTER;
                        Button cancel = (Button) dialogLayout.findViewById(R.id.okbtn);
                        TextView textpopup = (TextView) dialogLayout.findViewById(R.id.popuptxt);

                        textpopup.setText("Please Enter the bikeID or Scan");

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                //delgroup(grpnm);

                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else {
                        Intent intent = new Intent(Damage_Scan_light.this, Report_damages.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

            }
        });

        contentFrame = (ViewGroup) findViewById(R.id.scanner_frame);

        mScannerView = new ZXingScannerView(this);
        mScannerView.setResultHandler(this);

        if (isCameraAccessAllowed()) {
            contentFrame.addView(mScannerView);

        } else {
            requestStoragePermission();

        }


        flashon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView.setFlash(true);

            }
        });
        flashoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView.setFlash(false);

            }
        });
        /*lightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(light==0) {
                    mScannerView.setFlash(true);
                    lightbtn.setBackgroundResource(R.drawable.light_on);

                    light=1;
                }else {
                    mScannerView.setFlash(false);
                    lightbtn.setBackgroundResource(R.drawable.lightimg);

                    light=0;
                }

            }
        });*/


    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    private boolean isCameraAccessAllowed() {
        boolean flag = false;
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            flag = true;
        }
        return flag;
    }

    private void requestStoragePermission() {

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contentFrame.addView(mScannerView);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.permission_camera_rationale);
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        bikeidE.setText(rawResult.getText().toString());

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

    public void ReturnHome(View view){
        super.onBackPressed();
    }

}
