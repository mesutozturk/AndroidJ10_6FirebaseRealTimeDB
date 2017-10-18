package com.mstztrk.j10_6firebaserealtimedb.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mstztrk.j10_6firebaserealtimedb.R;
import com.mstztrk.j10_6firebaserealtimedb.model.Kisi;

import java.util.ArrayList;

/**
 * Created by Mesut on 18.10.2017.
 */

public class MyListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Kisi> kisiler;
    private LayoutInflater inflater;

    public MyListAdapter(Context context, ArrayList<Kisi> kisiler) {
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
        Kisi basilacakKisi = kisiler.get(i);
        TextView txtAd = view.findViewById(R.id.myitem_txtAd);
        TextView txtSoyad = view.findViewById(R.id.myitem_txtSoyd);
        Button btnAra = view.findViewById(R.id.myitem_btnAra);
        Button btnMail = view.findViewById(R.id.myitem_btnMail);
        txtAd.setText(basilacakKisi.getAd());
        txtSoyad.setText(basilacakKisi.getSoyad());
        return view;
    }
}
