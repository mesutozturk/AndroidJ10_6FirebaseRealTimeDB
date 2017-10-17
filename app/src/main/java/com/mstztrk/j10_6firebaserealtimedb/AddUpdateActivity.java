package com.mstztrk.j10_6firebaserealtimedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.mstztrk.j10_6firebaserealtimedb.BaseActivities.BaseActivity;
import com.mstztrk.j10_6firebaserealtimedb.model.Kisi;

public class AddUpdateActivity extends BaseActivity {
    EditText txtAd, txtSoyad, txtEmail, txtTelefon;
    Button btnEkle, btnGuncelle;
    boolean yeniMi = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);
        initMyComponents();
        Intent intent = getIntent();
        yeniMi = intent.getBooleanExtra("yeni", true);
        if (yeniMi) {
            btnEkle.setVisibility(View.VISIBLE);
            btnGuncelle.setVisibility(View.GONE);
        } else {
            btnGuncelle.setVisibility(View.VISIBLE);
            btnEkle.setVisibility(View.GONE);
        }
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress("Kişi kaydediliyor");
                Kisi kisi = new Kisi();
                kisi.setAd(txtAd.getText().toString());
                kisi.setSoyad(txtSoyad.getText().toString());
                kisi.setMail(txtEmail.getText().toString());
                kisi.setTelefon(txtTelefon.getText().toString());
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference().child("kisiler");
                myRef.child(kisi.getId()).setValue(kisi).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        hideProgress();
                        finish();
                    }
                });
            }
        });
    }

    private void initMyComponents() {
        txtAd = (EditText) findViewById(R.id.txtAd);
        txtSoyad = (EditText) findViewById(R.id.txtSoyad);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtTelefon = (EditText) findViewById(R.id.txtTelefon);
        btnEkle = (Button) findViewById(R.id.btnEkle);
        btnGuncelle = (Button) findViewById(R.id.btnGuncelle);
    }
}
