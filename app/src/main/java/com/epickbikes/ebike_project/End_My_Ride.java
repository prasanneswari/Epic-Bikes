package com.epickbikes.ebike_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class End_My_Ride extends AppCompatActivity {

    Button backbtn,unabletoend;
    TextView bikeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end__my__ride);
        //backbtn=(Button)findViewById(R.id.backbtn);
        unabletoend=(Button)findViewById(R.id.unabletoend);

        bikeid=(TextView)findViewById(R.id.bikeid);

       /* backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(End_My_Ride.this, Proceed_to_nextstep.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
        unabletoend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(End_My_Ride.this, End_trips.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        if (JournyScan_Flashlight.ScanbikeId != null) {
            bikeid.setText("#"+ JournyScan_Flashlight.ScanbikeId);
        } else if (Launching.bikeidlanch != null ) {
            bikeid.setText("#"+ Launching.bikeidlanch);
        }

        if ( JournyScan_Flashlight.otp != null) {

            Button otp1=(Button)findViewById(R.id.otp1);
            Button otp2=(Button)findViewById(R.id.otp2);
            Button otp3=(Button)findViewById(R.id.otp3);
            Button otp4=(Button)findViewById(R.id.otp4);

            String otpspilt1 = JournyScan_Flashlight.otp.substring(0, 1);
          //  System.out.println("output  otpspilt"+otpspilt1);

            String otpspilt2 = JournyScan_Flashlight.otp.substring(1, 2);
           // System.out.println("output  otpspilt"+otpspilt2);

            String otpspilt3 = JournyScan_Flashlight.otp.substring(2, 3);
           // System.out.println("output  otpspilt"+otpspilt3);

            String otpspilt4 = JournyScan_Flashlight.otp.substring(3, 4);
          //  System.out.println("output  otpspilt"+otpspilt4);


            otp1.setText(otpspilt1);
            otp2.setText(otpspilt2);
            otp3.setText(otpspilt3);
            otp4.setText(otpspilt4);

        } else if (Launching.otplanch != null) {

            Button otp1=(Button)findViewById(R.id.otp1);
            Button otp2=(Button)findViewById(R.id.otp2);
            Button otp3=(Button)findViewById(R.id.otp3);
            Button otp4=(Button)findViewById(R.id.otp4);

            String otpspilt1 = Launching.otplanch.substring(0, 1);
          //  System.out.println("output  otpspilt"+otpspilt1);

            String otpspilt2 = Launching.otplanch.substring(1, 2);
          //  System.out.println("output  otpspilt"+otpspilt2);

            String otpspilt3 = Launching.otplanch.substring(2, 3);
          //  System.out.println("output  otpspilt"+otpspilt3);

            String otpspilt4 = Launching.otplanch.substring(3, 4);
         //   System.out.println("output  otpspilt"+otpspilt4);


            otp1.setText(otpspilt1);
            otp2.setText(otpspilt2);
            otp3.setText(otpspilt3);
            otp4.setText(otpspilt4);

        }
    }
    public void ReturnHome(View view){
        super.onBackPressed();
    }

}
