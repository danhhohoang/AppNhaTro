package com.example.appnhatro.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FirebaseAdmin;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.HistoryTransaction;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TenantHomeListPostAdapter extends RecyclerView.Adapter<TenantHomeListPostAdapter.MyViewHolder>{
    private Context context;
    private int resource;
    private ArrayList<Post> posts;
    private OnItemClickListener onItemClickLisner;
    public TenantHomeListPostAdapter(Context context, int resource, ArrayList<Post> posts) {
        this.context = context;
        this.resource = resource;
        this.posts = posts;
    }

    @NonNull
    @Override
    public TenantHomeListPostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.tenant_item_home_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantHomeListPostAdapter.MyViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvTinhTrang.setText(post.getStatus());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.tvPrice.setText(formatter.format(Integer.valueOf(post.getPrice()))+"đ/Tháng");
        holder.tvArea.setText(formatter.format(Integer.valueOf(post.getArea()))+"");
        holder.tvNamePost.setText(post.getHouse_name());
        BitMap bitMap = new BitMap(post.getImage(),null);
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
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View.OnClickListener onItemClickLisner;
        ImageView hinh;
        TextView tvNamePost,tvArea,tvPrice,tvTinhTrang;
        Button btnXacNhan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamePost = itemView.findViewById(R.id.lblTenPhongHome);
            tvArea = itemView.findViewById(R.id.lblDienTichHome);
            tvTinhTrang = itemView.findViewById(R.id.lblTinhTrangHome);
            tvPrice = itemView.findViewById(R.id.lblGiaHome);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhanAdmin);
            hinh = itemView.findViewById(R.id.imgRoomHome);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickLisner != null) {
                onItemClickLisner.onClick(v);
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
