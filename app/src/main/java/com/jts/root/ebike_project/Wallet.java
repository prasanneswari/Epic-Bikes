package com.jts.root.ebike_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.jts.root.ebike_project.Phone_Auth.accesstoken;

public class Wallet extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    EditText orderid, custid;
    private ProgressDialog dialog_progress;
    AlertDialog.Builder builderLoading;
    RequestQueue sch_RequestQueue;
    String ORDER_ID,MID,CUST_ID,INDUSTRY_TYPE_ID,CHANNEL_ID,TXN_AMOUNT,WEBSITE,
            CALLBACK_URL,EMAIL,MOBILE_NO,CHECKSUMHASH,STATUSverify,CHECKSUMHASHverify,BANKNAMEverify,ORDERIDverify,TXNAMOUNTverify,
            TXNDATEverify,MIDverify,TXNIDverify,RESPCODEverify,PAYMENTMODEverify,BANKTXNIDverify,CURRENCYverify,GATEWAYNAMEverify,RESPMSGverify;
    String txnamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        dialog_progress = new ProgressDialog(Wallet.this);
        builderLoading = new AlertDialog.Builder(Wallet.this);

       /* if (ContextCompat.checkSelfPermission(Wallet.this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Wallet.this, new String[]{android.Manifest.permission.READ_SMS, android.Manifest.permission.RECEIVE_SMS}, 101);
        }*/
      //get the current intent
        Intent intent = getIntent();
      //get the attached extras from the intent
       //we should use the same key as we used to attach the data.
         txnamount = intent.getStringExtra("keyName");
        Log.d("sending string is :", txnamount);

        generatecheck();
    }
    public void generatecheck(){
        String param = "{\"access_token\":\"" + accesstoken + "\",\"TXN_AMOUNT\":\"" + txnamount + "\"}";
        Log.d("sending string is :", param.toString());
        Log.d("jsnresponse AddS", "---" + param);
        String cheksum_url = "https://epickbikes.com/api/v1/transaction/generateChecksum";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(param);
            Log.d("jsnresponse....", "---" + param);
            // dialog_progress.setMessage("connecting ...");
            dialog_progress.show();


            JSONSenderVolley(cheksum_url, lstrmdt);

        } catch (JSONException e) {

        }
    }

    public void JSONSenderVolley(String cheksum_url, final JSONObject json) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                cheksum_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSON cheksum resu--", "---" + response.toString());
                        //parseJsonResponse(response.toString());

                        dialog_progress.dismiss();
                        try {
                             ORDER_ID = response.getString("ORDER_ID");
                            Log.d("----ORDER_ID--", "---" + ORDER_ID);
                             MID = response.getString("MID");
                            Log.d("----MID--", "---" + MID);
                             CUST_ID = response.getString("CUST_ID");
                            Log.d("----CUST_ID--", "---" + CUST_ID);
                             INDUSTRY_TYPE_ID = response.getString("INDUSTRY_TYPE_ID");
                            Log.d("----INDUSTRY_TYPE_ID--", "---" + INDUSTRY_TYPE_ID);
                             CHANNEL_ID = response.getString("CHANNEL_ID");
                            Log.d("----CHANNEL_ID--", "---" + CHANNEL_ID);
                             TXN_AMOUNT = response.getString("TXN_AMOUNT");
                            Log.d("----TXN_AMOUNT--", "---" + TXN_AMOUNT);
                             WEBSITE = response.getString("WEBSITE");
                            Log.d("----WEBSITE--", "---" + WEBSITE);
                             CALLBACK_URL = response.getString("CALLBACK_URL");
                            Log.d("----CALLBACK_URL--", "---" + CALLBACK_URL);
                             EMAIL = response.getString("EMAIL");
                            Log.d("----EMAIL--", "---" + EMAIL);
                             MOBILE_NO = response.getString("MOBILE_NO");
                            Log.d("----MOBILE_NO--", "---" + MOBILE_NO);
                             CHECKSUMHASH = response.getString("CHECKSUMHASH");
                            Log.d("----CHECKSUMHASH--", "---" + CHECKSUMHASH);
                            initializePaytmPayment(ORDER_ID,MID,CUST_ID,INDUSTRY_TYPE_ID,CHANNEL_ID,TXN_AMOUNT,WEBSITE,
                                    CALLBACK_URL,EMAIL,MOBILE_NO,CHECKSUMHASH);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                Log.d("my test error-----", "shulamithi: " + String.valueOf(error));

                dialog_progress.dismiss();
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

    private void initializePaytmPayment(String ORDER_ID,String MID,String CUST_ID,String INDUSTRY_TYPE_ID,String CHANNEL_ID,String TXN_AMOUNT,String WEBSITE,
                                        String CALLBACK_URL,String EMAIL,String MOBILE_NO,String CHECKSUMHASH) {

        //getting paytm service
        //PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID",   MID );
        paramMap.put("ORDER_ID",ORDER_ID );
        paramMap.put("CUST_ID", CUST_ID);
        paramMap.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID );
        paramMap.put("CHANNEL_ID", CHANNEL_ID );
        paramMap.put("TXN_AMOUNT",  TXN_AMOUNT );
        paramMap.put("WEBSITE", WEBSITE );
        paramMap.put("CALLBACK_URL",  CALLBACK_URL );
        paramMap.put("EMAIL",EMAIL );
        paramMap.put("MOBILE_NO", MOBILE_NO );
        paramMap.put("CHECKSUMHASH", CHECKSUMHASH );

        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);
        Log.e("checksum ", "param"+ paramMap.toString());


        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(Wallet.this, true, true,
                Wallet.this  );

    }


    @Override
    public void onTransactionResponse(Bundle inResponse) {
        Log.e("checksum ", " respon true " + inResponse.toString());

        // E/checksum:  respon true Bundle[{STATUS=TXN_SUCCESS, CHECKSUMHASH=sBV+YmdxNFpsa2kE6DNqwaeVm1MLKm3MWd3TtQWBuOkI6RcQRlLHyKeum6tEdLqgtPj+W+0tW1JoMRNVetqdoPo6oO9xAwblMLQRIi3o72w=, BANKNAME=IDBI, ORDERID=8279374757277, TXNAMOUNT=1.00, TXNDATE=2019-02-05 19:22:41.0, MID=Centau48099281940555, TXNID=20190205111212800110168998655770488, RESPCODE=01, PAYMENTMODE=NB, BANKTXNID=201253516, CURRENCY=INR, GATEWAYNAME=IDBI, RESPMSG=Txn Success}]


        STATUSverify = inResponse.getString("STATUS");
        Log.d("----STATUS--", "---" + STATUSverify);
        CHECKSUMHASHverify = inResponse.getString("CHECKSUMHASH");
        Log.d("CHECKSUMHASHverify--", "---" + CHECKSUMHASHverify);
        BANKNAMEverify = inResponse.getString("BANKNAME");
        Log.d("--BANKNAMEverify--", "---" + BANKNAMEverify);
        ORDERIDverify = inResponse.getString("ORDERID");
        Log.d("-ORDERIDverify--", "---" + ORDERIDverify);
        TXNAMOUNTverify = inResponse.getString("TXNAMOUNT");
        Log.d("-TXNAMOUNTverify--", "---" + TXNAMOUNTverify);
        TXNDATEverify = inResponse.getString("TXNDATE");
        Log.d("-TXNDATEverify--", "---" + TXNDATEverify);
        MIDverify = inResponse.getString("MID");
        Log.d("----MIDverify--", "---" + MIDverify);
        TXNIDverify = inResponse.getString("TXNID");
        Log.d("-TXNIDverify--", "---" + TXNIDverify);
        RESPCODEverify = inResponse.getString("RESPCODE");
        Log.d("-RESPCODEverify--", "---" + RESPCODEverify);
        PAYMENTMODEverify = inResponse.getString("PAYMENTMODE");
        Log.d("-PAYMENTMODEverify-", "---" + PAYMENTMODEverify);
        BANKTXNIDverify = inResponse.getString("BANKTXNID");
        Log.d("-BANKTXNIDverify--", "---" + BANKTXNIDverify);
        CURRENCYverify = inResponse.getString("CURRENCY");
        Log.d("----CURRENCYverify--", "---" + CURRENCYverify);
        GATEWAYNAMEverify = inResponse.getString("GATEWAYNAME");
        Log.d("-GATEWAYNAMEverify-", "---" + GATEWAYNAMEverify);
        RESPMSGverify = inResponse.getString("RESPMSG");
        Log.d("-RESPMSGverify-", "---" + RESPMSGverify);
        verifycheck();

    }

        @Override
    public void networkNotAvailable() {
        Log.e("checksum ", " networkNotAvailable  "+ "networkNotAvailable" );

    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        Log.e("checksum ", " inErrorMessage  "+ inErrorMessage );

    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        Log.e("checksum ", " ui fail respon  "+ inErrorMessage );

    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);

    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon" );
        //intent
        Intent intent = new Intent(Wallet.this, Coins_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        Log.e("checksum ", "  transaction cancel " );

    }

    public void verifycheck(){
        String param = "{\"access_token\":\"" + accesstoken + "\",\"params\":{\"STATUS\":\""+STATUSverify+"\",\"BANKNAME\":\""+BANKNAMEverify+"\",\"ORDERID\":\""+ORDERIDverify+"\",\"TXNAMOUNT\":\""+TXNAMOUNTverify+"\",\"TXNDATE\":\""+TXNDATEverify+"\",\"MID\":\""+MIDverify+"\",\"TXNID\":\""+TXNIDverify+"\",\"RESPCODE\":\""+RESPCODEverify+"\",\"PAYMENTMODE\":\""+PAYMENTMODEverify+"\",\"BANKTXNID\":\""+BANKTXNIDverify+"\",\"CHECKSUMHASH\":\""+CHECKSUMHASHverify+"\",\"CURRENCY\":\""+CURRENCYverify+"\",\"GATEWAYNAME\":\""+GATEWAYNAMEverify+"\",\"RESPMSG\":\""+RESPMSGverify+"\"}}";
        Log.d("sending string is :", param.toString());
        Log.d("jsnresponse AddS", "---" + param);
        String cheksum_url = "https://epickbikes.com/api/v1/transaction/verifyChecksum";
        JSONObject lstrmdt = null;


        try {
            lstrmdt = new JSONObject(param);
            Log.d("jsnresponse....", "---" + param);
            // dialog_progress.setMessage("connecting ...");
            dialog_progress.show();

            JSONSenderVolleyverify(cheksum_url, lstrmdt);

        } catch (JSONException e) {

        }
    }

    public void JSONSenderVolleyverify(String cheksum_url, final JSONObject json) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                cheksum_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSON cheksum resu--", "---" + response.toString());
                        //parseJsonResponse(response.toString());

                        try {
                            String success=response.getString("success");
                            if(success.equals("true")) {
                                dialog_progress.dismiss();
                                Intent intent = new Intent(Wallet.this, Coins_page.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Checksum is added", Toast.LENGTH_LONG).show();
                            }else {
                                dialog_progress.dismiss();
                                Intent intent = new Intent(Wallet.this, Coins_page.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Checksum is failed", Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                Log.d("my test error-----", "shulamithi: " + String.valueOf(error));
                dialog_progress.dismiss();
                Intent intent = new Intent(Wallet.this, Coins_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
        addToRequestQueue(jsonObjReq);


        // JSONSenderVolley(gettrips_url, lstrmdt);

    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }
}
