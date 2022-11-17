package com.example.appnhatro.Firebase;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appnhatro.Activity.LandLordAddNewPost;
import com.example.appnhatro.Activity.LandLordFeedBack;
import com.example.appnhatro.Activity.LandLordPostDetailActivity;
import com.example.appnhatro.Activity.LandLordUpdatePostActivity;
import com.example.appnhatro.Adapters.LandLordCommentAdapter;
import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
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

public class FireBaseLandLord {
    private ConverImage converImage = new ConverImage();
    public FireBaseLandLord() {
    }

    public void readListPostFromUser(LandLordHomeListAdapter landLordHomeListAdapter, ArrayList<Post> list, String idLandLord) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Post> data = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post.getUserID().equals(idLandLord)) {
                        data.add(post);
                    }
                }
                list.clear();
                list.addAll(data);
                landLordHomeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addUpdatePost(Context context, Post post, Uri uri) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post");
        databaseReference.child(post.getId()).setValue(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        converImage.docAnhUpdatePost(uri,context,post.getImage());
                    }
                });
    }

    public void addUpdatePostNoImage(Context context, Post post) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post");
        databaseReference.child(post.getId()).setValue(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ((LandLordUpdatePostActivity) context).finish();
                    }
                });
    }

    public void addNewPost(Context context, Post post, Uri uri) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post");
        databaseReference.child(post.getId()).setValue(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        converImage.docAnhAddNewPost(uri,context,post.getImage());
                    }
                });
    }

    public void getId(Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsPost = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsPost.add(dataSnapshot.getKey());
                }
                String[] temp = dsPost.get(dsPost.size() - 1).split("P");
                String id = "";
                if (Integer.parseInt(temp[1]) < 9) {
                    id = "P0" + (Integer.parseInt(temp[1]) + 1);
                } else {
                    id = "P" + (Integer.parseInt(temp[1]) + 1);
                }
                ((LandLordAddNewPost) context).setId(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readOnePost(Context context, String id) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference rating = firebaseDatabase.getReference("Rating");
        databaseReference.child("Post").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post data = snapshot.getValue(Post.class);
                BitMap bitMap = new BitMap(data.getImage(), null);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/" + bitMap.getTenHinh());
                try {
                    final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
                    storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            ((LandLordPostDetailActivity) context).setDuLieu(data, bitMap.getHinh());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                rating.child(data.getId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int sum = 0;
                        int sl = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Rating rating = dataSnapshot.getValue(Rating.class);
                            Log.d("test", rating.getRating());
                            sum = sum + Integer.valueOf(rating.getRating() + "");
                            sl = sl + 1;
                        }
                        if (sl != 0) {
                            int tong = sum / sl;
                            ((LandLordPostDetailActivity) context).setRating(tong);
                        } else {
                            ((LandLordPostDetailActivity) context).setRating(0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getPost(Context context, String idPost) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post");
        databaseReference.child(idPost).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post data = snapshot.getValue(Post.class);
                BitMap bitMap = new BitMap(data.getImage(), null);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/" + bitMap.getTenHinh());
                try {
                    final File file = File.createTempFile(bitMap.getTenHinh().substring(0, bitMap.getTenHinh().length() - 4), "jpg");
                    storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            ((LandLordUpdatePostActivity) context).setDuLieu(data, bitMap.getHinh());
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

    public void deletePost(String idPost) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").child(idPost).removeValue();
    }

    public void readOnePostLandLord(Context context, String idPost, ArrayList<Rating> listRating, LandLordCommentAdapter commentAdapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference rating = firebaseDatabase.getReference("Rating");
        DatabaseReference like = firebaseDatabase.getReference("Like");
        databaseReference.child("Post").child(idPost).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post data = snapshot.getValue(Post.class);
                BitMap bitMap = new BitMap(data.getImage(), null);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/" + bitMap.getTenHinh());
                try {
                    final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
                    storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            ((LandLordPostDetailActivity) context).setDuLieu(data, bitMap.getHinh());


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
                                    like.child(idPost).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            ((LandLordPostDetailActivity) context).setLike(String.valueOf(snapshot.getChildrenCount()));
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
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
                            ((LandLordPostDetailActivity) context).setRating(tong);
                        } else {
                            ((LandLordPostDetailActivity) context).setRating(0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getNameUserFeedback(Context context,String idUser){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    user User = dataSnapshot.getValue(user.class);
                    if(User.getId().equals(idUser)){
                        ((LandLordFeedBack) context).setNameUser(User.getName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addAndUpdateFeedBackLandLord(Context context,Rating rating){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Rating").child(rating.getIdPost()).child(rating.getIdUser()).setValue(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ((LandLordFeedBack) context).finish();
            }
        });
    }
    public void deleteFeedBack(Rating rating){
        rating.setFeedback("");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Rating").child(rating.getIdPost()).child(rating.getIdUser()).setValue(rating);
    }
}
