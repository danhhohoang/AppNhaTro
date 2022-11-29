package com.example.appnhatro;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.PostAndPostFindPeople;
import com.example.appnhatro.Models.PostFindRoomateModel;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class TenantPostListAdapter extends RecyclerView.Adapter<TenantPostListAdapter.TenantPostList> implements Filterable {

    private Activity context;
    private int resource;
    private ArrayList<Post> mPostFindPP;
    private ArrayList<Post> mPostFindPPOld;
    private OnItemClickListener onItemClickLisner;
    private RecyclerCRUD recyclerCRUD;

    public TenantPostListAdapter(Activity context, int resource, ArrayList<Post> mPostFindPP, RecyclerCRUD recyclerCRUD) {
        this.context = context;
        this.resource = resource;
        this.mPostFindPP = mPostFindPP;
        this.mPostFindPPOld = mPostFindPP;
        this.recyclerCRUD = recyclerCRUD;
    }

    @NonNull
    @Override
    public TenantPostList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new TenantPostList(cardViewItem,recyclerCRUD);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostList holder, int position) {
        Post post = mPostFindPP.get(position);

        holder.house_name.setText(post.getHouse_name() );
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.price.setText(post.getPrice());
        holder.address.setText(post.getAddress());
        holder.area.setText(post.getArea());
        BitMap bitMap = new BitMap(post.getImage(),null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/post/"+bitMap.getTenHinh());
        try {
            final File file= File.createTempFile(bitMap.getTenHinh().substring(0,bitMap.getTenHinh().length()-4),"jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    holder.imageView.setImageBitmap(bitMap.getHinh());
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
        if (mPostFindPP != null){
            return mPostFindPP.size();

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
                    mPostFindPP = mPostFindPPOld;
                } else {
                    ArrayList<Post> list = new ArrayList<>();
                    for (Post post : mPostFindPPOld){
                        if (post.getHouse_name().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(post);
                        }
                    }
                    mPostFindPP = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mPostFindPP;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mPostFindPP = (ArrayList<Post>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class TenantPostList extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView house_name,address,area,price,title;
        ImageView imageView;
        CircleImageView avt;
        View.OnClickListener onClickListener;
        Button sua,xoa;
        CardView item;

        public TenantPostList(@NonNull View itemView,RecyclerCRUD recyclerCRUD) {
            super(itemView);
            house_name = itemView.findViewById(R.id.txt_tplHouse_name);
            address = itemView.findViewById(R.id.txt_tplAddress);
            area = itemView.findViewById(R.id.txt_tplArea);
            price = itemView.findViewById(R.id.txt_tplPrice);
            imageView = itemView.findViewById(R.id.iv_tplImage);
            item = itemView.findViewById(R.id.cv_tplCardView);
            item.setOnClickListener(this);
            sua = itemView.findViewById(R.id.btn_tplSua);
            xoa = itemView.findViewById(R.id.btn_tplXoa);
            avt = itemView.findViewById(R.id.ci_tplAVT);

            sua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerCRUD != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerCRUD.onUpdateClick(pos);
                        }
                    }
                }
            });

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerCRUD != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerCRUD.onDeleteClick(pos);
                        }
                    }
                }
            });
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

    public void setOnItemClickListener(TenantPostListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }
}
