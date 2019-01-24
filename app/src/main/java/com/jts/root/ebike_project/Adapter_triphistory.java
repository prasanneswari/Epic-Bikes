package com.jts.root.ebike_project;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter_triphistory extends ArrayAdapter<String> {

    private Context context;
    private String[] cost;
    private String[] startTime;
    private String[] endTime;
    private String[] currentdate;


    public static String lcusname;
    public static String lfullname;
    public static String lcuscontact;


    TextView tripT,dateT,hoursT,currentdateT;

    public Adapter_triphistory(Context context, String[] tripS,String[] currentdateS, String[] dateS, String[] hoursS) {

        super(context, R.layout.content_trip_history, tripS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.cost = tripS;
        this.startTime = dateS;
        this.endTime = hoursS;
        this.currentdate=currentdateS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_adapter_triphistory, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        tripT = (TextView) rowView.findViewById(R.id.trip);
        dateT = (TextView) rowView.findViewById(R.id.date);
        hoursT = (TextView) rowView.findViewById(R.id.hours);
        currentdateT = (TextView) rowView.findViewById(R.id.currentdate);

        try {

            //Assigning values from array to individual layouts in list view
            tripT.setText(cost[position]);
            dateT.setText(startTime[position]);
            hoursT.setText(endTime[position]);
            currentdateT.setText(currentdate[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* lcusname = cusname[position];
                lfullname = fullname[position];
                lcuscontact = cuscontact[position];
                lapntdate = apntdate[position];
                lvisitdate = visitdate[position];
                lnotes = notes[position];

                Log.d("Location" ," lcusname1111 :" + lcusname);
                Log.d("Location" ," lfullname2222 :" + lfullname);
                Log.d("Location" ," lcuscontact33333 :" + lcuscontact);
                Log.d("Location" ," lapntdate44444 :" + lapntdate);
                Log.d("Location" ," lvisitdate5555 :" + lvisitdate);
                Log.d("Location" ," lnotes66666 :" + lnotes);*/

            }
        });

        return rowView;
    }
}
