package com.example.sodsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

public class MainActivity5 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView isim,email,tel,sifre;
    Button cikis;
    EditText alankodu,telno;
    String user_id,not,tell;
    LinearLayout tel_layout;
    FileOutputStream outputStream1,outputStream2;
    FileInputStream inputStream1,inputStream2;
    @SuppressLint("SetTextI18n")
    private void init(){
        Intent intent = getIntent();
        if (intent != null) {
            user_id = intent.getStringExtra("user_id");
            not=intent.getStringExtra("not");
        }
        isim=findViewById(R.id.textView3);
        email=findViewById(R.id.textView30);
        tel=findViewById(R.id.textView31);
        sifre=findViewById(R.id.textView32);
        cikis=findViewById(R.id.cikis);
        tel_layout=findViewById(R.id.tel_layout);
        alankodu=findViewById(R.id.alankodu);
        telno=findViewById(R.id.tel);

        db.collection(user_id).document("kullanici_bilgi").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()){
                      DocumentSnapshot document = task.getResult();
                      if (document.exists()) {
                          isim.setText("İsim-Soyisim : ".concat(document.get("isim").toString()).concat(" ").concat(document.get("soyisim").toString()));
                          email.setText("Email : ".concat(document.get("email").toString()));
                          tel.setText("Telefon Numarası : ".concat(document.get("tel").toString()));
                          tell=document.get("tel").toString();
                          sifre.setText("Şifre : ".concat(document.get("sifre").toString()));
                      }
                  }
            }
        });
        if (not.equals("1")){
            alankodu.setText("+90");
            telno.setText(tell);
            isim.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            tel.setVisibility(View.INVISIBLE);
            sifre.setVisibility(View.INVISIBLE);
            cikis.setText("Onay Mesajı Gönder");
            tel_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        init();
        PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(getApplicationContext(),"doğrulama başarılı",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(),"doğrulama başarısız",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        Toast.makeText(getApplicationContext(),"kod gönderildi",Toast.LENGTH_SHORT).show();
                        // Kod gönderildiğinde yapılacak işlemler
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                        // Otomatik kod alma süresi dolduğunda yapılacak işlemler
                    }
                };
        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (not.equals("1") && !telno.getText().toString().isEmpty() && !alankodu.getText().toString().isEmpty()){
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+90"+telno.getText().toString(),        // Telefon numarası
                            60,                 // Kodun geçerlilik süresi
                            TimeUnit.SECONDS,   // Zaman birimi
                            MainActivity5.this,               // Activity (Telefon numarasını doğrulayan aktivite)
                            callbacks);         // Doğrulama geri çağrısı
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity5.this);
                    builder.setTitle("Çıkış Yap");
                    builder.setMessage("Çıkış yapmak istediğinize emin misiniz?");
                    builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File dir = getFilesDir();
                            File file = new File(dir, "id.txt");
                            boolean deleted = file.delete();
                            File dir1 = getFilesDir();
                            File file1 = new File(dir1, "login.txt");
                            boolean deleted1 = file1.delete();
                            File dir2 = getFilesDir();
                            File file2 = new File(dir2, "tel.txt");
                            boolean deleted2 = file2.delete();
                            Intent intent1=new Intent(MainActivity5.this,MainActivity.class);
                            startActivity(intent1);
                        }
                    });
                    builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Silme işleminden vazgeç
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }



            }
        });
    }
}