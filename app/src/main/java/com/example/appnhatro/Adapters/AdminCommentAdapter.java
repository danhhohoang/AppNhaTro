package com.example.appnhatro.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.example.appnhatro.item.Rating;
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
        int pos=position;
        Rating rating = ratings.get(pos);
        holder.tvRating.setText(rating.getRating());
        holder.tvComment.setText(rating.getComment());
        holder.tvDate.setText(rating.getDate());
        if(rating.getFeedback().equals("")){
            holder.lvFeedBack.setVisibility(View.GONE);
            ViewGroup.LayoutParams cardview=  holder.rcv.getLayoutParams();
            cardview.height=300;
        }else{
            holder.lvFeedBack.setVisibility(View.VISIBLE);
            holder.tvCommentLandLord.setText(rating.getFeedback());
        }
        holder.onItemClickLisner=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickLisner!=null){
                    onItemClickLisner.onItemClickListener(pos,holder.itemView);
                }
            }
        };
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferences = firebaseDatabase.getReference();
        databaseReferences.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user User = dataSnapshot.getValue(user.class);
                    if (User.getId().equals(rating.getIdUser())) {
                        BitMap bitMap = new BitMap(User.getAvatar(),null);
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/"+bitMap.getTenHinh());
                        try {
                            final File file= File.createTempFile(bitMap.getTenHinh(),"jpg");
                            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                    holder.imgUserItemCommentTenantLandLord.setImageBitmap(bitMap.getHinh());
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View.OnClickListener onItemClickLisner;
        TextView tvRating,tvComment,tvDate,tvPhanHoiButton,tvCommentLandLord;
        ImageView imgUserItemCommentTenantLandLord;
        LinearLayout lvFeedBack;
        CardView rcv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRating = itemView.findViewById(R.id.tvRatingItemAdmin);
            tvComment = itemView.findViewById(R.id.tvCommentItemAdmin);
            tvDate = itemView.findViewById(R.id.tvDateItemRatingAdmin);
            tvCommentLandLord = itemView.findViewById(R.id.tvCommentLandLordItemAdmin);
            imgUserItemCommentTenantLandLord = itemView.findViewById(R.id.imgUserItemCommentAdmin);
            lvFeedBack= itemView.findViewById(R.id.confirmLandLordAdmin);
            rcv = itemView.findViewById(R.id.rcv_HomeLanLord_Item_Admin);
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
