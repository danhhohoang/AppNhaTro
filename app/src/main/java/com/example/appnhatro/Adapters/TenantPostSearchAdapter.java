package com.example.appnhatro.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.FakeModels.PostFake;
import com.example.appnhatro.R;

import java.util.List;

public class TenantPostSearchAdapter extends RecyclerView.Adapter<TenantPostSearchAdapter.TenantPostSearchViewHolder>  {


    private List<PostFake> postFakeList;

    public TenantPostSearchAdapter(List<PostFake> postFakeList) {
        this.postFakeList = postFakeList;
    }

    @NonNull
    @Override
    public TenantPostSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tenant_search,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostSearchViewHolder holder, int position) {
        PostFake postFake = postFakeList.get(position);
        if (postFake == null)
        {
            return;
        }
        holder.tvPostTitle.setText(postFake.getPostTitle());
        holder.tvPostAddress.setText(postFake.getPostAddress());

//        int int2 =  post.getPostPrice();
//        int2.format
//        String[] strArray = str.split("");


        holder.tvPostPrice.setText(""+ postFake.getPostPrice()+"đ");
        holder.tvPostArea.setText("" + postFake.getPostArea() + " m2");
        //

        holder.ivPostAvatar.setImageResource(R.drawable.ic_user);
        holder.ivPostImageRoom.setImageResource(R.drawable.ic_user);

        //
        holder.tvPostStatus.setText(postFake.getPostStatus());
    }

    public void setFillterList(List<PostFake> filterList){
        this.postFakeList = filterList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(postFakeList != null)
        {
            return postFakeList.size();
        }
        return 0;

    }

    class TenantPostSearchViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPostTitle;
        private TextView tvPostAddress;
        private TextView tvPostArea;
        private TextView tvPostPrice;
        private ImageView ivPostImageRoom;
        private ImageView ivPostAvatar;
        private TextView tvPostStatus;

        public TenantPostSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPostTitle = itemView.findViewById(R.id.textview_item_post_title);
            tvPostAddress = itemView.findViewById(R.id.textview_item_post_address);
            tvPostArea = itemView.findViewById(R.id.textview_item_post_area);
            tvPostPrice = itemView.findViewById(R.id.textview_item_post_price);
            ivPostAvatar = itemView.findViewById(R.id.iv_post_image_room);
            ivPostImageRoom = itemView.findViewById(R.id.iv_post_image_room);
            tvPostStatus = itemView.findViewById(R.id.textview_item_post_status);
        }
    }
}
