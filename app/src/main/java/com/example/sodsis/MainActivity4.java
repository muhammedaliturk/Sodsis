/**************************************************************
 *                                                            *
 *  Proje: [SODSİS]                                           *
 *  Kodun Yazılma Tarihi: [01.02.2024]                        *
 *  Yazan: [Muhammed Ali TÜRK]                                *
 *  E-posta: [ali.turk@std.yildiz.edu.tr]                     *
 *  İnstagram: [@im.aliturkk]                                 *
 *                                                            *
 *************************************************************/
package com.example.sodsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity4 extends AppCompatActivity {

Spinner spinner;
TextView textView27,textView28;
EditText textView29;
CheckBox checkBox;
Button button;
int a=0;
int baslangic,bitis=0;


        FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity4.this,R.color.white));
        spinner=findViewById(R.id.spinner);
        textView27=findViewById(R.id.textView27);
        textView28=findViewById(R.id.textView28);
        textView29=findViewById(R.id.textView29);
        checkBox=findViewById(R.id.checkBox);
        button=findViewById(R.id.button2);
        Intent intent = getIntent();
        Intent intent2=new Intent(MainActivity4.this, MainActivity2.class);
        List<String> categories = new ArrayList<>();
        categories.add("OTOMASYON SEÇENEKLERİ");
        categories.add("Zamanlama");
        categories.add("Sıcaklığa göre");
        categories.add("Neme göre");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        // Spinner'da bir öğe seçildiğinde gerçekleşecek işlemleri belirleyin
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Seçilen öğeyi alın
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Zamanlama")){
                    a=1;
                    textView27.setVisibility(View.VISIBLE);
                    textView28.setVisibility(View.VISIBLE);
                    checkBox.setVisibility(View.VISIBLE);
                    textView29.setVisibility(View.INVISIBLE);
                } else if (item.equals("Sıcaklığa göre")) {
                    a=2;
                    textView27.setVisibility(View.INVISIBLE);
                    textView28.setVisibility(View.INVISIBLE);
                    checkBox.setVisibility(View.VISIBLE);
                    textView29.setHint("Limit Sıcaklık");
                    textView29.setVisibility(View.VISIBLE);
                } else if (item.equals("Neme göre")) {
                    a=3;
                    textView27.setVisibility(View.INVISIBLE);
                    textView28.setVisibility(View.INVISIBLE);
                    checkBox.setVisibility(View.VISIBLE);
                    textView29.setHint("Limit Nem");
                    textView29.setVisibility(View.VISIBLE);
                }
               // Toast.makeText(getApplicationContext(), "Seçilen öğe: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Bir öğe seçilmediğinde yapılacak işlemleri belirleyin (opsiyonel)
            }
        });
        textView28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar currentTime = Calendar.getInstance();
                int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentTime.get(Calendar.MINUTE);

                // TimePickerDialog oluştur ve saat bilgisini al
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity4.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Kullanıcı tarafından seçilen saat bilgisini al ve işle
                                // Burada seçilen saat ile yapmak istediğiniz işlemleri gerçekleştirebilirsiniz
                                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                // Toast.makeText(MainActivity4.this, "Seçilen saat: " + selectedTime, Toast.LENGTH_SHORT).show();
                                baslangic=(hourOfDay*60)+minute;
                                textView28.setText(selectedTime);
                            }
                        }, currentHour, currentMinute, true); // true parametresi 24 saat formatını açar

                // TimePickerDialog'ı göster
                timePickerDialog.show();
            }
        });
        textView27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar currentTime = Calendar.getInstance();
                int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentTime.get(Calendar.MINUTE);

                // TimePickerDialog oluştur ve saat bilgisini al
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity4.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Kullanıcı tarafından seçilen saat bilgisini al ve işle
                                // Burada seçilen saat ile yapmak istediğiniz işlemleri gerçekleştirebilirsiniz
                                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                               // Toast.makeText(MainActivity4.this, "Seçilen saat: " + selectedTime, Toast.LENGTH_SHORT).show();
                                bitis=(hourOfDay*60)+minute;
                                textView27.setText(selectedTime);
                            }
                        }, currentHour, currentMinute, true); // true parametresi 24 saat formatını açar

                // TimePickerDialog'ı göster
                timePickerDialog.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a==1 && !textView28.getText().toString().equals("Başlangıç zamanı") && !textView27.getText().toString().equals("Bitiş Zamanı")){
                    database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("otomasyon").setValue("1");
                    database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("start_time").setValue(String.valueOf(baslangic));
                    database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("finish_time").setValue(String.valueOf(bitis));
                    if (checkBox.isChecked()){
                        database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("tekrar").setValue("1");
                    }else   database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("tekrar").setValue("0");

                } else if (a==2 && !textView29.getText().toString().equals(null)) {
                    database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("otomasyon").setValue("2");
                    database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("limit").setValue(textView29.getText().toString());
                    if (checkBox.isChecked()){
                        database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("tekrar").setValue("1");
                    }else   database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("tekrar").setValue("0");
                }
                else if (a==3 && !textView29.getText().toString().equals(null)) {
                    database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("otomasyon").setValue("3");
                    database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("limit").setValue(textView29.getText().toString());
                    if (checkBox.isChecked()){
                        database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("tekrar").setValue("1");
                    }else   database.getReference(intent.getStringExtra("user_id")).child(intent.getStringExtra("name")).child("tekrar").setValue("0");
                }
                Toast.makeText(getApplicationContext(),"Verileriniz Kaydedilmiştir",Toast.LENGTH_SHORT).show();
               // startActivity(intent2);
              //  finish();


            }
        });
    }
}