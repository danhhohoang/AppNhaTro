package com.example.appnhatro;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{
    private Activity context;
    private int resource;
    private ArrayList<Post> personList;
    private  OnItemClickListener onItemClickLisner;
    public MyRecyclerViewAdapter(Activity context, int layoutId, ArrayList<Post> personList) {
        this.context = context;
        this.resource = layoutId;
        this.personList = personList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_horizontal,parent,false);

        return new MyViewHolder(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = personList.get(position);
        holder.tenPhong.setText(post.getHour_name());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.Gia.setText(formatter.format(Integer.valueOf(post.getPrice())));
        holder.tinhTrang.setText(post.getTitle());
        holder.dienTich.setText(formatter.format(Integer.valueOf(post.getArea())));
        BitMap bitMap = new BitMap(post.getImage(),null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(bitMap.getTenHinh());
        try {
            final File file= File.createTempFile(bitMap.getTenHinh().substring(0,bitMap.getTenHinh().length()-4),"jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("test", "taaa");
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    holder.hinhAnh.setImageBitmap(bitMap.getHinh());
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

    @Override
    public int getItemViewType(int position) {
        return resource;
    }

    // View holder definition
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView hinhAnh;
        TextView tenPhong;
        TextView Gia;
        TextView dienTich;
        TextView tinhTrang;
        View.OnClickListener onClickListener;
        CardView item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhAnh = itemView.findViewById(R.id.imgRoom);
            tenPhong = itemView.findViewById(R.id.lblTenPhong);
            Gia = itemView.findViewById(R.id.lblGia);
            dienTich = itemView.findViewById(R.id.lblDienTich);
            tinhTrang = itemView.findViewById(R.id.lblTinhTrang);
            item = itemView.findViewById(R.id.cardListHorizontal);
            item.setOnClickListener(this);
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }
}
