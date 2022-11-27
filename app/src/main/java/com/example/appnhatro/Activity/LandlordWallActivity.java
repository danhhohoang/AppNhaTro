package com.example.appnhatro.Activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.TenantSearchAdapter;
import com.example.appnhatro.Firebase.FireBaseTenantSearch;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.example.appnhatro.TenantPostDetail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LandlordWallActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtName,
            txtPhone;
    ArrayList<Post> dataList = new ArrayList<>();
    ArrayList<user> landlordInfo = new ArrayList<>();
    //gọi lại phần chức năng của tenant search này vì nó giống nhau, không cần phải tạo class mới
    FireBaseTenantSearch fireBaseTenantSearch = new FireBaseTenantSearch();
    TenantSearchAdapter tenantSearchAdapter;
    String mID; //id cua chu thue bat duoc tu activity khac truyen qua
    ShapeableImageView shapeableImageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_wall_layout);
        setControl();

        //Get intent
        Intent intent = getIntent();
        //mID ="KH01";
        mID = intent.getStringExtra("KEY_ID");

        //
        tenantSearchAdapter = new TenantSearchAdapter(this,R.layout.item_tenant_search,dataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //
        fireBaseTenantSearch.reTurnPostByLandlord(dataList,tenantSearchAdapter,mID);
        fireBaseTenantSearch.reTurnLandlordInfoByID(this,landlordInfo,mID);


        //set adapter
        recyclerView.setAdapter(tenantSearchAdapter);
        setEvent();
    }

    public void loadLandlordInfo() {
        if(landlordInfo.size() > 0) {
            user newUser = landlordInfo.get(0);
            txtName.setText(newUser.getName());
            txtPhone.setText("Số điện thoại: "+newUser.getPhone());

            //set tam thoi
            shapeableImageView.setImageResource(R.drawable.ic_user);
            try {
                BitMap bitMap = new BitMap(newUser.getAvatar(), null);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + bitMap.getTenHinh());

                final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
                storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        shapeableImageView.setImageBitmap(bitMap.getHinh());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setEvent() {

        tenantSearchAdapter.setOnItemClickListener(new TenantSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                Intent intentPost = new Intent(LandlordWallActivity.this, TenantPostDetail.class);
                intentPost.putExtra("it_id",dataList.get(position).getId());
                startActivity(intentPost);
            }
        });

    }

    private void setControl() {
        recyclerView = findViewById(R.id.recycler_landlord_wall);
        txtName = findViewById(R.id.txt_landlord_wall_name);
        txtPhone = findViewById(R.id.txt_landlord_wall_phone);
        shapeableImageView =findViewById(R.id.img_landlord_wall_avatar);
    }
}
