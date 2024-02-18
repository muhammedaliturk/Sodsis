package com.example.sodsis;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
ImageButton imageButton1;
LinearLayout top_layout;
    int Tarla1_sulama,Tarla2_sulama,Tarla3_sulama=0;

CardView cardView1,cardView2,cardView3;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tarla1_sulama = database.getReference("tarla1").child("sulama");
    DatabaseReference tarla2_sulama = database.getReference("tarla2").child("sulama");
    DatabaseReference tarla3_sulama = database.getReference("tarla3").child("sulama");
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
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        firebase_veriCekme(intent);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("tarla1_sulama",Tarla1_sulama);
                intent.putExtra("tarla2_sulama",Tarla2_sulama);
                intent.putExtra("tarla3_sulama",Tarla3_sulama);
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
    void firebase_veriCekme(Intent intent){

        tarla1_sulama.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()&& snapshot.getValue() != null){
                    Tarla1_sulama=Integer.parseInt(Objects.requireNonNull(snapshot.getValue(String.class).toString()));
                  //  Toast.makeText(getApplicationContext(),Objects.requireNonNull(snapshot.getValue(String.class).toString()),Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tarla2_sulama.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()&& snapshot.getValue() != null){
                    Tarla2_sulama=Integer.parseInt(Objects.requireNonNull(snapshot.getValue(String.class).toString()));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tarla3_sulama.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()&& snapshot.getValue() != null){
                    Tarla3_sulama=Integer.parseInt(Objects.requireNonNull(snapshot.getValue(String.class).toString()));
                   intent.putExtra("tarla3_sulama",Tarla3_sulama);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}