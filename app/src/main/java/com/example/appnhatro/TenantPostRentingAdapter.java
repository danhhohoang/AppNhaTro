package com.example.appnhatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TenantPostRentingAdapter extends RecyclerView.Adapter<TenantPostRentingAdapter.TenantPostRenting> {

    Context context;
    List<PostList> mPostList;

    public TenantPostRentingAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PostList> list){
        this.mPostList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TenantPostRentingAdapter.TenantPostRenting onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tenant_post_renting,parent,false);
        return new TenantPostRenting(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostRentingAdapter.TenantPostRenting holder, int position) {
        PostList postList = mPostList.get(position);
        if (postList == null){
            return;
        }
        holder.house_name.setText(postList.getHouse_name());
        holder.address.setText(postList.getAddress());
        holder.area.setText(String.valueOf(postList.getArea()));
        holder.price.setText(String.valueOf(postList.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (mPostList != null){
            return mPostList.size();
        }
        return 0;
    }

    public static class TenantPostRenting extends RecyclerView.ViewHolder{
        TextView post_id,user_id,area,price,house_name,address,attachment,status,wishlist;
        public TenantPostRenting(@NonNull View itemView) {
            super(itemView);
            house_name = itemView.findViewById(R.id.txt_tprHouseName);
            address = itemView.findViewById(R.id.txt_tprAddress);
            area = itemView.findViewById(R.id.txt_tprArea);
            price = itemView.findViewById(R.id.txt_tprPrice);
        }
    }

}
