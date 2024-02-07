package com.example.sodsis;

import static com.example.sodsis.R.color.white;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
ImageButton imageButton1;
LinearLayout top_layout;
CardView cardView1,cardView2,cardView3;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.white));
        imageButton1=findViewById(R.id.imageButton);
        cardView1=findViewById(R.id.cardview1);
        cardView2=findViewById(R.id.cardview2);
        cardView3=findViewById(R.id.cardview3);
        top_layout=findViewById(R.id.top_layout);
        animation();
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
    void animation(){
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        @SuppressLint("Recycle") ObjectAnimator slideIn = ObjectAnimator.ofFloat(cardView1, "translationX", -500f, 0f);
        slideIn.setDuration(600);
        slideIn.start();
        @SuppressLint("Recycle") ObjectAnimator slideIn1 = ObjectAnimator.ofFloat(cardView2, "translationX", -500f, 0f);
        slideIn1.setDuration(800);
        slideIn1.start();
        @SuppressLint("Recycle") ObjectAnimator slideIn2 = ObjectAnimator.ofFloat(cardView3, "translationX", -500f, 0f);
        slideIn2.setDuration(1000);
        slideIn2.start();
        @SuppressLint("Recycle") ObjectAnimator slideIn3 = ObjectAnimator.ofFloat(top_layout, "translationY", -400f, 0f);
        slideIn3.setDuration(500);
        slideIn3.start();
    }
}