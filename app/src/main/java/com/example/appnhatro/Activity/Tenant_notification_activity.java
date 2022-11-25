package com.example.appnhatro.Activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.Tenant_Notification_Adapter;
import com.example.appnhatro.Firebase.FirebaseTenantNofication;
import com.example.appnhatro.Models.Notificationbooking;
import com.example.appnhatro.R;
import com.example.appnhatro.ViewHolderImageHome;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Tenant_notification_activity extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private Tenant_Notification_Adapter tenant_notification_adapter;
    private ArrayList<Notificationbooking> notificationbookings = new ArrayList<>();
    SearchView sv_tpr;
    String iduser;
    SharedPreferences sharedPreferences;
    FirebaseTenantNofication firebaseTenantNofication = new FirebaseTenantNofication();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_notification_list);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        iduser = sharedPreferences.getString("idUser", "");
        ListPost();
    }

    public void ListPost() {

        RecyclerView recyclerView = findViewById(R.id.listnotifilecation);
        tenant_notification_adapter = new Tenant_Notification_Adapter(this, R.layout.tenant_post_notification_item, notificationbookings);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        firebaseTenantNofication.readPostFindPeople(notificationbookings, tenant_notification_adapter, iduser);
        Log.d("TAG", "onDataChange: "+iduser);
        recyclerView.setAdapter(tenant_notification_adapter);
        tenant_notification_adapter.setOnItemClickListener(new Tenant_Notification_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
            }
        });
        sv_tpr = findViewById(R.id.timkiem);
        sv_tpr.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                tenant_notification_adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tenant_notification_adapter.getFilter().filter(s);
                return false;
            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public static final void setAvatar(ImageView imageView, String avatar) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("user/"+avatar);
        try {
            final File file = File.createTempFile("ảnh", ".jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Notify", "Load Image Fail");

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
