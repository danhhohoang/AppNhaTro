package com.example.appnhatro;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Post;

import java.util.ArrayList;

public class ViewHolderImageHome extends RecyclerView.Adapter<ViewHolderImageHome.ViewHolder>{
    private Activity context;
    private int LayoutID;
    private ArrayList<Post> personList;
    private FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    public ViewHolderImageHome(Activity context, int layoutId, ArrayList<Post> personList) {
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
        Post post =personList.get(position);

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
