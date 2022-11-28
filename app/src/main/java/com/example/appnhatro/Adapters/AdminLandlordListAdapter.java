package com.example.appnhatro.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class AdminLandlordListAdapter extends RecyclerView.Adapter<AdminLandlordListAdapter.AdminLandlordList> {
    Context context;
    List<Post> posts;

    public AdminLandlordListAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public AdminLandlordList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_landlord_room_list,parent,false);
        return new AdminLandlordList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminLandlordList holder, int position) {
        Post post = posts.get(position);
        holder.idpost.setText(post.getId());
        BitMap bitMap = new BitMap(post.getImage(),null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/post/" + bitMap.getTenHinh());
        try {
            final File file= File.createTempFile(bitMap.getTenHinh().substring(0,bitMap.getTenHinh().length()-4),"jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    holder.imageView.setImageBitmap(bitMap.getHinh());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (posts != null){
            return posts.size();
        }
        return 0;
    }

    public static class AdminLandlordList extends RecyclerView.ViewHolder{
        TextView idpost;
        ImageView imageView;
        public AdminLandlordList(@NonNull View itemView) {
            super(itemView);
            idpost = itemView.findViewById(R.id.tv_alrIDpost);
            imageView = itemView.findViewById(R.id.iv_alrImage);
        }
    }
}
