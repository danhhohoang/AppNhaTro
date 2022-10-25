package com.example.appnhatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TenantPostDetailAdapter extends RecyclerView.Adapter<TenantPostDetailAdapter.TenantPostDetail> {

    Context context;
    List<PostList> mPostList;

    public TenantPostDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PostList> postLists){
        this.mPostList = postLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TenantPostDetail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tenant_post_detail,parent,false);
        return new TenantPostDetail(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostDetail holder, int position) {
        PostList postList = mPostList.get(position);
        if (postList == null){
            return;
        }
        holder.house_name.setText(postList.getHouse_name());
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

    public static class TenantPostDetail extends RecyclerView.ViewHolder{
        TextView house_name,address,area,price;
        public TenantPostDetail(@NonNull View itemView) {
            super(itemView);
            house_name = itemView.findViewById(R.id.txt_tpdHousename);
            area = itemView.findViewById(R.id.txt_tpdArea);
            price = itemView.findViewById(R.id.txt_tpdPrice);
        }
    }
}
