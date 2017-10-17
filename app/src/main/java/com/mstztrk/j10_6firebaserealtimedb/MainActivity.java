package com.mstztrk.j10_6firebaserealtimedb;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

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
        
    }

    private void listeyiDoldur() {

    }
}
