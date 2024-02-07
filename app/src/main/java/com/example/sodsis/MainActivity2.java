package com.example.sodsis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
LinearLayout linearLayout,linearLayout2,linearLayout3;
TextView textView8,textView6,textView17,textView16,textView10,textView11,textView12,
    textView4,textView5,textView9,textView19,textView18,textView20,textView21,textView22,textView23;
CardView cardView;
ImageButton imageButton3,imageButton3_copy,imageButton7,imageButton8;
ConstraintLayout weather_layout,constraintLayout;
int gun =0;
int field=0;
int prev_list=0;
int buGun_temp=0;
int yarin_temp=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity2.this,R.color.green_high));
        linearLayout=findViewById(R.id.linearLayout);
        textView9=findViewById(R.id.textView9);
        linearLayout2=findViewById(R.id.linearLayout2);
        linearLayout3=findViewById(R.id.linearLayout3);
        imageButton7=findViewById(R.id.imageButton7);
        weather_layout=findViewById(R.id.weather_layout);
        textView8=findViewById(R.id.textView8);
        textView6=findViewById(R.id.textView6);
        imageButton3=findViewById(R.id.imageButton3);
        imageButton3_copy=findViewById(R.id.imageButton3_copy);
        textView17=findViewById(R.id.textView17);
        textView16=findViewById(R.id.textView16);
        cardView=findViewById(R.id.cardview1);
        textView10=findViewById(R.id.textView10);
        textView11=findViewById(R.id.textView11);
        textView12=findViewById(R.id.textView12);
        textView4=findViewById(R.id.textView4);
        textView18=findViewById(R.id.textView18);
        textView19=findViewById(R.id.textView19);
        textView20=findViewById(R.id.textView20);
        textView21=findViewById(R.id.textView21);
        textView22=findViewById(R.id.textView22);
        textView23=findViewById(R.id.textView23);
        imageButton8=findViewById(R.id.imageButton8);
        textView5=findViewById(R.id.textView5);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable corn = getResources().getDrawable(R.drawable.corn);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable wheat = getResources().getDrawable(R.drawable.wheat);
        corn.setBounds(0, 0, corn.getIntrinsicWidth(), corn.getIntrinsicHeight());
        wheat.setBounds(0, 0, wheat.getIntrinsicWidth(), wheat.getIntrinsicHeight());
        constraintLayout=findViewById(R.id.constraintLayout);
        animation_in();
        updateNumberAnimated(25,25,25);
        if (Integer.parseInt(textView10.getText().toString())<20){
            imageButton3.setVisibility(View.INVISIBLE);
            imageButton3_copy.setVisibility(View.VISIBLE);
        }else{
            imageButton3.setVisibility(View.VISIBLE);
            imageButton3_copy.setVisibility(View.INVISIBLE);
        }
        ArrayList<ListItem> data = new ArrayList<>();
        data.add(new ListItem("Kayseri","Tarla 1","Mısır","200","10","6","0","90", R.drawable.field1));
        data.add(new ListItem("İstanbul","Tarla 2","Buğday","500","40","40","20","55" ,R.drawable.field2));
        data.add(new ListItem("Yahyalı","Tarla 3","Nohut","700","17","6","0","10" ,R.drawable.field2));

        ArrayAdapter<ListItem> adapter = new ArrayAdapter<ListItem>(this, R.layout.fields, R.id.textView, data) {
            @NonNull
            @Override
            public android.view.View getView(int position, @Nullable android.view.View convertView, @NonNull android.view.ViewGroup parent) {
                android.view.View itemView = super.getView(position, convertView, parent);

                TextView textView = itemView.findViewById(R.id.textView);
                TextView textView1=itemView.findViewById(R.id.textView7);
                ImageView imageView = itemView.findViewById(R.id.imageView);

                ListItem currentItem = getItem(position);

                if (currentItem != null) {
                    textView.setText(currentItem.getLoc());
                    textView1.setText(currentItem.getName());
                    imageView.setImageResource(currentItem.getImageResourceId());
                }

                return itemView;
            }
        };

        // Get a reference to the ListView and set the adapter
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable_full = getResources().getDrawable(R.drawable.water_full);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable_medium = getResources().getDrawable(R.drawable.water_medium);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable_loss = getResources().getDrawable(R.drawable.water_loss);
// Set the new drawable as the top drawable

         textView20.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 weather_layout.setBackgroundResource(R.drawable.weather_layout_bg);
                 textView20.setBackground(null);
                 textView23.setBackgroundResource(R.drawable.weather_layout_bg);
             }
         });
         textView23.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 weather_layout.setBackground(null);
                 textView20.setBackgroundResource(R.drawable.weather_layout_bg);
                 textView23.setBackground(null);
             }
         });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListItem selectedFieldItem = (ListItem) listView.getItemAtPosition(i);
                prev_list=Integer.parseInt(textView10.getText().toString());
                textView18.setVisibility(View.VISIBLE);
                textView19.setVisibility(View.VISIBLE);
                textView20.setVisibility(View.VISIBLE);
                textView21.setVisibility(View.VISIBLE);
                textView22.setVisibility(View.VISIBLE);
                textView23.setVisibility(View.VISIBLE);
                imageButton8.setVisibility(View.VISIBLE);
                if (field==0){
                    ObjectAnimator slideIn1 = ObjectAnimator.ofFloat(textView4, "alpha", 1f, 0f);
                    ObjectAnimator slideIn2 = ObjectAnimator.ofFloat(textView5, "translationX", 0f, 320f);
                    ObjectAnimator slideIn3 = ObjectAnimator.ofFloat(imageButton7, "alpha", 0f, 1f);
                    ObjectAnimator slideIn4 = ObjectAnimator.ofFloat(linearLayout2, "translationY", 0f, 500f);
                    ObjectAnimator slideIn5 = ObjectAnimator.ofFloat(linearLayout2, "alpha", 1f, 0f);
                    slideIn1.setDuration(100);slideIn2.setDuration(200);slideIn3.setDuration(200);slideIn4.setDuration(200);slideIn5.setDuration(200);
                    slideIn1.start();slideIn2.start();slideIn3.start(); slideIn4.start();slideIn5.start();
                }
                textView21.setText(selectedFieldItem.getWater().toString().concat(" %"));
                if (Integer.parseInt(selectedFieldItem.getWater().toString())>=60){
                    textView21.setCompoundDrawablesWithIntrinsicBounds(null, drawable_full, null, null);
                } else if (60>Integer.parseInt(selectedFieldItem.getWater().toString())&& Integer.parseInt(selectedFieldItem.getWater().toString())>40) {
                    textView21.setCompoundDrawablesWithIntrinsicBounds(null, drawable_medium, null, null);
                }else {
                    textView21.setCompoundDrawablesWithIntrinsicBounds(null, drawable_loss, null, null);
                }
                textView18.setText(selectedFieldItem.getType().toString());
                if(selectedFieldItem.getType().toString().equals("Mısır")){
                    textView18.setCompoundDrawablesWithIntrinsicBounds(null, corn, null, null);
                } else if (selectedFieldItem.getType().toString().equals("Buğday")) {
                    textView18.setCompoundDrawablesWithIntrinsicBounds(null, wheat, null, null);
                }
                textView19.setText(selectedFieldItem.getArea().toString().concat("m^2"));
                field=1;
                int temp=Integer.parseInt(selectedFieldItem.getTemp().toString());
                int hum=Integer.parseInt(selectedFieldItem.getHum().toString());
                int rain=Integer.parseInt(selectedFieldItem.getRain().toString());
                imageButton7.setVisibility(View.VISIBLE);
                textView5.setTextSize(30);
                textView5.setText(selectedFieldItem.getName());

                textView5.setShadowLayer(5, 5, 5, R.color.green_high);
                textView9.setText(selectedFieldItem.getLoc());
                linearLayout.setBackgroundResource(selectedFieldItem.getImageResourceId());
                onButtonClicked(temp,hum,rain);
                if (temp>=20 && prev_list<20){
                    weather_animation_in();
                }else if (temp<20 && prev_list>=20){
                    weather_animation_out();
                }
                listView.setEnabled(false);
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                field=0;
                prev_list=Integer.parseInt(textView10.getText().toString());
                linearLayout.setBackgroundResource(R.drawable.top_bg);
                textView18.setVisibility(View.INVISIBLE);
                textView19.setVisibility(View.INVISIBLE);
                textView20.setVisibility(View.INVISIBLE);
                textView21.setVisibility(View.INVISIBLE);
                textView22.setVisibility(View.INVISIBLE);
                textView23.setVisibility(View.INVISIBLE);
                ObjectAnimator slideIn1 = ObjectAnimator.ofFloat(textView4, "alpha", 0f, 1f);
                ObjectAnimator slideIn2 = ObjectAnimator.ofFloat(textView5, "translationX", 320f, 0f);
                ObjectAnimator slideIn3 = ObjectAnimator.ofFloat(imageButton7, "alpha", 1f, 0f);
                ObjectAnimator slideIn4 = ObjectAnimator.ofFloat(linearLayout2, "translationY", 500f, 0f);
                ObjectAnimator slideIn5 = ObjectAnimator.ofFloat(linearLayout2, "alpha", 0f, 1f);
                slideIn1.setDuration(100);slideIn2.setDuration(200);slideIn3.setDuration(200);slideIn4.setDuration(200);slideIn5.setDuration(200);
                slideIn1.start();slideIn2.start();slideIn3.start(); slideIn4.start();slideIn5.start();
                imageButton7.setVisibility(View.INVISIBLE);
                imageButton8.setVisibility(View.INVISIBLE);
                textView5.setTextSize(20);
                textView5.setText("Muhammed Ali");
                int temp =25;
                updateNumberAnimated(temp,25,25);
                listView.setEnabled(true);
                if (temp>=20 && prev_list<20){
                    weather_animation_in();
                }else if (temp<20 && prev_list>=20){
                    weather_animation_out();
                }
            }
        });
        textView8.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                buGun_temp=Integer.parseInt(textView10.getText().toString());
                yarin_temp=5;
                textView8.setTextSize(14);
                textView6.setTextSize(12);
                textView8.setTextColor(getResources().getColor(R.color.green_high));
                textView8.setBackgroundResource(R.drawable.weather_layout_bg);
                ViewGroup.LayoutParams params = textView8.getLayoutParams();
                params.height=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics());
                params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, getResources().getDisplayMetrics());
                textView8.setLayoutParams(params);
                ViewGroup.LayoutParams params2 = textView6.getLayoutParams();
                params2.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                params2.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                textView6.setLayoutParams(params2);
                textView6.setBackground(null);
                textView6.setTextColor(getResources().getColor(R.color.low_black));
                if (gun==0){
                    onButtonClicked(5,5,5);
                    if (buGun_temp>=20 && yarin_temp<20){
                        weather_animation_out();
                    } else if (buGun_temp<20 && yarin_temp>=20) {
                        weather_animation_in();
                    }
                }
                gun=1;
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buGun_temp=Integer.parseInt(textView10.getText().toString());
                yarin_temp=25;
                textView6.setTextSize(14);
                textView8.setTextSize(12);
                textView6.setTextColor(getResources().getColor(R.color.green_high));
                textView6.setBackgroundResource(R.drawable.weather_layout_bg);
                ViewGroup.LayoutParams params = textView6.getLayoutParams();
                params.height=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics());
                params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, getResources().getDisplayMetrics());
                textView6.setLayoutParams(params);
                ViewGroup.LayoutParams params2 = textView8.getLayoutParams();
                params2.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                params2.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                textView8.setLayoutParams(params2);
                textView8.setBackground(null);
                textView8.setTextColor(getResources().getColor(R.color.low_black));
                if (gun==1){
                    onButtonClicked(25,25,25);
                    if (buGun_temp>=20 && yarin_temp<20){
                        weather_animation_out();
                    } else if (buGun_temp<20 && yarin_temp>=20) {
                        weather_animation_in();
                    }
                }
                gun=0;
            }
        });


    }

    void animation_in(){

        ObjectAnimator translateYUp1 = ObjectAnimator.ofFloat(linearLayout, "translationY", -500f, 0f);
        translateYUp1.setDuration(700);
        translateYUp1.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(translateYUp1);
        animatorSet.start();
       //////////////////////////////////////////////////////////////////
        ObjectAnimator translateYUp3 = ObjectAnimator.ofFloat(linearLayout2, "translationY", 500f, 0f);
        translateYUp3.setDuration(700);
        translateYUp3.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playSequentially(translateYUp3);
        animatorSet2.start();
        ////////////////////////////////////////////////////////////////////////////////////////////////


        /*@SuppressLint("Recycle") ObjectAnimator slideIn1 = ObjectAnimator.ofFloat(cardView, "translationX", -500f, 0f);
        slideIn1.setDuration(800);
        slideIn1.start();*/
        @SuppressLint("Recycle") ObjectAnimator slideIn2 = ObjectAnimator.ofFloat(cardView, "alpha", 0f, 1f);
        @SuppressLint("Recycle") ObjectAnimator slideIn3 = ObjectAnimator.ofFloat(weather_layout, "translationY", -500f, 0f);
        @SuppressLint("Recycle") ObjectAnimator slideIn5 = ObjectAnimator.ofFloat(linearLayout3, "alpha", 0f, 1f);
        slideIn2.setDuration(400);
        slideIn5.setDuration(700);
        slideIn3.setDuration(750);
        slideIn3.start();
        slideIn2.start();
        slideIn5.start();

    }
    void weather_animation_in(){
        imageButton3_copy.setY(-500);
        imageButton3_copy.setVisibility(View.VISIBLE);
        ObjectAnimator translateYUp1 = ObjectAnimator.ofFloat(imageButton3, "translationY", 0f, -5f);
        translateYUp1.setDuration(20);
        translateYUp1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateYDown1 = ObjectAnimator.ofFloat(imageButton3, "translationY", -5f, 500f);
        translateYDown1.setDuration(150);
        translateYDown1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateYDown2 = ObjectAnimator.ofFloat(imageButton3_copy, "translationY", -200f, 30f);
        translateYDown2.setDuration(150);
        translateYDown2.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateYUp2 = ObjectAnimator.ofFloat(imageButton3_copy, "translationY", 30f, 25f);
        translateYUp2.setDuration(20);
        translateYUp2.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(translateYUp1,translateYDown1,translateYDown2,translateYUp2);
        animatorSet.start();


    }
    void weather_animation_out(){
        imageButton3.setY(500);
        imageButton3.setVisibility(View.VISIBLE);
        ObjectAnimator translateYUp1 = ObjectAnimator.ofFloat(imageButton3_copy, "translationY", 0f, 5f);
        translateYUp1.setDuration(20);
        translateYUp1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateYDown1 = ObjectAnimator.ofFloat(imageButton3_copy, "translationY", 5f, -500f);
        translateYDown1.setDuration(150);
        translateYDown1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateYDown2 = ObjectAnimator.ofFloat(imageButton3, "translationY", 200f, -10f);
        translateYDown2.setDuration(150);
        translateYDown2.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateYUp2 = ObjectAnimator.ofFloat(imageButton3, "translationY", -10f, -5f);
        translateYUp2.setDuration(20);
        translateYUp2.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(translateYUp1,translateYDown1,translateYDown2,translateYUp2);
        animatorSet.start();


    }
    private void updateNumberAnimated(int temp,int hum,int rain) {
        ValueAnimator animator1 = ValueAnimator.ofInt(Integer.parseInt(textView10.getText().toString()), temp);
        @SuppressLint("Recycle") ValueAnimator animator2 = ValueAnimator.ofInt(Integer.parseInt(textView11.getText().toString()), hum);
        @SuppressLint("Recycle") ValueAnimator animator3 = ValueAnimator.ofInt(Integer.parseInt(textView12.getText().toString()), rain);
        animator1.setDuration(500);
        animator2.setDuration(500);
        animator3.setDuration(500);

        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue1 = (int) valueAnimator.getAnimatedValue();
                textView10.setText(String.valueOf(animatedValue1));
            }
        });
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                int animatedValue2 = (int) valueAnimator.getAnimatedValue();
                textView11.setText(String.valueOf(animatedValue2));
            }
        });
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                int animatedValue3 = (int) valueAnimator.getAnimatedValue();
                textView12.setText(String.valueOf(animatedValue3));
            }
        });
        animator1.start();
        animator2.start();
        animator3.start();
    }
    private void onButtonClicked(int temp,int hum,int rain) {
        // Replace this with your actual logic to get the new value
        // Update the number animatedly
        updateNumberAnimated(temp,hum,rain);
    }
}