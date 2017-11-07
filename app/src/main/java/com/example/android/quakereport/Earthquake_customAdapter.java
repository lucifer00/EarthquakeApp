package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Prashant on 8/26/2017.
 */

public class Earthquake_customAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCATION_SEPARATOR="of";
    public Earthquake_customAdapter( Activity context, ArrayList<Earthquake> earthquakeArrayAdapter) {
        super(context,0,earthquakeArrayAdapter);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String primaryloc;
        String offsetloc;
        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.earthquake_custom_list,parent,false);
        }
        Earthquake data=getItem(position);
        TextView mag=(TextView)listItemView.findViewById(R.id.mag_text_view);
        TextView locationoffset=(TextView)listItemView.findViewById(R.id.location_offset);
        TextView primarylocation=(TextView)listItemView.findViewById(R.id.primary_location);
        TextView date=(TextView)listItemView.findViewById(R.id.date_text_view);
        TextView time=(TextView)listItemView.findViewById(R.id.time_text_view);
        Double magnitude=data.getMagnitude();
        GradientDrawable magnitudeCircle=(GradientDrawable)mag.getBackground();
        int magnitudeColor=getMagnitudeColor(magnitude);
        magnitudeCircle.setColor(magnitudeColor);
        DecimalFormat formatter=new DecimalFormat("0.0");
        mag.setText(formatter.format(magnitude));
        Long timeinmillisecd=data.getTimeInMilliSeconds();
        Date dateObject=new Date(timeinmillisecd);
        date.setText(formatDate(dateObject));
        time.setText(formatTime(dateObject));
        String originalLocation=data.getLocation();
        if(originalLocation.contains(LOCATION_SEPARATOR))
        {
            String[] parts=originalLocation.split(LOCATION_SEPARATOR);
            offsetloc=parts[0]+LOCATION_SEPARATOR;
            primaryloc=parts[1];
        }
        else
        {
            offsetloc=getContext().getString(R.string.near_the);
            primaryloc=originalLocation;
        }
        locationoffset.setText(offsetloc);
        primarylocation.setText(primaryloc);
        return listItemView;
    }
    private String formatDate(Date dateObject)
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("LLL dd,yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatTime(Date dateObject)
    {
        SimpleDateFormat timeFormat=new SimpleDateFormat("h:mm a");
    return timeFormat.format(dateObject);
    }
    private int getMagnitudeColor(double magnitude)
    {
        if(magnitude>=1.0&&magnitude<2.0)return ContextCompat.getColor(getContext(),R.color.magnitude1);
        if(magnitude>=2.0&&magnitude<3.0)return ContextCompat.getColor(getContext(),R.color.magnitude2);
        if(magnitude>=3.0&&magnitude<4.0)return ContextCompat.getColor(getContext(),R.color.magnitude3);
        if(magnitude>=4.0&&magnitude<5.0)return ContextCompat.getColor(getContext(),R.color.magnitude4);
        if(magnitude>=5.0&&magnitude<6.0)return ContextCompat.getColor(getContext(),R.color.magnitude5);
        if(magnitude>=6.0&&magnitude<7.0)return ContextCompat.getColor(getContext(),R.color.magnitude6);
        if(magnitude>=7.0&&magnitude<8.0)return ContextCompat.getColor(getContext(),R.color.magnitude7);
        if(magnitude>=8.0&&magnitude<9.0)return ContextCompat.getColor(getContext(),R.color.magnitude8);
        if(magnitude>=9.0&&magnitude<10.0)return ContextCompat.getColor(getContext(),R.color.magnitude9);
        if(magnitude>=10.0)return ContextCompat.getColor(getContext(),R.color.magnitude10plus);

        return 0;
    }
}
