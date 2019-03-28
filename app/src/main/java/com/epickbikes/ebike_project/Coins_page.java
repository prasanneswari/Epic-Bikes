package com.epickbikes.ebike_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epickbikes.ebike_project.Launching.coinslanch;
import static com.epickbikes.ebike_project.Launching.orgIdlanch;
import static com.epickbikes.ebike_project.Launching.refreshCoinslanch;
import static com.epickbikes.ebike_project.List_places.coinsposval;
import static com.epickbikes.ebike_project.List_places.orgposval;
import static com.epickbikes.ebike_project.List_places.profileposval;
import static com.epickbikes.ebike_project.List_places.refreshposval;
import static com.epickbikes.ebike_project.Phone_Auth.accesstoken;
import static com.epickbikes.ebike_project.Phone_Auth.coinslogin;
import static com.epickbikes.ebike_project.Phone_Auth.orgIdlogin;
import static com.epickbikes.ebike_project.Phone_Auth.refreshCoinslogin;

public class Coins_page extends AppCompatActivity {

    TextView coinsT,notes;
    ImageView profileimg;
    String url = profileposval;
    Button backbtn,addmoney,btn1,btn2,btn3;
    String addbtnmoney;
    int i=0;
    int[] array = {0,0,0};
    boolean amount=true;
    boolean amountadd=true;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_coins_page);

        coinsT=(TextView)findViewById(R.id.coins);
        notes=(TextView)findViewById(R.id.notes);
        //backbtn=(Button)findViewById(R.id.backbtn);
        addmoney=(Button)findViewById(R.id.addmoney);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        dialog_progress = new ProgressDialog(Coins_page.this);
        builderLoading = new AlertDialog.Builder(Coins_page.this);

        getusers();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(array[0]==0) {
                    array[0] = 1; // ON
                    addbtnmoney="50";
                  //  Log.d("clicked","btn1 :"+addbtnmoney);
                    btn1.setBackgroundColor(Color.BLUE);
                    btn1.setTextColor(Color.WHITE);
                    btn2.setBackgroundResource(R.drawable.border);
                    btn3.setBackgroundResource(R.drawable.border);
                    btn2.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);
                    array[1]=0;
                    array[2]=0;
                }  else if(array[0] == 1) {
                    array[0] = 0; //OFF
                    addbtnmoney="0";
                  //  Log.d("clicked","btn1 :"+addbtnmoney);
                    btn2.setClickable(true);
                    btn3.setClickable(true);
                    btn1.setBackgroundResource(R.drawable.border);
                    btn1.setTextColor(Color.BLACK);
                    if (btn2.isClickable() || btn3.isClickable()){
                        array[1] = 0;
                        array[2] = 0;

                    }else {
                        array[1] = 1;
                        array[2] = 1;
                    }

                }

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(array[1]==0) {
                    array[1] = 1; // ON
                    addbtnmoney="100";
                 //   Log.d("clicked","btn2 :"+addbtnmoney);

                    btn2.setBackgroundColor(Color.BLUE);
                    btn2.setTextColor(Color.WHITE);
                    btn1.setBackgroundResource(R.drawable.border);
                    btn3.setBackgroundResource(R.drawable.border);
                    btn1.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);


                    array[0]=0;
                    array[2]=0;

                } else if(array[1] == 1) {
                    array[1] = 0; //OFF
                    addbtnmoney="0";
                 //   Log.d("clicked","btn2 :"+addbtnmoney);
                    btn1.setClickable(true);
                    btn3.setClickable(true);
                    btn2.setBackgroundResource(R.drawable.border);
                    btn2.setTextColor(Color.BLACK);
                    if (btn1.isClickable() || btn3.isClickable()){
                        array[0] = 0;
                        array[2] = 0;

                    }else {
                        array[0] = 1;
                        array[2] = 1;
                    }
                }


            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (array[2] == 0) {
                    array[2] = 1; // ON
                    addbtnmoney="250";
                  //  Log.d("clicked","btn3 :"+addbtnmoney);

                    btn3.setBackgroundColor(Color.BLUE);
                    btn3.setTextColor(Color.WHITE);
                    btn2.setBackgroundResource(R.drawable.border);
                    btn1.setBackgroundResource(R.drawable.border);
                    btn2.setTextColor(Color.BLACK);
                    btn1.setTextColor(Color.BLACK);

                    array[1]=0;
                    array[0]=0;

                } else if(array[2] == 1) {
                    array[2] = 0; //OFF
                    addbtnmoney="0";
                  //  Log.d("clicked","btn3 :"+addbtnmoney);
                    btn2.setClickable(true);
                    btn1.setClickable(true);
                    btn3.setBackgroundResource(R.drawable.border);
                    btn3.setTextColor(Color.BLACK);
                    if (btn1.isClickable() || btn1.isClickable()){
                        array[0] = 0;
                        array[1] = 0;

                    }else {
                        array[0] = 1;
                        array[1] = 1;
                    }

                }


            }
        });

        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addbtnmoney == "50" || addbtnmoney == "100"|| addbtnmoney == "250") {
                    Intent intent = new Intent(Coins_page.this, Wallet.class);
                    intent.putExtra("keyName", addbtnmoney);  // pass your values and retrieve them in the other Activity using keyName
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Coins_page.this);
                    LayoutInflater inflater = (LayoutInflater) Coins_page.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                    textpopup.setText("Please select the money");

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

            }
        });
    }

    public void getusers(){
        //lengthvalL.clear();

        if (orgposval != null) {


            String AddS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgposval + "\"}";
            //   Log.d("sending string is :", AddS.toString());
            Log.d("jsnresponse orgposval", "---" + AddS);
            String getusers_url = "https://epickbikes.com/api/v1/orgUsers/coins";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(AddS);
                Log.d("jsnresponse....", "---" + AddS);
                // dialog_progress.setMessage("connecting ...");
                dialog_progress.show();

                JSONSenderVolleylistpalces(getusers_url, lstrmdt);

            } catch (JSONException e) {

            }
        }else if (orgIdlanch != null) {
            String AddS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlanch + "\"}";
            //   Log.d("sending string is :", AddS.toString());
            Log.d("jsnresponse orgIdlanch", "---" + AddS);
            String getusers_url = "https://epickbikes.com/api/v1/orgUsers/coins";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(AddS);
                Log.d("jsnresponse....", "---" + AddS);
                // dialog_progress.setMessage("connecting ...");
                dialog_progress.show();

                JSONSenderVolleylistpalces(getusers_url, lstrmdt);

            } catch (JSONException e) {

            }
        }else if (orgIdlogin != null) {
            String AddS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlogin + "\"}";
            //   Log.d("sending string is :", AddS.toString());
            Log.d("jsnresponse orgIdlogin", "---" + AddS);
            String getusers_url = "https://epickbikes.com/api/v1/orgUsers/coins";
            JSONObject lstrmdt = null;

            try {
                lstrmdt = new JSONObject(AddS);
                Log.d("jsnresponse....", "---" + AddS);
                // dialog_progress.setMessage("connecting ...");
                dialog_progress.show();

                JSONSenderVolleylistpalces(getusers_url, lstrmdt);

            } catch (JSONException e) {

            }
        }

    }
    public void JSONSenderVolleylistpalces(String getusers_url, final JSONObject json)
    {

        // Log.d("---Orgusers url-----", "---"+getusers_url);
        Log.d("555555", "Orguserjson"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getusers_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //  Log.d(" ", response.toString());
                        Log.d("----JSONOrguser volly--", "---"+response.toString());
                        List<String> lengthvalL = new ArrayList<String>();

                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");
                                    if (errorCode.contentEquals("true")){
                                        //Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();
                                        String data1 = response.getString("data");

                                        JSONObject dataObject = new JSONObject(data1);

                                        String coins = dataObject.getString("coins");
                                        String refreshCoins = dataObject.getString("refreshCoins");

                                        coinsT.setText(coins);
                                        notes.setText("'" + refreshCoins + "'");

/*

                                        JSONArray dataArray = new JSONArray(response.getString("data"));

                                        if (dataArray.length()!=0) {

                                            for (int i = 0; i < dataArray.length(); i++) {
                                                //  Log.d("array length---", "---" + i);


                                                JSONObject dataObject = new JSONObject(dataArray.get(i).toString());

                                                String coins = dataObject.getString("coins");
                                                String refreshCoins = dataObject.getString("refreshCoins");

                                                coinsT.setText(coins);
                                                notes.setText("'" + refreshCoins + "'");


                                                */
                                                  /*if (coinsposval!=null&&refreshposval!=null) {
                                                    coinsT.setText(coinsposval);
                                                    notes.setText("'" + refreshposval + "'");
                                                }else if (coinslanch!=null&&refreshCoinslanch!=null){
                                                    coinsT.setText(coinslanch);
                                                    notes.setText("'" + refreshCoinslanch + "'");
                                                }else if (coinslogin!=null&&refreshCoinslogin!=null){
                                                    coinsT.setText(coinslogin);
                                                    notes.setText("'" + refreshCoinslogin + "'");
                                                }*//*



                                            }
                                        }
                                        else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Coins_page.this);
                                            LayoutInflater inflater = (LayoutInflater) Coins_page.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
*/

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
                                        //   Log.d("errorCode", "" + errorCode);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Coins_page.this);
                                        LayoutInflater inflater = (LayoutInflater) Coins_page.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Coins_page.this);
                                builder.setMessage("Reasponse=Unable to get teh data");

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
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                //  Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();
/*
                final AlertDialog.Builder builder = new AlertDialog.Builder(List_places.this);
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

    public void ReturnHome(View view){
        super.onBackPressed();
    }
}
