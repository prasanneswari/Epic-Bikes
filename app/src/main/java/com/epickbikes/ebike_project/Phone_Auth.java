package com.epickbikes.ebike_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Phone_Auth extends AppCompatActivity {
    String phoneNumber;
    TextView  resendotp;
    Button verifyCodeButton,backbtn;
    // private Button signOutButton;

    //EditText verifyCodeET;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress;
    AlertDialog.Builder builderLoading;
    static String accesstoken;



    static String orgIdlogin;
    static String coinslogin;
    static String phoneNumberlogin;
    static String uidlogin;
    static String profileURLlogin;
    static String coinsposval;
    static String refreshCoinslogin,refreshposval,newtoken;

    Pinview pinview;
    static int locationdataarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone__auth);

        backbtn = (Button) findViewById(R.id.backbtn);
        verifyCodeButton = (Button) findViewById(R.id.verify_code_b);
        resendotp = (TextView) findViewById(R.id.resendotp);

        //verifyCodeET = (EditText) findViewById(R.id.phone_auth_code);
        dialog_progress = new ProgressDialog(Phone_Auth.this);
        builderLoading = new AlertDialog.Builder(Phone_Auth.this);

        pinview=(Pinview)findViewById(R.id.mypinview);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
              //  Log.d("pinview","value"+pinview.getValue());
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Phone_Auth.this, Register.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phons = Register.phoneNum.getText().toString();

                    String AddS = "{\"phoneNumber\":\"" + phons + "\"}";
                  //  Log.d("sending string is :", AddS.toString());
                    Log.d("jsnresponse AddS", "---" + AddS);
                    String login_url = "https://epickbikes.com/api/v1/login/requestOTP";
                    JSONObject lstrmdt = null;

                    try {
                        lstrmdt = new JSONObject(AddS);
                        Log.d("jsnresponse....", "---" + AddS);
                       // dialog_progress.setMessage("connecting ...");
                        dialog_progress.show();

                        JSONSenderVolleyresendotp(login_url, lstrmdt);

                    } catch (JSONException e) {

                    }
            }
        });


        verifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phons = Register.phoneNum.getText().toString();
               // String otps = verifyCodeET.getText().toString();
                String otps =pinview.getValue();
              //  Log.d("pinview is 999 :", otps);

                if (otps.isEmpty()) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
                    LayoutInflater inflater = (LayoutInflater) Phone_Auth.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                    textpopup.setText("Please enter the OTP");

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            //delgroup(grpnm);

                            dialog.dismiss();
                           // dialog_progress.dismiss();
                        }
                    });
                    dialog.show();
                    } else {

                    String verifyotpS = "{\"phoneNumber\":\"" + phons + "\",\"otp\":\"" + otps + "\"}";
                  //  Log.d("sending string is :", verifyotpS.toString());
                    Log.d("jsnresponse verifyotpS", "---" + verifyotpS);
                    String otp_url = "https://epickbikes.com/api/v1/login/verifyOTP";
                    JSONObject lstrmdt = null;

                    try {
                        lstrmdt = new JSONObject(verifyotpS);
                        Log.d("jsnresponse....", "---" + verifyotpS);
                       // dialog_progress.setMessage("connecting ...");
                        dialog_progress.show();

                        JSONSenderVolleyveryfyotp(otp_url, lstrmdt);

                    } catch (JSONException e) {

                    }
                }
            }
        });
    }


    public void shredsave(String token) {

      //  Log.d("..json_array.. ", String.valueOf(token));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Phone_Auth.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);

        editor.apply();
    }



    public void JSONSenderVolleyveryfyotp(String otp_url, final JSONObject json) {
      //  Log.d("---verificationotp-----", "---" + otp_url);
        Log.d("555555", "verification otp json" + json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                otp_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                     //   Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---" + response.toString());

                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    // String errorDesc = response.getString("error_desc");
                                    //String[] newdata = errorDesc.split("=");
                                    String success = response.getString("success");
                                    if (success.contentEquals("true")) {
                                        // Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();

                                        JSONObject tokenObject = new JSONObject(response.getString("data"));
                                      //  Log.d("token json data ", String.valueOf(tokenObject));

                                        if (tokenObject.length()!=0){
                                            accesstoken = tokenObject.getString("access_token");
                                           // Log.d("access token ", accesstoken);
                                            shredsave(accesstoken);
                                            dialog_progress.dismiss();
                                            notificationtoken();
                                            getusers();

                                        }
                                        else {
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
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


                                    } else  {
                                        String msg;
                                        if(response.has("message")){
                                            msg=response.getString("message");
                                        }
                                        else{
                                            msg="Unable to Start the trip";
                                        }
                                      //  Log.d("errorCode", "" + success);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
                                        LayoutInflater inflater = (LayoutInflater) Phone_Auth.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
                                LayoutInflater inflater = (LayoutInflater) Phone_Auth.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
              //  Log.d("my test error-----", "shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();

               /* String errormesg = String.valueOf(error);
                Log.d(" Error---------", "errormesg: " + errormesg);
                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(Phone_Auth.this);
                    builder1.setTitle("Info");
                    builder1.setMessage(String.valueOf(error));
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface1, int i) {
                            dialog_progress.dismiss();

                            dialogInterface1.dismiss();
                        }
                    });
                    AlertDialog dialog = builder1.create();
                    dialog.show();*/

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
        addToRequestQueue1(jsonObjReq);
    }

    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void JSONSenderVolleyresendotp(String login_url, final JSONObject json) {
      //  Log.d("---url-----", "---" + login_url);
        Log.d("555555", "00000000" + json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                login_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                      //  Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---" + response.toString());

                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");
                                    // String errorDesc = response.getString("error_desc");
                                    //String[] newdata = errorDesc.split("=");

                                    if (errorCode.contentEquals("true")) {
                                        //Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();
                                        pinview.requestFocus();
                                        for (int i = 0; i < pinview.getChildCount() ; i++) {
                                            EditText child = (EditText) pinview.getChildAt(i);
                                            child.setText("");

                                        }


                                    } else {
                                        String msg;
                                        if(response.has("message")){
                                            msg=response.getString("message");

                                        }
                                        else{
                                            msg="Unable to Start the trip";
                                        }
                                      //  Log.d("errorCode", "" + errorCode);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
                                        LayoutInflater inflater = (LayoutInflater) Phone_Auth.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
                                LayoutInflater inflater = (LayoutInflater) Phone_Auth.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
              //  Log.d("my test error-----", "shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();

               /* String errormesg = String.valueOf(error);
                Log.d(" Error---------", "errormesg: " + errormesg);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
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
                    dialog.show();*/

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
        addToRequestQueue2(jsonObjReq);
    }

    public <T> void addToRequestQueue2(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void notificationtoken(){

        String notificationS = "{\"access_token\":\"" + accesstoken + "\",\"notificationKey\":\"" + MyFirebase_InstanceId_Service.refreshedToken + "\"}";
      //  Log.d(" notification token :", notificationS.toString());
        Log.d("jsnresp notificationS", "---" + notificationS);
        String notification_url = "https://epickbikes.com/api/v1/orgUsers/updateNotificationKey";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(notificationS);
            Log.d("jsnresponse....", "---" + notificationS);

            JSONSenderVolleynotifications(notification_url, lstrmdt);

        } catch (JSONException e) {

        }

    }
    public void JSONSenderVolleynotifications(String notification_url, final JSONObject json) {
      //  Log.d("--notification url---", "---" + notification_url);
        Log.d("555555", "notificationS" + json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                notification_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    //    Log.d(" ", response.toString());
                        Log.d("jsonnotificationresp", "---" + response.toString());

                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    // String errorDesc = response.getString("error_desc");
                                    //String[] newdata = errorDesc.split("=");
                                    String success = response.getString("success");
                                    if (success.contentEquals("true")) {
                                        // Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();

                                        JSONObject tokenObject = new JSONObject(response.getString("data"));
                                      //  Log.d("token json data ", String.valueOf(tokenObject));

                                        if (tokenObject.length()!=0){
                                            //dialog_progress.dismiss();


                                        }
                                       /* else {
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
                                            builder.setMessage("Reasponse=Failed to get the json data");

                                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface1, int i) {

                                                    dialogInterface1.dismiss();
                                                }
                                            });

                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }*/


                                    } else  {
                                        String msg;
                                        if(response.has("message")){
                                            msg=response.getString("message");
                                        }
                                        else{
                                            msg="Unable to Start the trip";
                                        }
                                      //  Log.d("errorCode", "" + success);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
                                        LayoutInflater inflater = (LayoutInflater) Phone_Auth.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
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
                VolleyLog.d(" Error---------", "notification: " + String.valueOf(error));
              //  Log.d("my notification error", "shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();

/*
                String errormesg = String.valueOf(error);
                Log.d(" Error-notification", "errormesg: " + errormesg);

                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(Phone_Auth.this);
                    builder1.setTitle("Info");
                    builder1.setMessage(String.valueOf(error));
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface1, int i) {
                           // dialog_progress.dismiss();
                            dialogInterface1.dismiss();
                        }
                    });
                    AlertDialog dialog = builder1.create();
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
        addToRequestQueuenotify(jsonObjReq);
    }

    public <T> void addToRequestQueuenotify(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }


    public void getusers() {

        String AddS = "{\"access_token\":\"" + accesstoken + "\"}";
      //  Log.d("sending string is :", AddS.toString());
        Log.d("jsnresponse AddS", "---" + AddS);
        String getusers_url = "https://epickbikes.com/api/v1/orgUsers/";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(AddS);
            Log.d("jsnresponse....", "---" + AddS);

            JSONSenderVolleylistpalces(getusers_url, lstrmdt);

        } catch (JSONException e) {

        }
        /*dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
*/

    }

    public void JSONSenderVolleylistpalces(String getusers_url, final JSONObject json) {
       // Log.d("--listplaces-url-----", "---" + getusers_url);
        Log.d("555555", "listplaces json" + json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getusers_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                      //  Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---" + response.toString());
                        if (response!=null){

                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");

                                    if (errorCode.contentEquals("true")) {
                                         JSONArray dataArray= new JSONArray(response.getString("data"));
                                        if (dataArray.length()!=0) {

                                            Pref_Storage.setDetail(getApplicationContext(), "login_flag", "0");


                                          //  shrddataarrayval( dataArray.length());


                                            /*Log.d("----dataArray array valu--", "---" + dataArray);
                                            if (dataArray.length() > 1) {
                                                Intent intent = new Intent(Phone_Auth.this, List_places.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);

                                            } else {
                                                Intent intent = new Intent(Phone_Auth.this, Epick_Bikes.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }
*/
                                            locationdataarray=dataArray.length();
                                            for (int i = 0; i < dataArray.length(); i++) {
                                             //   Log.d("array length---", "---" + i);
                                                //locationdataarray=i;
                                              //  Log.d("dataArray array valu", "---" + locationdataarray);

                                                String orgusersarray= String.valueOf(i);
                                               // Log.d("orgusersarray---", "---" + orgusersarray);
                                                shredorgusers(orgusersarray);

                                                JSONObject dataObject = new JSONObject(dataArray.get(i).toString());

                                                coinslogin = dataObject.getString("coins");
                                                orgIdlogin = dataObject.getString("orgId");
                                                String orgName = dataObject.getString("orgName");
                                               // Log.d("orgName values....", "---" + orgName);

                                                phoneNumberlogin = dataObject.getString("phoneNumber");
                                                uidlogin = dataObject.getString("uid");
                                                profileURLlogin = dataObject.getString("profileURL");
                                                refreshCoinslogin = dataObject.getString("refreshCoins");
                                                saveordusersval(coinslogin,orgIdlogin,orgName,phoneNumberlogin,uidlogin,profileURLlogin,refreshCoinslogin);

                                                Intent intent = new Intent(Phone_Auth.this, Terms_Conditions.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }
                                        }else {
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
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
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
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
                                }

                            }else {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
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


                /*NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                }*/
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
              //  Log.d("my test error-----", "shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();
/*
                final AlertDialog.Builder builder = new AlertDialog.Builder(Phone_Auth.this);
                builder.setTitle("Info");
                builder.setMessage(String.valueOf(error));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {
                        //dialog_progress.dismiss();
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
        addToRequestQueue3(jsonObjReq);
    }

    public <T> void addToRequestQueue3(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }


    public void shredorgusers(String locationarray) {

       // Log.d("..json_array.. ", String.valueOf(locationarray));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Phone_Auth.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("array", locationarray);

        editor.apply();
    }

    public void saveordusersval(String coinslanch,String orgIdtrip,String orgName,String phoneNumberlanch,String uidlanch,String profileURLlanch,String refreshCoinslanch) {

       // Log.d("..sharedsave data.. ", String.valueOf(coinslanch));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Phone_Auth.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("coinslanch", coinslanch);
        editor.putString("orgId", orgIdtrip);
        editor.putString("orgName", orgName);
        editor.putString("phoneNumberlanch", phoneNumberlanch);
        editor.putString("uidlanch", uidlanch);
        editor.putString("profileURLlanch", profileURLlanch);
        editor.putString("refreshCoinslanch", refreshCoinslanch);

        editor.apply();

    }
}
