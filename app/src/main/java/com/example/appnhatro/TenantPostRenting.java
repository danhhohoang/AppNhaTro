package com.example.appnhatro;

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

import com.example.appnhatro.Firebase.FireBasePostRenting;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.Models.user;
import com.google.android.gms.tasks.OnFailureListener;
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

public class TenantPostRenting extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private TenantPostRentingAdapter tenantPostRentingAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    ImageView back, avt;
    SearchView sv_tpr;
    FireBasePostRenting fireBasePostRenting = new FireBasePostRenting();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_renting);
        ListPost();
        getuser();
    }

    public void ListPost(){
        RecyclerView recyclerView = findViewById(R.id.rcv_tpr);
        tenantPostRentingAdapter =  new TenantPostRentingAdapter(this, R.layout.layout_item_tenant_post_renting,posts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBasePostRenting.readPostFindPeople(posts,tenantPostRentingAdapter,"KH02");
        recyclerView.setAdapter(tenantPostRentingAdapter);
        back = findViewById(R.id.btn_back);
        avt = findViewById(R.id.avt);
        tenantPostRentingAdapter.setOnItemClickListener(new TenantPostRentingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBasePostRenting.readDataItem(position,posts,TenantPostRenting.this);
            }
        });
        sv_tpr = findViewById(R.id.sv_tpr);
        sv_tpr.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                tenantPostRentingAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tenantPostRentingAdapter.getFilter().filter(s);
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
    public void getuser(){
        DatabaseReference databaseReferencepost;
        databaseReferencepost = FirebaseDatabase.getInstance().getReference("Post");
        DatabaseReference databaseReferenceuser;
        databaseReferenceuser = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference databaseReferencets;
        databaseReferencets = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
        databaseReferencets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
                    if (transactionModel.getId_user().equals("KH02")){
                        databaseReferencepost.child("Post").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    Post post = dataSnapshot.getValue(Post.class);
                                    if (post.getId().equals(transactionModel.getPost())){
                                        databaseReferenceuser.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                    user User = dataSnapshot.getValue(user.class);
                                                    if (User.getId().equals(post.getUserID())){

                                                        setAvatar(avt, User.getAvatar());
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                                tenantPostRentingAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static final void setAvatar(ImageView imageView, String avatar) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + avatar);
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
