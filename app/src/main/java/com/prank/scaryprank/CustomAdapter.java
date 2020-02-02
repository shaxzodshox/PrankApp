package com.prank.scaryprank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<CustomItems> {

    public CustomAdapter(@NonNull Context context, ArrayList<CustomItems> customList) {
        super(context, 0,customList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    public View customView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner,parent,false);
        }
        CustomItems items =getItem(position);
        TextView myTxtView = convertView.findViewById(R.id.tvCustomSpinner);

        if(items!=null){
            myTxtView.setText(items.getSpinnerText());
        }
        return convertView;
    }
}
