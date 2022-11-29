package com.example.appnhatro;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
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
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TenantPostFavouriteAdapter extends RecyclerView.Adapter<TenantPostFavouriteAdapter.TenantPostFavourite> implements Filterable {
    private Activity context;
    private int resource;
    private ArrayList<Post> mPostLists;
    private ArrayList<Post> mPostListsOld;
    private OnItemClickListener onItemClickLisner;

    public TenantPostFavouriteAdapter(Activity context, int resource, ArrayList<Post> posts) {
        this.context = context;
        this.resource = resource;
        this.mPostLists = posts;
        this.mPostListsOld = posts;
    }

    @NonNull
    @Override
    public TenantPostFavourite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new TenantPostFavourite(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostFavourite holder, int position) {
        Post post = mPostLists.get(position);
        holder.house_name.setText(post.getHouse_name() );
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.price.setText(formatter.format(Integer.valueOf(post.getPrice()))+" VND");
        holder.address.setText(post.getAddress());
        holder.area.setText(formatter.format(Integer.valueOf(post.getArea())) + " m2");
        BitMap bitMap = new BitMap(post.getImage(),null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/post/" + bitMap.getTenHinh());
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
                            holder.circleImageView.setImageBitmap(bitMap2.getHinh());
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

    public static class TenantPostFavourite extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView post_id,user_id,area,price,house_name,address,attachment,status,wishlist;
        ImageView picture;
        CircleImageView circleImageView;
        View.OnClickListener onClickListener;
        CardView item;

        public TenantPostFavourite(@NonNull View itemView) {
            super(itemView);
            house_name = itemView.findViewById(R.id.txt_tpfHousename);
            address = itemView.findViewById(R.id.txt_tpfAddress);
            area = itemView.findViewById(R.id.txt_tpfArea);
            price = itemView.findViewById(R.id.txt_tpfPrice);
            picture = itemView.findViewById(R.id.iv_tpfPicture);
            circleImageView = itemView.findViewById(R.id.ci_tpfAVT);
            item = itemView.findViewById(R.id.cv_tpfCardView);
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
        this.onItemClickLisner = onItemClickListener;
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
}
