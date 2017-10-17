package com.mstztrk.j10_6firebaserealtimedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mstztrk.j10_6firebaserealtimedb.BaseActivities.BaseActivity;
import com.mstztrk.j10_6firebaserealtimedb.model.Kisi;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    Button btnYeni;
    ListView listView;
    ArrayList<Kisi> kisiler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnYeni = (Button) findViewById(R.id.btnYeni);
        listView = (ListView) findViewById(R.id.listView);
        listeyiDoldur();
        btnYeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                intent.putExtra("yeni", true);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                intent.putExtra("yeni", false);
                intent.putExtra("id", kisiler.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void listeyiDoldur() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("kisiler");
        showProgress("Lütfen bekleyiniz");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideProgress();
                kisiler = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Kisi gelen = postSnapshot.getValue(Kisi.class);
                    kisiler.add(gelen);
                }
                if (kisiler.size() == 0) return;
                ArrayAdapter<Kisi> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, kisiler);
                listView.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "Veri geldi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
