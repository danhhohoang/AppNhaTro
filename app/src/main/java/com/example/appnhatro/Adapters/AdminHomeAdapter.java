package com.example.appnhatro.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Activity.AdminHomeActivity;
import com.example.appnhatro.Firebase.FirebaseAdmin;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.HistoryTransaction;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
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

public class AdminHomeAdapter extends RecyclerView.Adapter<AdminHomeAdapter.MyViewHolder>{
    private Context context;
    private int resource;
    private ArrayList<HistoryTransaction> historyTransactions;
    private OnItemClickListener onItemClickLisner;
    private FirebaseAdmin firebaseAdmin=new FirebaseAdmin();
    public AdminHomeAdapter(Context context, int resource, ArrayList<HistoryTransaction> historyTransactions) {
        this.context = context;
        this.resource = resource;
        this.historyTransactions = historyTransactions;
    }

    @NonNull
    @Override
    public AdminHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.admin_item_home_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHomeAdapter.MyViewHolder holder, int position) {
        HistoryTransaction post = historyTransactions.get(position);
        holder.tvIdPost.setText(post.getPost());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.tvPrice.setText(formatter.format(Integer.valueOf(post.getPrice())*5/100));
        holder.tvIdUser.setText(post.getId_user());
        holder.tvDate.setText(post.getDate());
        if(post.getStatus().equals("2")){
            holder.btnXacNhan.setVisibility(View.GONE);
            holder.tvTienCanThu.setText("Tiền Hoa Hồng");
        }else {
            holder.btnXacNhan.setVisibility(View.VISIBLE);
            holder.tvTienCanThu.setText("Tiền Cần Thu");
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if(post.getId().equals(post.getUserID())){
                        BitMap bitMap = new BitMap(post.getImage(),null);
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/"+bitMap.getTenHinh());
                        try {
                            final File file= File.createTempFile(bitMap.getTenHinh(),"jpg");
                            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                    holder.hinh.setImageBitmap(bitMap.getHinh());
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                DatabaseReference databaseReference1 = firebaseDatabase.getReference();
                databaseReference1.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            user User = dataSnapshot.getValue(user.class);
                            if(User.getId().equals(post.getId_user())){
                                holder.tvNameUser.setText(User.getName());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final int pos = position;
        holder.onItemClickLisner=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickLisner!=null){
                    onItemClickLisner.onItemClickListener(pos,holder.itemView);
                }
            }
        };
        holder.btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn muốn xác nhận đã thu tiền.");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAdmin.xacNhanThuTien(post);
                    }
                });
                builder.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyTransactions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View.OnClickListener onItemClickLisner;
        ImageView hinh;
        TextView tvIdUser,tvNameUser,tvIdPost,tvPrice,tvDate,tvTienCanThu;
        Button btnXacNhan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdUser = itemView.findViewById(R.id.tvIdLandLordHomeAdmin);
            tvNameUser = itemView.findViewById(R.id.tvNameLandLordHomeAdmin);
            tvIdPost = itemView.findViewById(R.id.tvIdPostHomeAdmin);
            tvPrice = itemView.findViewById(R.id.tvIdMoneyHomeAdmin);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhanAdmin);
            tvDate = itemView.findViewById(R.id.tvDateHomeAdmin);
            hinh = itemView.findViewById(R.id.imgItemHomeAdmin);
            tvTienCanThu = itemView.findViewById(R.id.tvTienCanThu);
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
