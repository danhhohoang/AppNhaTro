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
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<PostList> mPostList;

    public TenantPostDetailAdapter(Context context,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setData(List<PostList> postLists){
        this.mPostList = postLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TenantPostDetail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tenant_post_detail,parent,false);
        return new TenantPostDetail(v,recyclerViewInterface);
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

    public static class TenantPostDetail extends RecyclerView.ViewHolder {
        TextView house_name,address,area,price;
        public TenantPostDetail(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            house_name = itemView.findViewById(R.id.txt_tpdHousename);
            area = itemView.findViewById(R.id.txt_tpdArea);
            price = itemView.findViewById(R.id.txt_tpdPrice);

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