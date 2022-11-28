package com.example.appnhatro;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.PostAndPostFindPeople;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.tool.RecylerViewLandLordList;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminLandlordListAdapter extends RecyclerView.Adapter<AdminLandlordListAdapter.AdminLandlordList> {
    private final RecyclerViewInterfaceAdminLandlord recyclerViewInterfaceAdminLandlord;
    ArrayList<user> mPostList;

    public AdminLandlordListAdapter(ArrayList<user>list,RecyclerViewInterfaceAdminLandlord recyclerViewInterfaceAdminLandlord) {
        this.recyclerViewInterfaceAdminLandlord = recyclerViewInterfaceAdminLandlord;
        this.mPostList = list;
    }

    public void setData(ArrayList<user> list){
        this.mPostList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminLandlordList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_landlord,parent,false);
        return new AdminLandlordList(v,recyclerViewInterfaceAdminLandlord);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminLandlordList holder, int position) {
        user user = mPostList.get(position);
        if (user == null){
            return;
        }
        holder.ID.setText(user.getId());
        holder.sdt.setText(user.getPhone());
        if(user.getStatus() == null){
            holder.status.setText("");
        }else{
            if(user.getStatus().equals("0")){
                holder.status.setText("Hoạt động");
            }else if(user.getStatus().equals("1")){
                holder.status.setText("Tạm khoá");
            }
        }
        TenantAccountActivity.setImage(holder.image,user.getAvatar());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewInterfaceAdminLandlord.onItemClickLandlord(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mPostList != null){
            return mPostList.size();
        }
        return 0;
    }

    public static class AdminLandlordList extends RecyclerView.ViewHolder{
        TextView ID,sdt,status;
        CircleImageView image;
        LinearLayout layout;
        ImageButton imageButton;
        public AdminLandlordList(@NonNull View itemView,RecyclerViewInterfaceAdminLandlord recyclerViewInterfaceAdminLandlord) {
            super(itemView);
            ID = itemView.findViewById(R.id.txt_alID);
            sdt = itemView.findViewById(R.id.txt_alSDT);
            status = itemView.findViewById(R.id.txt_alStatus);
            imageButton = itemView.findViewById(R.id.ib_allClick);
            image = itemView.findViewById(R.id.civDanhsach_AL);
            layout = itemView.findViewById(R.id.layout_ItemLLA);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterfaceAdminLandlord != null){
                        int pos = getBindingAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterfaceAdminLandlord.onClick(pos);
                        }
                    }
                }
            });
        }
    }
}
