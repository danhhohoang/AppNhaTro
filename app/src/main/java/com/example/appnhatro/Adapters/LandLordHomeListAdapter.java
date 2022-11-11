package com.example.appnhatro.Adapters;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class LandLordHomeListAdapter extends RecyclerView.Adapter<LandLordHomeListAdapter.MyViewHolder>{
    private Activity context;
    private int resource;
    private ArrayList<Post> personList;
    private OnItemClickListener onItemClickLisner;

    public LandLordHomeListAdapter(Activity context, int resource, ArrayList<Post> personList) {
        this.context = context;
        this.resource = resource;
        this.personList = personList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.lanlord_home_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = personList.get(position);
        holder.tvTenPhong.setText(post.getHouse_name());
        holder.tvDiaChi.setText(post.getAddress());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.tvGia.setText(formatter.format(Integer.valueOf(post.getPrice())));
        holder.tvDienTich.setText(formatter.format(Integer.valueOf(post.getArea())));
        BitMap bitMap = new BitMap(post.getImage(),null);
        Log.d("Tri", "image"+ post.getImage());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/"+bitMap.getTenHinh());
        try {
            final File file= File.createTempFile(bitMap.getTenHinh(),"jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    holder.hinh.setImageBitmap(bitMap.getHinh());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int pos = position;
        holder.onItemClickLisner=new View.OnClickListener() {
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

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View.OnClickListener onItemClickLisner;
        ImageView hinh;
        TextView tvTenPhong,tvDiaChi,tvGia,tvDienTich,tvTrangThai;
        Button btnXoa;
        CardView item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hinh =itemView.findViewById(R.id.imgItemHomeHouse);
            tvTenPhong=itemView.findViewById(R.id.tvItemHomeNameHouse);
            tvDiaChi=itemView.findViewById(R.id.tvItemHomeAddress);
            tvGia=itemView.findViewById(R.id.tvItemGiaHome);
            tvDienTich=itemView.findViewById(R.id.tvItemAreaHome);
            item = itemView.findViewById(R.id.rcv_HomeLanLord_Item);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickLisner != null) {
                onItemClickLisner.onClick(view);
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }
}
