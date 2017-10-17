package com.mstztrk.j10_6firebaserealtimedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mstztrk.j10_6firebaserealtimedb.BaseActivities.BaseActivity;
import com.mstztrk.j10_6firebaserealtimedb.model.Kisi;

public class AddUpdateActivity extends BaseActivity {
    EditText txtAd, txtSoyad, txtEmail, txtTelefon;
    Button btnEkle, btnGuncelle;
    boolean yeniMi = true;
    Kisi gelenKisi;

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
            String id = intent.getStringExtra("id");
            doldur(id);
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
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress("Kişi güncelleniyor");
                Kisi guncellenecekKisi = new Kisi();
                guncellenecekKisi.setAd(txtAd.getText().toString());
                guncellenecekKisi.setMail(txtEmail.getText().toString());
                guncellenecekKisi.setSoyad(txtSoyad.getText().toString());
                guncellenecekKisi.setTelefon(txtTelefon.getText().toString());
                guncellenecekKisi.setId(gelenKisi.getId());

                database = FirebaseDatabase.getInstance();
                myRef = database.getReference().child("kisiler");
                myRef.child(guncellenecekKisi.getId()).setValue(guncellenecekKisi).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        hideProgress();
                        finish();
                    }
                });
            }
        });
    }

    private void doldur(String id) {
        showProgress("Kişi bilgisi alınıyor");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("kisiler");
        Query query = myRef.child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideProgress();
                gelenKisi = dataSnapshot.getValue(Kisi.class);
                if (gelenKisi == null)
                    finish();
                txtAd.setText(gelenKisi.getAd());
                txtSoyad.setText(gelenKisi.getSoyad());
                txtEmail.setText(gelenKisi.getMail());
                txtTelefon.setText(gelenKisi.getTelefon());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
