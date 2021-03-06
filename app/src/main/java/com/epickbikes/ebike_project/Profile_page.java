package com.epickbikes.ebike_project;

import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.epickbikes.ebike_project.Launching.phoneNumberlanch;
import static com.epickbikes.ebike_project.Launching.profileURLlanch;
import static com.epickbikes.ebike_project.Launching.uidlanch;
import static com.epickbikes.ebike_project.List_places.phoneNumber;
import static com.epickbikes.ebike_project.List_places.profileposval;
import static com.epickbikes.ebike_project.List_places.uid;
import static com.epickbikes.ebike_project.Phone_Auth.phoneNumberlogin;
import static com.epickbikes.ebike_project.Phone_Auth.profileURLlogin;
import static com.epickbikes.ebike_project.Phone_Auth.uidlogin;

public class Profile_page extends AppCompatActivity {

    TextView profilename,profilenum,totaltrips,calories,co2saved;
    ImageView profilesrc;

    String url=profileposval;
    ImageView profileimg;
    Button backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile_page);

        profilename=(TextView)findViewById(R.id.profilename);
        profilenum=(TextView)findViewById(R.id.profilenumber);
      //  totaltrips=(TextView)findViewById(R.id.totaltrips);
       // calories=(TextView)findViewById(R.id.calories);
      //  co2saved=(TextView)findViewById(R.id.co2saved);
        profilesrc=(ImageView)findViewById(R.id.profilesrc);
        //backbtn=(Button)findViewById(R.id.backbtn);


        if (phoneNumber!=null&&uid!=null&&url!=null) {

            profilename.setText(uid);
            profilenum.setText(phoneNumber);
            loadimageurl(url);
        }else if (phoneNumberlanch!=null&&uidlanch!=null&&profileURLlanch!=null){
            profilename.setText(uidlanch);
            profilenum.setText(phoneNumberlanch);
            loadimageurl(profileURLlanch);
        }else if (phoneNumberlogin!=null&&uidlogin!=null&&profileURLlogin!=null){
            profilename.setText(uidlogin);
            profilenum.setText(phoneNumberlogin);
            loadimageurl(profileURLlogin);
        }/*else if (sharedphnum!=null&&shareduid!=null&&sharedprofleurl!=null){
            profilename.setText(shareduid);
            profilenum.setText(sharedphnum);
            loadimageurl(sharedprofleurl);
        }*/



       /* backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (triptime==true){
                    Intent intent = new Intent(Profile_page.this, Trip_Time.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(Profile_page.this, Epick_Bikes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });*/


    }


    private void loadimageurl(String url) {
        Picasso.with(this).load(url).placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(profilesrc, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public void ReturnHome(View view){
        super.onBackPressed();
    }
    }
