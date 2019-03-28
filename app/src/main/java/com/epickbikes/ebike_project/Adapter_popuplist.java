package com.epickbikes.ebike_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class Adapter_popuplist extends ArrayAdapter<String> {

    private Context context;
    private String[] Nbikeid;
    private String[] Nrange;
    private String[] Ntimestamp;
    private String[] Nnotes;

    public static String lcusname;
    public static String lfullname;
    public static String lcuscontact;
    public static String lapntdate;
    public static String lvisitdate;
    static int posval;
    public static String lnotes;
    TextView NbikeidT,NrangeT,NtimestampT,NnotesT;

    public Adapter_popuplist(Context context, String[] NbikeidS, String[] NrangeS) {

        super(context, R.layout.epick_bikes, NbikeidS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.Nbikeid = NbikeidS;
        this.Nrange = NrangeS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_adapter_popuplist, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
         posval=position;
        NbikeidT = (TextView) rowView.findViewById(R.id.bikeid);
        NrangeT = (TextView) rowView.findViewById(R.id.range);

        try {

            //Assigning values from array to individual layouts in list view
            NbikeidT.setText(Nbikeid[position]);
            NrangeT.setText(Nrange[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Userview_task.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ltcame = tcname[position];
                ltstatusname = tstatusname[position];
                lDtapntdate = tapntdate[position];
                lStcmpltdate = tcmpltdate[position];
                ltapnttype = tapnttype[position];

                Log.d("Location" ," ltcame1111 :" + ltcame);
                Log.d("Location" ," ltstatusname2222 :" + ltstatusname);
                Log.d("Location" ," lDtapntdate33333 :" + lDtapntdate);
                Log.d("Location" ," lStcmpltdate44444 :" + lStcmpltdate);
                Log.d("Location" ," ltapnttype55555 :" + ltapnttype);

            }
        });*/
        return rowView;
    }

}
