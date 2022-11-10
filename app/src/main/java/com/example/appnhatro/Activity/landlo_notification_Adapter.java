package com.example.appnhatro.Activity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appnhatro.R;

import java.util.ArrayList;

public class landlo_notification_Adapter extends ArrayAdapter<landlord_report_models> {

    public landlo_notification_Adapter(Context context, ArrayList<landlord_report_models> landlord_report_modelsArrayList){
        super(context, R.layout.landlord_notification_list,landlord_report_modelsArrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        landlord_report_models user = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.landlord_notification_list,parent,false);
        }

        ImageView imgAVT = convertView.findViewById(R.id.imageAVT);
        EditText Name = convertView.findViewById(R.id.edtname);
        EditText MaReport = convertView.findViewById(R.id.edtIDreport);
        EditText NoiDung = convertView.findViewById(R.id.edtNoiDung);
        EditText MaPhong = convertView.findViewById(R.id.edtMaPhong);

        imgAVT.setImageResource(user.imageID);
        Name.setText(user.Name);
        MaReport.setText(user.MaReport);
        NoiDung.setText(user.NoiDung);
        MaPhong.setText(user.MaPhong);
        return super.getView(position, convertView, parent);
    }

}
