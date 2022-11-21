package com.example.appnhatro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.R;
import com.example.appnhatro.item.Rating;

import java.util.ArrayList;

public class AdminCommentAdapter extends RecyclerView.Adapter<AdminCommentAdapter.MyViewHolder>{
    Context context;
    int resource;
    ArrayList<Rating> ratings;
    private OnItemClickListener onItemClickLisner;

    public AdminCommentAdapter(Context context, int resource, ArrayList<Rating> ratings) {
        this.context = context;
        this.resource = resource;
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public AdminCommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.admin_item_comment_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCommentAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View.OnClickListener onItemClickLisner;
        TextView tvRating,tvComment,tvDate,tvCommentLandLord;
        LinearLayout lvFeedBack;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRating = itemView.findViewById(R.id.tvRatingItemAdmin);
            tvComment = itemView.findViewById(R.id.tvCommentItemAdmin);
            tvDate = itemView.findViewById(R.id.tvDateItemRatingAdmin);
            tvCommentLandLord = itemView.findViewById(R.id.tvCommentLandLordItemAdmin);
            lvFeedBack= itemView.findViewById(R.id.confirmLandLordAdmin);
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
