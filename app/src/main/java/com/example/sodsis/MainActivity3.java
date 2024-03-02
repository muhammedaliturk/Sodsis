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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

EditText name,area;
TextView loc,type;
Button textView26;
Button button,button4;
    private ArrayAdapter<String> adapter1,adapter2;
    Dialog dialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private List<String> cities = Arrays.asList("İstanbul", "Ankara", "İzmir", "Bursa", "Adana", "Antalya",
            "Konya", "Eskişehir", "Diyarbakır", "Trabzon", "Samsun", "Gaziantep", "Kayseri", "Mersin", "Kocaeli",
            "Manisa", "Balıkesir", "Aydın", "Hatay", "Malatya", "Şanlıurfa", "Denizli", "Erzurum", "Kahramanmaraş",
            "Van", "Sivas", "Adıyaman", "Muğla", "Tekirdağ", "Zonguldak", "Çorum", "Aksaray", "Kırıkkale", "Isparta",
            "Osmaniye", "Bolu", "Çanakkale", "Kütahya", "Kırşehir", "Nevşehir", "Niğde", "Ağrı", "Afyonkarahisar",
            "Amasya", "Artvin", "Yalova", "Kastamonu", "Karabük", "Kırklareli", "Karaman", "Batman", "Şırnak",
            "Kilis", "Bartın", "Rize", "Giresun", "Ordu", "Tunceli", "Erzincan", "Gümüşhane", "Bayburt", "Siirt",
            "Hakkari", "Ardahan", "Iğdır");
    private List<String> mahsul = Arrays.asList("Mısır","Arpa","Buğday","Nohut","kabak","karpuz","elma","kiraz",
            "domates","salatalık");
    int a=0;
    int b=0;
    String user_id;
    @SuppressLint({"MissingInflatedId", "UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity3.this,R.color.green_high));
        Intent intent = getIntent();
        name=findViewById(R.id.name);
        loc=findViewById(R.id.loc);
        Intent intent1 = new Intent(MainActivity3.this, MainActivity2.class);
        type=findViewById(R.id.type);
        textView26=findViewById(R.id.button3);
        area=findViewById(R.id.area);
        button4=findViewById(R.id.button4);
        button=findViewById(R.id.button);
        dialog = new Dialog(MainActivity3.this);
        dialog.setContentView(R.layout.filter_citys_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        EditText sehir_text = dialog.findViewById(R.id.sehir_text);
        ListView listView = dialog.findViewById(R.id.listview);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mahsul);
        internet_kontrol();
        if (intent.getStringExtra("came").toString().equals("0")){
            textView26.setVisibility(View.VISIBLE);
            name.setText(intent.getStringExtra("name"));
            loc.setText(intent.getStringExtra("loc"));
            type.setText(intent.getStringExtra("type"));
            area.setText(intent.getStringExtra("area"));
            user_id=intent.getStringExtra("user_id");
            b=1;
        }else {
            user_id=intent.getStringExtra("user_id");
            textView26.setVisibility(View.INVISIBLE);
        }
        textView26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
                    builder.setTitle("Tarlayı Sil");
                    builder.setMessage(" ' "+ name.getText().toString()+" ' "+" tarlasını silmek istediğinize emin misiniz?");
                    builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.collection(user_id).document(intent.getStringExtra("id")).delete();
                            database.getReference(user_id).child(intent.getStringExtra("name")).removeValue().isSuccessful();
                            Toast.makeText(getApplicationContext(),"Tarla verisi Silinmiştir!",Toast.LENGTH_SHORT).show();
                            intent1.putExtra("user_id",user_id);
                            startActivity(intent1);
                            finish();
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
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
                finish();
            }
        });
        sehir_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString(),listView);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(a==1){
                    loc.setText(listView.getItemAtPosition(i).toString());
                    dialog.cancel();
                }else {
                    type.setText(listView.getItemAtPosition(i).toString());
                    dialog.cancel();
                }

            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(adapter1);
                dialog.show();
                a=1;
                sehir_text.setHint("Konum");
              //  sehir_text.setText(loc.getText().toString());
            }
        });
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(adapter2);
                dialog.show();
                a=2;
                sehir_text.setHint("Ekin Türü");
               // sehir_text.setText(loc.getText().toString());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b==0){
                    if (name.getText().toString().isEmpty()){
                        name.setError("Bu alan boş Bırakılamaz");
                    } else if (loc.getText().toString().isEmpty()) {
                        loc.setError("Bu alan boş Bırakılamaz");
                    } else if (type.getText().toString().isEmpty()) {
                        type.setError("Bu alan boş Bırakılamaz");
                    } else if (area.getText().toString().isEmpty()) {
                        area.setError("Bu alan boş Bırakılamaz");
                    }else {
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name.getText().toString());
                        user.put("loc", loc.getText().toString());
                        user.put("type", type.getText().toString());
                        user.put("area", area.getText().toString());
                        user.put("tank", "0");
                        database.getReference(user_id).child(name.getText().toString()).child("sulama").setValue("0");
                        db.collection(user_id)
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getApplicationContext(),name.getText().toString() + "Tarlası Eklenmiştir",Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                                        intent.putExtra("user_id",user_id);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), (CharSequence) e,Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }else{
                    if (name.getText().toString().isEmpty()){
                        name.setError("Bu alan boş Bırakılamaz");
                    } else if (loc.getText().toString().isEmpty()) {
                        loc.setError("Bu alan boş Bırakılamaz");
                    } else if (type.getText().toString().isEmpty()) {
                        type.setError("Bu alan boş Bırakılamaz");
                    } else if (area.getText().toString().isEmpty()) {
                        area.setError("Bu alan boş Bırakılamaz");
                    }else {
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name.getText().toString());
                        user.put("loc", loc.getText().toString());
                        user.put("type", type.getText().toString());
                        user.put("area", area.getText().toString());
                        user.put("tank", "0");
                        db.collection(user_id)
                                .document(intent.getStringExtra("id").toString())
                                .update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(),name.getText().toString() + "Tarlası Güncellenmiştir.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                                        intent.putExtra("user_id",user_id);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                    }
                }

            }
        });


    }
    private void filter(String text, ListView listView) {
        List<String> filteredList = new ArrayList<>();

        for (String item : cities) {
            if (item.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredList);
        listView.setAdapter(adapter1);
    }
    @SuppressLint("SetTextI18n")
    void internet_kontrol(){
        if (!com.example.sodsis.internet.internetBaglantisiVarMi(getApplicationContext())) {
            name.setText("Lütfen İnternet Bağlantınızı Kontrol Edin");
            name.setBackground(null);
            loc.setVisibility(View.INVISIBLE);
            area.setVisibility(View.INVISIBLE);
            button.setClickable(false);
            button.setVisibility(View.INVISIBLE);
            type.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"internet yok",Toast.LENGTH_SHORT).show();
        }
    }

}
