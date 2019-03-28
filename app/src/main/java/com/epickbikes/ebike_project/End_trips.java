package com.epickbikes.ebike_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.goodiebag.pinview.Pinview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class End_trips extends AppCompatActivity {

    Button backbtn,endtripbtn,helpme;
    TextView bikeidT,enterotp;
    Pinview endtripE;
    LinearLayout linerlayout;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;
    //String eotp="13165";

    private long timeRemaining=5000;
    static String _id;

    String url = List_places.profileposval;
    int Count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_end_trips);

        //backmenubtn=(Button)findViewById(R.id.backmenu);
        endtripbtn=(Button)findViewById(R.id.endtripbtn);
        bikeidT=(TextView) findViewById(R.id.bikeid);
/*
        enterotp=(TextView) findViewById(R.id.enterotpT);
        linerlayout=(LinearLayout)findViewById(R.id.linerid);
*/
        //backbtn=(Button)findViewById(R.id.backbtn);
        helpme=(Button)findViewById(R.id.helpme);
        dialog_progress = new ProgressDialog(End_trips.this);
        builderLoading = new AlertDialog.Builder(End_trips.this);
     //   Log.d("jsnresponse endS", "---" + eotp);

        endtripE=(Pinview)findViewById(R.id.endtrip);
        endtripE.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
               // Log.d("pinview","value"+pinview.getValue());
            }
        });

        gettrips();


       /* backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(End_trips.this, End_My_Ride.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
        helpme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(End_trips.this, Contact_Us.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        endtripbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String endtrip=endtripE.getValue();
              //  Log.d("pinview is 999 :", endtrip);

                String bikeid=bikeidT.getText().toString();
                /*String bikeid = bikeidS.substring(2, 6);
                System.out.println("output  bikeid"+bikeid);*/

                if (endtrip.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(End_trips.this);
                    LayoutInflater inflater = (LayoutInflater) End_trips.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                    textpopup.setText("Please enter the eotp");

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

                    }else {

                    if (List_places.orgposval != null) {
                        String endS = "{\"access_token\":\"" + Phone_Auth.accesstoken + "\",\"orgId\":\"" + List_places.orgposval + "\",\"bikeId\":\"" + bikeid + "\",\"eotp\":\"" + endtrip + "\"}";
                      //  Log.d("sending string is :", endS);
                        Log.d("jsnresponse orgposval", "---" + endS);
                        String endtrip_url = "https://epickbikes.com/api/v1/trips/end";
                        JSONObject lstrmdt = null;

                        try {
                            lstrmdt = new JSONObject(endS);
                            Log.d("jsnresponse....", "---" + endS);
                            //dialog_progress.setMessage("connecting ...");
                            dialog_progress.show();

                            JSONSenderVolleyendtrip(endtrip_url, lstrmdt);
                        } catch (JSONException e) {

                        }

                    } else if (Launching.orgIdlanch != null) {
                        String endS = "{\"access_token\":\"" + Phone_Auth.accesstoken + "\",\"orgId\":\"" + Launching.orgIdlanch + "\",\"bikeId\":\"" + bikeid + "\",\"eotp\":\"" + endtrip + "\"}";
                       // Log.d("sending string is :", endS.toString());
                        Log.d("jsnresponse orgIdlanch", "---" + endS);
                        String endtrip_url = "https://epickbikes.com/api/v1/trips/end";
                        JSONObject lstrmdt = null;

                        try {
                            lstrmdt = new JSONObject(endS);
                            Log.d("jsnresponse....", "---" + endS);
                            //dialog_progress.setMessage("connecting ...");
                            dialog_progress.show();

                            JSONSenderVolleyendtrip(endtrip_url, lstrmdt);
                        } catch (JSONException e) {

                        }

                    } else if (Phone_Auth.orgIdlogin != null) {
                        String endS = "{\"access_token\":\"" + Phone_Auth.accesstoken + "\",\"orgId\":\"" + Phone_Auth.orgIdlogin + "\",\"bikeId\":\"" + bikeid + "\",\"eotp\":\"" + endtrip + "\"}";
                      //  Log.d("sending string is :", endS.toString());
                        Log.d("jsnresponse orgIdlogin", "---" + endS);
                        String endtrip_url = "https://epickbikes.com/api/v1/trips/end";
                        JSONObject lstrmdt = null;

                        try {
                            lstrmdt = new JSONObject(endS);
                            Log.d("jsnresponse....", "---" + endS);
                           // dialog_progress.setMessage("connecting ...");
                            dialog_progress.show();

                            JSONSenderVolleyendtrip(endtrip_url, lstrmdt);
                        } catch (JSONException e) {

                        }

                    }
                    /*if (Launching.sharedsaveorgid != null) {
                        String endS = "{\"access_token\":\"" + Phone_Auth.accesstoken + "\",\"orgId\":\"" + Launching.sharedsaveorgid + "\",\"bikeId\":\"" + Launching.bikeidlanch + "\",\"eotp\":\"" + Launching.eotplanch + "\"}";
                       // Log.d("sending string is :", endS.toString());
                        Log.d("jsnresp sharedsaveorgid", "---" + endS);
                        String endtrip_url = "https://epickbikes.com/api/v1/trips/end";
                        JSONObject lstrmdt = null;

                        try {
                            lstrmdt = new JSONObject(endS);
                            Log.d("jsnresponse....", "---" + endS);
                            //dialog_progress.setMessage("connecting ...");
                            dialog_progress.show();

                            JSONSenderVolleyendtrip(endtrip_url, lstrmdt);
                        } catch (JSONException e) {

                        }

                    }*/
                }

            }
        });


    }

    public void gettrips(){
        String gettripsS = "{\"access_token\":\"" + Phone_Auth.accesstoken + "\"}";
      //  Log.d("sending string is :", gettripsS.toString());
        Log.d("jsnresponse endS", "---" + gettripsS);
        String gettrip_url = "https://epickbikes.com/api/v1/trips/active";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(gettripsS);
            Log.d("jsnresponse....", "---" + gettripsS);
           // dialog_progress.setMessage("connecting ...");
            dialog_progress.show();

            JSONSenderVolleygettrips(gettrip_url, lstrmdt);
        } catch (JSONException e) {

        }
        }

    public void JSONSenderVolleygettrips(String gettrip_url, final JSONObject json)
    {
       // Log.d("---active trips url---", "---"+gettrip_url);
        Log.d("555555", "active trips json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                gettrip_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    //    Log.d(" ", response.toString());
                        Log.d("JSON activetrips Volley", "---"+response.toString());

                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");
                                    if (errorCode.contentEquals("true")) {
                                        //Toast.makeText(getApplicationContext(), "Response=successfully strat trip", Toast.LENGTH_LONG).show();
                                        if (response.has("data")){
                                            //String dataObj=response.getString("data");
                                            JSONObject dataObj=new JSONObject(response.getString("data"));
                                            if(dataObj.length()!=0){
                                                dialog_progress.dismiss();
/*
                                                linerlayout.setVisibility(View.VISIBLE);
                                                endtripE.setVisibility(View.VISIBLE);
*/
                                                //something
                                               // Log.d("---enetring to if -----", "---");

                                                _id = dataObj.getString("id");
                                             //   Log.d("---tripId -----", "---"+_id);

                                                if (JournyScan_Flashlight.ScanbikeId != null ) {
                                                    bikeidT.setText(JournyScan_Flashlight.ScanbikeId);
                                                } else if (Launching.bikeidlanch != null ) {
                                                    bikeidT.setText(Launching.bikeidlanch);
                                                }

                                                /*if (ScanbikeId != null && otp != null) {
                                                    bikeidT.setText(ScanbikeId);
                                                    enterotp.setText("'" + "#" + otp + "'");
                                                } else if (bikeidlanch != null && otplanch != null) {
                                                    bikeidT.setText(bikeidlanch);
                                                    enterotp.setText("'" + "#" + otplanch + "'");
                                                }*/
                                            }
                                            else{
                                             //   Log.d("entering to else", "---");

                                                Thread.sleep(1000);

                                                Count+=1;
                                                if(Count==5){
                                                    Intent intent = new Intent(End_trips.this, Trip_summary.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);

                                                }else {
                                                    gettrips();
                                                }

                                            }

                                        }

                                    }
                                    else {
                                        String msg;
                                        if(response.has("message")){
                                            msg=response.getString("message");
                                        }
                                        else{
                                            msg="Unable to Start the trip";
                                        }
                                       // Log.d("errorCode", "" + errorCode);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(End_trips.this);
                                        LayoutInflater inflater = (LayoutInflater) End_trips.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                                dialog_progress.dismiss();
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(End_trips.this);
                            LayoutInflater inflater = (LayoutInflater) End_trips.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
              //  Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();
/*
                final AlertDialog.Builder builder = new AlertDialog.Builder(End_trips.this);
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
        addToRequestQueuegettrips(jsonObjReq);
    }
    public <T> void addToRequestQueuegettrips(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void JSONSenderVolleyendtrip(String endtrip_url, final JSONObject json)
    {
      //  Log.d("--end trip url-----", "---"+endtrip_url);
        Log.d("555555", "end trip json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                endtrip_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                      //  Log.d(" ", response.toString());
                        Log.d("-JSON end trip Volley--", "---"+response.toString());

                        if (response!=null) {

                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");
                                    // String errorDesc = response.getString("error_desc");
                                    //String[] newdata = errorDesc.split("=");

                                    if (errorCode.contentEquals("true")){
                                        //Toast.makeText(getApplicationContext(), "Response=successfully strat trip", Toast.LENGTH_LONG).show();

                                        dialog_progress.dismiss();
                                        Intent intent = new Intent(End_trips.this, Trip_summary.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
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
                                        AlertDialog.Builder builder = new AlertDialog.Builder(End_trips.this);
                                        LayoutInflater inflater = (LayoutInflater) End_trips.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(End_trips.this);
                                LayoutInflater inflater = (LayoutInflater) End_trips.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
               // Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();
/*
                final AlertDialog.Builder builder = new AlertDialog.Builder(End_trips.this);
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
    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

    public void ReturnHome(View view) {
        super.onBackPressed();
    }


}

