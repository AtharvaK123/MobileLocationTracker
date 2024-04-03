package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.icu.text.Transliterator;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    List<String> addresses, time;
    Context context;
    int xmlResource;
    float size;

    public CustomAdapter(Context context, int resource, String[][] objects) {
        super(context, resource);
        xmlResource = resource;
        for(int i = 0; i < objects.length; i ++) {
            addresses.add(objects[i][0]);
            time.add(objects[i][1]);
        }
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View adapterLayout = layoutInflater.inflate(xmlResource, null);



        TextView addr = adapterLayout.findViewById(R.id.Addresses);
        TextView tim = adapterLayout.findViewById(R.id.Time);
        time = new ArrayList<String>();

        addr.setTypeface(null, Typeface.NORMAL);
        addr.setTextSize(size);
       // tim.setText(time.get(position).toString());

        if(position == 0) {
            //addr.setText(list.get(position).toString());
            addr.setTypeface(null, Typeface.BOLD);
            addr.setTextSize(24);
        }
        else{
            //addr.setText(list.get(position).toString());
            addr.setTextSize(14);
        }
        return adapterLayout;
    }

}
