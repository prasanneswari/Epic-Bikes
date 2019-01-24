package com.jts.root.ebike_project;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextPaint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.jts.root.ebike_project.Launching.orgIdlanch;
import static com.jts.root.ebike_project.Launching.phoneNumberlanch;
import static com.jts.root.ebike_project.Launching.profileURLlanch;
import static com.jts.root.ebike_project.Launching.sharedprofleurl;
import static com.jts.root.ebike_project.Launching.sharedsaveorgid;
import static com.jts.root.ebike_project.Launching.uidlanch;
import static com.jts.root.ebike_project.List_places.orgId;
import static com.jts.root.ebike_project.List_places.orgposval;
import static com.jts.root.ebike_project.List_places.phoneNumber;
import static com.jts.root.ebike_project.List_places.profileURL;
import static com.jts.root.ebike_project.List_places.profileposval;
import static com.jts.root.ebike_project.List_places.uid;
import static com.jts.root.ebike_project.Phone_Auth.accesstoken;
import static com.jts.root.ebike_project.Phone_Auth.orgIdlogin;
import static com.jts.root.ebike_project.Phone_Auth.phoneNumberlogin;
import static com.jts.root.ebike_project.Phone_Auth.profileURLlogin;
import static com.jts.root.ebike_project.Phone_Auth.uidlogin;


public class Epick_Bikes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
     List<LatLng> bikearray = new ArrayList<LatLng>();
     List<String> bikeidL = new ArrayList<>();
     List<String> nameidL= new ArrayList<>();
     ArrayList<String> listTitle= new ArrayList<String>();

    static double lat;
    static double lang;

    Button unlockbtn;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress;
    AlertDialog.Builder builderLoading;
    static String bikeId;
    static String bikepos,bikenamepos, eotp, otp, latfleet, lngfleet,fleetId,name;
    ImageView profileimg;
    NavigationView navigationView;
    private View navHeader;
    String url = profileposval;
    TextView headerprofilename;
    LinearLayout maplayout;


    String[] status, tripcount, bat, bikeid, orgid, fleetid, macid, eotpst, otpst;


    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    private static float MAP_ZOOM_MAX = 3;
    private static float MAP_ZOOM_MIN = 21;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    private static long backButtonCount;
    View backgroundcolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epick_bikes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog_progress = new ProgressDialog(Epick_Bikes.this);
        builderLoading = new AlertDialog.Builder(Epick_Bikes.this);
        maplayout=(LinearLayout)findViewById(R.id.maplayout);
        //Log.d("url:", url);
        listTitle.clear();
        bikearray.clear();


        unlockbtn = (Button) findViewById(R.id.unlockbtn);
        unlockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Epick_Bikes.this, JournyScan_Flashlight.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.fabmenu);
      /*  final FloatingActionButton help = findViewById(R.id.helpid);
        FloatingActionButton gpslock = (FloatingActionButton) findViewById(R.id.gpslock);

        help.setOnClickListener(new View.OnClickListener() {
            @Override3
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(Epick_Bikes.this,help);
                popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        //noinspection SimplifiableIfStatement
                        if (id == R.id.unlock) {
                            unableto_unlockpopup();

                        } else if (id == R.id.endtrip) {
                            unableto_endtrippopup();
                        } else if (id == R.id.damage) {
                            Intent intent = new Intent(Epick_Bikes.this, Report_damages.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else if (id == R.id.settingsfaqs) {
                            Intent intent = new Intent(Epick_Bikes.this, FAQs_page.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });
*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.half_black));


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);
        profileimg = (ImageView) navHeader.findViewById(R.id.profileimg);
        headerprofilename = (TextView) navHeader.findViewById(R.id.headerprofile);


        if (uid!=null) {
            headerprofilename.setText(uid);
        }else if (uidlanch!=null){
            headerprofilename.setText(uidlanch);
        }else if (uidlogin!=null){
            headerprofilename.setText(uidlogin);
        }


        if (url != null) {

            Glide.with(this).load(url)
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profileimg);
        } else if (profileURLlanch != null) {
            Glide.with(this).load(profileURLlanch)
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profileimg);
        } else if (profileURLlogin != null) {
            Glide.with(this).load(profileURLlogin)
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profileimg);
        }
    }

    public void onMenuExpanded() {
        backgroundcolor.setVisibility(View.VISIBLE);
    }

    public void onMenuCollapsed() {
        backgroundcolor.setVisibility(View.GONE);
    }


    public void unabletounlock(View view){
        unableto_unlockpopup();


    }
    public void unabletoend(View view){
        unableto_endtrippopup();

    }
    public void reportdamage(View view){
        Intent intent = new Intent(Epick_Bikes.this, Report_damages.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void faqs(View view){
        Intent intent = new Intent(Epick_Bikes.this, FAQs_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }



    /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.unlock) {
            Intent intent = new Intent(Epick_Bikes.this, Unableto_unlock.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.endtrip) {
            Intent intent = new Intent(Epick_Bikes.this, Unableto_endtrip.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.damage) {
            Intent intent = new Intent(Epick_Bikes.this, Report_damages.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.settingsfaqs) {
            Intent intent = new Intent(Epick_Bikes.this, FAQs_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent intent = new Intent(Epick_Bikes.this, Profile_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.coins) {
            Intent intent = new Intent(Epick_Bikes.this, Coins_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.trip) {
            Intent intent = new Intent(Epick_Bikes.this, Trip_history.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.faqs) {
            Intent intent = new Intent(Epick_Bikes.this, FAQs_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.contactus) {
            Intent intent = new Intent(Epick_Bikes.this, Contact_Us.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.switchlocation) {
            Intent intent = new Intent(Epick_Bikes.this, List_places.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        String getclickval = marker.getTitle();
        Log.d("get val:", String.valueOf(getclickval));
        //int intval= Integer.parseInt(getclickval);
        /*if (getclickval.equals("current Location")) {

        } else {*/

        bikenamepos=nameidL.get(Integer.parseInt(getclickval));
        System.out.println(bikenamepos + "bikenamepos");

        bikepos = bikeidL.get(Integer.parseInt(getclickval));
            System.out.println(bikepos + "bikepossjdbjbd");

            if (orgposval!=null) {

                String AddS = "{\"orgId\":\"" + orgposval + "\",\"access_token\":\"" + accesstoken + "\",\"fleetId\":\"" + bikepos + "\"}";
                Log.d("sending string is :", AddS.toString());
                Log.d("jsnresponse AddS", "---" + AddS);
                String getbikes_url = "https://epickbikes.com/api/v1/bikes/";
                JSONObject lstrmdt = null;

                try {
                    lstrmdt = new JSONObject(AddS);
                    Log.d("jsnresponse....", "---" + AddS);
                   // dialog_progress.setMessage("connecting ...");
                    dialog_progress.show();

                    JSONSenderVolleygetbikesmap(getbikes_url, lstrmdt);
                } catch (JSONException e) {

                }

            }
            else if(orgIdlanch!=null){
                //String AddS = "{\"orgId\":\"test\",\"access_token\":\"" + accesstoken + "\",\"fleetId\":\"" + bikepos + "\"}";

                String AddS = "{\"orgId\":\"" + orgIdlanch + "\",\"access_token\":\"" + accesstoken + "\",\"fleetId\":\"" + bikepos + "\"}";
                Log.d("sending string is :", AddS.toString());
                Log.d("jsnresponse AddS", "---" + AddS);
                String getbikes_url = "https://epickbikes.com/api/v1/bikes/";
                JSONObject lstrmdt = null;

                try {
                    lstrmdt = new JSONObject(AddS);
                    Log.d("jsnresponse....", "---" + AddS);
                   // dialog_progress.setMessage("connecting ...");
                    dialog_progress.show();

                    JSONSenderVolleygetbikesmap(getbikes_url, lstrmdt);
                } catch (JSONException e) {

                }

            }
            else if (orgIdlogin!=null){
                String AddS = "{\"orgId\":\"" + orgIdlogin + "\",\"access_token\":\"" + accesstoken + "\",\"fleetId\":\"" + bikepos + "\"}";
                Log.d("sending string is :", AddS.toString());
                Log.d("jsnresponse AddS", "---" + AddS);
                String getbikes_url = "https://epickbikes.com/api/v1/bikes/";
                JSONObject lstrmdt = null;

                try {
                    lstrmdt = new JSONObject(AddS);
                    Log.d("jsnresponse....", "---" + AddS);
                  //  dialog_progress.setMessage("connecting ...");
                    dialog_progress.show();

                    JSONSenderVolleygetbikesmap(getbikes_url, lstrmdt);
                } catch (JSONException e) {

                }

            }
        //}
        return false;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        getallbikes();


        Log.d("---url---bikearray--", "---"+bikearray);


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void JSONSenderVolleygetbikesmap(String getbikes_url, final JSONObject json)
    {
        Log.d("---bikes url-----", "---"+getbikes_url);
        Log.d("555555", "bike json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getbikes_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSONbikesVolley--", "---"+response.toString());
                        List<String> statusL = new ArrayList<String>();
                        List<String> tripCountL = new ArrayList<String>();
                        List<String> batL = new ArrayList<String>();
                        List<String> bikevalL = new ArrayList<String>();
                        List<String> orgIdL = new ArrayList<String>();
                        //List<String> macIdL = new ArrayList<String>();
                        List<String> fleetIdL = new ArrayList<String>();
                        List<String> eotpL = new ArrayList<String>();
                        List<String> otpL = new ArrayList<String>();

                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");
                                    if (errorCode.contentEquals("true")){
                                        // Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();
                                        JSONArray dataArray = new JSONArray(response.getString("data"));

                                        if (dataArray.length()!=0) {
                                            for (int i = 0; i < dataArray.length(); i++) {
                                                Log.d("array length---", "---" + i);
                                                JSONObject dataObject = new JSONObject(dataArray.get(i).toString());

                                                String status = dataObject.getString("status");
                                                String tripCount = dataObject.getString("tripCount");
                                                String bat = dataObject.getString("bat");
                                                bikeId = dataObject.getString("bikeId");
                                                String orgId = dataObject.getString("orgId");
                                                //String macId = dataObject.getString("macId").toLowerCase();
                                                String fleetId = dataObject.getString("fleetId");
                                                eotp = dataObject.getString("eotp");
                                                Log.d("eotp11", "" + eotp);

                                                otp = dataObject.getString("otp");

                                                statusL.add(status);
                                                tripCountL.add(tripCount);
                                                batL.add(bat);
                                                bikevalL.add(bikeId);
                                                orgIdL.add(orgId);
                                                //macIdL.add(macId);
                                                fleetIdL.add(fleetId);
                                                eotpL.add(eotp);
                                                otpL.add(otp);


                                            }
                                            status = new String[statusL.size()];
                                            tripcount = new String[tripCountL.size()];
                                            bat = new String[batL.size()];
                                            bikeid = new String[bikevalL.size()];
                                            orgid = new String[orgIdL.size()];
                                            // fleetid = new String[macIdL.size()];
                                            macid = new String[fleetIdL.size()];
                                            eotpst = new String[eotpL.size()];
                                            eotpst = new String[otpL.size()];


                                            for (int l = 0; l < otpL.size(); l++) {
                                                status[l] = statusL.get(l);
                                                tripcount[l] = tripCountL.get(l);
                                                bat[l] = batL.get(l);
                                                bikeid[l] = bikevalL.get(l);
                                                orgid[l] = orgIdL.get(l);
                                                //fleetid[l] = macIdL.get(l);
                                                macid[l] = fleetIdL.get(l);
                                                eotpst[l] = eotpL.get(l);
                                                eotpst[l] = otpL.get(l);

                                                Log.d("status ", status[l]);
                                                Log.d("tripcount ", tripcount[l]);
                                                Log.d("bat ", bat[l]);
                                                Log.d("bikeid ", bikeid[l]);
                                                Log.d("orgid ", orgid[l]);
                                                //Log.d("fleetid ", fleetid[l]);
                                                Log.d("macid ", macid[l]);
                                                Log.d("eotpst ", eotpst[l]);
                                                Log.d("duration ", eotpst[l]);


                                            }
                                            bikemap_popup();
                                        }else {

                                            AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
                                            LayoutInflater inflater = (LayoutInflater) Epick_Bikes.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                                            textpopup.setText("No bikes available");

                                            cancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    // TODO Auto-generated method stub
                                                    //delgroup(grpnm);

                                                    dialog.dismiss();
                                                    dialog_progress.dismiss();
                                                }
                                            });
                                            dialog.show();
                                        }
                                    }
                                    else {
                                        // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                        String msg;
                                        if(response.has("message")){
                                            msg=response.getString("message");
                                        }
                                        else{
                                            msg="Unable to Start the trip";
                                        }
                                       // Log.d("errorCode", "" + errorCode);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
                                        LayoutInflater inflater = (LayoutInflater) Epick_Bikes.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                                        textpopup.setText(msg);

                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                // TODO Auto-generated method stub
                                                //delgroup(grpnm);

                                                dialog.dismiss();
                                                dialog_progress.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
                                LayoutInflater inflater = (LayoutInflater) Epick_Bikes.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                                textpopup.setText("Unable to get the data");

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        //delgroup(grpnm);

                                        dialog.dismiss();
                                        dialog_progress.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();
/*
                final AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
                builder.setTitle("Info");
                builder.setMessage(String.valueOf(error));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {

                        dialog_progress.dismiss();
                        dialogInterface1.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
*/
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                //headers.put("Content-Type","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                //return (headers != null || headers.isEmpty()) ? headers : super.getHeaders();
                return headers;
            }
        };

        jsonObjReq.setTag("");
        addToRequestQueue2(jsonObjReq);
    }
    public <T> void addToRequestQueue2(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void unableto_unlockpopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
        LayoutInflater inflater = (LayoutInflater) Epick_Bikes.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
        final View dialogLayout = inflater.inflate(R.layout.content_unableto_unlock,
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

        ImageView callusbtn = (ImageView) dialogLayout.findViewById(R.id.callusbtn);
        ImageView textusbtn = (ImageView) dialogLayout.findViewById(R.id.tellusbtn);
        Button backbtn=(Button) dialogLayout.findViewById(R.id.close_popup);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

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

        dialog.show();
    }

    public void unableto_endtrippopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
        LayoutInflater inflater = (LayoutInflater) Epick_Bikes.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
        final View dialogLayout = inflater.inflate(R.layout.content_unableto_endtrip,
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

        ImageView callusbtn = (ImageView) dialogLayout.findViewById(R.id.callusbtn);
        ImageView textusbtn = (ImageView) dialogLayout.findViewById(R.id.tellusbtn);
        Button backbtn=(Button) dialogLayout.findViewById(R.id.close_popup);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

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

        dialog.show();
    }

    public void bikemap_popup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
        LayoutInflater inflater = (LayoutInflater) Epick_Bikes.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
        final View dialogLayout = inflater.inflate(R.layout.popup_list,
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

        TextView fleetid = (TextView) dialogLayout.findViewById(R.id.fleettxt);
        ListView listval = (ListView) dialogLayout.findViewById(R.id.popuplist);
        Button closepopupbtn=(Button) dialogLayout.findViewById(R.id.close_popup);

        closepopupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        Adapter_popuplist reqAdapter = new Adapter_popuplist(Epick_Bikes.this,bikeid,bat);
        listval.setAdapter(reqAdapter);



        //textnotesT.setMovementMethod(new ScrollingMovementMethod());
        fleetid.setText(bikenamepos);
        builder.setView(dialogLayout);

        dialog.show();
    }

    public void getallbikes() {
        if (orgposval!=null) {

            String AddS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgposval + "\"}";
            Log.d("sending string is :", AddS.toString());
            Log.d("jsnresponse AddS", "---" + AddS);
            String getbikes_url = "https://epickbikes.com/api/v1/fleets/";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(AddS);
                Log.d("jsnresponse....", "---" + AddS);
               // dialog_progress.setMessage("connecting ...");
                dialog_progress.show();

                JSONSenderVolleygetbikes(getbikes_url, lstrmdt);
            } catch (JSONException e) {

            }

        }else if (orgIdlanch!=null){
           // String AddS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"test\"}";

             String AddS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlanch + "\"}";
            Log.d("sending string is :", AddS.toString());
            Log.d("jsnresponse AddS", "---" + AddS);
            String getbikes_url = "https://epickbikes.com/api/v1/fleets/";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(AddS);
                Log.d("jsnresponse....", "---" + AddS);
                //dialog_progress.setMessage("connecting ...");
                dialog_progress.show();

                JSONSenderVolleygetbikes(getbikes_url, lstrmdt);
            } catch (JSONException e) {

            }

        }else if (orgIdlogin!=null){
            String AddS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlogin + "\"}";
            Log.d("sending string is :", AddS.toString());
            Log.d("jsnresponse AddS", "---" + AddS);
            String getbikes_url = "https://epickbikes.com/api/v1/fleets/";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(AddS);
                Log.d("jsnresponse....", "---" + AddS);
              //  dialog_progress.setMessage("connecting ...");
                dialog_progress.show();

                JSONSenderVolleygetbikes(getbikes_url, lstrmdt);
            } catch (JSONException e) {

            }

        }else if ( sharedsaveorgid !=null){
            String AddS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + sharedsaveorgid + "\"}";
            Log.d("sending string is :", AddS.toString());
            Log.d("jsnresponse AddS", "---" + AddS);
            String getbikes_url = "https://epickbikes.com/api/v1/fleets/";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(AddS);
                Log.d("jsnresponse....", "---" + AddS);
               // dialog_progress.setMessage("connecting ...");
                dialog_progress.show();

                JSONSenderVolleygetbikes(getbikes_url, lstrmdt);
            } catch (JSONException e) {

            }
        }
    }
    public void JSONSenderVolleygetbikes(String getbikes_url, final JSONObject json) {
        Log.d("--fleets-url-----", "---" + getbikes_url);
        Log.d("555555", "fleetjson" + json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getbikes_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSON fleet Volley--", "---" + response.toString());

                        if (response!=null){

                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");
                                    if (errorCode.contentEquals("true")) {
                                        // Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();


                                        JSONArray dataArray = new JSONArray(response.getString("data"));
                                        if (dataArray.length()!=0) {

                                            for (int i1 = 0; i1 < dataArray.length(); i1++) {
                                                Log.d("array length---", "---" + i1);
                                                JSONObject dataObject = new JSONObject(dataArray.get(i1).toString());
                                                latfleet = dataObject.getString("lat");
                                                lngfleet = dataObject.getString("lng");
                                                name = dataObject.getString("name");
                                                fleetId = dataObject.getString("fleetId");
                                                String count = dataObject.getString("count");
                                                listTitle.add(count);
                                                Log.d("listTitle values", "listTitle" + listTitle);

                                                double latString = Double.parseDouble(latfleet);
                                                double langString = Double.parseDouble(lngfleet);
                                                Log.d("langituede,latitude", "---" + latString + langString);

                                                final LatLng stationlatlang = new LatLng(latString, langString);
                                                Log.d("stationlatlang", "---" + stationlatlang);

                                                nameidL.add(name);
                                                bikeidL.add(fleetId);
                                                bikearray.add(stationlatlang);
                                                Log.d("bikearray values", "---" + bikearray);

                                                MarkerOptions markerOptions = new MarkerOptions();
                                               /* for (int i = 0; i < bikearray.size(); i++) {
                                                    BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.stationimg);
                                                    List<Marker> Markerlist = new ArrayList<>();

                                                     markerOptions.position(bikearray.get(i))
                                                            .title(String.valueOf(i))
                                                            .snippet(count)
                                                            .icon(icon1);

                                                    //MarkerPostion.setTag(0);
                                                   // Markerlist.add(MarkerPostion);

                                                    adapter = new CustomInfoWindowAdapter(Epick_Bikes.this);
                                                    mMap.setInfoWindowAdapter(adapter);
                                                    //adapter.notifyAll();
                                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bikearray.get(i), 25));
                                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bikearray.get(i), 16));
                                                }*/
                                                //mMap.addMarker(markerOptions).showInfoWindow();

                                            }

                                            for (int i = 0; i < bikearray.size(); i++) {


                                                   /* Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                                                    Bitmap bmp = Bitmap.createBitmap(600, 300, conf);
                                                    Canvas canvas = new Canvas(bmp);
                                                    Paint color = new Paint();

                                                    color.setTextSize(50);
                                                    color.setColor(Color.RED);
                                                    color.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.stationimg), 0, 0, color);
                                                    canvas.drawText(listTitle.get(i), 0, 50, color); // paint defines the text color, stroke width, size
                                                    Log.d("listTitle values", "listTitle111" + listTitle.get(i));
*/
                                                BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.location_pin);

                                                mMap.addMarker(new MarkerOptions()
                                                            .position(bikearray.get(i))
                                                            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.stationimg))
                                                            .title(String.valueOf(i))
                                                            //.icon(BitmapDescriptorFactory.fromBitmap(bmp))
                                                            .anchor(0.5f, 1)
                                                             .icon(icon1)
                                                    );

                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bikearray.get(i), 25));
                                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bikearray.get(i), 10),5000,null);
                                            }
                                            mMap.setOnMarkerClickListener(Epick_Bikes.this);

                                           // bikeidS = new String[bikeidL.size()];

                                        }else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
                                            LayoutInflater inflater = (LayoutInflater) Epick_Bikes.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                                            textpopup.setText("Unable to get the data");

                                            cancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    // TODO Auto-generated method stub
                                                    //delgroup(grpnm);

                                                    dialog.dismiss();
                                                    dialog_progress.dismiss();
                                                }
                                            });
                                            dialog.show();
                                        }

                                    } else {
                                        // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                        String msg;
                                        if(response.has("message")){
                                            msg=response.getString("message");
                                        }
                                        else{
                                            msg="Unable to Start the trip";
                                        }
                                        Log.d("errorCode", "" + errorCode);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
                                        LayoutInflater inflater = (LayoutInflater) Epick_Bikes.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                                        textpopup.setText(msg);

                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                // TODO Auto-generated method stub
                                                //delgroup(grpnm);

                                                dialog.dismiss();
                                                dialog_progress.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
                                LayoutInflater inflater = (LayoutInflater) Epick_Bikes.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                                textpopup.setText("Unable to get the data");

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        //delgroup(grpnm);

                                        dialog.dismiss();
                                        dialog_progress.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                Log.d("my test error-----", "shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();
/*
                final AlertDialog.Builder builder = new AlertDialog.Builder(Epick_Bikes.this);
                builder.setTitle("Info");
                builder.setMessage(String.valueOf(error));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {

                       // dialog_progress.dismiss();
                        dialogInterface1.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
*/
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                //headers.put("Content-Type","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                //return (headers != null || headers.isEmpty()) ? headers : super.getHeaders();
                return headers;
            }
        };
        jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        lat=location.getLatitude();
        lang=location.getLongitude();
        // BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 25));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

       /* MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
      */
        //move map camera
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }



}
