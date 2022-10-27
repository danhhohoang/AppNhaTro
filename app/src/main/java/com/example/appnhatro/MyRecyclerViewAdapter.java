package com.example.appnhatro;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{
    private Activity context;
    private int layout;
    private ArrayList<Room> personList;

    public MyRecyclerViewAdapter(Activity context, int layoutId, ArrayList<Room> personList) {
        this.context = context;
        this.layout = layoutId;
        this.personList = personList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_horizontal,parent,false);

        return new MyViewHolder(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Room room = personList.get(position);
       //holder.hinhAnh.setBackgroundResource(R.drawable.house);

        if (room.getHinhAnh().equals("1")) {
            holder.hinhAnh.setImageResource(R.drawable.house);
        } else if (room.getHinhAnh().equals("2")) {
            holder.hinhAnh.setImageResource(R.drawable.background);
        } else {
            holder.hinhAnh.setImageResource(R.drawable.house);
        }
        holder.tenPhong.setText(room.getTenPhong());
        holder.Gia.setText("" + room.getGia() +"");
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    // View holder definition
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView hinhAnh;
        TextView tenPhong;
        TextView Gia;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhAnh = itemView.findViewById(R.id.imgRoom);
            tenPhong = itemView.findViewById(R.id.txtTenPhong);
            Gia = itemView.findViewById(R.id.txtGia);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
