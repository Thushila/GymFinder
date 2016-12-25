package com.example.shiwantha.testone.adaptor;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiwantha.testone.Entity.TrainerObj;
import com.example.shiwantha.testone.GymProfileActivity;
import com.example.shiwantha.testone.MainActivity;
import com.example.shiwantha.testone.R;
import com.example.shiwantha.testone.TrainerProfileActivity;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.trainer_detail_card, null, true);

        CardView cardView = (CardView) rowView.findViewById(R.id.trainerDetailCard);
        TextView trainerName = (TextView) rowView.findViewById(R.id.trainerName);
        ImageView trainerPic = (ImageView) rowView.findViewById(R.id.trainerPic);
        RatingBar trainerRatingBar = (RatingBar) rowView.findViewById(R.id.trainerRatingBar);
        ImageView trainerAvailability = (ImageView) rowView.findViewById(R.id.trainertAvailability);
        TextView location = (TextView) rowView.findViewById(R.id.location);
        TextView trainerDistance = (TextView) rowView.findViewById(R.id.trainerDistance);

        trainerName.setText(trainerObjArray.get(position).getName());
        String distance=String.format(String.valueOf(trainerObjArray.get(position).getDistance()));
        trainerDistance.setText(distance);

        location.setText(trainerObjArray.get(position).getLocation());
        trainerRatingBar.setRating(trainerObjArray.get(position).getRating());
        if (trainerObjArray.get(position).getAvailability()==true){
            trainerAvailability.setVisibility(View.VISIBLE);
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, TrainerProfileActivity.class);
                intent.putExtra("trainerObj", trainerObjArray.get(position));
                context.startActivity(intent);
//                Toast.makeText(context, "hollo "+position, Toast.LENGTH_SHORT).show();
            }
        });


        return rowView;
    }
}
