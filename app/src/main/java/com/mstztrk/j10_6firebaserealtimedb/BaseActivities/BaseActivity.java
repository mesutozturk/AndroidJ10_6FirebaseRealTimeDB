package com.mstztrk.j10_6firebaserealtimedb.BaseActivities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Mesut on 17.10.2017.
 */

public class BaseActivity extends AppCompatActivity {
    public FirebaseDatabase database;
    public DatabaseReference myRef;
    public ProgressDialog pDialog;

    public void showProgress(String message) {
        if (pDialog == null) {
            pDialog = new ProgressDialog(this);
            pDialog.setMessage(message);
            pDialog.setTitle("Lütfen Bekleyin");
            pDialog.setIndeterminate(true);
        }
        pDialog.show();
    }

    public void hideProgress() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }
}
