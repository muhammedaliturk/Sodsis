package com.example.sodsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity3 extends AppCompatActivity {
EditText name,loc,type,area;
Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity3.this,R.color.white));
        name=findViewById(R.id.name);
        loc=findViewById(R.id.loc);
        type=findViewById(R.id.type);
        area=findViewById(R.id.area);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()){
                    name.setError("Bu alan boş Bırakılamaz");
                } else if (loc.getText().toString().isEmpty()) {
                    loc.setError("Bu alan boş Bırakılamaz");
                } else if (type.getText().toString().isEmpty()) {
                    type.setError("Bu alan boş Bırakılamaz");
                } else if (area.getText().toString().isEmpty()) {
                    area.setError("Bu alan boş Bırakılamaz");
                }else {

                }
            }
        });


    }
}