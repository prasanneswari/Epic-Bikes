package com.jts.root.ebike_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.jts.root.ebike_project.Launching.orgIdlanch;
import static com.jts.root.ebike_project.Launching.profileURLlanch;
import static com.jts.root.ebike_project.List_places.orgposval;
import static com.jts.root.ebike_project.List_places.profileposval;
import static com.jts.root.ebike_project.Phone_Auth.accesstoken;
import static com.jts.root.ebike_project.Phone_Auth.orgIdlogin;
import static com.jts.root.ebike_project.Phone_Auth.profileURLlogin;

public class JournyScan_Flashlight extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    Button backbtn,submit,addB,flashon,flashoff;
    ImageButton lightbtn;
    EditText bikeidE;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;
    static String eotp,bat;
    private ZXingScannerView mScannerView;
    private ViewGroup contentFrame;

    private int CAMERA_PERMISSION_CODE = 23;

    static String ScanbikeId,bikeStatus,otp;
    int i;

    int light=0;
    String bikeidS;

    ImageView profileimg;
    String url = profileposval;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_journy_scan__flashlight);

       //menubackscan=(Button)findViewById(R.id.backmenuscan);
        submit=(Button)findViewById(R.id.submitbtn);
        //backbtn=(Button)findViewById(R.id.backbtn);
        //lightbtn=(ImageButton) findViewById(R.id.light);
        flashon=(Button) findViewById(R.id.flashon);
        flashoff=(Button) findViewById(R.id.flashoff);

        bikeidE=(EditText) findViewById(R.id.bikeid);
        dialog_progress = new ProgressDialog(JournyScan_Flashlight.this);
        builderLoading = new AlertDialog.Builder(JournyScan_Flashlight.this);
        /*mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
*/
        bikeidE.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        contentFrame = (ViewGroup) findViewById(R.id.scanner_frame);

        mScannerView = new ZXingScannerView(this);
        mScannerView.setResultHandler(this);

        if (isCameraAccessAllowed()) {
            contentFrame.addView(mScannerView);

        } else {
            requestStoragePermission();

        }

        flashon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView.setFlash(true);

            }
        });
        flashoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView.setFlash(false);

            }
        });
/*
        lightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(light==0) {
                    mScannerView.setFlash(true);
                    lightbtn.setBackgroundResource(R.drawable.light_on);

                    light=1;
                }else {
                    mScannerView.setFlash(false);
                    lightbtn.setBackgroundResource(R.drawable.lightimg);

                    light=0;
                }
            }
        });*/

        /*backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JournyScan_Flashlight.this, Epick_Bikes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 bikeidS = bikeidE.getText().toString().trim();
                 if (bikeidS.isEmpty()) {

                     AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
                     LayoutInflater inflater = (LayoutInflater) JournyScan_Flashlight.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                     textpopup.setText("Please Enter the BikeID or Scan");

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
                     if (orgposval != null) {
                         String AddS = "{\"orgId\":\"" + orgposval + "\",\"access_token\":\"" + accesstoken + "\"}";
                         Log.d("sending string is :", AddS.toString());
                         Log.d("jsnresponse AddS", "---" + AddS);
                         String getbikes_url = "https://epickbikes.com/api/v1/bikes/" + bikeidS;
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
                         String AddS = "{\"orgId\":\"" + orgIdlanch + "\",\"access_token\":\"" + accesstoken + "\"}";
                         Log.d("sending string is :", AddS.toString());
                         Log.d("jsnresponse AddS", "---" + AddS);
                         String getbikes_url = "https://epickbikes.com/api/v1/bikes/" + bikeidS;
                         JSONObject lstrmdt = null;

                         try {
                             lstrmdt = new JSONObject(AddS);
                             Log.d("jsnresponse....", "---" + AddS);
                            // dialog_progress.setMessage("connecting ...");
                             dialog_progress.show();

                             JSONSenderVolleygetbikes(getbikes_url, lstrmdt);
                         } catch (JSONException e) {

                         }

                     }else if (orgIdlogin!=null){
                         String AddS = "{\"orgId\":\"" + orgIdlogin + "\",\"access_token\":\"" + accesstoken + "\"}";
                         Log.d("sending string is :", AddS.toString());
                         Log.d("jsnresponse AddS", "---" + AddS);
                         String getbikes_url = "https://epickbikes.com/api/v1/bikes/" + bikeidS;
                         JSONObject lstrmdt = null;

                         try {
                             lstrmdt = new JSONObject(AddS);
                             Log.d("jsnresponse....", "---" + AddS);
                           //  dialog_progress.setMessage("connecting ...");
                             dialog_progress.show();

                             JSONSenderVolleygetbikes(getbikes_url, lstrmdt);
                         } catch (JSONException e) {

                         }

                     }
                 }
                }
        });


    }
    public void bikeisbooked(){

        AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
        LayoutInflater inflater = (LayoutInflater) JournyScan_Flashlight.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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


      /*  final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("please enter room names");
        dialog.setContentView(R.layout.update_popup_forecast);*/

        Button cancel = (Button) dialogLayout.findViewById(R.id.okbtn);
        TextView textpopup = (TextView) dialogLayout.findViewById(R.id.popuptxt);

        if (bikeStatus.equals("BOOKED")) {
            textpopup.setText("Bike is already in Trip,Please select another bike.’");
        }else if (bikeStatus.equals("UNAVAILABLE")){
            textpopup.setText("Bike is not AVAILABLE");

        }

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



    public void Not_bikeidval(){

        AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
        LayoutInflater inflater = (LayoutInflater) JournyScan_Flashlight.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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


      /*  final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("please enter room names");
        dialog.setContentView(R.layout.update_popup_forecast);*/

        Button cancel = (Button) dialogLayout.findViewById(R.id.okbtn);
        TextView textpopup = (TextView) dialogLayout.findViewById(R.id.popuptxt);

        textpopup.setText("Please Enter Correct BikeId");

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

    public void bikemap_popup(){

        AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
        LayoutInflater inflater = (LayoutInflater) JournyScan_Flashlight.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.bikemap_popup,
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

      /*  final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("please enter room names");
        dialog.setContentView(R.layout.update_popup_forecast);*/

        Button cancel = (Button) dialogLayout.findViewById(R.id.cancelbtn);
        Button confirm = (Button) dialogLayout.findViewById(R.id.confirmbtn);
        final TextView bikeidT = (TextView) dialogLayout.findViewById(R.id.bikeid);
        final TextView rangeT = (TextView) dialogLayout.findViewById(R.id.range);

        //textnotesT.setMovementMethod(new ScrollingMovementMethod());
        //textnotesT.setText(notes);

        builder.setView(dialogLayout);
        bikeidT.setText(ScanbikeId);
        rangeT.setText(bat);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //delgroup(grpnm);
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bikeidS=bikeidT.getText().toString();
                String rangeS=rangeT.getText().toString();

                if (orgposval!=null) {
                    //String starttripS = "{\"access_token\":\"9882d962e3343a77f79f6a0e9de0e8f0:ffab2a6ef2668e0687755a0cb784ba0e\",\"orgId\":test,\"bikeId\":\"13165\"}";
                    String starttripS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgposval + "\",\"bikeId\":\"" + bikeidS + "\"}";
                    Log.d("sending string is :", starttripS.toString());
                    Log.d("jsnresponse starttripS", "---" + starttripS);
                    String starttrip_url = "https://epickbikes.com/api/v1/trips/start";
                    JSONObject lstrmdt = null;

                    try {
                        lstrmdt = new JSONObject(starttripS);
                        Log.d("jsnresponse....", "---" + starttripS);
                      //  dialog_progress.setMessage("connecting ...");
                        dialog_progress.show();

                        JSONSenderVolleystarttrip(starttrip_url, lstrmdt);
                    } catch (JSONException e) {

                    }

                }else if (orgIdlanch!=null){
                    String starttripS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlanch + "\",\"bikeId\":\"" + bikeidS + "\"}";
                    Log.d("sending string is :", starttripS.toString());
                    Log.d("jsnresponse starttripS", "---" + starttripS);
                    String starttrip_url = "https://epickbikes.com/api/v1/trips/start";
                    JSONObject lstrmdt = null;

                    try {
                        lstrmdt = new JSONObject(starttripS);
                        Log.d("jsnresponse....", "---" + starttripS);
                       // dialog_progress.setMessage("connecting ...");
                        dialog_progress.show();

                        JSONSenderVolleystarttrip(starttrip_url, lstrmdt);
                    } catch (JSONException e) {

                    }

                }else if (orgIdlogin!=null){
                    String starttripS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlogin + "\",\"bikeId\":\"" + bikeidS + "\"}";
                    Log.d("sending string is :", starttripS.toString());
                    Log.d("jsnresponse starttripS", "---" + starttripS);
                    String starttrip_url = "https://epickbikes.com/api/v1/trips/start";
                    JSONObject lstrmdt = null;

                    try {
                        lstrmdt = new JSONObject(starttripS);
                        Log.d("jsnresponse....", "---" + starttripS);
                       // dialog_progress.setMessage("connecting ...");
                        dialog_progress.show();

                        JSONSenderVolleystarttrip(starttrip_url, lstrmdt);
                    } catch (JSONException e) {

                    }

                }

                //dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void JSONSenderVolleystarttrip(String starttrip_url, final JSONObject json)
    {
        Log.d("---start trip url-----", "---"+starttrip_url);
        Log.d("555555", "start trip json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                starttrip_url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("JSON start trip Volle", "---"+response.toString());
                       if (response!=null){
                           if (response.has("success")){
                               try {
                                   String errorCode = response.getString("success");
                                   // String errorDesc = response.getString("error_desc");
                                   //String[] newdata = errorDesc.split("=");
                                   if (errorCode.contentEquals("true")){
                                       // Toast.makeText(getApplicationContext(), "Response=successfully strat trip", Toast.LENGTH_LONG).show();
                                       dialog_progress.dismiss();
                                       Intent intent = new Intent(JournyScan_Flashlight.this, Trip_Time.class);
                                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                       startActivity(intent);                                   }
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
                                       AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
                                       LayoutInflater inflater = (LayoutInflater) JournyScan_Flashlight.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                               AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
                               LayoutInflater inflater = (LayoutInflater) JournyScan_Flashlight.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                               textpopup.setText("Reasponse=Unable to get the data");

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

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    Log.d("response","start res : "+response.toString());
                    if(response.statusCode==500){

                        AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
                        LayoutInflater inflater = (LayoutInflater) JournyScan_Flashlight.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                        textpopup.setText("InActiveTrip");

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
                    //Toast.makeText(getApplicationContext(),"errorMessage:"+response.statusCode, Toast.LENGTH_SHORT).show();
                }else{
                    String errorMessage=error.getClass().getSimpleName();
                    if(!TextUtils.isEmpty(errorMessage)){
                        Toast.makeText(getApplicationContext(),"errorMessage:"+errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
               /* VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                Log.d("my test error-----","shulamithi: " + String.valueOf(error));
               // Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
                builder.setTitle("Info");
                builder.setMessage(String.valueOf(error));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {
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
        addToRequestQueue1(jsonObjReq);
    }
    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }
    public void JSONSenderVolleygetbikes(String getbikes_url, final JSONObject json)
    {
        Log.d("---bikeid url-----", "---"+getbikes_url);
        Log.d("555555", "bikeid json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getbikes_url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("JSON bikeid Volley--", "---"+response.toString());

                        if (response!=null){
                            if (response.has("success")){
                                try {

                                    String errorCode = response.getString("success");
                                    if (errorCode.contentEquals("true")){
                                        //  Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();

                                       // JSONArray dataArray = new JSONArray(response.getString("data"));


/*
                                            for (i = 0; i < dataArray.length(); i++) {
                                                Log.d("array length---", "---" + i);
*/

                                            JSONObject dataObject = response.getJSONObject("data");
                                            if(dataObject.length()!=0){

                                                bikeStatus = dataObject.getString("status");
                                                Log.d("array bikestatus---", "---" + bikeStatus);

                                                bat = dataObject.getString("bat");
                                                Log.d("array bat---", "---" + bat);

                                                ScanbikeId = dataObject.getString("bikeId");
                                                Log.d("array scanbikeid---", "---" + ScanbikeId);

                                                //String macId = dataObject.getString("macId").toLowerCase();
                                                eotp = dataObject.getString("eotp");
                                                Log.d("array eotp---", "---" + eotp);

                                                otp = dataObject.getString("otp");
                                                Log.d("array otp---", "---" + otp);


                                                shredsave(ScanbikeId, otp, eotp);

                                                if (bikeStatus.equals("BOOKED")) {
                                                    bikeisbooked();
                                                }else if (bikeStatus.equals("UNAVAILABLE")){

                                                    bikeisbooked();

                                                } else if (bikeStatus.equals("AVAILABLE")){
                                                    // }
                                           /* if (i != 0) {
                                                bikemap_popup();
                                            } else {
                                                Not_bikeidval();
                                            }*/
                                                    bikemap_popup();
                                                }


                                        }else {
                                            Not_bikeidval();
/*
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
                                            builder.setMessage("Reasponse=Failed to get the json data");

                                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface1, int i) {

                                                    dialogInterface1.dismiss();
                                                }
                                            });

                                            AlertDialog dialog = builder.create();
                                            dialog.show();*/
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
                                        AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
                                        LayoutInflater inflater = (LayoutInflater) JournyScan_Flashlight.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
                                LayoutInflater inflater = (LayoutInflater) JournyScan_Flashlight.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(JournyScan_Flashlight.this);
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
        addToRequestQueue(jsonObjReq);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
    private boolean isCameraAccessAllowed() {
        boolean flag = false;
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            flag = true;
        }
        return flag;
    }
    private void requestStoragePermission() {

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contentFrame.addView(mScannerView);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.permission_camera_rationale);
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        }
    }
    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        bikeidE.setText(rawResult.getText().toString());

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }


    public void shredsave(String bikeid,String otp,String eotp) {

        Log.d("..json_array.. ", String.valueOf(bikeid));
        Log.d("..json_array.. ", String.valueOf(otp));
        Log.d("..json_array.. ", String.valueOf(eotp));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JournyScan_Flashlight.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("bikeid", bikeid);
        editor.putString("otp", otp);
        editor.putString("eotp", eotp);

        editor.apply();

    }
    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

    public void ReturnHome(View view){
        super.onBackPressed();
    }
}
