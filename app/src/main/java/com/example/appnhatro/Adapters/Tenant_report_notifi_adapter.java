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

import com.example.appnhatro.Interface.Admininterface;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.NotifiAdminmodels;
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

public class Tenant_report_notifi_adapter extends RecyclerView.Adapter<Tenant_report_notifi_adapter.Tenantreport> implements Filterable {
    private Activity context;
    private int resource;
    private ArrayList<NotifiAdminmodels> mReportLists;
    private ArrayList<NotifiAdminmodels> mReportListsOld;
    private Tenant_report_notifi_adapter.OnItemClickListener onItemClickLisner;
    private Admininterface admininterface;
    public Tenant_report_notifi_adapter(Activity context, int resource, ArrayList<NotifiAdminmodels> mBookingLists, Admininterface admininterface) {
        this.context = context;
        this.resource = resource;
        this.mReportLists = mBookingLists;
        this.mReportListsOld = mBookingLists;
        this.admininterface = admininterface;
    }

    @NonNull
    @Override
    public Tenant_report_notifi_adapter.Tenantreport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new Tenantreport(cardViewItem, admininterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Tenant_report_notifi_adapter.Tenantreport holder, int position) {
        NotifiAdminmodels notifiAdminmodels = mReportLists.get(position);
        if (notifiAdminmodels == null){
            return;
        }
        holder.name.setText(notifiAdminmodels.getName());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.id.setText(notifiAdminmodels.getIdAdmin());
        holder.status.setText(notifiAdminmodels.getStatus());
        holder.name_house.setText(notifiAdminmodels.getHouse_name());
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
        databaseReference.child("user").child(notifiAdminmodels.getIdAdmin()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user _user = snapshot.getValue(user.class);
                BitMap bitMap = new BitMap(_user.getAvatar(), null);
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
        if (mReportLists != null){
            return mReportLists.size();
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
                    mReportLists = mReportListsOld;
                } else {
                    ArrayList<NotifiAdminmodels> list = new ArrayList<>();
                    for (NotifiAdminmodels notifiAdminmodels : mReportListsOld){
                        if (notifiAdminmodels.getIduser().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(notifiAdminmodels);
                        }
                    }
                    mReportLists = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mReportLists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mReportLists = (ArrayList<NotifiAdminmodels>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class Tenantreport extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idUser, name, status, name_house, tieude, notes, id, idPost;
        ImageView picture;
        View.OnClickListener onClickListener;
        CardView item;

        public Tenantreport(@NonNull View itemView, Admininterface admininterface) {
            super(itemView);
            name = itemView.findViewById(R.id.edt_rpname);
            id = itemView.findViewById(R.id.edt_rpid);
            name_house = itemView.findViewById(R.id.edt_rpnamehouse);
            status = itemView.findViewById(R.id.edt_rpStatus);
            picture = itemView.findViewById(R.id.img_AVTtn);
            item = itemView.findViewById(R.id.carnotifitn);
            item.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
        private void Notification(Post post) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Notification");
            databaseReference.child(post.getId()).setValue(post);
        }
    }
    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);
    }

    public void setOnItemClickListener(Tenant_report_notifi_adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }
}
