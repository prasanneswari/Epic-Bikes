package com.jts.root.ebike_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.jts.root.ebike_project.Phone_Auth.locationdataarray;

public class Splash_Screen extends AppCompatActivity {

    Button backbtn,backleftbtn;
    TextView nexttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        backbtn=(Button)findViewById(R.id.backbtn);
        backleftbtn=(Button)findViewById(R.id.backleft);
        nexttext=(TextView)findViewById(R.id.nexttext);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash_Screen.this, Phone_Auth.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



        backleftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (locationdataarray > 1) {
                    Intent intent = new Intent(Splash_Screen.this, List_places.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(Splash_Screen.this, Epick_Bikes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        nexttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (locationdataarray > 1) {
                    Intent intent = new Intent(Splash_Screen.this, List_places.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(Splash_Screen.this, Epick_Bikes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }
}






























