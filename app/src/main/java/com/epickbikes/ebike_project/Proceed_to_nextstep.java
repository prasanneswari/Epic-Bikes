package com.epickbikes.ebike_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.epickbikes.ebike_project.Phone_Auth.accesstoken;


public class Proceed_to_nextstep extends AppCompatActivity {

    Button backbtn,proceesnextstep;
    TextView bikeid;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_to_nextstep);
        //backbtn=(Button)findViewById(R.id.backbtn);
        proceesnextstep=(Button)findViewById(R.id.processnextstep);
        bikeid=(TextView)findViewById(R.id.bikeid);
        dialog_progress = new ProgressDialog(Proceed_to_nextstep.this);
        builderLoading = new AlertDialog.Builder(Proceed_to_nextstep.this);


       /* backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Proceed_to_nextstep.this, Trip_Time.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/

        proceesnextstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endmessage();

            }
        });

        if (JournyScan_Flashlight.ScanbikeId != null) {
            bikeid.setText("#"+ JournyScan_Flashlight.ScanbikeId);
        } else if (Launching.bikeidlanch != null ) {
            bikeid.setText("#"+ Launching.bikeidlanch);
        }

    }
    public void ReturnHome(View view){
        super.onBackPressed();

    }

    public void endmessage(){

        String endmessageS = "{\"access_token\":\"" + accesstoken + "\"}";
        //  Log.d("sending endmessageS:", endmessageS.toString());
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
        //  Log.d("jsnresponse....", "---" + endmessageS);


    }
    public void JSONSenderVolleyendmessage(String endmessage_url, final JSONObject json)
    {
        Log.d("---endmessage url---", "---"+endmessage_url);
        //  Log.d("555555", "end message json"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                endmessage_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //  Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---"+response.toString());
                        if (response!=null) {
                            try {
                                if(response.has("success")) {
                                    String errorCode = response.getString("success");


                                    // String errorDesc = response.getString("error_desc");
                                    //String[] newdata = errorDesc.split("=");

                                    if (errorCode.contentEquals("true")) {

                                        Intent intent = new Intent(Proceed_to_nextstep.this, End_My_Ride.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog_progress.dismiss();
                                        //  triptime_class=false;

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
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Proceed_to_nextstep.this);
                                        LayoutInflater inflater = (LayoutInflater) Proceed_to_nextstep.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Proceed_to_nextstep.this);
                                    LayoutInflater inflater = (LayoutInflater) Proceed_to_nextstep.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                //  Log.d("my test error-----","shulamithi: " + String.valueOf(error));
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


}
