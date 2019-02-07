package com.jts.root.ebike_project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;
import static com.jts.root.ebike_project.Launching.orgIdlanch;
import static com.jts.root.ebike_project.Launching.profileURLlanch;
import static com.jts.root.ebike_project.Launching.sharedsaveorgid;
import static com.jts.root.ebike_project.List_places.orgposval;
import static com.jts.root.ebike_project.List_places.profileposval;
import static com.jts.root.ebike_project.Phone_Auth.accesstoken;
import static com.jts.root.ebike_project.Phone_Auth.orgIdlogin;
import static com.jts.root.ebike_project.Phone_Auth.profileURLlogin;

public class Trip_history extends Activity {


    ListView triphistorylst;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress;
    AlertDialog.Builder builderLoading;

    ImageView profileimg;
    Button backbtn;

    String costS, startTimeS, durationS, totaldate, totaltime;

    private final static String TAG = Trip_history.class.getSimpleName();

    private int pageCount = 0;

    int offset = 0;


    private Adapter_triphistory adapter;
    private ProgressDialog dialog;
    private ArrayList<Triphistory_Java> triphistory;
    private final static String url_page1 = "http://www.json-generator.com/api/json/get/ckDtZPkgJe?indent=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_trip_history);

        triphistorylst = (ListView) findViewById(R.id.historylst);
        //backbtn=(Button) findViewById(R.id.backbtn);

        dialog_progress = new ProgressDialog(Trip_history.this);
        builderLoading = new AlertDialog.Builder(Trip_history.this);
        setListViewAdapter();
        getDataFromUrl(url_page1);

        triphistorylst.setOnScrollListener(onScrollListener());


    }

    private void setListViewAdapter() {
        triphistory = new ArrayList<Triphistory_Java>();
        adapter = new Adapter_triphistory(Trip_history.this, triphistory);
        triphistorylst.setAdapter(adapter);
    }

    private void getDataFromUrl(String url) {
        //new LoadCountriesFromUrlTask(this,url).execute();
        // String s1= "{\"message\":[{\"id\":5,\"name\":\"Korea\"},{\"id\":6,\"name\":\"Japan\"},{\"id\":7,\"name\":\"Singapore\"},{\"id\":8,\"name\":\"Malaysia\"},{\"id\":9,\"name\":\"India\"}]}";
        //parseJsonResponse(s1);
        triphistory_post(offset);
    }


    private AbsListView.OnScrollListener onScrollListener() {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = triphistorylst.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (triphistorylst.getLastVisiblePosition() >= count - threshold && pageCount < 200000) {
                        Log.i(TAG, "loading more data");
                        // Execute LoadMoreDataTask AsyncTask

                        //String s1= "{\"message\":[{\"id\":1,\"name\":\"Vietnam\"},{\"id\":2,\"name\":\"China\"},{\"id\":3,\"name\":\"USA\"},{\"id\":4,\"name\":\"England\"},{\"id\":10,\"name\":\"Russia\"}]}";
                        //parseJsonResponse(s1);
                        triphistory_post(offset);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }

        };
    }

    public void triphistory_post(int data1) {

        if (orgposval != null) {

            String get_tripsS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgposval + "\",\"offset\":" + data1 + "}";
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
        } else if (orgIdlanch != null) {
            String get_tripsS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlanch + "\",\"offset\":" + data1 + "}";
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
        } else if (orgIdlogin != null) {
            String get_tripsS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + orgIdlogin + "\",\"offset\":" + data1 + "}";
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

        } else if (sharedsaveorgid != null) {
            String get_tripsS = "{\"access_token\":\"" + accesstoken + "\",\"orgId\":\"" + sharedsaveorgid + "\",\"offset\":" + data1 + "}";
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

    public void JSONSenderVolley(String gettrips_url, final JSONObject json) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                gettrips_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSON trips Volley--", "---" + response.toString());
                        //parseJsonResponse(response.toString());

                        offset += 5;

                        parseJsonResponse(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                Log.d("my test error-----", "shulamithi: " + String.valueOf(error));

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


        // JSONSenderVolley(gettrips_url, lstrmdt);

    }

    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void ReturnHome(View view) {
        super.onBackPressed();
    }

    public void parseJsonResponse(String result) {
        Log.i(TAG, result);
        pageCount++;
        try {
                    JSONObject json = new JSONObject(result);
                    String errorCode = json.getString("success");
                    if (errorCode.contentEquals("true")) {

                        JSONArray jArray = new JSONArray(json.getString("data"));
                        for (int i = 0; i < jArray.length(); i++) {
                            dialog_progress.dismiss();

                            JSONObject jObject = jArray.getJSONObject(i);
                            Triphistory_Java country = new Triphistory_Java();
                            startTimeS = jObject.getString("startTime");
                            country.setCost(jObject.getString("cost"));
                            country.setEndTime(jObject.getString("duration"));

                            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            df1.setTimeZone(TimeZone.getTimeZone("GMT"));
                            String string1 = startTimeS;//input time
                            Date result1 = df1.parse(string1);
                            System.out.println("result time" + result1);
                            //String date = "Tue Dec 18 15:13:31 IST 2018";
                            DateFormat inputFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zz yyy");
                            String date = inputFormat.format(result1);
                            Date d = inputFormat.parse(date);
                            //DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            System.out.println("out put time" + outputFormat.format(d));

                            String t1 = outputFormat.format(d);

                            totaldate = t1.substring(0, 10);
                            System.out.println("output totola date" + totaldate);

                            totaltime = t1.substring(11, 18);
                            System.out.println("output totola time" + totaltime);

                            country.setCurrentdate(totaldate);
                            country.setStartTime(totaltime);


                            triphistory.add(country);

                        }

                        adapter.notifyDataSetChanged();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



