package com.example.appnhatro.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImagePostDetailAdapter extends RecyclerView.Adapter<ImagePostDetailAdapter.ViewHolder>{
    private Activity context;
    private int LayoutID;
    private ArrayList<String> personList;
    public ImagePostDetailAdapter(Activity context, int layoutId, ArrayList<String> personList) {
        this.context = context;
        this.LayoutID = layoutId;
        this.personList = personList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View studentView =
                inflater.inflate(R.layout.list_view_item_home_tenant, parent, false);
        ViewHolder viewHolder = new ViewHolder(studentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String post =personList.get(position);
        BitMap bitMap = new BitMap(post,null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/" + bitMap.getTenHinh());
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
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hinh=itemView.findViewById(R.id.imageHoma);
        }
    }
}
