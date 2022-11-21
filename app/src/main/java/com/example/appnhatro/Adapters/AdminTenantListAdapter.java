package com.example.appnhatro.Adapters;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class AdminTenantListAdapter extends RecyclerView.Adapter<AdminTenantListAdapter.AdminTenantListViewHoler> {

    private final Activity context;
    private final int resource;
    private final ArrayList<user> userArrayList;
    private TenantSearchAdapter.OnItemClickListener onItemClickListener;


    public AdminTenantListAdapter(Activity context, int resource, ArrayList<user> userArrayList) {
        this.context = context;
        this.resource = resource;
        this.userArrayList = userArrayList;

    }

    @NonNull
    @Override
    public AdminTenantListViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().inflate(viewType, parent, false);


        return new AdminTenantListViewHoler(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminTenantListViewHoler holder, int position) {
        user _user = userArrayList.get(position);
        holder.textViewID.setText(_user.getId());
        holder.textViewPhone.setText(_user.getPhone());

        BitMap bitMap = new BitMap(_user.getAvatar(), null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + bitMap.getTenHinh());

        try {
            final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    holder.avatar.setImageBitmap(bitMap.getHinh());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    static class AdminTenantListViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView avatar;
        TextView textViewID;
        TextView textViewPhone;
        CardView itemCardView;
        View.OnClickListener onClickListener;


        public AdminTenantListViewHoler(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.img_item_admin_tenant_list_avatar);
            textViewID = itemView.findViewById(R.id.txt_item_admin_tenant_list_id);
            textViewPhone = itemView.findViewById(R.id.txt_item_admin_tenant_list_phone);
            itemCardView = itemView.findViewById(R.id.cardView_item_admin_tenant_list);
        }


        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        }


    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);
    }

    public void setOnItemClickListener(TenantSearchAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
