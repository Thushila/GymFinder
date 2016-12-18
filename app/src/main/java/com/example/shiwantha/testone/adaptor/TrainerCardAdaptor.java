package com.example.shiwantha.testone.adaptor;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shiwantha.testone.Entity.TrainerObj;
import com.example.shiwantha.testone.R;

import java.util.ArrayList;

/**
 * Created by MSI on 12/16/2016.
 */

public class TrainerCardAdaptor extends ArrayAdapter<TrainerObj> {

    private final Activity context;
    private final ArrayList<TrainerObj> trainerObjArray;

    public TrainerCardAdaptor(Activity context, ArrayList<TrainerObj> trainerObjArray) {
        super(context, R.layout.trainer_detail_card, trainerObjArray);
        this.context = context;
        this.trainerObjArray = trainerObjArray;


    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.trainer_detail_card, null, true);


        TextView trainerName = (TextView) rowView.findViewById(R.id.trainerName);
        ImageView trainerPic = (ImageView) rowView.findViewById(R.id.trainerPic);
        RatingBar trainerRatingBar = (RatingBar) rowView.findViewById(R.id.trainerRatingBar);
//      ImageView trainerAvailability = (ImageView) rowView.findViewById(R.id.trainerAvailability);
        TextView location = (TextView) rowView.findViewById(R.id.location);

        trainerName.setText(trainerObjArray.get(position).getName());
        location.setText(trainerObjArray.get(position).getLocation());
        trainerRatingBar.setRating(trainerObjArray.get(position).getRating());

        return rowView;
    }
}
