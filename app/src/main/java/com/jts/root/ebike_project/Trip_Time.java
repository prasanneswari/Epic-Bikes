package com.jts.root.ebike_project;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.jts.root.ebike_project.Epick_Bikes.lang;
import static com.jts.root.ebike_project.Epick_Bikes.lat;
import static com.jts.root.ebike_project.JournyScan_Flashlight.ScanbikeId;
import static com.jts.root.ebike_project.JournyScan_Flashlight.otp;
import static com.jts.root.ebike_project.Launching.bikeidlanch;
import static com.jts.root.ebike_project.Launching.differencetm;
import static com.jts.root.ebike_project.Launching.milliseconds;
import static com.jts.root.ebike_project.Launching.orgIdlanch;
import static com.jts.root.ebike_project.Launching.otplanch;
import static com.jts.root.ebike_project.Launching.profileURLlanch;
import static com.jts.root.ebike_project.Launching.secondslaunch;
import static com.jts.root.ebike_project.Launching.sharedprofleurl;
import static com.jts.root.ebike_project.Launching.timeInMilliseconds;
import static com.jts.root.ebike_project.Launching.timestart;
import static com.jts.root.ebike_project.List_places.orgposval;
import static com.jts.root.ebike_project.List_places.profileURL;
import static com.jts.root.ebike_project.Phone_Auth.accesstoken;
import static com.jts.root.ebike_project.Phone_Auth.orgIdlogin;
import static com.jts.root.ebike_project.Phone_Auth.profileURLlogin;

public class Trip_Time extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    TextView bikeidEmap, unlockotpmap, triptmmap;
    Button endbtn;


    ImageView profileimg;
    NavigationView navigationView;
    private View navHeader;
    String url = profileURL;
    RequestQueue sch_RequestQueue;

    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long timeSwap1 = 0L;
    long finalTime = 0L;

    public static final long MILLIS_TO_MINITS=60000;
    public static final long MILLIS_TO_HOURS=3600000;


    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    int hours,minutes, seconds;
    int hours1,minutes1, seconds1;

    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;

    private static long backButtonCount;

    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings;
    private LinearLayout unabletoend;
    private LinearLayout unabletounlock;
    private LinearLayout reportdamage;
    private LinearLayout faqs;
    View backgroungdim;
    boolean mMapIsTouched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epik_bike_map);
        bikeidEmap = (TextView) findViewById(R.id.bikeidmap);
        unlockotpmap = (TextView) findViewById(R.id.unlockotpmap);
        triptmmap = (TextView) findViewById(R.id.triptmmap);
        endbtn = (Button) findViewById(R.id.endbtn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog_progress = new ProgressDialog(Trip_Time.this);
        builderLoading = new AlertDialog.Builder(Trip_Time.this);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            checkLocationPermission();
        }


        if (ScanbikeId != null && otp != null) {
            bikeidEmap.setText("#"+ScanbikeId);
            unlockotpmap.setText("OTP:"+otp);

        } else if (bikeidlanch != null && otplanch != null) {
            bikeidEmap.setText("#"+bikeidlanch);
            unlockotpmap.setText("OTP:"+otplanch);

        }


        if (orgposval != null) {
            shredsave(orgposval);
        } else if (orgIdlanch != null) {
            shredsave(orgIdlanch);

        } else if (orgIdlogin != null) {
            shredsave(orgIdlogin);

        }
        if (timestart==true){

            Log.d("timeInMilliseconds","timeInMilliseconds"+milliseconds);
            //startTime = differencetm;

            timeSwap1 = milliseconds;
            timeSwap = timeSwap1;
            startTime = SystemClock.uptimeMillis();
            myHandler.postDelayed(updateTimerMethod, 0);

        }else {
            startTime = SystemClock.uptimeMillis();
            myHandler.postDelayed(updateTimerMethod, 0);

        }
        endbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endmessage();

            }
        });



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open1, R.string.navigation_drawer_close2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.half_black));


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);
        profileimg = (ImageView) navHeader.findViewById(R.id.profileimg);

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



        fabSettings = (FloatingActionButton) this.findViewById(R.id.helpid);
        backgroungdim=(View) findViewById(R.id.background_dimmer) ;

        faqs = (LinearLayout) this.findViewById(R.id.faqs);
        reportdamage = (LinearLayout) this.findViewById(R.id.reportdamage);
        unabletoend= (LinearLayout) this.findViewById(R.id.uanbletoend);
        unabletounlock= (LinearLayout) this.findViewById(R.id.unableunlock);


        unabletoend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                unableto_endtrippopup();

            }
        });
        unabletounlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                unableto_unlockpopup();

            }
        });
        reportdamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trip_Time.this, Report_damages.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trip_Time.this, FAQs_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                    backgroungdim.setVisibility(View.GONE);

                } else {
                    openSubMenusFab();
                    backgroungdim.setVisibility(View.VISIBLE);

                }
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();

        backgroungdim.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Interpret MotionEvent data
                // Handle touch here
                mMapIsTouched=true;
                backgroungdim.setVisibility(View.GONE);
                faqs.setVisibility(View.INVISIBLE);
                reportdamage.setVisibility(View.INVISIBLE);
                unabletoend.setVisibility(View.INVISIBLE);
                unabletounlock.setVisibility(View.INVISIBLE);

                fabExpanded=false;

                return true;
            }
        });

    }
    private void closeSubMenusFab(){
        faqs.setVisibility(View.INVISIBLE);
        reportdamage.setVisibility(View.INVISIBLE);
        unabletoend.setVisibility(View.INVISIBLE);
        unabletounlock.setVisibility(View.INVISIBLE);

        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        faqs.setVisibility(View.VISIBLE);
        reportdamage.setVisibility(View.VISIBLE);
        unabletoend.setVisibility(View.VISIBLE);
        unabletounlock.setVisibility(View.VISIBLE);

        //Change settings icon to 'X' icon
        fabExpanded = true;
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent intent = new Intent(Trip_Time.this, Profile_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.coins) {
            Intent intent = new Intent(Trip_Time.this, Coins_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.trip) {
            Intent intent = new Intent(Trip_Time.this, Trip_history.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.faqs) {
            Intent intent = new Intent(Trip_Time.this, FAQs_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.contactus) {
            Intent intent = new Intent(Trip_Time.this, Contact_Us.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        else if (id == R.id.switchlocation) {
            /*Intent intent = new Intent(Trip_Time.this, List_places.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/

            AlertDialog.Builder builder = new AlertDialog.Builder(Trip_Time.this);
            LayoutInflater inflater = (LayoutInflater) Trip_Time.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

            textpopup.setText("You are in trip ! So unable to Switch the Location. Please End the Trip if you want !");

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }


    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void shredsave(String orgid) {

        Log.d("..sharedsave data.. ", String.valueOf(orgid));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Trip_Time.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("orgid", orgid);

        editor.apply();

    }
    public void endmessage(){

        String endmessageS = "{\"access_token\":\"" + accesstoken + "\"}";
        Log.d("sending endmessageS:", endmessageS.toString());
        Log.d("jsnresponse endmessageS", "---" + endmessageS);
        String endmessage_url = "https://epickbikes.com/api/v1/trips/endMessage";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(endmessageS);
           // dialog_progress.setMessage("connecting ...");
            dialog_progress.show();

            JSONSenderVolleyendmessage(endmessage_url, lstrmdt);
        } catch (JSONException e) {
            Log.d("Exception....", "some error"+e );
        }
        Log.d("jsnresponse....", "---" + endmessageS);


    }
    public void JSONSenderVolleyendmessage(String endmessage_url, final JSONObject json)
    {
        Log.d("---endmessage url---", "---"+endmessage_url);
        Log.d("555555", "end message json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                endmessage_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---"+response.toString());
                        if (response!=null) {
                            try {
                                if(response.has("success")) {
                                    String errorCode = response.getString("success");


                                    // String errorDesc = response.getString("error_desc");
                                    //String[] newdata = errorDesc.split("=");

                                    if (errorCode.contentEquals("true")) {

                                        Intent intent = new Intent(Trip_Time.this, Proceed_to_nextstep.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog_progress.dismiss();

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
                                        Log.d("errorCode", "" + errorCode);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Trip_Time.this);
                                        LayoutInflater inflater = (LayoutInflater) Trip_Time.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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


                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Trip_Time.this);
                                    LayoutInflater inflater = (LayoutInflater) Trip_Time.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                            } catch (JSONException e) {
                                e.printStackTrace();
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Trip_Time.this);
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
        addToRequestQueue1(jsonObjReq);
    }
    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }


  private Runnable updateTimerMethod = new Runnable() {

      public void run() {
          //Log.d("log","timeswap enter"+timeSwap);
          timeInMillies = SystemClock.uptimeMillis() - startTime;
          finalTime = timeSwap + timeInMillies;
          //Log.d("finalTime-----","finalTime " +finalTime);

          seconds=(int)((finalTime/1000)%60);
          minutes=(int)(((finalTime/MILLIS_TO_MINITS))%60);
          hours=(int)((finalTime/(MILLIS_TO_HOURS))%24);


         /* seconds = (int) (finalTime / 1000);
          //Log.d("seconds-----","seconds " +seconds);

          minutes = seconds / 60;
          //Log.d("minutes-----","minutes " +minutes);

          hours = minutes / 60;
          //Log.d("hours-----","hours " +hours);

          // int milliseconds = (int) (finalTime % 1000);

          seconds = seconds % 60;
          //Log.d("seconds11-----","seconds11 " +seconds);*/

          triptmmap.setText("" + String.format("%02d", hours) + ":" + "" + String.format("%02d", minutes)+ ":" + "" + String.format("%02d", seconds));
          myHandler.postDelayed(this, 0);

      }
  };

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

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        LatLng latLng = new LatLng(lat, lang);
        //Place current location marker
       /* LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        lat=location.getLatitude();
        lang=location.getLongitude();*/
        // BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.red_cycle));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 25));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        //move map camera
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
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
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void unableto_unlockpopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Trip_Time.this);
        LayoutInflater inflater = (LayoutInflater) Trip_Time.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(Trip_Time.this);
        LayoutInflater inflater = (LayoutInflater) Trip_Time.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

}
