package com.example.appnhatro.Firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.appnhatro.Activity.BookingActivity;
import com.example.appnhatro.Activity.Detail_Notification_Landlor;
import com.example.appnhatro.Activity.PostActivity;
import com.example.appnhatro.Activity.RepportActivity;
import com.example.appnhatro.Activity.TenantCommentActivity;
import com.example.appnhatro.Adapters.TeantCommentAdapter;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.HistoryTransaction;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    public void getRatingPost(Context context, String idPost, TeantCommentAdapter teantCommentAdapter,ArrayList<Rating> listRating,Rating ratingcomment,String idUser) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rating = firebaseDatabase.getReference("Rating");
        rating.child(idPost).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum = 0;
                int sl = 0;
                ArrayList<Rating> list = new ArrayList<>();
                Rating ratinguser = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Rating rating = dataSnapshot.getValue(Rating.class);
                    sum = sum + Integer.valueOf(rating.getRating() + "");
                    sl = sl + 1;
                    if(rating.getIdUser().equals(idUser)){
                        ratingcomment.setIdPost(rating.getIdPost());
                        ratingcomment.setFeedback(rating.getFeedback());
                        ratingcomment.setDate(rating.getDate());
                        ratingcomment.setRating(rating.getRating());
                        ratingcomment.setIdUser(rating.getIdUser());
                        ratingcomment.setComment(rating.getComment());
                        ratinguser=rating;
                        ((TenantPostDetail) context).setVisibilityWriteComment();
                    }else{
                        list.add(rating);
                    }
                }
                if (sl != 0) {
                    int tong = sum / sl;
                    ((TenantPostDetail) context).setRatingg(tong,String.valueOf(sl));
                } else {
                    ((TenantPostDetail) context).setRatingg(0,String.valueOf(sl));
                }
                if(ratinguser!=null) {
                    listRating.clear();
                    listRating.add(ratinguser);
                    listRating.addAll(list);
                }else {
                    listRating.clear();
                    listRating.addAll(list);
                }
                if(listRating.size()==0){
                    ((TenantPostDetail) context).setLayOutComment(false);
                }else {
                    ((TenantPostDetail) context).setLayOutComment(true);
                }
                teantCommentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addRating(String postId, String userId, String rating) {
        Rating rating1 = new Rating(postId, userId, rating,"Comment tanetn","Comment LandLord","20/11/2020");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Rating");
        databaseReference.child(postId).child(userId).setValue(rating1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
//    public void deleteRating(String userId, String ReportID){
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.ugetReference("Rating");
//        databaseReference.child(ReportID).child(userId).removeValue();
//    }
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
                String[] temp = dsBooking.get(dsBooking.size() - 1).split("BK");
                String id = "";
                if (Integer.parseInt(temp[1]) < 10) {
                    id = "BK0" + (Integer.parseInt(temp[1]) + 1);
                } else {
                    id = "BK01" + (Integer.parseInt(temp[1]) + 1);
                }
                ((BookingActivity) context).IdBooking(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void IdNotificationLandlor(Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsNotification = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsNotification.add(dataSnapshot.getKey());
                }
                String[] temp = dsNotification.get(dsNotification.size() - 1).split("TB_");
                String id = "";
                if (Integer.parseInt(temp[1]) < 10) {
                    id = "TB_0" + (Integer.parseInt(temp[1]) + 1);
                } else {
                    id = "TB_0" + (Integer.parseInt(temp[1]) + 1);
                }
                ((Detail_Notification_Landlor) context).IdNotification(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void docAnh(Uri filePath, Context context, String tenHinh){
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/post/"+ tenHinh+".jpg");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    public void addAndUpdateComment(Context context,Rating rating){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Rating").child(rating.getIdPost()).child(rating.getIdUser()).setValue(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ((TenantCommentActivity) context).finish();
            }
        });
    }
    public void getNameUserInComment(Context context,String idUser){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("user").child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user User = snapshot.getValue(user.class);
                ((TenantCommentActivity) context).setNameUser(User.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void deleteCommentForUser(Rating rating){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Rating").child(rating.getIdPost()).child(rating.getIdUser()).removeValue();
    }
    public void checkHistory(Context context, String idPost,String idUser){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("HistoryTransaction").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean check = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HistoryTransaction historyTransaction = dataSnapshot.getValue(HistoryTransaction.class);
                    if(historyTransaction.getId_user().equals(idUser)&&historyTransaction.getPost().equals(idPost)){
                        check = true;
                    }
                }
                if(check==true){
                    ((TenantPostDetail) context).gotoCommentActivity();
                }else {
                    ((TenantPostDetail) context).dialogg();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void readOnePostOGhep(Context context, String id) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post_Oghep").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void docPostLienQuanPost(ArrayList<Post> list, MyRecyclerViewAdapter myRecyclerViewAdapter) {
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
    public void docPostLienQuanOGhep(ArrayList<Post> list, MyRecyclerViewAdapter myRecyclerViewAdapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post_Oghep").addValueEventListener(new ValueEventListener() {
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
}
