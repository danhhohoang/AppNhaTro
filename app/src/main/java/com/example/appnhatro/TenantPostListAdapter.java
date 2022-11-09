package com.example.appnhatro;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.PostFindRoomateModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TenantPostListAdapter extends RecyclerView.Adapter<TenantPostListAdapter.TenantPostList> {

    private Activity context;
    private int resource;
    private ArrayList<PostFindRoomateModel> mPostFindPP;
    private ArrayList<Post> mPostLists;
    private OnItemClickListener onItemClickLisner;

    public TenantPostListAdapter(Activity context, int resource, ArrayList<PostFindRoomateModel> mPostFindPP, ArrayList<Post> mPostLists) {
        this.context = context;
        this.resource = resource;
        this.mPostFindPP = mPostFindPP;
        this.mPostLists = mPostLists;
    }

    @NonNull
    @Override
    public TenantPostList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new TenantPostList(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostList holder, int position) {
        Post post = mPostLists.get(position);
        PostFindRoomateModel postFindRoomateModel = mPostFindPP.get(position);

        holder.house_name.setText(post.getHouse_name() );
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.price.setText(formatter.format(Integer.valueOf(post.getPrice())));
        holder.address.setText(post.getAddress());
        holder.area.setText(formatter.format(Integer.valueOf(post.getArea())));
        holder.title.setText(postFindRoomateModel.get_title() );

        //Event processing
        final int pos = position;
        holder.onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickLisner!=null){
                    onItemClickLisner.onItemClickListener(pos,holder.itemView);
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        if (mPostFindPP != null){
            return mPostFindPP.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return resource;
    }

    public static class TenantPostList extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView house_name,address,area,price,title;
        View.OnClickListener onClickListener;

        public TenantPostList(@NonNull View itemView) {
            super(itemView);
            house_name = itemView.findViewById(R.id.txt_tplHouse_name);
            address = itemView.findViewById(R.id.txt_tplAddress);
            area = itemView.findViewById(R.id.txt_tplArea);
            price = itemView.findViewById(R.id.txt_tplPrice);
            title = itemView.findViewById(R.id.txt_tplTitle);
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);
    }

    public void setOnItemClickListener(TenantPostListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }
}
