package com.example.appnhatro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.example.appnhatro.tool.RecycleViewUpdateStatus;

import java.util.List;

public class HistoryTranstionAdapter extends RecyclerView.Adapter<HistoryTranstionAdapter.HistoryTranstion> {
    Context context;
    List<TransactionModel> transactionModels;

    public HistoryTranstionAdapter(Context context, List<TransactionModel> transactionModels) {
        this.context = context;
        this.transactionModels = transactionModels;
    }

    @NonNull
    @Override
    public HistoryTranstion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_transaction,parent,false);
        return new HistoryTranstion(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryTranstion holder, int position) {
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

    public static class HistoryTranstion extends RecyclerView.ViewHolder{
        TextView user,price,status;
        public HistoryTranstion(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.tv_htName);
            price = itemView.findViewById(R.id.tv_htPrice);
            status = itemView.findViewById(R.id.tv_htStatus);
        }
    }
}
