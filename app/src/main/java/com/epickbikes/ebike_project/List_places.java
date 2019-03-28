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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epickbikes.ebike_project.Phone_Auth.accesstoken;

public class List_places extends AppCompatActivity {
    ListView placelist;
    Button backbtn;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;
    static String lengthvalues;
    static String orgId;
    static String orgposval;
    static String coins;
    static String phoneNumber;
    static String uid;
    static String profileURL;
    static String coinsposval;
    static String profileposval,refreshCoins,refreshposval,newtoken;
    static List<String> orgL = new ArrayList<String>();
    static List<String> listcoinsL = new ArrayList<String>();
    static List<String> profileL = new ArrayList<String>();
    static List<String> refreshCoinsL = new ArrayList<String>();

    static int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_places);
        placelist=(ListView)findViewById(R.id.placelst);
        dialog_progress = new ProgressDialog(List_places.this);
        builderLoading = new AlertDialog.Builder(List_places.this);
        //lengthvalL.clear();

        getusers();

    }

    public void sharedget() {

        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(List_places.this);
        accesstoken = preferences1.getString("token","Value");

       // Log.d("sharedprefere newtoken", accesstoken);
    }

    public void getusers(){
        sharedget();
        //lengthvalL.clear();

            String AddS = "{\"access_token\":\"" + accesstoken + "\"}";
         //   Log.d("sending string is :", AddS.toString());
            Log.d("jsnresponse AddS", "---" + AddS);
            String getusers_url = "https://epickbikes.com/api/v1/orgUsers/";
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
                        lengthvalL.clear();

                        if (response!=null){
                            if (response.has("success")){
                                try {
                                    String errorCode = response.getString("success");
                                    if (errorCode.contentEquals("true")){
                                        //Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();

                                        JSONArray dataArray = new JSONArray(response.getString("data"));

                                        if (dataArray.length()!=0) {

                                            for (int i = 0; i < dataArray.length(); i++) {
                                              //  Log.d("array length---", "---" + i);

                                                String orgusersarray= String.valueOf(i);
                                              //  Log.d("orgusersarray---", "---" + orgusersarray);
                                                shredorgusers(orgusersarray);

                                                JSONObject dataObject = new JSONObject(dataArray.get(i).toString());

                                                coins = dataObject.getString("coins");
                                                orgId = dataObject.getString("orgId");
                                                String orgName = dataObject.getString("orgName");
                                              //  Log.d("orgName values....", "---" + orgName);

                                                phoneNumber = dataObject.getString("phoneNumber");
                                                uid = dataObject.getString("uid");
                                                profileURL = dataObject.getString("profileURL");
                                                refreshCoins = dataObject.getString("refreshCoins");

                                                orgL.add(orgId);
                                                listcoinsL.add(coins);
                                                profileL.add(profileURL);
                                                refreshCoinsL.add(refreshCoins);

                                                lengthvalues = orgName ;
                                              //  Log.d("length values....", "---" + lengthvalues);
                                                lengthvalL.add(lengthvalues);

                                            }
                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(List_places.this, R.layout.list_back, R.id.listtext, lengthvalL);
                                            placelist.setAdapter(adapter);
                                            placelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                public void onItemClick(AdapterView<?> parent, View v,
                                                                        int position, long id) {
                                                    String clickpos = parent.getAdapter().getItem(position).toString();
                                                    index = position;
                                                    orgposval = orgL.get(index);
                                                    coinsposval = listcoinsL.get(index);
                                                    profileposval = profileL.get(index);
                                                    refreshposval = refreshCoinsL.get(index);
                                                    //gridclickpos= index;
                                                 //   Log.d("statuspos", "--------------" + orgL.get(index));
                                                    //Log.d("statuspos ing444", "--------------" +unitid[Integer.parseInt(clickpos)]);

                                                    Intent intent = new Intent(List_places.this, Epick_Bikes.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);

                                                    // Toast.makeText(getApplicationContext(), clickpos,Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(List_places.this);
                                            LayoutInflater inflater = (LayoutInflater) List_places.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                     //   Log.d("errorCode", "" + errorCode);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(List_places.this);
                                        LayoutInflater inflater = (LayoutInflater) List_places.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                final AlertDialog.Builder builder = new AlertDialog.Builder(List_places.this);
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

    public void shredorgusers(String locationarray) {

       // Log.d("..json_array.. ", String.valueOf(locationarray));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(List_places.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("array", locationarray);

        editor.apply();
    }
}
