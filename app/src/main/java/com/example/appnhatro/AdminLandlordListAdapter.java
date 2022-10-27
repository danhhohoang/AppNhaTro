package com.example.appnhatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminLandlordListAdapter extends RecyclerView.Adapter<AdminLandlordListAdapter.AdminLandlordList> {
    Context context;
    List<LandLordList> mPostList;

    public AdminLandlordListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<LandLordList> list){
        this.mPostList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminLandlordList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_landlord,parent,false);
        return new AdminLandlordList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminLandlordList holder, int position) {
        LandLordList landLordList = mPostList.get(position);
        if (landLordList != null){
            return;
        }
        holder.ID.setText(String.valueOf(landLordList.getID()));
        holder.sdt.setText(String.valueOf(landLordList.getSdt()));
        holder.status.setText(landLordList.getSdt());
        holder.moneyhavepay.setText(String.valueOf(landLordList.getMoneyhavepay()));
    }

    @Override
    public int getItemCount() {
        if (mPostList != null){
            return mPostList.size();
        }
        return 0;
    }

    public static class AdminLandlordList extends RecyclerView.ViewHolder{
        TextView ID,sdt,status,moneyhavepay;
        public AdminLandlordList(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.txt_alID);
            sdt = itemView.findViewById(R.id.txt_alSDT);
            status = itemView.findViewById(R.id.txt_alStatus);
            moneyhavepay = itemView.findViewById(R.id.txt_alMoneyhavepay);
        }
    }
}
