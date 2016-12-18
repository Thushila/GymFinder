package com.example.shiwantha.testone.adaptor;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shiwantha.testone.Entity.NutritionistObj;
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

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.nutritionist_detail_card, null,true);

        TextView nutritionistName = (TextView) rowView.findViewById(R.id.nutritionistName);
        ImageView nutritionistPic = (ImageView) rowView.findViewById(R.id.nutritionistPic);
        RatingBar nutritionistRate =(RatingBar) rowView.findViewById(R.id.nutritionistRate);
        ImageView nutritionistAvailability = (ImageView) rowView.findViewById(R.id.nutritionistAvailability);
        TextView nutritionistAddress = (TextView) rowView.findViewById(R.id.nutritionistAddress);

        nutritionistName.setText(nutritionistObjArray.get(position).getName());
        nutritionistAddress.setText(nutritionistObjArray.get(position).getNo()+" "+nutritionistObjArray.get(position).getStreet()+" "+nutritionistObjArray.get(position).getCity());
        nutritionistRate.setRating((float)nutritionistObjArray.get(position).getRating());
       // availability.setImageIcon(Icon.createWithFilePath());


//        nutritionistName.setText(itemname[position]);
//        nutritionistPic.setImageResource(imgid[position]);
//        extratxt.setText("Description "+itemname[position]);@drawable/ic_thumb_up_black_24dp
        return rowView;

    };


}
