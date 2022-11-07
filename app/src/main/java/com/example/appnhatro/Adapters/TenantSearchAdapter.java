package com.example.appnhatro.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.FakeModels.PostFake;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.MyRecyclerViewAdapter;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TenantSearchAdapter extends RecyclerView.Adapter<TenantSearchAdapter.TenantSearchViewHolder> {

    private Activity context;
    private int resource;
    private ArrayList<Post> postList;
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
        //do có xung đột trong quá trình thiết kế cơ sở dữ liệu
        //House_name chính là cái mà người dùng sẽ đặt tên cho căn nhà
        holder.txtAddress.setText(post.getAddress() + " " + post.getAddress_district());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        holder.txtPrice.setText(decimalFormat.format(Integer.valueOf(post.getPrice())));
        holder.txtArea.setText(post.getArea());
        holder.txtStatus.setText("status is update later");
        holder.imgAvatar.setImageResource(R.drawable.ic_user);

        BitMap bitMap = new BitMap(post.getImage(),null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(bitMap.getTenHinh());
        try {
            final File file= File.createTempFile(bitMap.getTenHinh().substring(0,bitMap.getTenHinh().length()-4),"jpg");
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
        //Event processing
        final int pos = position;
        holder.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClickListener(pos,holder.itemView);
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
            imgRoom = itemView.findViewById(R.id.iv_post_image_room);
            imgAvatar = itemView.findViewById(R.id.iv_avatar);
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
