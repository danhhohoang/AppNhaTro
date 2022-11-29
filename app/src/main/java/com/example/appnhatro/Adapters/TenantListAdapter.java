package com.example.appnhatro.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Activity.AdminTenantDetaiActivity;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.example.appnhatro.tool.RecyclerViewTenantList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TenantListAdapter extends RecyclerView.Adapter<TenantListAdapter.TenantListViewHolder> {
    private final RecyclerViewTenantList recyclerViewTenantList;
    private final ArrayList<user> userArrayList;
    private final Activity context;
    private final int resource;

    public TenantListAdapter(Activity context, int resource, ArrayList<user> userArrayList,RecyclerViewTenantList recyclerViewTenantList) {
        this.userArrayList = userArrayList;
        this.context = context;
        this.resource = resource;
        this.recyclerViewTenantList = recyclerViewTenantList;
    }

    @NonNull
    @Override
    public TenantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_tenant_list, parent, false);
        return new TenantListViewHolder(view,recyclerViewTenantList);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantListViewHolder holder, int position) {
        final user mUser = userArrayList.get(position);

        holder.textViewID.setText(mUser.getId());
        holder.textViewName.setText(mUser.getName());
        holder.textViewPhone.setText(mUser.getPhone());
        if (mUser.getStatus().equals("1")) {
            holder.textViewID.setTextColor(Color.RED);
            holder.textViewName.setTextColor(Color.RED);
            holder.textViewIDStatic.setTextColor(Color.RED);
            holder.textViewNameStatic.setTextColor(Color.RED);
            holder.textViewPhone.setTextColor(Color.RED);
            holder.textViewPhoneStatic.setTextColor(Color.RED);
        }
        BitMap bitMap = new BitMap(mUser.getAvatar(), null);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + bitMap.getTenHinh());
        try {
            final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
            storageReference.getFile(file).addOnSuccessListener(taskSnapshot -> {
                bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                holder.avatar.setImageBitmap(bitMap.getHinh());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemCardView.setOnClickListener(v -> goToDetail(mUser));

    }

    public void goToDetail(user mUser) {
        Intent intent = new Intent(context, AdminTenantDetaiActivity.class);
        intent.putExtra("idUser", mUser.getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (userArrayList != null) {
            return userArrayList.size();
        }
        return 0;
    }

    public class TenantListViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView textViewID;
        TextView textViewName;
        CardView itemCardView;
        TextView textViewIDStatic;
        TextView textViewNameStatic;
        TextView textViewPhoneStatic;
        TextView textViewPhone;

        public TenantListViewHolder(@NonNull View itemView, RecyclerViewTenantList recyclerViewTenantList) {
            super(itemView);
            avatar = itemView.findViewById(R.id.img_item_admin_tenant_list_avatar);
            textViewID = itemView.findViewById(R.id.txt_item_admin_tenant_list_id);
            textViewName = itemView.findViewById(R.id.txt_item_admin_tenant_list_name);
            itemCardView = itemView.findViewById(R.id.cardView_item_admin_tenant_list);
            textViewIDStatic = itemView.findViewById(R.id.txt_item_admin_tenant_list_id_static);
            textViewNameStatic = itemView.findViewById(R.id.txt_item_admin_tenant_list_name_static);
            textViewPhoneStatic = itemView.findViewById(R.id.txt_item_admin_tenant_list_phone_static);
            textViewPhone = itemView.findViewById(R.id.txt_item_admin_tenant_list_phone);

            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewTenantList != null){
                        int pos = getBindingAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewTenantList.onClick(pos);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resource;
    }
}
