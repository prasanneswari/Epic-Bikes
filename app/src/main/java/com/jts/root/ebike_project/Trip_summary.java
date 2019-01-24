package com.jts.root.ebike_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.jts.root.ebike_project.End_trips._id;
import static com.jts.root.ebike_project.Launching.orgIdlanch;
import static com.jts.root.ebike_project.Launching.profileURLlanch;
import static com.jts.root.ebike_project.Launching.sharedprofleurl;
import static com.jts.root.ebike_project.Launching.sharedsaveorgid;
import static com.jts.root.ebike_project.List_places.orgposval;
import static com.jts.root.ebike_project.List_places.profileposval;
import static com.jts.root.ebike_project.Phone_Auth.accesstoken;
import static com.jts.root.ebike_project.Phone_Auth.orgIdlogin;
import static com.jts.root.ebike_project.Phone_Auth.profileURLlogin;

public class Trip_summary extends AppCompatActivity {

    Button backmenubtn,okbtn;
    TextView triptmT,distanceT,fareT;
    RatingBar ratingbar;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;
    String ratingval;

    String url=profileposval;
    ImageView profileimg;
    NavigationView navigationView;
    private View navHeader;
    String _idTripsummary;
    TextView ratingtext;
    private static long backButtonCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_trip_summary);


       // backmenubtn=(Button)findViewById(R.id.backmenu);
        okbtn=(Button)findViewById(R.id.okbtn);
        triptmT=(TextView) findViewById(R.id.triptime);
        distanceT=(TextView) findViewById(R.id.distance);
        fareT=(TextView) findViewById(R.id.fare);
        ratingbar=(RatingBar) findViewById(R.id.ratingBar);
        ratingtext=(TextView)findViewById(R.id.ratingtext);

        dialog_progress = new ProgressDialog(Trip_summary.this);
        builderLoading = new AlertDialog.Builder(Trip_summary.this);
        lasttrip();

        LayerDrawable starts=(LayerDrawable)ratingbar.getProgressDrawable();
        starts.getDrawable(2).setColorFilter(Color.parseColor("#ff8b00"),PorterDuff.Mode.SRC_ATOP);


/*
        Drawable drawable = ratingbar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ff8b00"),PorterDuff.Mode.SRC_ATOP);
*/



        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                ratingval= String.valueOf(ratingbar.getRating());

                //txtRatingValue.setText(String.valueOf(rating));
                Log.d("log message","rating rate"+String.valueOf(rating));

                if (rating<2){
                    ratingtext.setText("Bad");
                }else if (rating<3){
                    ratingtext.setText("Below Average");

                }else if (rating<=4){
                    ratingtext.setText("Average");

                }else if (rating==5){
                    ratingtext.setText("Good");

                }

            }
        });
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ratingval==null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Trip_summary.this);
                    LayoutInflater inflater = (LayoutInflater) Trip_summary.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                    textpopup.setText("Please give me a rating");

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
                else {

                    if (_id != null) {
                        if (orgposval != null) {
                            String ratingS = "{\"tripId\":\"" + _id + "\",\"rating\":\"" + ratingval + "\",\"orgId\":\"" + orgposval + "\",\"access_token\":\"" + accesstoken + "\"}";
                            Log.d("sending string is :", ratingS.toString());
                            Log.d("jsnresponse endS", "---" + ratingS);
                            String rating_url = "https://epickbikes.com/api/v1/trips/rating-update";
                            JSONObject lstrmdt = null;

                            try {
                                lstrmdt = new JSONObject(ratingS);
                                Log.d("jsnresponse....", "---" + ratingS);
                               // dialog_progress.setMessage("connecting ...");
                                dialog_progress.show();

                                JSONSenderVolleyrating(rating_url, lstrmdt);
                            } catch (JSONException e) {

                            }

                        } else if (orgIdlanch != null) {
                            String ratingS = "{\"tripId\":\"" + _id + "\",\"rating\":\"" + ratingval + "\",\"orgId\":\"" + orgIdlanch + "\",\"access_token\":\"" + accesstoken + "\"}";
                            Log.d("sending string is :", ratingS.toString());
                            Log.d("jsnresponse endS", "---" + ratingS);
                            String rating_url = "https://epickbikes.com/api/v1/trips/rating-update";
                            JSONObject lstrmdt = null;

                            try {
                                lstrmdt = new JSONObject(ratingS);
                                Log.d("jsnresponse....", "---" + ratingS);
                              //  dialog_progress.setMessage("connecting ...");
                                dialog_progress.show();

                                JSONSenderVolleyrating(rating_url, lstrmdt);
                            } catch (JSONException e) {

                            }

                        } else if (orgIdlogin != null) {
                            String ratingS = "{\"tripId\":\"" + _id + "\",\"rating\":\"" + ratingval + "\",\"orgId\":\"" + orgIdlogin + "\",\"access_token\":\"" + accesstoken + "\"}";
                            Log.d("sending string is :", ratingS.toString());
                            Log.d("jsnresponse endS", "---" + ratingS);
                            String rating_url = "https://epickbikes.com/api/v1/trips/rating-update";
                            JSONObject lstrmdt = null;

                            try {
                                lstrmdt = new JSONObject(ratingS);
                                Log.d("jsnresponse....", "---" + ratingS);
                              //  dialog_progress.setMessage("connecting ...");
                                dialog_progress.show();

                                JSONSenderVolleyrating(rating_url, lstrmdt);
                            } catch (JSONException e) {

                            }

                        }
                        if (sharedsaveorgid != null) {
                            String ratingS = "{\"tripId\":\"" + _id + "\",\"rating\":\"" + ratingval + "\",\"orgId\":\"" + sharedsaveorgid + "\",\"access_token\":\"" + accesstoken + "\"}";
                            Log.d("sending string is :", ratingS.toString());
                            Log.d("jsnresponse endS", "---" + ratingS);
                            String rating_url = "https://epickbikes.com/api/v1/trips/rating-update";
                            JSONObject lstrmdt = null;

                            try {
                                lstrmdt = new JSONObject(ratingS);
                                Log.d("jsnresponse....", "---" + ratingS);
                              //  dialog_progress.setMessage("connecting ...");
                                dialog_progress.show();

                                JSONSenderVolleyrating(rating_url, lstrmdt);
                            } catch (JSONException e) {

                            }

                        }
                    }
                     else {
                        if (orgposval != null) {
                            String ratingS = "{\"tripId\":\"" + _idTripsummary + "\",\"rating\":\"" + ratingval + "\",\"orgId\":\"" + orgposval + "\",\"access_token\":\"" + accesstoken + "\"}";
                            Log.d("sending string is :", ratingS.toString());
                            Log.d("jsnresponse endS", "---" + ratingS);
                            String rating_url = "https://epickbikes.com/api/v1/trips/rating-update";
                            JSONObject lstrmdt = null;

                            try {
                                lstrmdt = new JSONObject(ratingS);
                                Log.d("jsnresponse....", "---" + ratingS);
                               // dialog_progress.setMessage("connecting ...");
                                dialog_progress.show();

                                JSONSenderVolleyrating(rating_url, lstrmdt);
                            } catch (JSONException e) {

                            }

                        } else if (orgIdlanch != null) {
                            String ratingS = "{\"tripId\":\"" + _idTripsummary + "\",\"rating\":\"" + ratingval + "\",\"orgId\":\"" + orgIdlanch + "\",\"access_token\":\"" + accesstoken + "\"}";
                            Log.d("sending string is :", ratingS.toString());
                            Log.d("jsnresponse endS", "---" + ratingS);
                            String rating_url = "https://epickbikes.com/api/v1/trips/rating-update";
                            JSONObject lstrmdt = null;

                            try {
                                lstrmdt = new JSONObject(ratingS);
                                Log.d("jsnresponse....", "---" + ratingS);
                              //  dialog_progress.setMessage("connecting ...");
                                dialog_progress.show();

                                JSONSenderVolleyrating(rating_url, lstrmdt);
                            } catch (JSONException e) {

                            }

                        } else if (orgIdlogin != null) {
                            String ratingS = "{\"tripId\":\"" + _idTripsummary + "\",\"rating\":\"" + ratingval + "\",\"orgId\":\"" + orgIdlogin + "\",\"access_token\":\"" + accesstoken + "\"}";
                            Log.d("sending string is :", ratingS.toString());
                            Log.d("jsnresponse endS", "---" + ratingS);
                            String rating_url = "https://epickbikes.com/api/v1/trips/rating-update";
                            JSONObject lstrmdt = null;

                            try {
                                lstrmdt = new JSONObject(ratingS);
                                Log.d("jsnresponse....", "---" + ratingS);
                              //  dialog_progress.setMessage("connecting ...");
                                dialog_progress.show();

                                JSONSenderVolleyrating(rating_url, lstrmdt);
                            } catch (JSONException e) {

                            }

                        }
                        if (sharedsaveorgid != null) {
                            String ratingS = "{\"tripId\":\"" + _idTripsummary + "\",\"rating\":\"" + ratingval + "\",\"orgId\":\"" + sharedsaveorgid + "\",\"access_token\":\"" + accesstoken + "\"}";
                            Log.d("sending string is :", ratingS.toString());
                            Log.d("jsnresponse endS", "---" + ratingS);
                            String rating_url = "https://epickbikes.com/api/v1/trips/rating-update";
                            JSONObject lstrmdt = null;

                            try {
                                lstrmdt = new JSONObject(ratingS);
                                Log.d("jsnresponse....", "---" + ratingS);
                             //   dialog_progress.setMessage("connecting ...");
                                dialog_progress.show();

                                JSONSenderVolleyrating(rating_url, lstrmdt);
                            } catch (JSONException e) {

                            }

                        }

                    }
                }
            }
        });


    }


    public void lasttrip(){


        String lasttripS = "{\"access_token\":\"" + accesstoken + "\"}";
        Log.d("sending string is :", lasttripS.toString());
        Log.d("jsnresponse endS", "---" + lasttripS);
        String lasttrip_url = "https://epickbikes.com/api/v1/trips/last";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(lasttripS);
            Log.d("jsnresponse....", "---" + lasttripS);
           // dialog_progress.setMessage("connecting ...");
            dialog_progress.show();

            JSONSenderVolleylasttrip(lasttrip_url, lstrmdt);
        } catch (JSONException e) {

        }
    }
    public void JSONSenderVolleylasttrip(String lasttrip_url, final JSONObject json)
    {
        Log.d("---last trip url-----", "---"+lasttrip_url);
        Log.d("555555", "last trip json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                lasttrip_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("JSON last trip Volley--", "---"+response.toString());
                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");

                                    if (errorCode.contentEquals("true")){
                                        dialog_progress.dismiss();
                                        //Toast.makeText(getApplicationContext(), "Response=successfully strat trip", Toast.LENGTH_LONG).show();

                                        JSONObject dataObject = response.getJSONObject("data");
                                        if (dataObject.length()!=0) {

                                            String cost = dataObject.getString("cost");
                                            String bikeid = dataObject.getString("bikeId");
                                            String startTime = dataObject.getString("startTime");
                                            String orgposval = dataObject.getString("orgId");
                                            String phoneNumber = dataObject.getString("phoneNumber");
                                            String duration = dataObject.getString("duration");
                                            _idTripsummary = dataObject.getString("id");
                                            Log.d("---_idTripsummary -----", "---"+_idTripsummary);


/*
                                            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                            df1.setTimeZone(TimeZone.getTimeZone("GMT"));
                                            String string1 = startTime;//input time
                                            Date result1 = df1.parse(string1);
                                            System.out.println("result time"+result1);
                                            //String date = "Tue Dec 18 15:13:31 IST 2018";
                                            DateFormat inputFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zz yyy");
                                            String date = inputFormat.format(result1);
                                            Date d = inputFormat.parse(date);
                                            //DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                                            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            System.out.println("out put time"+outputFormat.format(d));

                                            String t1=outputFormat.format(d);

                                            String totaldate = t1.substring(0, 10);
                                            System.out.println("output totola date"+totaldate);

                                            String totaltime = t1.substring(11, 18);
                                            System.out.println("output totola time"+totaldate);
*/



                                            triptmT.setText(duration);
                                            fareT.setText(cost+"Coins");
                                        }else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Trip_summary.this);
                                            LayoutInflater inflater = (LayoutInflater) Trip_summary.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Trip_summary.this);
                                        LayoutInflater inflater = (LayoutInflater) Trip_summary.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                            }
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Trip_summary.this);
                            LayoutInflater inflater = (LayoutInflater) Trip_summary.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();
/*
                final AlertDialog.Builder builder = new AlertDialog.Builder(Trip_summary.this);
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
        addToRequestQueuelasttrip(jsonObjReq);
    }
    public <T> void addToRequestQueuelasttrip(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void JSONSenderVolleyrating(String rating_url, final JSONObject json)
    {
        Log.d("---rating url-----", "---"+rating_url);
        Log.d("555555", "rating json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                rating_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("-JSON rating Volley--", "---"+response.toString());
                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");
                                    if (errorCode.contentEquals("true")){
                                        dialog_progress.dismiss();
                                        //Toast.makeText(getApplicationContext(), "Response=successfully strat trip", Toast.LENGTH_LONG).show();

                                        SharedPreferences getlistplacesval = PreferenceManager.getDefaultSharedPreferences(Trip_summary.this);
                                        String dataArray = getlistplacesval.getString("array", "null");

                                        if (dataArray.length() > 1) {
                                            Intent intent = new Intent(Trip_summary.this, List_places.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }else {
                                            Intent intent = new Intent(Trip_summary.this, Epick_Bikes.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }


                                        JSONObject dataObject = new JSONObject(response.getString("data"));

                                       /* String cost = dataObject.getString("cost");
                                        String bikeId = dataObject.getString("bikeId");
                                        String startTime = dataObject.getString("startTime");
                                        String orgId = dataObject.getString("orgId");
                                        String phoneNumber = dataObject.getString("phoneNumber");
                                        String rating = dataObject.getString("rating");*/

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
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Trip_summary.this);
                                        LayoutInflater inflater = (LayoutInflater) Trip_summary.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(Trip_summary.this);
                                LayoutInflater inflater = (LayoutInflater) Trip_summary.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Trip_summary.this);
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
        addToRequestQueuerating(jsonObjReq);
    }
    public <T> void addToRequestQueuerating(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
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
}
