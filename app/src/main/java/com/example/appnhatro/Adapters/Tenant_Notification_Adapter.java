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
import com.example.appnhatro.Models.Notificationbooking;
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

public class Tenant_Notification_Adapter extends RecyclerView.Adapter<Tenant_Notification_Adapter.TenantNotification> implements Filterable {

    private Activity context;
    private int resource;
    private ArrayList<Notificationbooking> mBookingLists;
    private ArrayList<Notificationbooking> mBookingListsOld;
    private OnItemClickListener onItemClickLisner;

    public Tenant_Notification_Adapter(Activity context, int resource, ArrayList<Notificationbooking> mBookingLists) {
        this.context = context;
        this.resource = resource;
        this.mBookingLists = mBookingLists;
        this.mBookingListsOld = mBookingLists;
    }

    @NonNull
    @Override
    public Tenant_Notification_Adapter.TenantNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new TenantNotification(cardViewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull Tenant_Notification_Adapter.TenantNotification holder, int position) {
        Notificationbooking notificationbooking = mBookingLists.get(position);
        if (notificationbooking == null){
            return;
        }

        holder.name.setText(notificationbooking.getName() );
        holder.time.setText(notificationbooking.getTime());
        holder.date.setText(notificationbooking.getDate());
        holder.status.setText(notificationbooking.getStatus());
        holder.housename.setText(notificationbooking.getHousename());
        holder.notes.setText(notificationbooking.getNotes());
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
        databaseReference.child("user").child(notificationbooking.getIdLandlor()).addListenerForSingleValueEvent(new ValueEventListener() {
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
                            holder.picture.setImageBitmap(bitMap2.getHinh());
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
                    ArrayList<Notificationbooking> list = new ArrayList<>();
                    for (Notificationbooking notificationbooking : mBookingListsOld){
                        if (notificationbooking.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(notificationbooking);
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
                mBookingLists = (ArrayList<Notificationbooking>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class TenantNotification extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idUser, name, phone, time, date, notes, id, idPost, status, housename;
        ImageView picture;
        View.OnClickListener onClickListener;
        CardView item;

        public TenantNotification(@NonNull View itemView) {
            super(itemView);
            housename = itemView.findViewById(R.id.edtnamehouse);
            name = itemView.findViewById(R.id.edtten);
            time = itemView.findViewById(R.id.edtgio);
            date = itemView.findViewById(R.id.edttime);
            status = itemView.findViewById(R.id.edtStatus);
            picture = itemView.findViewById(R.id.img_AVT);
            notes =itemView.findViewById(R.id.edtnote);
            item = itemView.findViewById(R.id.carnotifi);
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

    public void setOnItemClickListener(Tenant_Notification_Adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }

}
