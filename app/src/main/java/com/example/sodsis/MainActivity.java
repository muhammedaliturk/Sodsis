package com.example.sodsis;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ConstraintLayout mid_layout,constraintLayout2;
    EditText email_login,sifre_login,email_signup,sifre_signup,isim1,soy_isim,tel,email_forgot_password;
    LinearLayout signup_layout,login_layout,forget_layout;
    FileOutputStream outputStream1,outputStream2;
    FileInputStream inputStream1,inputStream2;
    Button giris;
    TextView signup,login,forgot_password;
    private TextView lastClickedTextView;

    ProgressDialog progressDialog;
    int login_signup_situation=0;
    private void init(){
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.green_high));
        try {
            outputStream1 = openFileOutput("login.txt", Context.MODE_APPEND);//MODE_PRIVATE ilk önce
            outputStream2 = openFileOutput("id.txt", Context.MODE_APPEND);//MODE_PRIVATE ilk önce
            inputStream1 = openFileInput("login.txt");
            inputStream2 = openFileInput("id.txt");
        } catch (Exception e) {
            System.out.println(e);
        }
        email_login=findViewById(R.id.email_login);
        sifre_login=findViewById(R.id.sifre_login);
        email_signup=findViewById(R.id.email_signup);
        sifre_signup=findViewById(R.id.sifre_signup);
        giris=findViewById(R.id.giris);
        mid_layout=findViewById(R.id.mid_layout);
        constraintLayout2=findViewById(R.id.constraintLayout2);
        forget_layout=findViewById(R.id.forget_layout);
        email_forgot_password=findViewById(R.id.email_forgot_password);
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        isim1=findViewById(R.id.isim);
        soy_isim=findViewById(R.id.soy_isim);
        forgot_password=findViewById(R.id.forgot_password);
        tel=findViewById(R.id.tel);
        signup_layout=findViewById(R.id.signup_layout);
        login_layout=findViewById(R.id.login_layout);
        progressDialog= new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Giriş Yapılıyor");
        if (login_kayit_cekme().toString().equals("1")){
            Toast.makeText(getApplicationContext(),"giris yapilmiş",Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(MainActivity.this, MainActivity2.class);
            intent1.putExtra("user_id",login_id_cekme().toString());
            startActivity(intent1);
        } else {
            mid_layout.setVisibility(View.VISIBLE);
            constraintLayout2.setVisibility(View.VISIBLE);
        }
        login_layout.setVisibility(View.VISIBLE);
        signup_layout.setVisibility(View.INVISIBLE);
        underlineText((TextView) login);
        forget_layout.setVisibility(View.INVISIBLE);
        ObjectAnimator slideIn1 = ObjectAnimator.ofFloat(signup_layout, "translationX", 0f, 1000f);
        slideIn1.setDuration(10);
        slideIn1.start();
        ObjectAnimator slideIn2 = ObjectAnimator.ofFloat(forget_layout, "translationX", 0f, -1000f);
        slideIn2.setDuration(10);
        slideIn2.start();
        email_login.requestFocus();

    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //signIn(email.getText().toString(),sifre.getText().toString());
                if (login_signup_situation==0){
                    if (email_login.getText().toString().isEmpty()){
                        email_login.setError("Email is not be empty");
                        email_login.requestFocus();
                    } else if (sifre_login.getText().toString().isEmpty()) {
                        sifre_login.setError("Password is not be empty");
                        sifre_login.requestFocus();
                    }else{
                        progressDialog.show();
                        SignInTask signInTask = new SignInTask();
                        signInTask.execute(email_login.getText().toString(),sifre_login.getText().toString());
                    }

                }else if(login_signup_situation==1){
                    signUp(email_signup.getText().toString(),
                            sifre_signup.getText().toString(),
                            isim1.getText().toString(),
                            soy_isim.getText().toString(),
                            tel.getText().toString());


                }else{
                    if (email_forgot_password.getText().toString().isEmpty()){
                        email_forgot_password.setError("Email is not be empty");
                        email_forgot_password.requestFocus();
                    }else {
                        Toast.makeText(getApplicationContext(),"sifre yenile",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgot_password();
                login_signup_situation=2;
                underlineText((TextView) view);
                email_forgot_password.requestFocus();
                text_reset();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_signup_situation!=0){
                    login_animation(login_signup_situation);
                    login_signup_situation=0;
                    underlineText((TextView) view);
                    email_login.requestFocus();
                    text_reset();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_signup_situation==0){
                    signup_animation();
                    login_signup_situation=1;
                    underlineText((TextView) view);
                    isim1.requestFocus();
                    text_reset();

                }
            }
        });



    }
    private class SignInTask extends AsyncTask<String, Void, Boolean> {

        private FirebaseAuth mAuth;

        public SignInTask() {
            mAuth = FirebaseAuth.getInstance();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            Intent intent1 = new Intent(MainActivity.this, MainActivity2.class);

            try {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, task -> {
                    if (task.isSuccessful()) {
                        // Giriş başarılı
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user!=null){
                            intent1.putExtra("user_id",user.getUid());
                            Toast.makeText(MainActivity.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
                            login_kayit_girme(user.getUid().toString());
                            progressDialog.cancel();
                            startActivity(intent1);
                            finish();
                        }


                    } else {
                        progressDialog.cancel();
                        // Giriş başarısız
                        // Kullanıcıya uygun bir geri bildirim sağlayabilirsiniz
                        // Örneğin, Toast mesajı gösterebilirsiniz.
                        Toast.makeText(MainActivity.this, "Giriş başarısız!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    // Hata durumunda
                    e.printStackTrace();
                });
                return true;
            } catch (Exception e) {
                progressDialog.cancel();
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
        }
    }
    private void signIn(String Email, String Sifre) {
        if (Email.isEmpty()){
            progressDialog.cancel();
            email_login.setError("Email Boş OLAMAZ");
            email_login.requestFocus();
        } else if (Sifre.isEmpty()) {
            progressDialog.cancel();
            sifre_login.setError("Şifre Boş OLAMAZ");
            sifre_login.requestFocus();
        }else {
            // birşeyler yap
        }
    }
    private void login_animation(int i){
        if (i==1){
            underlineText((TextView) login);
            login_layout.setVisibility(View.VISIBLE);
            login.setTextColor(getResources().getColor(R.color.green_high));
            signup.setTextColor(getResources().getColor(R.color.low_black));
            ObjectAnimator translateXUp1 = ObjectAnimator.ofFloat(signup_layout, "translationX", 0f, -5f);
            translateXUp1.setDuration(20);
            translateXUp1.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator translateXDown1 = ObjectAnimator.ofFloat(signup_layout, "translationX", -5f, 1000f);
            translateXDown1.setDuration(150);
            translateXDown1.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator translateXDown2 = ObjectAnimator.ofFloat(login_layout, "translationX", -1000f, 5f);
            translateXDown2.setDuration(150);
            translateXDown2.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator translateXUp2 = ObjectAnimator.ofFloat(login_layout, "translationX", 5f, 0f);
            translateXUp2.setDuration(20);
            translateXUp2.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator alpha1 = ObjectAnimator.ofFloat(signup_layout, "alpha", 1f, 0f);
            alpha1.setDuration(20);
            alpha1.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator alpha2 = ObjectAnimator.ofFloat(login_layout, "alpha", 0f, 1f);
            alpha2.setDuration(20);
            alpha2.setInterpolator(new AccelerateDecelerateInterpolator());
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(alpha2,translateXUp1,translateXDown1,translateXDown2,translateXUp2,alpha1);
            animatorSet.start();
            login_signup_situation=0;
        }else {
            login_layout.setVisibility(View.VISIBLE);
            login.setTextColor(getResources().getColor(R.color.green_high));
            signup.setTextColor(getResources().getColor(R.color.low_black));
            ObjectAnimator translateXUp1 = ObjectAnimator.ofFloat(forget_layout, "translationX", 0f, 10f);
            translateXUp1.setDuration(30);
            translateXUp1.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator translateXDown1 = ObjectAnimator.ofFloat(forget_layout, "translationX", 10f, -1000f);
            translateXDown1.setDuration(150);
            translateXDown1.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator translateXDown2 = ObjectAnimator.ofFloat(login_layout, "translationX", 1000f, -10f);
            translateXDown2.setDuration(150);
            translateXDown2.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator translateXUp2 = ObjectAnimator.ofFloat(login_layout, "translationX", -10f, 0f);
            translateXUp2.setDuration(30);
            translateXUp2.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator alpha1 = ObjectAnimator.ofFloat(forget_layout, "alpha", 1f, 0f);
            alpha1.setDuration(20);
            alpha1.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator alpha2 = ObjectAnimator.ofFloat(login_layout, "alpha", 0f, 1f);
            alpha2.setDuration(20);
            alpha2.setInterpolator(new AccelerateDecelerateInterpolator());
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(alpha2,translateXUp1,translateXDown1,translateXDown2,translateXUp2,alpha1);
            animatorSet.start();
            login_signup_situation=0;
        }


    }
    private void signup_animation(){
        signup_layout.setVisibility(View.VISIBLE);
        signup.setTextColor(getResources().getColor(R.color.green_high));
        login.setTextColor(getResources().getColor(R.color.low_black));
        ObjectAnimator translateXUp1 = ObjectAnimator.ofFloat(login_layout, "translationX", 0f, 5f);
        translateXUp1.setDuration(20);
        translateXUp1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateXDown1 = ObjectAnimator.ofFloat(login_layout, "translationX", 5f, -1000f);
        translateXDown1.setDuration(150);
        translateXDown1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateXDown2 = ObjectAnimator.ofFloat(signup_layout, "translationX", 1000f, -5f);
        translateXDown2.setDuration(150);
        translateXDown2.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateXUp2 = ObjectAnimator.ofFloat(signup_layout, "translationX", -5f, 0f);
        translateXUp2.setDuration(20);
        translateXUp2.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(login_layout, "alpha", 1f, 0f);
        alpha1.setDuration(20);
        alpha1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(signup_layout, "alpha", 0f, 1f);
        alpha2.setDuration(20);
        alpha2.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alpha2,translateXUp1,translateXDown1,translateXDown2,translateXUp2,alpha1);
        animatorSet.start();
    }
    private void forgot_password(){
        signup.setTextColor(getResources().getColor(R.color.low_black));
        login.setTextColor(getResources().getColor(R.color.low_black));
        forget_layout.setVisibility(View.VISIBLE);
        ObjectAnimator translateXUp1 = ObjectAnimator.ofFloat(login_layout, "translationX", 0f, -5f);
        translateXUp1.setDuration(20);
        translateXUp1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateXDown1 = ObjectAnimator.ofFloat(login_layout, "translationX", -5f, 1000f);
        translateXDown1.setDuration(150);
        translateXDown1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateXDown2 = ObjectAnimator.ofFloat(forget_layout, "translationX", -1000f, 5f);
        translateXDown2.setDuration(150);
        translateXDown2.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator translateXUp2 = ObjectAnimator.ofFloat(forget_layout, "translationX", 5f, 0f);
        translateXUp2.setDuration(20);
        translateXUp2.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(login_layout, "alpha", 1f, 0f);
        alpha1.setDuration(20);
        alpha1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(forget_layout, "alpha", 0f, 1f);
        alpha2.setDuration(20);
        alpha2.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alpha2,translateXUp1,translateXDown1,translateXDown2,translateXUp2,alpha1);
        animatorSet.start();
    }
    private void signUp(String email, String password,String isim, String soyisim, String Tel) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        if (TextUtils.isEmpty(email)) {
            progressDialog.cancel();
            email_signup.setError("email cannot be empty");
            email_signup.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            progressDialog.cancel();
            sifre_signup.setError("password cannot be empty");
            sifre_signup.requestFocus();
        } else if (TextUtils.isEmpty(isim)) {
            progressDialog.cancel();
            isim1.setError("name cannot be empty");
            isim1.requestFocus();
        } else if (TextUtils.isEmpty(soyisim)) {
            progressDialog.cancel();
            soy_isim.setError("surname cannot be empty");
            soy_isim.requestFocus();
        } else if (TextUtils.isEmpty(Tel)) {
            progressDialog.cancel();
            tel.setError("kurumid cannot be empty");
            tel.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser()!=null && mAuth.getCurrentUser().getUid()!=null){
                                    signUp_veri_kayit(email,password,isim,soyisim,Tel,
                                            Objects.requireNonNull(mAuth.getCurrentUser()).getUid().toString());
                                    Toast.makeText(MainActivity.this, "Üye olma başarılı", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                // Kayıt işlemi başarısız oldu
                                Toast.makeText(MainActivity.this,
                                        "Üye olma başarısız: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    private void signUp_veri_kayit(String email, String password,String isim, String soyisim, String tel,String id){
        Map<String, Object> user = new HashMap<>();
        user.put("isim", isim);
        user.put("email", email);
        user.put("soyisim", soyisim);
        user.put("sifre", password);
        user.put("tel", tel);
        user.put("id",id);
        db.collection(id).document("kullanici_bilgi").set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "veriler kayıt edildi", Toast.LENGTH_SHORT).show();
                login_animation(login_signup_situation);
            }
        });
    }
    private void underlineText(TextView textView) {
        if (lastClickedTextView != null && lastClickedTextView != textView) {
            removeUnderline(lastClickedTextView);
        }
        if ((textView.getPaintFlags() & Paint.UNDERLINE_TEXT_FLAG) != Paint.UNDERLINE_TEXT_FLAG) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            animateUnderline(textView);
        }
        lastClickedTextView = textView;
    }

    private void removeUnderline(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
    }

    private void animateUnderline(TextView textView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, View.ALPHA, 0f, 1f);
        animator.setDuration(300);
        animator.start();
    }
    private void text_reset(){
        email_login.setText(null);
        email_signup.setText(null);
        email_forgot_password.setText(null);
        sifre_login.setText(null);
        sifre_signup.setText(null);
        isim1.setText(null);
        soy_isim.setText(null);
        tel.setText(null);
    }
    private void login_kayit_girme(String id){
        try {
            outputStream1.write("1".getBytes());
            outputStream2.write(id.getBytes());
            outputStream1.close();
            outputStream2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String login_kayit_cekme(){
        int a = 65;
        email_signup.setText(Character.toString((char) a));

        int x;
        String okunan = "";
        try {

            while ((x = inputStream1.read()) != -1) {
                okunan += Character.toString((char) x);
            }
            inputStream1.close();

        } catch (Exception e) {

        }





        return okunan;
    }
    private String login_id_cekme(){
        int a = 65;
        isim1.setText(Character.toString((char) a));

        int x;
        String okunan = "";
        try {

            while ((x = inputStream2.read()) != -1) {
                okunan += Character.toString((char) x);
            }
            inputStream2.close();

        } catch (Exception e) {

        }





        return okunan;
    }


}