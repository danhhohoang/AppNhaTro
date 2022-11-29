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

import java.util.List;

public class HistoryTransactionTenantAdapter extends RecyclerView.Adapter<HistoryTransactionTenantAdapter.HistoryTransactionTenant> {
    Context context;
    List<TransactionModel> transactionModels;

    public HistoryTransactionTenantAdapter(Context context, List<TransactionModel> transactionModels) {
        this.context = context;
        this.transactionModels = transactionModels;
    }

    @NonNull
    @Override
    public HistoryTransactionTenant onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_transaction_tentant,parent,false);
        return new HistoryTransactionTenant(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryTransactionTenant holder, int position) {
        TransactionModel transactionModel = transactionModels.get(position);
        holder.user.setText(transactionModel.getId_user());
        holder.price.setText(transactionModel.getDeposits());
        if (transactionModel.getStatus().equals("0")){
            holder.status.setText("Đang chờ xác nhận");
        }if (transactionModel.getStatus().equals("1")){
            holder.status.setText("Đã xác nhận thanh toán");
        }
        holder.date.setText(transactionModel.getDate());
    }

    @Override
    public int getItemCount() {
        if (transactionModels != null){
            return transactionModels.size();
        }
        return 0;
    }

    public static class HistoryTransactionTenant extends RecyclerView.ViewHolder{
        TextView user,price,status,date;
        public HistoryTransactionTenant(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_httDate);
            user = itemView.findViewById(R.id.tv_httName);
            price = itemView.findViewById(R.id.tv_httPrice);
            status = itemView.findViewById(R.id.tv_httStatus);
        }
    }
}
