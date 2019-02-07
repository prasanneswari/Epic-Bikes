package com.jts.root.ebike_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.jts.root.ebike_project.Launching.coinslanch;
import static com.jts.root.ebike_project.Launching.profileURLlanch;
import static com.jts.root.ebike_project.Launching.refreshCoinslanch;
import static com.jts.root.ebike_project.Launching.sharedcoins;
import static com.jts.root.ebike_project.Launching.sharedrefercoins;
import static com.jts.root.ebike_project.List_places.coinsposval;
import static com.jts.root.ebike_project.List_places.profileposval;
import static com.jts.root.ebike_project.List_places.refreshposval;
import static com.jts.root.ebike_project.Phone_Auth.coinslogin;
import static com.jts.root.ebike_project.Phone_Auth.profileURLlogin;
import static com.jts.root.ebike_project.Phone_Auth.refreshCoinslogin;

public class Coins_page extends AppCompatActivity {

    TextView coinsT,notes;
    ImageView profileimg;
    String url = profileposval;
    Button backbtn,addmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_coins_page);

        coinsT=(TextView)findViewById(R.id.coins);
        notes=(TextView)findViewById(R.id.notes);
        //backbtn=(Button)findViewById(R.id.backbtn);
        addmoney=(Button)findViewById(R.id.addmoney);

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


        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Coins_page.this, Wallet.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
            }
        });
    }
    public void ReturnHome(View view){
        super.onBackPressed();
    }
}
