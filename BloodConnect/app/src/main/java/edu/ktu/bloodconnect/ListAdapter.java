package edu.ktu.bloodconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Donor> {

    public ListAdapter(Context context, List<Donor> objects){
        super(context, R.layout.list_item_design, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_design, null);
        }

        TextView name = v.findViewById(R.id.donor_name);
        TextView bloodType = v.findViewById(R.id.donor_blood_type);
        TextView city = v.findViewById(R.id.donor_city);

        Donor item = getItem(position);

        name.setText("Name: " + item.getFirstName());
        bloodType.setText("Blood Type " + item.getBloodType());
        city.setText("City: " + item.getCity());

        return v;
    }
}

