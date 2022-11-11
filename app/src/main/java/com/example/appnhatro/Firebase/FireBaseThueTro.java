package com.example.appnhatro.Firebase;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appnhatro.Activity.BookingActivity;
import com.example.appnhatro.Activity.PostActivity;
import com.example.appnhatro.Activity.RepportActivity;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.MyRecyclerViewAdapter;
import com.example.appnhatro.TenantPostDetail;
import com.example.appnhatro.item.Rating;
import com.example.appnhatro.tool.ConverImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    public void docListPost(ArrayList<Post> list, MyRecyclerViewAdapter myRecyclerViewAdapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Post> posts = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post student = dataSnapshot.getValue(Post.class);
                    posts.add(student);
                }
                list.clear();
                list.addAll(posts);
                myRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readDataItem(int position, ArrayList<Post> list, Context context) {
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

    public void readOnePost(Context context, String id) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post data = snapshot.getValue(Post.class);
                BitMap bitMap = new BitMap(data.getImage(), null);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/"+bitMap.getTenHinh());
                try {
                    final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
                    storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            ((TenantPostDetail) context).setDuLieu(data, bitMap.getHinh());
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

    public void getRatingPost(Context context, String idPost, String idUser) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Rating").child(idPost).child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Rating rating = snapshot.getValue(Rating.class);
                    ((TenantPostDetail) context).setRating(Integer.valueOf(rating.getRating()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addRating(String postId, String userId, String rating) {
        Rating rating1 = new Rating(postId, userId, rating);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Rating");
        databaseReference.child(postId).child(userId).setValue(rating1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
    public void deleteRating(String userId, String ReportID){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Rating");
        databaseReference.child(ReportID).child(userId).removeValue();
    }
    public void autoid(Context context){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Report").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsPost = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsPost.add(dataSnapshot.getKey());
                }
                String[] temp = dsPost.get(dsPost.size() - 1).split("RP");
                String id="";
                if(Integer.parseInt(temp[1]) < 10){
                    id = "RP0" + (Integer.parseInt(temp[1]) + 1);
                }else {
                    id = "RP" + (Integer.parseInt(temp[1]) + 1);
                }
                Log.d("test", id);
                ((RepportActivity) context).SetID(id);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void IdPost(Context context){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post_Oghep").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsPost = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dsPost.add(dataSnapshot.getKey());
                }
                String[] temp = dsPost.get(dsPost.size()-1).split("P_tenant_");
                String id="";
                if (Integer.parseInt(temp[1]) < 10){
                    id = "P_tenant_0" + (Integer.parseInt(temp[1]) + 1);
                }else {
                    id = "P_tenant_0" + (Integer.parseInt(temp[1]) + 1);
                }
                Log.d("test", id);
                ((PostActivity) context).setID(id);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void IdBooking(Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("booking").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsBooking = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsBooking.add(dataSnapshot.getKey());
                }
                String[] temp = dsBooking.get(dsBooking.size() - 1).split("BK_");
                String id = "";
                if (Integer.parseInt(temp[1]) < 10) {
                    id = "BK_0" + (Integer.parseInt(temp[1]) + 1);
                } else {
                    id = "BK_01" + (Integer.parseInt(temp[1]) + 1);
                }
                Log.d("test", id);
                ((BookingActivity) context).IdBooking(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
