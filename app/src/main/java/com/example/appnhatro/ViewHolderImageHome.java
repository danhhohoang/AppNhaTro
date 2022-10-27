package com.example.appnhatro;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewHolderImageHome extends RecyclerView.Adapter<ViewHolderImageHome.ViewHolder>{
    private Activity context;
    private int LayoutID;
    private ArrayList<String> personList;

    public ViewHolderImageHome(Activity context, int layoutId, ArrayList<String> personList) {
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
            String hinhh =personList.get(position);
        if (hinhh.equals("haha")) {
            holder.hinh.setImageResource(R.drawable.house);
        }
        else if (hinhh.equals("hoho")) {
            holder.hinh.setImageResource(R.drawable.background);
        }
        else if (hinhh.equals("hehe")) {
            holder.hinh.setImageResource(R.drawable.house);
        }
        else {
            holder.hinh.setImageResource(R.mipmap.ic_launcher);
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
