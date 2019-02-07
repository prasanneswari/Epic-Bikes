package com.jts.root.ebike_project;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_triphistory extends ArrayAdapter<Triphistory_Java> {


    private Activity activity;

    public Adapter_triphistory(Activity activity,
                          List<Triphistory_Java> triphistory) {
        super(activity, R.layout.content_trip_history,triphistory);
        this.activity = activity;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater =
                (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.activity_adapter_triphistory, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        }  else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        Triphistory_Java country = getItem(position);

        holder.tripT.setText(country.getCost());
        holder.dateT.setText(country.getStartTime());
        holder.hoursT.setText(country.getEndTime());
        holder.currentdateT.setText(country.getCurrentdate());

        return convertView;
    }

    private static class ViewHolder {
        private TextView tripT,dateT,hoursT,currentdateT;;

        public ViewHolder(View rowView) {
            tripT = (TextView) rowView.findViewById(R.id.trip);
            dateT = (TextView) rowView.findViewById(R.id.date);
            hoursT = (TextView) rowView.findViewById(R.id.hours);
            currentdateT = (TextView) rowView.findViewById(R.id.currentdate);
        }
    }
}


//this is real adapter classs

  /* private Context context;
    private List<String> cost;
    private List<String> startTime;
    private List<String> endTime;
    private List<String> currentdate;




    TextView tripT,dateT,hoursT,currentdateT;

    public Adapter_triphistory(Context context, List<String> tripS, List<String> currentdateS, List<String> dateS, List<String> hoursS) {

        super(context, R.layout.content_trip_history, tripS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.cost = tripS;
        this.startTime = dateS;
        this.endTime = hoursS;
        this.currentdate=currentdateS;

    }
    public void addlistitemadapter(List<String> tripl,List<String> currentdatel,List<String> datel,List<String> hoursl){
        cost.addAll(tripl);
        startTime.addAll(currentdatel);
        endTime.addAll(datel);
        currentdate.addAll(hoursl);
        this.notifyDataSetChanged();

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
            tripT.setText(cost.get(position));
            dateT.setText(startTime.get(position));
            hoursT.setText(endTime.get(position));
            currentdateT.setText(currentdate.get(position));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rowView;
    }
}*/