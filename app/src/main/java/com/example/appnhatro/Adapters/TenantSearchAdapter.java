package com.example.appnhatro.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import com.example.appnhatro.TenantPostDetail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class TenantSearchAdapter extends RecyclerView.Adapter<TenantSearchAdapter.TenantSearchViewHolder> {

    private final Activity context;
    private final int resource;
    private final ArrayList<Post> postList;
    private OnItemClickListener onItemClickListener;
    public TenantSearchAdapter(Activity context, int resource, ArrayList<Post> postList) {
        this.context = context;
        this.resource = resource;
        this.postList = postList;
    }
    @NonNull
    @Override
    public TenantSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater()
                .inflate(viewType, parent, false);
        return new TenantSearchViewHolder(cardViewItem);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TenantSearchViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.txtHouseName.setText(post.getHouse_name());
        holder.txtAddress.setText("Địa chỉ: "+ post.getAddress() + " " + post.getAddress_district());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        holder.txtPrice.setText("Giá: "+decimalFormat.format(Integer.valueOf(post.getPrice())) +"đ");
        holder.txtArea.setText("Diện tích: "+ post.getArea() +"m²");
        holder.txtStatus.setText("Trạng thái: "+post.getStatus());
        if(post.getId().contains("tenant")){
            holder.txtType.setText("Loại: Ở Ghép");
        }else{
            holder.txtType.setText("Loại: Cho thuê");
        }

        try {
            BitMap bitMap = new BitMap(post.getImage(), null);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/" + bitMap.getTenHinh());

            final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    holder.imgRoom.setImageBitmap(bitMap.getHinh());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("user").child(post.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user _user = snapshot.getValue(user.class);
                BitMap bitMap2 = new BitMap(_user.getAvatar(), null);
                StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("images/user/" + bitMap2.getTenHinh());

                try {
                    final File file2 = File.createTempFile(bitMap2.getTenHinh(), "jpg");
                    storageReference2.getFile(file2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitMap2.setHinh(BitmapFactory.decodeFile(file2.getAbsolutePath()));
                            holder.imgAvatar .setImageBitmap(bitMap2.getHinh());
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TenantPostDetail.class);
                intent.putExtra("it_id",post.getId());
                context.startActivity(intent);
            }
        });


        //Event processing
        final int pos = position;
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(pos, holder.itemView);
                }
            }
        };
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return resource;
    }

    static class TenantSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtHouseName;
        TextView txtAddress;
        TextView txtArea;
        TextView txtPrice;
        TextView txtStatus;
        TextView txtType;
        ImageView imgRoom;
        ImageView imgAvatar;
        CardView item;
        View.OnClickListener onClickListener;


        public TenantSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHouseName = itemView.findViewById(R.id.textview_item_post_housename);
            txtAddress = itemView.findViewById(R.id.textview_item_post_address);
            txtArea = itemView.findViewById(R.id.textview_item_post_area);
            txtPrice = itemView.findViewById(R.id.textview_item_post_price);
            txtStatus = itemView.findViewById(R.id.textview_item_post_status);
            txtType = itemView.findViewById(R.id.textview_item_post_type);
            imgRoom = itemView.findViewById(R.id.iv_post_image_room);
            imgAvatar = itemView.findViewById(R.id.iv_item_tenant_search_avatar);
            //
            item = itemView.findViewById(R.id.cardView_item_search_layout);
            item.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
