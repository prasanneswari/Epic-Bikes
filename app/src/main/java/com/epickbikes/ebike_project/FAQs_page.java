package com.epickbikes.ebike_project;

import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class FAQs_page extends AppCompatActivity {

    ImageView close1,close2,close3,close4,close5,close6,close7,close8;
    ImageView expand1,expand2,expand3,expand4,expand5,expand6,expand7,expand8;
    TextView text1,text2,text3,text4,text5,text6,text7,text8;
    Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_faqs_page);

        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
        text4=(TextView)findViewById(R.id.text4);
        text5=(TextView)findViewById(R.id.text5);
        text6=(TextView)findViewById(R.id.text6);
        text7=(TextView)findViewById(R.id.text7);
        text8=(TextView)findViewById(R.id.text8);
        expand1=(ImageView)findViewById(R.id.expandimg1);
        expand2=(ImageView)findViewById(R.id.expandimg2);
        expand3=(ImageView)findViewById(R.id.expandimg3);
        expand4=(ImageView)findViewById(R.id.expandimg4);
        expand5=(ImageView)findViewById(R.id.expandimg5);
        expand6=(ImageView)findViewById(R.id.expandimg6);
        expand7=(ImageView)findViewById(R.id.expandimg7);
        expand8=(ImageView)findViewById(R.id.expandimg8);

        close1=(ImageView)findViewById(R.id.closeimg1);
        close2=(ImageView)findViewById(R.id.closeimg2);
        close3=(ImageView)findViewById(R.id.closeimg3);
        close4=(ImageView)findViewById(R.id.closeimg4);
        close5=(ImageView)findViewById(R.id.closeimg5);
        close6=(ImageView)findViewById(R.id.closeimg6);
        close7=(ImageView)findViewById(R.id.closeimg7);
        close8=(ImageView)findViewById(R.id.closeimg8);

        //backbtn=(Button)findViewById(R.id.backbtn);


        /*backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (triptime==true){
                    Intent intent = new Intent(FAQs_page.this, Trip_Time.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(FAQs_page.this, Epick_Bikes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });*/

        expand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setVisibility(View.VISIBLE);
                close1.setVisibility(View.VISIBLE);
                expand1.setVisibility(View.GONE);

                //text1.setText(View.VISIBLE);
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setVisibility(View.GONE);
                expand1.setVisibility(View.VISIBLE);
                close1.setVisibility(View.GONE);


            }
        });

        expand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text2.setVisibility(View.VISIBLE);
                close2.setVisibility(View.VISIBLE);
                expand2.setVisibility(View.GONE);

                //text1.setText(View.VISIBLE);
            }
        });
        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text2.setVisibility(View.GONE);
                expand2.setVisibility(View.VISIBLE);
                close2.setVisibility(View.GONE);


            }
        });
        expand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text3.setVisibility(View.VISIBLE);
                close3.setVisibility(View.VISIBLE);
                expand3.setVisibility(View.GONE);

                //text1.setText(View.VISIBLE);
            }
        });
        close3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text3.setVisibility(View.GONE);
                expand3.setVisibility(View.VISIBLE);
                close3.setVisibility(View.GONE);


            }
        });
        expand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text4.setVisibility(View.VISIBLE);
                close4.setVisibility(View.VISIBLE);
                expand4.setVisibility(View.GONE);

                //text1.setText(View.VISIBLE);
            }
        });
        close4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text4.setVisibility(View.GONE);
                expand4.setVisibility(View.VISIBLE);
                close4.setVisibility(View.GONE);


            }
        });
        expand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text5.setVisibility(View.VISIBLE);
                close5.setVisibility(View.VISIBLE);
                expand5.setVisibility(View.GONE);

                //text1.setText(View.VISIBLE);
            }
        });
        close5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text5.setVisibility(View.GONE);
                expand5.setVisibility(View.VISIBLE);
                close5.setVisibility(View.GONE);


            }
        });
        expand6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text6.setVisibility(View.VISIBLE);
                close6.setVisibility(View.VISIBLE);
                expand6.setVisibility(View.GONE);

                //text1.setText(View.VISIBLE);
            }
        });
        close6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text6.setVisibility(View.GONE);
                expand6.setVisibility(View.VISIBLE);
                close6.setVisibility(View.GONE);


            }
        });
        expand7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text7.setVisibility(View.VISIBLE);
                close7.setVisibility(View.VISIBLE);
                expand7.setVisibility(View.GONE);

                //text1.setText(View.VISIBLE);
            }
        });
        close7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text7.setVisibility(View.GONE);
                expand7.setVisibility(View.VISIBLE);
                close7.setVisibility(View.GONE);


            }
        });
        expand8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text8.setVisibility(View.VISIBLE);
                close8.setVisibility(View.VISIBLE);
                expand8.setVisibility(View.GONE);

                //text1.setText(View.VISIBLE);
            }
        });
        close8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text8.setVisibility(View.GONE);
                expand8.setVisibility(View.VISIBLE);
                close8.setVisibility(View.GONE);


            }
        });



/*
        faqs_lst=(ListView)findViewById(R.id.faqslst);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FAQs_page.this, R.layout.faqs_backlst,R.id.listtext, faqs_values);
        faqs_lst.setAdapter(adapter);
*/



    }
    public void ReturnHome(View view){
        super.onBackPressed();
    }


}
