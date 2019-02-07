package com.jts.root.ebike_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.jts.root.ebike_project.Damage_Scan_light.bikeidreportval;
import static com.jts.root.ebike_project.Launching.orgIdlanch;
import static com.jts.root.ebike_project.Launching.sharedsaveorgid;
import static com.jts.root.ebike_project.List_places.orgposval;
import static com.jts.root.ebike_project.Phone_Auth.accesstoken;
import static com.jts.root.ebike_project.Phone_Auth.orgIdlogin;

public class Report_damages extends AppCompatActivity {

    //Variables
    EditText commentdamageT;
    Button submitdamageB, uploadbtn, backbtn;
    TextView scaniddamage, uploadedit;
    ImageView upload1, upload2, upload3;
    CheckBox lockC, frameC, handleC, brakesC, batteryC, wheelsC;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress;
    AlertDialog.Builder builderLoading;
    ArrayList<String> damageL = new ArrayList<>();

    FirebaseStorage storage;
    StorageReference storageRef, imageRef;
    private static final int Selected = 100;
    ProgressDialog progressDialog;
    StorageTask<UploadTask.TaskSnapshot> uploadTask;
    Uri filepath;
    String urlIMAGE;
    LinearLayout uploadlayout, displaylayout;

    private File imageFile;


    public static final int CAMERA_REQUEST = 9999;


    private static final int RESULT_LOAD_IMAGE = 1;
    String result = null;
    ArrayList<String> imageurlL = new ArrayList<>();
    ArrayList<String> imagedisplyL = new ArrayList<String>();

    ImageView imgupload3, imgupload1, imgupload2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_report_damages);

        //Init view
        commentdamageT = (EditText) findViewById(R.id.commentdamage);
        submitdamageB = (Button) findViewById(R.id.submitbtndamage);
        //backbtn = (Button) findViewById(R.id.backbtn);
        uploadbtn = (Button) findViewById(R.id.upload);
        upload1 = (ImageView) findViewById(R.id.upload1);
        upload2 = (ImageView) findViewById(R.id.upload2);
        upload3 = (ImageView) findViewById(R.id.upload3);
        lockC = (CheckBox) findViewById(R.id.lock);
        frameC = (CheckBox) findViewById(R.id.frame);
        handleC = (CheckBox) findViewById(R.id.handle);
        brakesC = (CheckBox) findViewById(R.id.brakes);
        batteryC = (CheckBox) findViewById(R.id.battery);
        wheelsC = (CheckBox) findViewById(R.id.wheels);
        scaniddamage = (TextView) findViewById(R.id.scaniddamage);
        uploadlayout = (LinearLayout) findViewById(R.id.uploadlayout);
        displaylayout = (LinearLayout) findViewById(R.id.displaylayout);
        uploadedit = (TextView) findViewById(R.id.uploadedit);

        dialog_progress = new ProgressDialog(Report_damages.this);
        builderLoading = new AlertDialog.Builder(Report_damages.this);

        if (scaniddamage != null) {
            scaniddamage.setText(bikeidreportval);

        }
        if (bikeidreportval == null) {
            submitdamageB.setVisibility(View.INVISIBLE);
        } else {
            submitdamageB.setVisibility(View.VISIBLE);

        }
        storage = FirebaseStorage.getInstance();

        //uploadListAdapter = new UploadListAdapter(fileNameList, fileDoneList);

        // storageRef = storage.getReference();
        storageRef = FirebaseStorage.getInstance().getReference("uploads");


        scaniddamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Report_damages.this, Damage_Scan_light.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        /*backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (triptime==true){
                    Intent intent = new Intent(Report_damages.this, Trip_Time.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Report_damages.this, Epick_Bikes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });*/

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ChooseImage();
                uploadimg_popup();


            }

        });
        uploadedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimg_popup();

            }
        });

        submitdamageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentE = commentdamageT.getText().toString();
                String ScanidE = scaniddamage.getText().toString();
                damageL.clear();

                if (lockC.isChecked()) {
                    damageL.add("\"" + lockC.getText().toString() + "\"");
                    Log.d("checkboc list values :", String.valueOf(damageL));

                }
                if (frameC.isChecked()) {
                    damageL.add("\"" + frameC.getText().toString() + "\"");
                    Log.d("checkboc list values :", String.valueOf(damageL));
                }

                if (handleC.isChecked()) {
                    damageL.add("\"" + handleC.getText().toString() + "\"");
                }
                if (brakesC.isChecked()) {
                    damageL.add("\"" + brakesC.getText().toString() + "\"");
                }
                if (batteryC.isChecked()) {
                    damageL.add("\"" + batteryC.getText().toString() + "\"");
                }
                if (wheelsC.isChecked()) {
                    damageL.add("\"" + wheelsC.getText().toString() + "\"");
                }

                if (!(lockC.isChecked()) && !(frameC.isChecked()) && !(handleC.isChecked()) && !(brakesC.isChecked()) && !(batteryC.isChecked()) && !(wheelsC.isChecked())) {
                    //submitdamageB.setVisibility(View.INVISIBLE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Report_damages.this);
                    LayoutInflater inflater = (LayoutInflater) Report_damages.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                    textpopup.setText("At least one item is marked in Checkbox");

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

                } /*else if (urlIMAGE == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Report_damages.this);
                    LayoutInflater inflater = (LayoutInflater) Report_damages.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                    textpopup.setText("Please Upload the image");

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

                } */else {

                    if (orgposval != null) {

                        String damageS = "{\"orgId\":\"" + orgposval + "\",\"access_token\":\"" + accesstoken + "\",\"bikeId\":\"" + ScanidE + "\",\"comment\":\"" + commentE + "\",\"damage\":" + damageL + ",\"url\":\"" + imageurlL + "\"}";
                        Log.d("sending string is :", damageS.toString());
                        Log.d("jsnresponse endS", "---" + damageS);
                        String damage_url = "https://epickbikes.com/api/v1/damage/add";
                        JSONObject lstrmdt = null;

                        try {
                            lstrmdt = new JSONObject(damageS);
                            Log.d("jsnresponse....", "---" + damageS);
                          //  dialog_progress.setMessage("connecting ...");
                            dialog_progress.show();

                            JSONSenderVolleyendtrip(damage_url, lstrmdt);
                        } catch (JSONException e) {

                        }

                    } else if (orgIdlanch != null) {
                        String damageS = "{\"orgId\":\"" + orgIdlanch + "\",\"access_token\":\"" + accesstoken + "\",\"bikeId\":\"" + ScanidE + "\",\"comment\":\"" + commentE + "\",\"damage\":" + damageL + ",\"url\":\"" + imageurlL + "\"}";
                        Log.d("sending string is :", damageS.toString());
                        Log.d("jsnresponse endS", "---" + damageS);
                        String damage_url = "https://epickbikes.com/api/v1/damage/add";
                        JSONObject lstrmdt = null;

                        try {
                            lstrmdt = new JSONObject(damageS);
                            Log.d("jsnresponse....", "---" + damageS);
                          //  dialog_progress.setMessage("connecting ...");
                            dialog_progress.show();

                            JSONSenderVolleyendtrip(damage_url, lstrmdt);
                        } catch (JSONException e) {

                        }

                    } else if (orgIdlogin != null) {
                        String damageS = "{\"orgId\":\"" + orgIdlogin + "\",\"access_token\":\"" + accesstoken + "\",\"bikeId\":\"" + ScanidE + "\",\"comment\":\"" + commentE + "\",\"damage\":" + damageL + ",\"url\":\"" + imageurlL + "\"}";
                        Log.d("sending string is :", damageS.toString());
                        Log.d("jsnresponse endS", "---" + damageS);
                        String damage_url = "https://epickbikes.com/api/v1/damage/add";
                        JSONObject lstrmdt = null;

                        try {
                            lstrmdt = new JSONObject(damageS);
                            Log.d("jsnresponse....", "---" + damageS);
                          //  dialog_progress.setMessage("connecting ...");
                            dialog_progress.show();

                            JSONSenderVolleyendtrip(damage_url, lstrmdt);
                        } catch (JSONException e) {

                        }

                    } else if (sharedsaveorgid != null) {
                        String damageS = "{\"orgId\":\"" + sharedsaveorgid + "\",\"access_token\":\"" + accesstoken + "\",\"bikeId\":\"" + ScanidE + "\",\"comment\":\"" + commentE + "\",\"damage\":" + damageL + ",\"url\":\"" + imageurlL + "\"}";
                        Log.d("sending string is :", damageS.toString());
                        Log.d("jsnresponse endS", "---" + damageS);
                        String damage_url = "https://epickbikes.com/api/v1/damage/add";
                        JSONObject lstrmdt = null;

                        try {
                            lstrmdt = new JSONObject(damageS);
                            Log.d("jsnresponse....", "---" + damageS);
                           // dialog_progress.setMessage("connecting ...");
                            dialog_progress.show();

                            JSONSenderVolleyendtrip(damage_url, lstrmdt);
                        } catch (JSONException e) {

                        }

                    }
                }
            }
        });

    }

    public void JSONSenderVolleyendtrip(String damage_url, final JSONObject json) {
        Log.d("---end trip url-----", "---" + damage_url);
        //Log.d("555555", "00000000"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                damage_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("JSON end trip Volley", "---" + response.toString());

                        if (response != null) {
                            if (response.has("success")) {
                                try {
                                    String errorCode = response.getString("success");

                                    if (errorCode.contentEquals("true")) {
                                        //Toast.makeText(getApplicationContext(), "Response=successfully strat trip", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Report_damages.this);
                                        LayoutInflater inflater = (LayoutInflater) Report_damages.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                                        textpopup.setText("Successfully adding the report damages");

                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                // TODO Auto-generated method stub
                                                //delgroup(grpnm);

                                                Intent intent = new Intent(Report_damages.this, Report_damages.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);

                                            }
                                        });
                                        dialog.show();
                                        /* Intent intent = new Intent(Report_damages.this, Epick_Bikes.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);*/

                                    } else {
                                        // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                        String msg;
                                        if (response.has("message")) {
                                            msg = response.getString("message");
                                        } else {
                                            msg = "Unable to Start the trip";
                                        }
                                        Log.d("errorCode", "" + errorCode);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Report_damages.this);
                                        LayoutInflater inflater = (LayoutInflater) Report_damages.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Report_damages.this);
                                LayoutInflater inflater = (LayoutInflater) Report_damages.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
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
                Log.d("my test error-----", "shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();
/*
                final AlertDialog.Builder builder = new AlertDialog.Builder(Report_damages.this);
                builder.setTitle("Info");
                builder.setMessage(String.valueOf(error));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {

                        // dialog_progress.dismiss();
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
        addToRequestQueue1(jsonObjReq);
    }

    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void uploadimg_popup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Report_damages.this);
        LayoutInflater inflater = (LayoutInflater) Report_damages.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
        final View dialogLayout = inflater.inflate(R.layout.upload_phots_popup,
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

        imgupload1 = (ImageView) dialogLayout.findViewById(R.id.uploadimg1);
        imgupload2 = (ImageView) dialogLayout.findViewById(R.id.uploadimg2);
        imgupload3 = (ImageView) dialogLayout.findViewById(R.id.uploadimg3);

        Button uploadpohotsbtn = (Button) dialogLayout.findViewById(R.id.uploadphoto);

        Button backbtn = (Button) dialogLayout.findViewById(R.id.close_popup);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        imgupload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImage();


            }
        });
        imgupload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImage();


            }
        });

        imgupload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImage();


            }
        });
        uploadpohotsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadlayout.setVisibility(View.GONE);
                displaylayout.setVisibility(View.VISIBLE);

                for (int i=0;i<=imageurlL.size();i++) {
                    if (i == 0 && i < imageurlL.size()) {
                        Picasso.with(getApplicationContext()).load(imageurlL.get(i)).placeholder(R.mipmap.ic_launcher)
                                .error(R.drawable.cycle)
                                .resize(50, 50)
                                .into(upload1, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });

                    }
                    if (i == 1 && i < imageurlL.size() ) {
                        Picasso.with(getApplicationContext()).load(imageurlL.get(i)).placeholder(R.mipmap.ic_launcher)
                                .error(R.drawable.cycle)
                                .resize(50, 50)
                                .into(upload2, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                    if (i == 2 && i < imageurlL.size()) {
                        Picasso.with(getApplicationContext()).load(imageurlL.get(i)).placeholder(R.mipmap.ic_launcher)
                                .error(R.drawable.cycle)
                                .resize(50, 50)
                                .into(upload3, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });

                    }
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

   /* public void ChooseImage() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("entering ", "imagevie");

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
           *//* filepath=data.getData();
            Log.d("filepath", "" + filepath);*//*

            if (data.getClipData() != null) {

                int totalItemsSelected = data.getClipData().getItemCount();
                Log.d("totalItemsSelected", "url image" + totalItemsSelected);
                for (int i = 0; i < totalItemsSelected; i++) {

                    filepath = data.getClipData().getItemAt(i).getUri();
                    imagedisplyL.add(String.valueOf(filepath));
                    Log.d("imagedisplyL", "imagedisplyL" + imagedisplyL);

                }


                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);

                // imgupload1.setImageBitmap(bitmap);

                Picasso.with(getApplicationContext()).load(filepath).placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.cycle)
                        .resize(50, 50)
                        .into(imgupload1, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }


        }
        }

    private void uploadImage() {

        if(filepath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageRef.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            urlIMAGE = downloadUrl.toString();
                            Log.d("my test error-----", "url image" + urlIMAGE);
                            imageurlL.add(urlIMAGE);


                            progressDialog.dismiss();

                            Toast.makeText(Report_damages.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Report_damages.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }*/

    public void ChooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if (imageReturnedIntent.getClipData() != null) {

                int totalItemsSelected = imageReturnedIntent.getClipData().getItemCount();
                Log.d("totalItemsSelected", "url image" + totalItemsSelected);
                for (int i = 0; i < totalItemsSelected; i++) {

                    filepath = imageReturnedIntent.getClipData().getItemAt(i).getUri();

                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMax(100);
                    progressDialog.setMessage("Uploading...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                    UploadFoto();
                }
            }
        }


    }

    private String getFileExtension(Uri uri) {
        if (uri.getScheme().equals("content")) {

            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {

                result = result.substring(cut + 1);
            }
        }
        return result;

    }

    public void UploadFoto() {
        imageurlL.clear();

        //imageRef = storageRef.child("myphotos/");
        imageRef = storageRef.child(System.currentTimeMillis()
                + "." + getFileExtension(filepath));


        uploadTask = imageRef.putFile(filepath)

                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        progressDialog.incrementProgressBy((int) progress);

                        progressDialog.show();
                        progressDialog.setCancelable(false);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        urlIMAGE = downloadUrl.toString();
                        Log.d("my test error-----", "url image" + urlIMAGE);
                        imageurlL.add(urlIMAGE);
                        Log.d("my test error-----", "imageurlL" + imageurlL);
                        for (int i=0;i<=imageurlL.size();i++) {
                            if (i == 0 && i < imageurlL.size()) {
                                Picasso.with(getApplicationContext()).load(imageurlL.get(i)).placeholder(R.mipmap.ic_launcher)
                                        .error(R.drawable.cycle)
                                        .resize(50, 50)
                                        .into(imgupload1, new com.squareup.picasso.Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {

                                            }
                                        });

                            }
                            if (i == 1 && i < imageurlL.size() ) {
                                Picasso.with(getApplicationContext()).load(imageurlL.get(i)).placeholder(R.mipmap.ic_launcher)
                                        .error(R.drawable.cycle)
                                        .resize(50, 50)
                                        .into(imgupload2, new com.squareup.picasso.Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {

                                            }
                                        });
                            }
                            if (i == 2 && i < imageurlL.size()) {
                                Picasso.with(getApplicationContext()).load(imageurlL.get(i)).placeholder(R.mipmap.ic_launcher)
                                        .error(R.drawable.cycle)
                                        .resize(50, 50)
                                        .into(imgupload3, new com.squareup.picasso.Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {

                                            }
                                        });

                            }
                        }
                        progressDialog.dismiss();
                        //YourUrlImage.setText("Your Download URl : " + urlIMAGE);
                    }
                });

    }
    public void ReturnHome(View view){
        super.onBackPressed();
    }


}