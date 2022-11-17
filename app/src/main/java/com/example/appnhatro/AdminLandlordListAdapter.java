package com.example.appnhatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.PostAndPostFindPeople;
import com.example.appnhatro.Models.user;

import java.util.ArrayList;
import java.util.List;

public class AdminLandlordListAdapter extends RecyclerView.Adapter<AdminLandlordListAdapter.AdminLandlordList> implements Filterable {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<user> mPostList;
    List<user> mPostListOld;

    public AdminLandlordListAdapter(Context context,RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
    }

    public void setData(List<user> list){
        this.mPostList = list;
        this.mPostListOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminLandlordList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_landlord,parent,false);
        return new AdminLandlordList(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminLandlordList holder, int position) {
        user landLordList = mPostList.get(position);
        if (landLordList == null){
            return;
        }
        holder.ID.setText(String.valueOf(landLordList.getId()));
        holder.sdt.setText(String.valueOf(landLordList.getPhone()));
        //holder.status.setText(landLordList.getStatus());
        //holder.moneyhavepay.setText(String.valueOf(landLordList.getMoneyhavepay()));
    }

    @Override
    public int getItemCount() {
        if (mPostList != null){
            return mPostList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    mPostList = mPostListOld;
                } else {
                    List<user> list = new ArrayList<>();
                    for (user User : mPostListOld){
                        if (User.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(User);
                        }
                    }
                    mPostList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mPostList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mPostList = (ArrayList<user>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class AdminLandlordList extends RecyclerView.ViewHolder{
        TextView ID,sdt,status,moneyhavepay;
        public AdminLandlordList(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ID = itemView.findViewById(R.id.txt_alID);
            sdt = itemView.findViewById(R.id.txt_alSDT);
            status = itemView.findViewById(R.id.txt_alStatus);
            moneyhavepay = itemView.findViewById(R.id.txt_alMoneyhavepay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
