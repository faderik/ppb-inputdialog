package com.erik.inputdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class KontakAdapter extends ArrayAdapter<Kontak> {
    public static class ViewHolder{
        TextView name; TextView number;
    }

    public KontakAdapter(Context context, int resource, List<Kontak> obj){
        super(context, resource, obj);
    }

    public View getView(int pos, View convertView, ViewGroup parent){
        Kontak dtKontak = getItem(pos);
        ViewHolder viewKontak;

        if(convertView == null){
            viewKontak = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
            viewKontak.name = convertView.findViewById(R.id.tName);
            viewKontak.number = convertView.findViewById(R.id.tNumber);

            convertView.setTag(viewKontak);
//            Button btn = convertView.findViewById(R.id.btnShow);
//            btn.setTag(pos);
//            btn.setOnClickListener(op);

        }
        else{
            viewKontak = (ViewHolder) convertView.getTag();
        }

        viewKontak.name.setText(dtKontak.getName());
        viewKontak.number.setText(dtKontak.getNumber());
        return convertView;

    }
}
