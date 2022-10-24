package com.example.appnhatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TenantPostFavouriteAdapter extends RecyclerView.Adapter<TenantPostFavouriteAdapter.TenantPostFavourite> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<PostList> mPostLists;

    public TenantPostFavouriteAdapter(Context context,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setData(List<PostList> postLists){
        this.mPostLists = postLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TenantPostFavourite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_tenant_post_favourite,parent,false);
        return new TenantPostFavourite(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostFavourite holder, int position) {
        PostList postList = mPostLists.get(position);
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
        if (mPostLists != null){
            return mPostLists.size();
        }
        return 0;
    }

    public static class TenantPostFavourite extends RecyclerView.ViewHolder{

        TextView post_id,user_id,area,price,house_name,address,attachment,status,wishlist;

        public TenantPostFavourite(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            house_name = itemView.findViewById(R.id.txt_tpfHousename);
            address = itemView.findViewById(R.id.txt_tpfAddress);
            area = itemView.findViewById(R.id.txt_tpfArea);
            price = itemView.findViewById(R.id.txt_tpfPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
