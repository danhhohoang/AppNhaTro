package com.example.appnhatro.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.Notificationbooking;
import com.example.appnhatro.R;

import java.text.DecimalFormat;
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
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.idPost.setText(notificationbooking.getIdNotifi());
        holder.date.setText(notificationbooking.getDate());
        holder.status.setText(notificationbooking.getStatus());
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
        TextView idUser, name, phone, time, date, notes, id, idPost, status;
        ImageView picture;
        View.OnClickListener onClickListener;
        CardView item;

        public TenantNotification(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.edtname);
            idPost = itemView.findViewById(R.id.edtMABD);
            date = itemView.findViewById(R.id.edtTD);
            status = itemView.findViewById(R.id.edtStatus);
//          picture = itemView.findViewById(R.id.iv_tprPicture);
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
