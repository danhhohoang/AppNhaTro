package com.example.appnhatro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Activity.TenantCommentActivity;
import com.example.appnhatro.R;
import com.example.appnhatro.item.Rating;

import java.util.ArrayList;

public class TeantCommentAdapter extends RecyclerView.Adapter<TeantCommentAdapter.MyViewHolder>{
    Context context;
    private int resource;
    private ArrayList<Rating> ratings;
    private String idUser;
    private OnItemClickListener onItemClickLisner;
    public TeantCommentAdapter(Context context, int resource, ArrayList<Rating> ratings,String user) {
        this.context = context;
        this.resource = resource;
        this.ratings = ratings;
        this.idUser = user;
    }

    @NonNull
    @Override
    public TeantCommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_comment_post_detail_tenant_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeantCommentAdapter.MyViewHolder holder, int position) {
        int pos=position;
        Rating rating = ratings.get(pos);
        holder.tvRating.setText(rating.getRating());
        holder.tvComment.setText(rating.getComment());
        holder.tvDate.setText(rating.getDate());
        if(rating.getIdUser().equals(idUser)){
            holder.tvMenu.setVisibility(View.VISIBLE);
        }else{
            holder.tvMenu.setVisibility(View.GONE);
        }
        if(rating.getFeedback().equals("")){
            holder.lvFeedBack.setVisibility(View.GONE);
            ViewGroup.LayoutParams cardview=  holder.rcv.getLayoutParams();
            cardview.height=300;
        }else{
            holder.lvFeedBack.setVisibility(View.VISIBLE);
            holder.tvCommentLandLord.setText(rating.getFeedback());
        }
        holder.onItemClickLisner=new View.OnClickListener() {
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
        return ratings.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View.OnClickListener onItemClickLisner;
        TextView tvRating,tvComment,tvDate,tvCommentLandLord,tvMenu;
        LinearLayout lvFeedBack;
        ImageView imgUserItemCommentTenant;
        CardView rcv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRating = itemView.findViewById(R.id.tvRatingItemTenant);
            tvComment = itemView.findViewById(R.id.tvCommentItemTenant);
            tvDate = itemView.findViewById(R.id.tvDateItemRatingTenant);
            tvCommentLandLord = itemView.findViewById(R.id.confirmTenant);
            tvMenu = itemView.findViewById(R.id.tvOptions);
            lvFeedBack = itemView.findViewById(R.id.confirmLandLord);
            rcv = itemView.findViewById(R.id.rcv_HomeTenant_Item);
            imgUserItemCommentTenant =itemView.findViewById(R.id.imgUserItemCommentTenant);
            tvMenu.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickLisner != null) {
                onItemClickLisner.onClick(v);
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }
}
