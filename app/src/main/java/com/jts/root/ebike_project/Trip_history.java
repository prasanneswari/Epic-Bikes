package com.jts.root.ebike_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.jts.root.ebike_project.Launching.orgIdlanch;
import static com.jts.root.ebike_project.Launching.profileURLlanch;
import static com.jts.root.ebike_project.Launching.sharedsaveorgid;
import static com.jts.root.ebike_project.List_places.orgposval;
import static com.jts.root.ebike_project.List_places.profileposval;
import static com.jts.root.ebike_project.Phone_Auth.accesstoken;
import static com.jts.root.ebike_project.Phone_Auth.orgIdlogin;
import static com.jts.root.ebike_project.Phone_Auth.profileURLlogin;

public class Trip_history extends AppCompatActivity {


    ListView triphistorylst;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;
    String[] cost = {};
    String[] bikeId = {};
    String[] startTime = {};
    String[] currentdate = {"12/21/1234"};

    String[] orgId = {};
    String[] phoneNumber = {};
    String[] endTime = {};
    String[] co2 = {};
    String[] calories = {};
    String[] duration = {};

    ImageView profileimg;
    Button backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_trip_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        triphistorylst=(ListView)findViewById(R.id.historylst);
        //backbtn=(Button) findViewById(R.id.backbtn);

        dialog_progress = new ProgressDialog(Trip_history.this);
        builderLoading = new AlertDialog.Builder(Trip_history.this);
        triphistory_post();

        /*backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (triptime==true){
                    Intent intent = new Intent(Trip_history.this, Trip_Time.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Trip_history.this, Epick_Bikes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });*/

    }

    public void triphistory_post(){

        if (orgposval!=null) {

            String get_tripsS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgposval + "\",\"offset\":" + 2 + "}";
            Log.d("sending string is :", get_tripsS.toString());
            Log.d("jsnresponse get_tripsS", "---" + get_tripsS);
            String gettrips_url = "https://epickbikes.com/api/v1/trips/";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(get_tripsS);
                Log.d("jsnresponse....", "---" + get_tripsS);
              //  dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                JSONSenderVolley(gettrips_url, lstrmdt);
            } catch (JSONException e) {

            }

        }else if (orgIdlanch!=null){
            String get_tripsS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlanch + "\",\"offset\":" + 2 + "}";
            Log.d("sending string is :", get_tripsS.toString());
            Log.d("jsnresponse get_tripsS", "---" + get_tripsS);
            String gettrips_url = "https://epickbikes.com/api/v1/trips/";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(get_tripsS);
                Log.d("jsnresponse....", "---" + get_tripsS);
              //  dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                JSONSenderVolley(gettrips_url, lstrmdt);
            } catch (JSONException e) {

            }

        }else if (orgIdlogin!=null){
            String get_tripsS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlogin + "\",\"offset\":" + 2 + "}";
            Log.d("sending string is :", get_tripsS.toString());
            Log.d("jsnresponse get_tripsS", "---" + get_tripsS);
            String gettrips_url = "https://epickbikes.com/api/v1/trips/";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(get_tripsS);
                Log.d("jsnresponse....", "---" + get_tripsS);
              //  dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                JSONSenderVolley(gettrips_url, lstrmdt);
            } catch (JSONException e) {

            }

        }else if (sharedsaveorgid!=null){
            String get_tripsS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + sharedsaveorgid + "\",\"offset\":" + 2 + "}";
            Log.d("sending string is :", get_tripsS.toString());
            Log.d("jsnresponse get_tripsS", "---" + get_tripsS);
            String gettrips_url = "https://epickbikes.com/api/v1/trips/";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(get_tripsS);
                Log.d("jsnresponse....", "---" + get_tripsS);
               // dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                JSONSenderVolley(gettrips_url, lstrmdt);
            } catch (JSONException e) {

            }

        }
    }
    public void JSONSenderVolley(String gettrips_url, final JSONObject json)
    {
        Log.d("---trips url-----", "---"+gettrips_url);
        Log.d("555555", "trips json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                gettrips_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSON trips Volley--", "---"+response.toString());
                        List<String> costL = new ArrayList<String>();
                        List<String> bikeIdL = new ArrayList<String>();
                        List<String> startTimeL = new ArrayList<String>();
                        List<String> orgIdL = new ArrayList<String>();
                        List<String> phoneNumberL = new ArrayList<String>();
                        List<String> co2L = new ArrayList<String>();
                        List<String> caloriesL = new ArrayList<String>();
                        List<String> endTimeL = new ArrayList<String>();
                        List<String> durationL = new ArrayList<String>();
                        List<String> totaldateL = new ArrayList<String>();

                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");

                                    if (errorCode.contentEquals("true")){
                                        //Toast.makeText(getApplicationContext(), "Response=successfully strat trip", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();

                                        JSONArray dataArray = new JSONArray(response.getString("data"));
                                        if (dataArray.length()!=0) {


                                            for (int i = 0; i < dataArray.length(); i++) {
                                                Log.d("array length---", "---" + i);
                                                JSONObject dataObject = new JSONObject(dataArray.get(i).toString());

                                                String costS = dataObject.getString("cost");
                                                String bikeIdS = dataObject.getString("bikeId");
                                                String startTime = dataObject.getString("startTime");
                                                String orgIdS = dataObject.getString("orgId");
                                                String phoneNumberS = dataObject.getString("phoneNumber");
                                                String co2S = dataObject.getString("co2");
                                                String caloriesS = dataObject.getString("calories");
                                                String durationS = dataObject.getString("duration");
                                                String endTimeS = dataObject.getString("endTime");


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


                                                //DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");


                                                costL.add(costS);
                                                bikeIdL.add(bikeIdS);
                                                startTimeL.add(totaltime);
                                                orgIdL.add(orgIdS);
                                                phoneNumberL.add(phoneNumberS);
                                                endTimeL.add(endTimeS);
                                                co2L.add(co2S);
                                                caloriesL.add(caloriesS);
                                                durationL.add(durationS);
                                                totaldateL.add(totaldate);

                                            }
                                            cost = new String[costL.size()];
                                            bikeId = new String[bikeIdL.size()];
                                            startTime = new String[startTimeL.size()];
                                            orgId = new String[orgIdL.size()];
                                            phoneNumber = new String[phoneNumberL.size()];
                                            endTime = new String[endTimeL.size()];
                                            co2 = new String[co2L.size()];
                                            calories = new String[caloriesL.size()];
                                            duration = new String[durationL.size()];
                                            currentdate = new String[totaldateL.size()];


                                            for (int l = 0; l < startTimeL.size(); l++) {
                                                cost[l] = costL.get(l);
                                                bikeId[l] = bikeIdL.get(l);
                                                startTime[l] = startTimeL.get(l);
                                                orgId[l] = orgIdL.get(l);
                                                phoneNumber[l] = phoneNumberL.get(l);
                                                endTime[l] = endTimeL.get(l);
                                                co2[l] = co2L.get(l);
                                                calories[l] = caloriesL.get(l);
                                                duration[l] = durationL.get(l);
                                                currentdate[l] = totaldateL.get(l);

                                                Log.d("cost ", cost[l]);
                                                Log.d("bikeId ", bikeId[l]);
                                                Log.d("startTime ", startTime[l]);
                                                Log.d("orgId ", orgId[l]);
                                                Log.d("phoneNumber ", phoneNumber[l]);
                                                Log.d("endTime ", endTime[l]);
                                                Log.d("co2 ", co2[l]);
                                                Log.d("calories ", calories[l]);
                                                Log.d("duration ", duration[l]);
                                                Log.d("currentdate ", currentdate[l]);

                                            }


                                            Adapter_triphistory reqAdapter = new Adapter_triphistory(Trip_history.this, cost,currentdate,startTime, duration);
                                            triphistorylst.setAdapter(reqAdapter);
                                        }else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Trip_history.this);
                                            LayoutInflater inflater = (LayoutInflater) Trip_history.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Trip_history.this);
                                        LayoutInflater inflater = (LayoutInflater) Trip_history.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Trip_history.this);
                                LayoutInflater inflater = (LayoutInflater) Trip_history.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Trip_history.this);
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
    public void ReturnHome(View view){
        super.onBackPressed();
    }
}
