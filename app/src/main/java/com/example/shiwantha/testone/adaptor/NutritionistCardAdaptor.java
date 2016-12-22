package com.example.shiwantha.testone.adaptor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shiwantha.testone.Entity.NutritionistObj;
import com.example.shiwantha.testone.NutritionistProfileActivity;
import com.example.shiwantha.testone.R;

import java.util.ArrayList;

public class NutritionistCardAdaptor extends ArrayAdapter<NutritionistObj>{ //NutritionistObj

    private final Activity context;
    private final ArrayList<NutritionistObj> nutritionistObjArray;


    public NutritionistCardAdaptor(Activity context,  ArrayList<NutritionistObj> nutritionistObjArray) {
         super(context, R.layout.nutritionist_detail_card, nutritionistObjArray);
        Log.e("Test1","OnPost Data vvvvvvvvv:: "+nutritionistObjArray.get(0).getName());
        // TODO Auto-generated constructor stub

        this.context=context;
        this.nutritionistObjArray=nutritionistObjArray;

    }

    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.nutritionist_detail_card, null,true);


        CardView cardView = (CardView) rowView.findViewById(R.id.nutritionistDetailCard);
        TextView nutritionistName = (TextView) rowView.findViewById(R.id.nutritionistName);
        ImageView nutritionistPic = (ImageView) rowView.findViewById(R.id.nutritionistPic);
        RatingBar nutritionistRate =(RatingBar) rowView.findViewById(R.id.nutritionistRate);
        ImageView nutritionistAvailability = (ImageView) rowView.findViewById(R.id.nutritionistAvailability);
        TextView nutritionistAddress = (TextView) rowView.findViewById(R.id.nutritionistAddress);




        nutritionistName.setText(nutritionistObjArray.get(position).getName());
        nutritionistAddress.setText(nutritionistObjArray.get(position).getNo()+" "+nutritionistObjArray.get(position).getStreet()+" "+nutritionistObjArray.get(position).getCity());
        nutritionistRate.setRating((float)nutritionistObjArray.get(position).getRating());
        if (nutritionistObjArray.get(position).getAvailability()==true){
            nutritionistAvailability.setVisibility(View.VISIBLE);
        }
       // availability.setImageIcon(Icon.createWithFilePath());


//        nutritionistName.setText(itemname[position]);
//        nutritionistPic.setImageResource(imgid[position]);
//        extratxt.setText("Description "+itemname[position]);@drawable/ic_thumb_up_black_24dp

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NutritionistProfileActivity.class);
                intent.putExtra("nutritionistObj", nutritionistObjArray.get(position));
                context.startActivity(intent);
//                Toast.makeText(context, "hollo "+position, Toast.LENGTH_SHORT).show();
            }
        });
        return rowView;

    };


}
