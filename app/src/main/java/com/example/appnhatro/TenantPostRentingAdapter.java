package com.example.appnhatro;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class TenantPostRentingAdapter extends RecyclerView.Adapter<TenantPostRentingAdapter.TenantPostRenting> implements Filterable {

    private Activity context;
    private int resource;
    private ArrayList<Post> mPostLists;
    private ArrayList<Post> mPostListsOld;
    private OnItemClickListener onItemClickLisner;

    public TenantPostRentingAdapter(Activity context, int resource, ArrayList<Post> mPostLists) {
        this.context = context;
        this.resource = resource;
        this.mPostLists = mPostLists;
        this.mPostListsOld = mPostLists;
    }

    @NonNull
    @Override
    public TenantPostRentingAdapter.TenantPostRenting onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new TenantPostRenting(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostRentingAdapter.TenantPostRenting holder, int position) {
        Post post = mPostLists.get(position);
        if (post == null){
            return;
        }
        holder.house_name.setText(post.getHouse_name() );
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.price.setText(formatter.format(Integer.valueOf(post.getPrice())));
        holder.address.setText(post.getAddress());
        holder.area.setText(formatter.format(Integer.valueOf(post.getArea())));
        BitMap bitMap = new BitMap(post.getImage(),null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/post/" + post.getImage());
        try {
            final File file= File.createTempFile(bitMap.getTenHinh().substring(0,bitMap.getTenHinh().length()-4),"jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    holder.picture.setImageBitmap(bitMap.getHinh());
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
                            holder.avt.setImageBitmap(bitMap2.getHinh());
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
        //Event processing
        final int pos = position;
        holder.onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickLisner!=null){
                    onItemClickLisner.onItemClickListener(pos,holder.itemView);
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        if (mPostLists != null){
            return mPostLists.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return resource;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    mPostLists = mPostListsOld;
                } else {
                    ArrayList<Post> list = new ArrayList<>();
                    for (Post post : mPostListsOld){
                        if (post.getHouse_name().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(post);
                        }
                    }
                    mPostLists = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mPostLists;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mPostLists = (ArrayList<Post>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class TenantPostRenting extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView post_id,user_id,area,price,house_name,address,attachment,status,wishlist;
        ImageView picture;
        CircleImageView avt;
        View.OnClickListener onClickListener;
        CardView item;

        public TenantPostRenting(@NonNull View itemView) {
            super(itemView);
            house_name = itemView.findViewById(R.id.txt_tprHouseName);
            address = itemView.findViewById(R.id.txt_tprAddress);
            area = itemView.findViewById(R.id.txt_tprArea);
            price = itemView.findViewById(R.id.txt_tprPrice);
            picture = itemView.findViewById(R.id.iv_tprPicture);
            avt = itemView.findViewById(R.id.avt);
            item = itemView.findViewById(R.id.cv_tprCardView);
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

    public void setOnItemClickListener(TenantPostRentingAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }

}
