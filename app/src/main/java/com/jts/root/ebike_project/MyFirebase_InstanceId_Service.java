package com.jts.root.ebike_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.jts.root.ebike_project.Phone_Auth.accesstoken;

/**
 * Created by root on 28/11/18.
 */

public class MyFirebase_InstanceId_Service extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    static String refreshedToken;
    @Override
    public void onTokenRefresh() {

         refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}
