package com.example.appnhatro;

import android.app.Activity;
import android.content.SharedPreferences;
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

import com.example.appnhatro.Interface.Admininterface;
import com.example.appnhatro.Adapters.Landlord_Notification_Adapter;
import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.ReportModels;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Admin_notification_adapter  extends RecyclerView.Adapter<Admin_notification_adapter.AdminNotification> implements Filterable {
    private Activity context;
    private int resource;
    private ArrayList<ReportModels> mReportLists;
    private ArrayList<ReportModels> mReportListsOld;
    private Landlord_Notification_Adapter.OnItemClickListener onItemClickLisner;
    String ID = "";
    String getID;
    SharedPreferences sharedPreferences;
    FireBaseThueTro fireBaseThueTro;
    private Admininterface admininterface;
    public Admin_notification_adapter(Activity context, int resource, ArrayList<ReportModels> mBookingLists, Admininterface admininterface) {
        this.context = context;
        this.resource = resource;
        this.mReportLists = mBookingLists;
        this.mReportListsOld = mBookingLists;
        this.admininterface = admininterface;
    }

    @NonNull
    @Override
    public Admin_notification_adapter.AdminNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new AdminNotification(cardViewItem, admininterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_notification_adapter.AdminNotification holder, int position) {
        ReportModels reportModels = mReportLists.get(position);
        if (reportModels == null){
            return;
        }
        holder.name.setText(reportModels.getTenNguoiGui() );
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.MaBaiDang.setText(reportModels.getID_BaiDang());
        holder.tieude.setText(reportModels.getTitle());
        holder.name_house.setText(reportModels.getPost_Name());

//        BitMap bitMap = new BitMap(datLichModels.getImage(),null);
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference(bitMap.getTenHinh());
//        try {
//            final File file= File.createTempFile(bitMap.getTenHinh().substring(0,bitMap.getTenHinh().length()-4),"jpg");
//            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
//                    holder.picture.setImageBitmap(bitMap.getHinh());
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
                    ArrayList<ReportModels> list = new ArrayList<>();
                    for (ReportModels reportModels : mReportListsOld){
                        if (reportModels.getTenNguoiGui().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(reportModels);
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
                mReportLists = (ArrayList<ReportModels>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class AdminNotification extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idUser, name, MaBaiDang, name_house, tieude, notes, id, idPost;
        ImageView picture;
        View.OnClickListener onClickListener;
        CardView item;
        Button Dy;

        public AdminNotification(@NonNull View itemView, Admininterface admininterface) {
            super(itemView);
//          sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
//          getID = sharedPreferences.getString("idUser", "");
            name = itemView.findViewById(R.id.edt_ADname);
            MaBaiDang = itemView.findViewById(R.id.edtMABD);
            tieude = itemView.findViewById(R.id.edtTD);
            name_house = itemView.findViewById(R.id.edtStatus);
//          picture = itemView.findViewById(R.id.iv_tprPicture);
            Dy  = itemView.findViewById(R.id.btn_danhan);
            item = itemView.findViewById(R.id.admincard);
            item.setOnClickListener(this);

            Dy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (admininterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            admininterface.onclick(pos);
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
        private void Notification(Post post) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Notification");
            databaseReference.child(post.getId()).setValue(post);
        }
    }
    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);
    }

    public void setOnItemClickListener(Landlord_Notification_Adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }

//    @Override
//    protected void onResume(){
//        super.onResume();
//        fireBaseThueTro.IdNotificationAdmin(Admin_notification_adapter.this);
//    }
//    public void IdNotificationad (String id){
//        ID = id;
//    }
}
