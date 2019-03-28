package com.epickbikes.ebike_project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static com.epickbikes.ebike_project.Phone_Auth.accesstoken;

/**
 * Created by root on 5/12/18.
 */

public class Launching extends AppCompatActivity {
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;

    static String orgIdlanch;
    static String coinslanch;
    static String phoneNumberlanch;
    static String uidlanch;
    static String profileURLlanch;
    static String coinsposval;
    static String refreshCoinslanch,refreshposval,newtoken;
    static String bikeidlanch,otplanch,sharedsaveorgid,eotplanch,latlangtrip_time;
    static JSONArray dataArray;
    static String sharedcoins,sharedordname,sharedphnum,shareduid,sharedprofleurl,sharedrefercoins;

    static int hourslaunch,minuteslaunch,secondslaunch;
    static boolean timestart;
    static long timeInMilliseconds;
    static long differencetm,milliseconds;


    ImageView logo;
    private static int SPLASH_TIME_OUT = 4000;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launching);


        if (Pref_Storage.checkDetail(getApplicationContext(), "login_flag")) {
            // Home Screen


           // Log.d("entering to home", "");

            getusers();


        } else {
            StartAnimations();

            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {

                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(Launching.this, Register.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);

            //Display Login Screen
            /*Log.d("entering to login", "");

            Intent intent = new Intent(Launching.this, Register.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/

        }
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        logo=(ImageView)findViewById(R.id.epic_bike_logo);
        logo.clearAnimation();
        logo.startAnimation(anim);


       /* anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);*/


    }

    public void sharedget() {

        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(Launching.this);
        accesstoken = preferences1.getString("token","Value");

       // Log.d("sharedprefere newtoken", accesstoken);
    }

    public void getusers(){
        sharedget();

        String AddS = "{\"access_token\":\"" + accesstoken + "\"}";
      //  Log.d("sending string is :", AddS.toString());
        Log.d("jsnresponse AddS", "---" + AddS);
        String getusers_url = "https://epickbikes.com/api/v1/orgUsers/";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(AddS);
            JSONSenderVolleylistpalces(getusers_url, lstrmdt);
        } catch (JSONException e) {
            Log.d("Exception....", "some error"+e );
        }
        Log.d("jsnresponse....", "---" + AddS);
        /*dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
*/
    }

    public void locationData(JSONObject response){
        try {
            if(response.has("success")) {
                String errorCode = response.getString("success");


                // String errorDesc = response.getString("error_desc");
                //String[] newdata = errorDesc.split("=");

                if (errorCode.contentEquals("true")) {

                    dataArray = new JSONArray(response.getString("data"));

                    Log.d("dataArray array valu", "---" + dataArray);

                    Log.d("dataArray array length", "---" + dataArray.length());

                    if (dataArray.length() == 1) {

                        for (int i = 0; i < dataArray.length(); i++) {
                            //  Log.d("array length---", "---" + i);

                            JSONObject dataObject = new JSONObject(dataArray.get(i).toString());

                            coinslanch = dataObject.getString("coins");
                            orgIdlanch = dataObject.getString("orgId");
                            //  String orgName = dataObject.getString("orgName");
                            //   Log.d("orgName values....", "---" + orgName);

                            phoneNumberlanch = dataObject.getString("phoneNumber");
                            uidlanch = dataObject.getString("uid");
                            profileURLlanch = dataObject.getString("profileURL");
                            refreshCoinslanch = dataObject.getString("refreshCoins");
                        }
                        Intent intent = new Intent(Launching.this, Epick_Bikes.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {

                        Intent intent = new Intent(Launching.this, List_places.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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
                   // Log.d("errorCode", "" + errorCode);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Launching.this);
                    builder.setMessage(msg.toString());

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface1, int i) {

                            dialog_progress.dismiss();
                            dialogInterface1.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }else{

                //dfg
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void JSONSenderVolleylistpalces(String getusers_url, final JSONObject json)
    {
        //Log.d("---url-----", "---"+getusers_url);
        Log.d("555555", "00000000"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getusers_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                      //  Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---"+response.toString());
                        if (response!=null) {
                            //get shresd prefe data
                            //if(data!=null){
                            // get active trip api call
                            SharedPreferences triptimeshrd = PreferenceManager.getDefaultSharedPreferences(Launching.this);
                            sharedsaveorgid = triptimeshrd.getString("orgidtrip", "null");

                            Log.d("sharedprefere orgid", sharedsaveorgid);

                            if (sharedsaveorgid.equals("null")) {

                                locationData(response);
                            }
                            else{
                                String gettripsS = "{\"access_token\":\"" + accesstoken + "\"}";
                            //    Log.d("sending string is :", gettripsS.toString());
                                Log.d("jsnresponse endS", "---" + gettripsS);
                                String gettrip_url = "https://epickbikes.com/api/v1/trips/active";
                                JSONObject lstrmdt = null;

                                try {
                                    lstrmdt = new JSONObject(gettripsS);
                                    Log.d("jsnresponse....", "---" + gettripsS);

                                    JSONSenderVolleygettrips(gettrip_url, lstrmdt);
                                } catch (JSONException e) {
                                    Log.d("catch....", "---" + e);

                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
               // Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                 Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
               /* final AlertDialog.Builder builder = new AlertDialog.Builder(Launching.this);
                builder.setTitle("Info");
                builder.setMessage(String.valueOf(error));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {

//                        dialog_progress.dismiss();
                        dialogInterface1.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();*/
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
        addToRequestQueue(jsonObjReq);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void JSONSenderVolleygettrips(String gettrip_url, final JSONObject json)
    {
       // Log.d("---url-----", "---"+gettrip_url);
        Log.d("555555", "00000000"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                gettrip_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                      //  Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---"+response.toString());

                        if (response!=null){

                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");
                                    if (errorCode.contentEquals("true")){

                                        JSONObject dataObject = new JSONObject(response.getString("data"));
                                        if (dataObject.length()!=0) {

                                             timestart=true;

                                            String startTime = dataObject.getString("startTime");
                                           // System.out.println("startTime formate"+startTime);

                                            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                            df1.setTimeZone(TimeZone.getTimeZone("GMT"));
                                            String string1 = startTime;//input time
                                            Date result1 = df1.parse(string1);
                                          //  System.out.println("result time"+result1);
                                            //String date = "Tue Dec 18 15:13:31 IST 2018";
                                            DateFormat inputFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zz yyy");
                                            String date = inputFormat.format(result1);
                                            Date d = inputFormat.parse(date);
                                            //DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                                            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                           // System.out.println("out put time"+outputFormat.format(d));

                                            Calendar c = Calendar.getInstance();
                                           // System.out.println("Current time => "+c.getTime());

                                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String currentDateTimeString = df.format(c.getTime());
                                           // System.out.println("current local time"+currentDateTimeString);

                                            // time diff
                                            String t1=outputFormat.format(d); // from json time
                                            String t2=currentDateTimeString; // local time
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                            Date d1 = null;
                                            Date d2 = null;
                                            try {
                                                d1 = format.parse(t1);
                                                d2 = format.parse(t2);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            long diff = d2.getTime() - d1.getTime();
                                             differencetm = diff / 1000;
                                            // System.out.println("Time in seconds: " + differencetm + " seconds.");

                                             milliseconds=differencetm * 1000;
                                          //   System.out.println("Time in milliseconds: " + milliseconds );


                                             SharedPreferences journyscanshrd = PreferenceManager.getDefaultSharedPreferences(Launching.this);
                                             bikeidlanch = journyscanshrd.getString("bikeid", "value");
                                             otplanch = journyscanshrd.getString("otp", "value");
                                             eotplanch = journyscanshrd.getString("eotp", "value");

                                           //  Log.d("sharedprefere orgid", bikeidlanch);
                                           //  Log.d("sharedprefere otplanch", otplanch);
                                           //  Log.d("sharedprefere eotplanch", eotplanch);


                                            SharedPreferences phoneauthshard = PreferenceManager.getDefaultSharedPreferences(Launching.this);
                                            coinslanch = phoneauthshard.getString("coinslanch", "null");
                                            orgIdlanch = phoneauthshard.getString("orgId", "null");
                                            phoneNumberlanch = phoneauthshard.getString("phoneNumberlanch", "null");
                                            uidlanch = phoneauthshard.getString("uidlanch", "null");
                                            profileURLlanch = phoneauthshard.getString("profileURLlanch", "null");
                                            refreshCoinslanch = phoneauthshard.getString("refreshCoinslanch", "null");

                                           // Log.d("sharedprefere orgid", coinslanch);

                                            Intent intent = new Intent(Launching.this, Trip_Time.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }else {
                                            lasttrip();
                                        }
                                    }else {
                                        String msg;
                                        if(response.has("message")){
                                            msg=response.getString("message");
                                        }
                                        else{
                                            msg="Unable to Start the trip";
                                        }
                                      //  Log.d("errorCode", "" + errorCode);
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Launching.this);
                                        builder.setMessage(msg.toString());

                                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface1, int i) {

                                                dialog_progress.dismiss();
                                                dialogInterface1.dismiss();
                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Launching.this);
                                builder.setTitle("Info");
                                builder.setMessage("Reasponse=Unable to get the data");
                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface1, int i) {

                                        dialog_progress.dismiss();
                                        dialogInterface1.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error--------- ", "shulamithi: " + String.valueOf(error));
              //  Log.d(" my test error----- ","shulamithi: " + String.valueOf(error));
                 Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
               /* final AlertDialog.Builder builder = new AlertDialog.Builder(Launching.this);
                builder.setTitle("Info");
                builder.setMessage(String.valueOf(error));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {

                      //  dialog_progress.dismiss();
                        dialogInterface1.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();*/
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
        addToRequestQueuegettrips(jsonObjReq);
    }
    public <T> void addToRequestQueuegettrips(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void lasttrip() {

            String lasttripS = "{\"access_token\":\"" + accesstoken + "\"}";
            //Log.d("sending string is :", lasttripS.toString());
            Log.d("jsnresponse endS", "---" + lasttripS);
            String lasttrip_url = "https://epickbikes.com/api/v1/trips/last";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(lasttripS);
                Log.d("jsnresponse....", "---" + lasttripS);
                JSONSenderVolleylasttrip(lasttrip_url, lstrmdt);
            } catch (JSONException e) {
        }
    }

    public void JSONSenderVolleylasttrip(String lasttrip_url, final JSONObject json)
    {
      //  Log.d("---last trip url-----", "---"+lasttrip_url);
        Log.d("555555", "last trip json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                lasttrip_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                     //   Log.d(" ", response.toString());
                        Log.d("JSON last trip Vol", "---"+response.toString());
                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");

                                    if (errorCode.contentEquals("true")){
                                       // dialog_progress.dismiss();
                                        //Toast.makeText(getApplicationContext(), "Response=successfully strat trip", Toast.LENGTH_LONG).show();

                                        JSONObject dataObject = response.getJSONObject("data");
                                        if (dataObject.length()!=0) {
                                            Log.d("sharedprefere ", "entering to data");

                                            //String rating = dataObject.getString("rating");

                                                if (dataObject.has("rating")){

                                                    SharedPreferences phoneauthshard = PreferenceManager.getDefaultSharedPreferences(Launching.this);
                                                    coinslanch = phoneauthshard.getString("coinslanch", "null");
                                                    orgIdlanch = phoneauthshard.getString("orgId", "null");
                                                    phoneNumberlanch = phoneauthshard.getString("phoneNumberlanch", "null");
                                                    uidlanch = phoneauthshard.getString("uidlanch", "null");
                                                    profileURLlanch = phoneauthshard.getString("profileURLlanch", "null");
                                                    refreshCoinslanch = phoneauthshard.getString("refreshCoinslanch", "null");

                                                  //  Log.d("sharedprefere orgid", coinslanch);
                                                    SharedPreferences.Editor editor = phoneauthshard.edit();
                                                    editor.remove("latlang");
                                                    editor.commit();

                                                    Intent intent = new Intent(Launching.this, Epick_Bikes.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    SharedPreferences phoneauthshard = PreferenceManager.getDefaultSharedPreferences(Launching.this);
                                                    coinslanch = phoneauthshard.getString("coinslanch", "null");
                                                    orgIdlanch = phoneauthshard.getString("orgId", "null");
                                                    phoneNumberlanch = phoneauthshard.getString("phoneNumberlanch", "null");
                                                    uidlanch = phoneauthshard.getString("uidlanch", "null");
                                                    profileURLlanch = phoneauthshard.getString("profileURLlanch", "null");
                                                    refreshCoinslanch = phoneauthshard.getString("refreshCoinslanch", "null");

                                                    latlangtrip_time = phoneauthshard.getString("latlang", "null");


                                                    //  Log.d("sharedprefere orgid", coinslanch);endid

                                                    Intent intent = new Intent(Launching.this, Trip_summary.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }
                                        }
                                        else {
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(Launching.this);
                                            builder.setMessage("Reasponse=Unable to get the data");

                                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface1, int i) {

                                                  //  dialog_progress.dismiss();
                                                    dialogInterface1.dismiss();
                                                }
                                            });

                                            AlertDialog dialog = builder.create();
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
                                      //  Log.d("errorCode", "" + errorCode);
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Launching.this);
                                        builder.setMessage(msg.toString());

                                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface1, int i) {

                                              //  dialog_progress.dismiss();
                                                dialogInterface1.dismiss();
                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Launching.this);
                            builder.setMessage("Reasponse=Unable to get the data");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface1, int i) {

                                   // dialog_progress.dismiss();
                                    dialogInterface1.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
              //  Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                  Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
               /* final AlertDialog.Builder builder = new AlertDialog.Builder(Launching.this);
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
                dialog.show();*/
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
}
class Pref_Storage {
    private static SharedPreferences sharedPreferences = null;

    public static void openPref(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.app_name),
                Context.MODE_PRIVATE);
    }

    public static void deleteKey(Context context, String key) {
        HashMap<String, String> result = new HashMap<String, String>();

        Pref_Storage.openPref(context);
        for (Map.Entry<String, ?> entry : Pref_Storage.sharedPreferences.getAll()
                .entrySet()) {
            result.put(entry.getKey(), (String) entry.getValue());
        }

        boolean b = result.containsKey(key);
        if (b) {
            Pref_Storage.openPref(context);
            SharedPreferences.Editor prefsPrivateEditor = Pref_Storage.sharedPreferences.edit();
            prefsPrivateEditor.remove(key);

            prefsPrivateEditor.commit();
            prefsPrivateEditor = null;
            Pref_Storage.sharedPreferences = null;
        }
    }

    public static void setDetail(Context context, String key, String value) {
        Pref_Storage.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref_Storage.sharedPreferences.edit();
        prefsPrivateEditor.putString(key, value);

        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref_Storage.sharedPreferences = null;
    }

    public static Boolean checkDetail(Context context, String key) {
        HashMap<String, String> result = new HashMap<String, String>();
        Pref_Storage.openPref(context);
        for (Map.Entry<String, ?> entry : Pref_Storage.sharedPreferences.getAll()
                .entrySet()) {
            result.put(entry.getKey(), (String) entry.getValue());
        }
        boolean b = result.containsKey(key);
        return b;
    }
    public static String getDetail(Context context, String key) {
        HashMap<String, String> result = new HashMap<String, String>();
        Pref_Storage.openPref(context);
        for (Map.Entry<String, ?> entry : Pref_Storage.sharedPreferences.getAll()
                .entrySet()) {
            result.put(entry.getKey(), (String) entry.getValue());
        }
        String b = result.get(key);
        return b;
    }
}
