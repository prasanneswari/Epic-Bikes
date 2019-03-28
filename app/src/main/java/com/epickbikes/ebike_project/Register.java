package com.epickbikes.ebike_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    static EditText phoneNum;
    Button sendCodeButton;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress;
    AlertDialog.Builder builderLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phoneNum = (EditText) findViewById(R.id.phone);
        sendCodeButton = (Button) findViewById(R.id.send_code_b);
        dialog_progress = new ProgressDialog(Register.this);
        builderLoading = new AlertDialog.Builder(Register.this);


        TextWatcher m_MobileWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().contains("+91")) {
                    phoneNum.setText("+91");
                    Selection.setSelection(phoneNum.getText(), phoneNum.getText().length());
                }
                if(s.length() > 0){
                    if(!phoneValidator(s)){
                        phoneNum.setError("Enter valid number");

                       /* Drawable err_indiactor = getResources().getDrawable(R.drawable.indicator_input_error);
                        phoneNum.setCompoundDrawablesWithIntrinsicBounds(null, null, err_indiactor, null);
*/

                    }
                }
            }
        };

        // phoneNum = (EditText) m_Main.findViewById(R.id.input_Number);
        phoneNum.addTextChangedListener(m_MobileWatcher);


        phoneNum.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        // pinview.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phons = phoneNum.getText().toString();

                if (phons.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    LayoutInflater inflater = (LayoutInflater) Register.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                    textpopup.setText("Please enter the Phone Number");

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
                }
                else {

                    String AddS = "{\"phoneNumber\":\"" + phons + "\"}";
                 //   Log.d("sending string is :", AddS.toString());
                    Log.d("jsnresponse AddS", "---" + AddS);
                    String login_url = "https://epickbikes.com/api/v1/login/requestOTP";
                    JSONObject lstrmdt = null;

                    try {
                        lstrmdt = new JSONObject(AddS);
                        Log.d("jsnresponse....", "---" + AddS);
                       // dialog_progress.setMessage("connecting ...");
                        dialog_progress.show();


                        JSONSenderVolleyreqotp(login_url, lstrmdt);

                    } catch (JSONException e) {

                    }
                }
            }
        });
    }

    //phone validation code
    private boolean phoneValidator(CharSequence phone){
        if(phone.length() < 6 ||phone.length() < 13  || phone.length() > 13)
            return false;
        else
            return true;
    }
    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }


    public void JSONSenderVolleyreqotp(String login_url, final JSONObject json) {
       // Log.d("---reqotpurl-----", "---" + login_url);
        Log.d("555555", "reqotp" + json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                login_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                     //   Log.d(" ", response.toString());
                        Log.d("JSONSenderVolleyreqotp", "---" + response.toString());

                        if (response != null) {

                            try {
                                if(response.has("success")) {
                                    String errorCode = response.getString("success");
                                    // String errorDesc = response.getString("error_desc");
                                    //String[] newdata = errorDesc.split("=");

                                    if (errorCode.contentEquals("true")) {
                                        //Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();
                                        Intent intent = new Intent(Register.this, Phone_Auth.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    } else {
                                        String msg;
                                        if(response.has("message")){
                                            msg=response.getString("message");

                                        }
                                        else{
                                            msg="Unable to Start the trip";
                                        }
                                      //  Log.d("errorCode", "" + errorCode);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                        LayoutInflater inflater = (LayoutInflater) Register.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                                }else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                    LayoutInflater inflater = (LayoutInflater) Register.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "reqotp: " + String.valueOf(error));
               // Log.d("my reqotp error-----", "shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
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
        addToRequestQueue(jsonObjReq);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }
}
