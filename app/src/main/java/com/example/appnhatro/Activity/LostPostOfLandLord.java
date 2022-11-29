package com.example.appnhatro.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.AdminLandlordListAdapter;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class LostPostOfLandLord extends AppCompatActivity {
    RecyclerView rc;
    ArrayList<Post> list;
    ImageView back;
    SearchView sv_alrSearch;
    AdminLandlordListAdapter adminLandlordListAdapter;
    DatabaseReference databaseReferencePost;
    DatabaseReference databaseReferencePostOghep;
    CircleImageView avt;
    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_lanlord_room_list);

        setControl();
        getAvatar();
        onRead("");
        id = getIntent().getStringExtra("idpost");

        rc = findViewById(R.id.rcv_alr);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adminLandlordListAdapter = new AdminLandlordListAdapter(this,list);
        rc.setAdapter(adminLandlordListAdapter);
    }

    private void setControl() {
        avt = findViewById(R.id.ci_alrAvt);
        sv_alrSearch = findViewById(R.id.sv_alr);
        back = findViewById(R.id.iv_alrBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sv_alrSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                list.clear();
                onRead(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                list.clear();
                onRead(s);
                return false;
            }
        });
    }


    public void onRead(String keyword){
        databaseReferencePost = FirebaseDatabase.getInstance().getReference("Post");
        databaseReferencePostOghep = FirebaseDatabase.getInstance().getReference("Post_Oghep");
        databaseReferencePost.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                if (post.getUserID().equals(id)){
                    if(post!=null){
                        if(post.getHouse_name().toLowerCase().contains(keyword.toLowerCase())){
                            list.add(post);
                        }
                        adminLandlordListAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< list.size();i++ ){
                    if (post.getId() == list.get(i).getId()){
                        list.set(i,post);
                        adminLandlordListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< list.size();i++ ){
                    if (post.getId() == list.get(i).getId()){
                        list.remove(i);
                        adminLandlordListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< list.size();i++ ){
                    if (post.getId() == list.get(i).getId()){
                        list.remove(i);
                        adminLandlordListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseReferencePostOghep.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                if (post.getUserID().equals(id)){
                    if(post!=null){
                        if(post.getHouse_name().toLowerCase().contains(keyword.toLowerCase())){
                            list.add(post);
                        }
                        adminLandlordListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< list.size();i++ ){
                    if (post.getId() == list.get(i).getId()){
                        list.set(i,post);
                        adminLandlordListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< list.size();i++ ){
                    if (post.getId() == list.get(i).getId()){
                        list.remove(i);
                        adminLandlordListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< list.size();i++ ){
                    if (post.getId() == list.get(i).getId()){
                        list.remove(i);
                        adminLandlordListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static final void setAvatarUser(ImageView imageView, String avatar) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/"+avatar);
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

    public void getAvatar(){
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    user User = dataSnapshot.getValue(user.class);
                    if (User.getId().equals("KH02")){
                        setAvatarUser(avt,User.getAvatar());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
