package com.jts.root.ebike_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.jts.root.ebike_project.JournyScan_Flashlight.ScanbikeId;
import static com.jts.root.ebike_project.JournyScan_Flashlight.otp;
import static com.jts.root.ebike_project.Launching.bikeidlanch;
import static com.jts.root.ebike_project.Launching.otplanch;

public class Proceed_to_nextstep extends AppCompatActivity {

    Button backbtn,proceesnextstep;
    TextView bikeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_to_nextstep);
        //backbtn=(Button)findViewById(R.id.backbtn);
        proceesnextstep=(Button)findViewById(R.id.processnextstep);
        bikeid=(TextView)findViewById(R.id.bikeid);

       /* backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Proceed_to_nextstep.this, Trip_Time.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/

        proceesnextstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Proceed_to_nextstep.this, End_My_Ride.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        if (ScanbikeId != null) {
            bikeid.setText("#"+ScanbikeId);
        } else if (bikeidlanch != null ) {
            bikeid.setText("#"+bikeidlanch);
        }

    }
    public void ReturnHome(View view){
        super.onBackPressed();

    }

}
