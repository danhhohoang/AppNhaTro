package com.example.appnhatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TenantPostListAdapter extends RecyclerView.Adapter<TenantPostListAdapter.TenantPostList> {

    Context context;
    List<PostListFindPeople> mPostList;

    public TenantPostListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PostListFindPeople> list){
        this.mPostList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TenantPostList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tenant_post_list,parent,false);
        return new TenantPostList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostList holder, int position) {
        PostListFindPeople postListFindPeople = mPostList.get(position);
        if (position == 0){
            return;
        }
        holder.sex.setText(postListFindPeople.getSex());
    }

    @Override
    public int getItemCount() {
        if (mPostList != null){
            return mPostList.size();
        }
        return 0;
    }

    public static class TenantPostList extends RecyclerView.ViewHolder{
        TextView sex;
        public TenantPostList(@NonNull View itemView) {
            super(itemView);
            sex = itemView.findViewById(R.id.txt_tplSex);
        }
    }
}
