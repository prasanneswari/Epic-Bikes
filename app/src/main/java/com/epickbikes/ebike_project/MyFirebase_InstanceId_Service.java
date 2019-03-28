package com.epickbikes.ebike_project;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

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
