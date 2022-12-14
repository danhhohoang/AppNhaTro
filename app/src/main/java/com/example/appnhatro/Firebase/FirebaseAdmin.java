package com.example.appnhatro.Firebase;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.appnhatro.Activity.AdminPostDetailActivity;
import com.example.appnhatro.Activity.LandLordPostDetailActivity;
import com.example.appnhatro.Adapters.AdminCommentAdapter;
import com.example.appnhatro.Adapters.AdminHomeAdapter;
import com.example.appnhatro.Adapters.AdminListPostAdapter;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.HistoryTransaction;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.TenantPostDetail;
import com.example.appnhatro.item.Rating;
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

public class FirebaseAdmin {
    public void readAllListPost(AdminListPostAdapter adminListPostAdapter, ArrayList<Post> list) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Post> data = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    data.add(post);
                }
                list.clear();
                list.addAll(data);
                adminListPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readOnePostAdmin(Context context, String idPost, ArrayList<Rating> listRating, AdminCommentAdapter commentAdapter, ArrayList<String> imagePost) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference rating = firebaseDatabase.getReference("Rating");
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post data = dataSnapshot.getValue(Post.class);
                    if (data.getId().equals(idPost)) {
                        imagePost.clear();
                        imagePost.add(data.getImage());
                        imagePost.add(data.getImage1());
                        imagePost.add(data.getImage2());
                        DatabaseReference databaseReferences = firebaseDatabase.getReference();
                        databaseReferences.child("user").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    user User = dataSnapshot.getValue(user.class);
                                    if (User.getId().equals(data.getUserID())) {
                                        BitMap bitMap = new BitMap(User.getAvatar(), null);
                                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + bitMap.getTenHinh());
                                        try {
                                            final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
                                            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                                    ((AdminPostDetailActivity) context).setDuLieu(data, bitMap.getHinh());
                                                }
                                            });
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        rating.child(idPost).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<Rating> ratings = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Rating data = dataSnapshot.getValue(Rating.class);
                                    ratings.add(data);
                                }
                                listRating.clear();
                                listRating.addAll(ratings);
                                commentAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        rating.child(data.getId()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int sum = 0;
                                int sl = 0;
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Rating rating = dataSnapshot.getValue(Rating.class);
                                    sum = sum + Integer.valueOf(rating.getRating() + "");
                                    sl = sl + 1;
                                }
                                if (sl != 0) {
                                    int tong = sum / sl;
                                    ((AdminPostDetailActivity) context).setRating(tong);
                                } else {
                                    ((AdminPostDetailActivity) context).setRating(0);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readItemInHome(AdminHomeAdapter adminHomeAdapter, ArrayList<HistoryTransaction> historyTransactions) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("HistoryTransaction").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<HistoryTransaction> data = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        HistoryTransaction post = dataSnapshot.getValue(HistoryTransaction.class);
                        if (post.getStatus().equals("1")) {
                            data.add(post);
                        }
                    }
                    historyTransactions.clear();
                    historyTransactions.addAll(data);
                    adminHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void xacNhanThuTien(HistoryTransaction historyTransaction) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        historyTransaction.setStatus("2");
        databaseReference.child("HistoryTransaction").child(historyTransaction.getId()).setValue(historyTransaction).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    public void deletePost(String idPost, Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("HistoryTransaction").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean check = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HistoryTransaction data = dataSnapshot.getValue(HistoryTransaction.class);
                    if (data.getPost().equals(idPost)) {
                        check = true;
                        break;
                    }
                }
                if (check == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Th??ng b??o");
                    builder.setMessage("Kh??ng th??? xo?? b??i ????ng");
                    builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Th??ng b??o");
                    builder.setMessage("B???n ch???c ch???n mu???n xo?? b??i");
                    builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseReference.child("Post").child(idPost).removeValue();
                            databaseReference.child("Like").child(idPost).removeValue();
                            ((LandLordPostDetailActivity) context).finish();
                        }
                    });
                    builder.setNegativeButton("Kh??ng ?????ng ??", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void readItemHistoryTransaction(AdminHomeAdapter adminHomeAdapter, ArrayList<HistoryTransaction> historyTransactions) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("HistoryTransaction").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<HistoryTransaction> data = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        HistoryTransaction post = dataSnapshot.getValue(HistoryTransaction.class);
                        if (post.getStatus().equals("2")) {
                            data.add(post);
                        }
                    }
                    historyTransactions.clear();
                    historyTransactions.addAll(data);
                    adminHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
