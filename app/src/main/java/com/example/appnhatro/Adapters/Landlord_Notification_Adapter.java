package com.example.appnhatro.Adapters;

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
import com.example.appnhatro.Models.DatLichModels;
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
import java.util.ArrayList;

public class Landlord_Notification_Adapter extends RecyclerView.Adapter<Landlord_Notification_Adapter.LandlordNotification> implements Filterable {

    private Activity context;
    private int resource;
    private ArrayList<DatLichModels> mBookingLists;
    private ArrayList<DatLichModels> mBookingListsOld;
    private OnItemClickListener onItemClickLisner;

    public Landlord_Notification_Adapter(Activity context, int resource, ArrayList<DatLichModels> mBookingLists) {
        this.context = context;
        this.resource = resource;
        this.mBookingLists = mBookingLists;
        this.mBookingListsOld = mBookingLists;
    }

    @NonNull
    @Override
    public Landlord_Notification_Adapter.LandlordNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new LandlordNotification(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull Landlord_Notification_Adapter.LandlordNotification holder, int position) {
        DatLichModels datLichModels = mBookingLists.get(position);
        if (datLichModels == null){
            return;
        }
        holder.name.setText(datLichModels.getName() );
        holder.HouseName.setText(datLichModels.getHouname());
        holder.date.setText(datLichModels.getDate());
        holder.time.setText(datLichModels.getTime());
        holder.notes.setText(datLichModels.getNotes());
        final int pos = position;
        holder.onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickLisner!=null){
                    onItemClickLisner.onItemClickListener(pos,holder.itemView);
                }
            }
        };
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("user").child(datLichModels.getIdUser()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user _use= snapshot.getValue(user.class);
                BitMap bitMap = new BitMap(_use.getAvatar(), null);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + bitMap.getTenHinh());
                try {
                    final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
                    storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            holder.picture.setImageBitmap(bitMap.getHinh());
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mBookingLists != null){
            return mBookingLists.size();
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
                    mBookingLists = mBookingListsOld;
                } else {
                    ArrayList<DatLichModels> list = new ArrayList<>();
                    for (DatLichModels datLichModels : mBookingListsOld){
                        if (datLichModels.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(datLichModels);
                        }
                    }
                    mBookingLists = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mBookingLists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mBookingLists = (ArrayList<DatLichModels>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class LandlordNotification extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idUser, name, HouseName, time, date, notes, id, idPost;
        ImageView picture;
        View.OnClickListener onClickListener;
        CardView item;

        public LandlordNotification(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            HouseName = itemView.findViewById(R.id.txthouname);
            date = itemView.findViewById(R.id.txt_Ngay);
            time = itemView.findViewById(R.id.txtGio);
            notes = itemView.findViewById(R.id.txtnote);
            picture = itemView.findViewById(R.id.imgavt);
            item = itemView.findViewById(R.id.cv_tprCardView1);
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

    public void setOnItemClickListener(Landlord_Notification_Adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }

}
