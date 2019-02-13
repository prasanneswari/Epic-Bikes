package com.jts.root.ebike_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import static com.jts.root.ebike_project.Launching.coinslanch;
import static com.jts.root.ebike_project.Launching.profileURLlanch;
import static com.jts.root.ebike_project.Launching.refreshCoinslanch;
import static com.jts.root.ebike_project.Launching.sharedcoins;
import static com.jts.root.ebike_project.Launching.sharedrefercoins;
import static com.jts.root.ebike_project.List_places.coinsposval;
import static com.jts.root.ebike_project.List_places.profileposval;
import static com.jts.root.ebike_project.List_places.refreshposval;
import static com.jts.root.ebike_project.Phone_Auth.accesstoken;
import static com.jts.root.ebike_project.Phone_Auth.coinslogin;
import static com.jts.root.ebike_project.Phone_Auth.profileURLlogin;
import static com.jts.root.ebike_project.Phone_Auth.refreshCoinslogin;

public class Coins_page extends AppCompatActivity {

    TextView coinsT,notes;
    ImageView profileimg;
    String url = profileposval;
    Button backbtn,addmoney,btn1,btn2,btn3;
    String addbtnmoney;
    int i=0;
    int[] array = {0,0,0};
    boolean amount=true;
    boolean amountadd=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_coins_page);

        coinsT=(TextView)findViewById(R.id.coins);
        notes=(TextView)findViewById(R.id.notes);
        //backbtn=(Button)findViewById(R.id.backbtn);
        addmoney=(Button)findViewById(R.id.addmoney);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);

        if (coinsposval!=null&&refreshposval!=null) {
            coinsT.setText(coinsposval);
            notes.setText("'" + refreshposval + "'");
        }else if (coinslanch!=null&&refreshCoinslanch!=null){
            coinsT.setText(coinslanch);
            notes.setText("'" + refreshCoinslanch + "'");
        }else if (coinslogin!=null&&refreshCoinslogin!=null){
            coinsT.setText(coinslogin);
            notes.setText("'" + refreshCoinslogin + "'");
        }/*else if (sharedcoins!=null&&sharedrefercoins!=null){
            coinsT.setText(sharedcoins);
            notes.setText("'" + sharedrefercoins + "'");
        }*/


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(array[0]==0) {
                    array[0] = 1; // ON
                    addbtnmoney="50";
                    Log.d("clicked","btn1 :"+addbtnmoney);
                    btn1.setBackgroundColor(Color.BLUE);
                    btn1.setTextColor(Color.WHITE);
                    btn2.setBackgroundResource(R.drawable.border);
                    btn3.setBackgroundResource(R.drawable.border);
                    btn2.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);
                    array[1]=0;
                    array[2]=0;
                }  else if(array[0] == 1) {
                    array[0] = 0; //OFF
                    addbtnmoney="0";
                    Log.d("clicked","btn1 :"+addbtnmoney);
                    btn2.setClickable(true);
                    btn3.setClickable(true);
                    btn1.setBackgroundResource(R.drawable.border);
                    btn1.setTextColor(Color.BLACK);
                    if (btn2.isClickable() || btn3.isClickable()){
                        array[1] = 0;
                        array[2] = 0;

                    }else {
                        array[1] = 1;
                        array[2] = 1;
                    }

                }


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(array[1]==0) {
                    array[1] = 1; // ON
                    addbtnmoney="100";
                    Log.d("clicked","btn2 :"+addbtnmoney);

                    btn2.setBackgroundColor(Color.BLUE);
                    btn2.setTextColor(Color.WHITE);
                    btn1.setBackgroundResource(R.drawable.border);
                    btn3.setBackgroundResource(R.drawable.border);
                    btn1.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);


                    array[0]=0;
                    array[2]=0;

                } else if(array[1] == 1) {
                    array[1] = 0; //OFF
                    addbtnmoney="0";
                    Log.d("clicked","btn2 :"+addbtnmoney);
                    btn1.setClickable(true);
                    btn3.setClickable(true);
                    btn2.setBackgroundResource(R.drawable.border);
                    btn2.setTextColor(Color.BLACK);
                    if (btn1.isClickable() || btn3.isClickable()){
                        array[0] = 0;
                        array[2] = 0;

                    }else {
                        array[0] = 1;
                        array[2] = 1;
                    }
                }


            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (array[2] == 0) {
                    array[2] = 1; // ON
                    addbtnmoney="250";
                    Log.d("clicked","btn3 :"+addbtnmoney);

                    btn3.setBackgroundColor(Color.BLUE);
                    btn3.setTextColor(Color.WHITE);
                    btn2.setBackgroundResource(R.drawable.border);
                    btn1.setBackgroundResource(R.drawable.border);
                    btn2.setTextColor(Color.BLACK);
                    btn1.setTextColor(Color.BLACK);

                    array[1]=0;
                    array[0]=0;

                } else if(array[2] == 1) {
                    array[2] = 0; //OFF
                    addbtnmoney="0";
                    Log.d("clicked","btn3 :"+addbtnmoney);
                    btn2.setClickable(true);
                    btn1.setClickable(true);
                    btn3.setBackgroundResource(R.drawable.border);
                    btn3.setTextColor(Color.BLACK);
                    if (btn1.isClickable() || btn1.isClickable()){
                        array[0] = 0;
                        array[1] = 0;

                    }else {
                        array[0] = 1;
                        array[1] = 1;
                    }

                }


            }
        });

        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addbtnmoney == "50" || addbtnmoney == "100"|| addbtnmoney == "250") {
                    Intent intent = new Intent(Coins_page.this, Wallet.class);
                    intent.putExtra("keyName", addbtnmoney);  // pass your values and retrieve them in the other Activity using keyName
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Coins_page.this);
                    LayoutInflater inflater = (LayoutInflater) Coins_page.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                    textpopup.setText("Please select the money");

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            //delgroup(grpnm);

                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

            }
        });
    }
    public void ReturnHome(View view){
        super.onBackPressed();
    }
}
