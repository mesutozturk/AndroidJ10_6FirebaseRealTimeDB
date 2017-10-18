package com.mstztrk.j10_6firebaserealtimedb.Adapters;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mstztrk.j10_6firebaserealtimedb.R;
import com.mstztrk.j10_6firebaserealtimedb.model.Kisi;

import java.util.ArrayList;

import de.cketti.mailto.EmailIntentBuilder;

/**
 * Created by Mesut on 18.10.2017.
 */

public class MyListAdapter extends BaseAdapter {
    private AppCompatActivity context;
    private ArrayList<Kisi> kisiler;
    private LayoutInflater inflater;

    public MyListAdapter(AppCompatActivity context, ArrayList<Kisi> kisiler) {
        this.context = context;
        this.kisiler = kisiler;
        inflater = inflater.from(context);
    }

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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.mylist_item, null);
        final Kisi basilacakKisi = kisiler.get(i);
        TextView txtAd = view.findViewById(R.id.myitem_txtAd);
        TextView txtSoyad = view.findViewById(R.id.myitem_txtSoyd);
        Button btnAra = view.findViewById(R.id.myitem_btnAra);
        Button btnMail = view.findViewById(R.id.myitem_btnMail);
        txtAd.setText(basilacakKisi.getAd());
        txtSoyad.setText(basilacakKisi.getSoyad());
        btnAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(basilacakKisi.getTelefon()))
                    Toast.makeText(context, "Kişinin telefon numarası yok", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + basilacakKisi.getTelefon()));

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "Arama yetkiniz yok", Toast.LENGTH_SHORT).show();

                        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, 100);
                        return;
                    }
                    context.startActivity(intent);
                }
            }
        });
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(Intent.ACTION_SEND);
                //intent.setType("text/plain");
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, basilacakKisi.getMail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Bu mail android dersinden gönderilmiştir");
                intent.putExtra(Intent.EXTRA_TEXT, "Merhaba " + basilacakKisi.getAd() + " " + basilacakKisi.getSoyad());
                context.startActivity(intent.createChooser(intent, "Mail Gönder"));*/

                try {
                    EmailIntentBuilder.from(context)
                            .to(basilacakKisi.getMail())
                            .subject("Bu mail android dersinden gönderilmiştir")
                            .body("Merhaba " + basilacakKisi.getAd() + " " + basilacakKisi.getSoyad())
                            .start();
                } catch (Exception ex) {
                    Toast.makeText(context, "Hata: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("x", ex.getMessage());
                }

            }
        });

        return view;
    }
}
