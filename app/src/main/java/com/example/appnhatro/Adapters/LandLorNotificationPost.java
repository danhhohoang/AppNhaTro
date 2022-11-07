package com.example.appnhatro.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Activity.tenant_notification_activity;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Dangbaimodels;
import com.example.appnhatro.Models.LandlorReport;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.MyRecyclerViewAdapter;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LandLorNotificationPost extends RecyclerView.Adapter<LandLorNotificationPost.Adapter> {
    private Activity context;
    private int resource;
    private ArrayList<Dangbaimodels> personList;
    private OnItemClickListener onItemClickLisner;

    public LandLorNotificationPost(Activity context, int resource, ArrayList<Dangbaimodels> personList) {
        this.context = context;
        this.resource = resource;
        this.personList = personList;
    }

    @NonNull
    @Override
    public Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.tenant_post_notification_layout, parent, false);
        return  new Adapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter holder, int position) {
        Dangbaimodels dangbaimodels = personList.get(position);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.MaBD.setText(dangbaimodels.getId());
        holder.TieuDe.setText(dangbaimodels.getAddress());
        holder.GiaPhong.setText(formatter.format(Integer.valueOf(dangbaimodels.getPrice())));
        BitMap bitMap = new BitMap(dangbaimodels.getImage1(),null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(bitMap.getTenHinh());
        try {
            final File file= File.createTempFile(bitMap.getTenHinh().substring(0,bitMap.getTenHinh().length()-4),"jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    holder.imgAVT.setImageBitmap(bitMap.getHinh());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Event processing
        final int pos = position;
        holder.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickLisner!=null){
                    onItemClickLisner.onItemClickListener(pos,holder.itemView);
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class Adapter extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgAVT;
        TextView Name, MaBD, TieuDe, GiaPhong;
        View.OnClickListener onClickListener;
        public Adapter(@NonNull View itemView) {
            super(itemView);
            imgAVT = itemView.findViewById(R.id.imgAVT);
            Name = itemView.findViewById(R.id.edtname);
            MaBD = itemView.findViewById(R.id.edtMABD);
            TieuDe = itemView.findViewById(R.id.edtTD);
            GiaPhong = itemView.findViewById(R.id.edtgia);
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);
    }

    public void setOnItemClickListener(LandLorNotificationPost.OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }

}
