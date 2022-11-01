package com.example.appnhatro.Firebase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appnhatro.HomeTenantActivity;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.ImagePost;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.MyRecyclerViewAdapter;
import com.example.appnhatro.Room;
import com.example.appnhatro.TenantPostDetail;
import com.example.appnhatro.TenantPostDetailAdapter;
import com.example.appnhatro.tool.ConverImage;
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

public class FireBaseThueTro {
    public FireBaseThueTro() {
    }
    private ConverImage converImage = new ConverImage();
    public void docListPost( ArrayList<Post> list,MyRecyclerViewAdapter myRecyclerViewAdapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post student = dataSnapshot.getValue(Post.class);
                    list.add(student);
                }
                myRecyclerViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void readDataItem(int position,ArrayList<Post> list,Context context){
        Post data = list.get(position);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Post").child(data.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent intent = new Intent(context, TenantPostDetail.class);
                        intent.putExtra("it_id", data.getId());
                        intent.putExtra("it_idLogin", "KH02");
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public void readOnePost(Context context, String id){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post data = snapshot.getValue(Post.class);
                BitMap bitMap = new BitMap(data.getImage(),null);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(bitMap.getTenHinh());
                try {
                    final File file= File.createTempFile(bitMap.getTenHinh().substring(0,bitMap.getTenHinh().length()-4),"jpg");
                    Log.d("test", file+"");
                    storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            Log.d("test", bitMap.getHinh()+"");
                            ((TenantPostDetail) context).setDuLieu(data,bitMap.getHinh());
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference();
                            databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        user User = dataSnapshot.getValue(user.class);
                                        if (User.getId().equals(data.getUserID())) {
                                            ((TenantPostDetail) context).setName(User);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

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
}
