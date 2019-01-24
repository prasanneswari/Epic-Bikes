package com.jts.root.ebike_project;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Contact_Us extends AppCompatActivity {

    Button backbtn;
    ImageView callusbtn,textusbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__us);
       // backbtn=(Button)findViewById(R.id.backbtn);
        callusbtn=(ImageView) findViewById(R.id.callusbtn);
        textusbtn=(ImageView)findViewById(R.id.tellusbtn);

       /* backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (triptime==true){
                    Intent intent = new Intent(Contact_Us.this, Trip_Time.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Contact_Us.this, Epick_Bikes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });*/

        callusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String phoneNo = call.getText().toString();
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(Unable_to_endtrip.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }*/

                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:7382734008"));
                startActivity(intent);
            }
        });
        textusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String smsNumber = "7382734008";
                    String smsText = "defalut text message";

                    Uri uri = Uri.parse("smsto:" + smsNumber);
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", smsText);
                    startActivity(intent);
                   /* Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("sms_body", "default content");
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);*/

                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }
    public void ReturnHome(View view){
        super.onBackPressed();
    }
}
