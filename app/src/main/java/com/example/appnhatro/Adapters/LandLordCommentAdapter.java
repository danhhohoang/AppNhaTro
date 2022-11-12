package com.example.appnhatro.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.R;
import com.example.appnhatro.item.Rating;

import java.util.ArrayList;

public class LandLordCommentAdapter extends RecyclerView.Adapter<LandLordCommentAdapter.MyViewHolder>{
    Context context;
    int resource;
    ArrayList<Rating> ratings;
    private OnItemClickListener onItemClickLisner;
    public LandLordCommentAdapter(Context context, int resource, ArrayList<Rating> ratings) {
        this.context = context;
        this.resource = resource;
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public LandLordCommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.landlord_item_comment_post_layout, parent, false);
        return new LandLordCommentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LandLordCommentAdapter.MyViewHolder holder, int position) {
        int pos=position;
        Rating rating = ratings.get(pos);
        holder.tvRating.setText(rating.getRating());
        holder.tvComment.setText(rating.getComment());
        holder.tvDate.setText(rating.getDate());
        if(rating.getFeedback().equals("")){
            holder.lvFeedBack.setVisibility(View.GONE);
            holder.tvPhanHoiButton.setVisibility(View.VISIBLE);
        }else{
            holder.tvPhanHoiButton.setVisibility(View.GONE);
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
        TextView tvRating,tvComment,tvDate,tvPhanHoiButton,tvCommentLandLord,tvMenu;
        LinearLayout lvFeedBack;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRating = itemView.findViewById(R.id.tvRatingItemLandLord);
            tvComment = itemView.findViewById(R.id.tvCommentItemLandLord);
            tvDate = itemView.findViewById(R.id.tvDateItemRatingLandLord);
            tvPhanHoiButton = itemView.findViewById(R.id.tvPhanHoi);
            tvCommentLandLord = itemView.findViewById(R.id.tvCommentLandLordItemLandLordItem);
            tvMenu = itemView.findViewById(R.id.textViewOptions);
            lvFeedBack= itemView.findViewById(R.id.confirmLandLord);
            tvPhanHoiButton.setOnClickListener(this);
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
