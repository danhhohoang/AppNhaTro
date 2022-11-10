package com.example.appnhatro;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.PostAndPostFindPeople;
import com.example.appnhatro.Models.PostFindRoomateModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TenantPostListAdapter extends RecyclerView.Adapter<TenantPostListAdapter.TenantPostList> implements Filterable {

    private Activity context;
    private int resource;
    private ArrayList<PostAndPostFindPeople> mPostFindPP;
    private ArrayList<PostAndPostFindPeople> mPostFindPPOld;
    private OnItemClickListener onItemClickLisner;

    public TenantPostListAdapter(Activity context, int resource, ArrayList<PostAndPostFindPeople> mPostFindPP) {
        this.context = context;
        this.resource = resource;
        this.mPostFindPP = mPostFindPP;
        this.mPostFindPPOld = mPostFindPP;
    }

    @NonNull
    @Override
    public TenantPostList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new TenantPostList(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantPostList holder, int position) {
        PostAndPostFindPeople postAndPostFindPeople = mPostFindPP.get(position);

        holder.house_name.setText(postAndPostFindPeople.getHouse_name() );
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.price.setText(postAndPostFindPeople.getPrice());
        holder.address.setText(postAndPostFindPeople.getAddress());
        holder.area.setText(postAndPostFindPeople.getArea());
        holder.title.setText(postAndPostFindPeople.getTitlePFPP() );

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
                    ArrayList<PostAndPostFindPeople> list = new ArrayList<>();
                    for (PostAndPostFindPeople postAndPostFindPeople : mPostFindPPOld){
                        if (postAndPostFindPeople.getHouse_name().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(postAndPostFindPeople);
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
                mPostFindPP = (ArrayList<PostAndPostFindPeople>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class TenantPostList extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView house_name,address,area,price,title;
        View.OnClickListener onClickListener;
        CardView item;

        public TenantPostList(@NonNull View itemView) {
            super(itemView);
            house_name = itemView.findViewById(R.id.txt_tplHouse_name);
            address = itemView.findViewById(R.id.txt_tplAddress);
            area = itemView.findViewById(R.id.txt_tplArea);
            price = itemView.findViewById(R.id.txt_tplPrice);
            title = itemView.findViewById(R.id.txt_tplTitle);
            item = itemView.findViewById(R.id.cv_tplCardView);
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

    public void setOnItemClickListener(TenantPostListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickLisner = onItemClickListener;
    }
}
