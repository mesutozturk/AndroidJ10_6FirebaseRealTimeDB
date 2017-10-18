package com.mstztrk.j10_6firebaserealtimedb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mstztrk.j10_6firebaserealtimedb.Adapters.MyListAdapter;
import com.mstztrk.j10_6firebaserealtimedb.BaseActivities.BaseActivity;
import com.mstztrk.j10_6firebaserealtimedb.model.Kisi;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    Button btnYeni;
    ListView listView;
    ArrayList<Kisi> kisiler;
    LayoutInflater inflater;
    //BaseAdapter baseAdapter;
    MyListAdapter myListAdapter;

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

        /*baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                if (kisiler == null)
                    return 0;
                return kisiler.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = inflater.inflate(R.layout.mylist_item, null);
                Kisi basilacakKisi = kisiler.get(i);
                TextView txtAd = view.findViewById(R.id.myitem_txtAd);
                TextView txtSoyad = view.findViewById(R.id.myitem_txtSoyd);
                Button btnAra = view.findViewById(R.id.myitem_btnAra);
                Button btnMail = view.findViewById(R.id.myitem_btnMail);
                txtAd.setText(basilacakKisi.getAd());
                txtSoyad.setText(basilacakKisi.getSoyad());
                return view;
            }
        };
        listView.setAdapter(baseAdapter);
*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                intent.putExtra("yeni", false);
                intent.putExtra("id", kisiler.get(position).getId());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final int pos = position;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                showProgress("Siliniyor");
                                database = FirebaseDatabase.getInstance();
                                myRef = database.getReference().child("kisiler").child(kisiler.get(pos).getId());
                                myRef.removeValue();
                                hideProgress();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(MainActivity.this, "Silme işleminden vazgeçildi", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Silmek istediğinize emin misiniz?").setPositiveButton("Evet", dialogClickListener)
                        .setNegativeButton("Hayır", dialogClickListener).show();

                return true;
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
                //ArrayAdapter<Kisi> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, kisiler);
                //listView.setAdapter(adapter);
                //baseAdapter.notifyDataSetChanged();

                myListAdapter = new MyListAdapter(MainActivity.this, kisiler);
                listView.setAdapter(myListAdapter);
                Toast.makeText(MainActivity.this, "Veri geldi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
