package com.example.appnhatro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.HistoryTransaction;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.example.appnhatro.tool.RecycleViewUpdateStatus;

import java.util.List;

public class UpdateStatusLandlordAdapter extends RecyclerView.Adapter<UpdateStatusLandlordAdapter.UpdateStatusLandlord> {
    Context context;
    List<TransactionModel> transactionModels;
    RecycleViewUpdateStatus recycleViewUpdateStatus;

    public UpdateStatusLandlordAdapter(Context context, List<TransactionModel> transactionModels, RecycleViewUpdateStatus recycleViewUpdateStatus) {
        this.context = context;
        this.transactionModels = transactionModels;
        this.recycleViewUpdateStatus = recycleViewUpdateStatus;
    }

    @NonNull
    @Override
    public UpdateStatusLandlord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_update_status_accept_payment,parent,false);
        return new UpdateStatusLandlord(v,recycleViewUpdateStatus);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateStatusLandlord holder, int position) {
        TransactionModel transactionModel = transactionModels.get(position);
        holder.user.setText(transactionModel.getId_user());
        holder.price.setText(transactionModel.getDeposits());
        holder.status.setText(transactionModel.getStatus());
    }

    @Override
    public int getItemCount() {
        if (transactionModels != null){
            return transactionModels.size();
        }
        return 0;
    }

    public static class UpdateStatusLandlord extends RecyclerView.ViewHolder{
        TextView user,price,status;
        Button accept;
        public UpdateStatusLandlord(@NonNull View itemView,RecycleViewUpdateStatus recycleViewUpdateStatus) {
            super(itemView);
            user = itemView.findViewById(R.id.tv_usapName);
            price = itemView.findViewById(R.id.tv_usapPrice);
            status = itemView.findViewById(R.id.tv_usapStatus);
            accept = itemView.findViewById(R.id.btn_usapAccept);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleViewUpdateStatus != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recycleViewUpdateStatus.onclick(pos);
                        }
                    }
                }
            });
        }
    }
}
