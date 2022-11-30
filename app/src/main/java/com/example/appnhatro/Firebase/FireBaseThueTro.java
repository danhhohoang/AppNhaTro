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
import com.example.appnhatro.Adapters.ImagePostDetailAdapter;
import com.example.appnhatro.Adapters.TeantCommentAdapter;
import com.example.appnhatro.Adapters.TenantHomeListPostAdapter;
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
import java.util.concurrent.ThreadLocalRandom;

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

    public void readOnePost(Context context, String id, ArrayList<String> imagePost, ImagePostDetailAdapter imagePostDetailAdapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post data = snapshot.getValue(Post.class);
                imagePost.clear();
                imagePost.add(data.getImage());
                imagePost.add(data.getImage1());
                imagePost.add(data.getImage2());
                ((TenantPostDetail) context).setDuLieu(data);
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
                                            ((TenantPostDetail) context).setName(User, bitMap.getHinh());
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
                imagePostDetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getRatingPost(Context context, String idPost, TeantCommentAdapter teantCommentAdapter, ArrayList<Rating> listRating, Rating ratingcomment, String idUser) {
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
                    if (rating.getIdUser().equals(idUser)) {
                        ratingcomment.setIdPost(rating.getIdPost());
                        ratingcomment.setFeedback(rating.getFeedback());
                        ratingcomment.setDate(rating.getDate());
                        ratingcomment.setRating(rating.getRating());
                        ratingcomment.setIdUser(rating.getIdUser());
                        ratingcomment.setComment(rating.getComment());
                        ratinguser = rating;
                        ((TenantPostDetail) context).setVisibilityWriteComment();
                    } else {
                        list.add(rating);
                    }
                }
                if (sl != 0) {
                    int tong = sum / sl;
                    ((TenantPostDetail) context).setRatingg(tong, String.valueOf(sl));
                } else {
                    ((TenantPostDetail) context).setRatingg(0, String.valueOf(sl));
                }
                if (ratinguser != null) {
                    listRating.clear();
                    listRating.add(ratinguser);
                    listRating.addAll(list);
                } else {
                    listRating.clear();
                    listRating.addAll(list);
                }
                if (listRating.size() == 0) {
                    ((TenantPostDetail) context).setLayOutComment(false);
                } else {
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
        Rating rating1 = new Rating(postId, userId, rating, "Comment tanetn", "Comment LandLord", "20/11/2020");
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
    public void autoid(Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Report").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsPostrp = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsPostrp.add(dataSnapshot.getKey());
                }
                String id = "";
                if (dsPostrp.size() == 0) {
                    id = "RP01";
                }else {
                        String[] temp = dsPostrp.get(dsPostrp.size() - 1).split("RP");

                        if (Integer.parseInt(temp[1]) < 9) {
                            id = "RP0" + (Integer.parseInt(temp[1]) + 1);
                        } else {
                            id = "RP" + (Integer.parseInt(temp[1]) + 1);
                        }
                }

                ((RepportActivity) context).SetID(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void IdPost(Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post_Oghep").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsPost = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsPost.add(dataSnapshot.getKey());
                }
                String id = "";
                if (dsPost.size() == 0){
                    id = "P_tenant_01";
                }
                else {
                    String[] temp = dsPost.get(dsPost.size() - 1).split("P_tenant_");
                    if (Integer.parseInt(temp[1]) < 9) {
                        id = "P_tenant_0" + (Integer.parseInt(temp[1]) + 1);
                    } else {
                        id = "P_tenant_" + (Integer.parseInt(temp[1]) + 1);
                    }
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
                String id = "";
                if (dsBooking.size() == 0){
                    id = "BK01";
                }else {
                    String[] temp = dsBooking.get(dsBooking.size() - 1).split("BK");
                    if (Integer.parseInt(temp[1]) < 10) {
                        id = "BK0" + (Integer.parseInt(temp[1]) + 1);
                    } else {
                        id = "BK01" + (Integer.parseInt(temp[1]) + 1);
                    }
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
                String id = "";
                if (dsNotification.size() == 0){
                    id = "TB_01";
                }else {
                    String[] temp = dsNotification.get(dsNotification.size() - 1).split("TB_");

                    if (Integer.parseInt(temp[1]) < 9) {
                        id = "TB_0" + (Integer.parseInt(temp[1]) + 1);
                    } else {
                        id = "TB_" + (Integer.parseInt(temp[1]) + 1);
                    }
                }
                ((Detail_Notification_Landlor) context).IdNotification(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void docAnh(Uri filePath, Context context, String tenHinh) {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh + ".jpg");
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
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    public void addAndUpdateComment(Context context, Rating rating) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Rating").child(rating.getIdPost()).child(rating.getIdUser()).setValue(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ((TenantCommentActivity) context).finish();
            }
        });
    }

    public void getNameUserInComment(Context context, String idUser) {
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

    public void deleteCommentForUser(Rating rating) {
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
                    if (historyTransaction.getId_user().equals(idUser) && !historyTransaction.getPost().equals(idPost)&&!historyTransaction.getStatus().equals("0")) {
                        check = true;
                    }
                }
                if (check == true) {
                    ((TenantPostDetail) context).gotoCommentActivity();
                } else {
                    ((TenantPostDetail) context).dialogg();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readOnePostOGhep(Context context, String id, ArrayList<String> imagePost,ImagePostDetailAdapter imagePostDetailAdapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post_Oghep").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post data = snapshot.getValue(Post.class);
                imagePost.clear();
                imagePost.add(data.getImage());
                imagePost.add(data.getImage1());
                imagePost.add(data.getImage2());
                ((TenantPostDetail) context).setDuLieu(data);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                databaseReference.child("user").addValueEventListener(new ValueEventListener() {
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
                                            ((TenantPostDetail) context).setName(User, bitMap.getHinh());
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                imagePostDetailAdapter.notifyDataSetChanged();
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
                ArrayList<Post> getposts = new ArrayList<>();
                if (posts.size() < 5) {
                    getposts.addAll(posts);
                } else {
                    do {
                        int random = ThreadLocalRandom.current().nextInt(0, posts.size());
                        if (getposts.size() == 0) {
                            getposts.add(posts.get(random));
                            posts.remove(posts.get(random));
                        } else {
                            Boolean check = false;
                            for (Post key : getposts) {
                                if (key.getId().equals(posts.get(random).getId())) {
                                    check = true;
                                }
                            }
                            if (!check) {
                                getposts.add(posts.get(random));
                                posts.remove(posts.get(random));
                            }
                        }
                    } while (getposts.size() < 4);
                }
                list.clear();
                list.addAll(getposts);
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
                ArrayList<Post> getposts = new ArrayList<>();
                if (posts.size() < 5) {
                    getposts.addAll(posts);
                } else {
                    do {
                        int random = ThreadLocalRandom.current().nextInt(0, posts.size());
                        if (getposts.size() == 0) {
                            getposts.add(posts.get(random));
                            posts.remove(posts.get(random));
                        } else {
                            Boolean check = false;
                            for (Post key : getposts) {
                                if (key.getId().equals(posts.get(random).getId())) {
                                    check = true;
                                }
                            }
                            if (!check) {
                                getposts.add(posts.get(random));
                                posts.remove(posts.get(random));
                            }
                        }
                    } while (getposts.size() < 4);
                }
                list.clear();
                list.addAll(getposts);
                myRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getPostRatingCao(ArrayList<Post> listPost, TenantHomeListPostAdapter myRecyclerViewAdapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rating = firebaseDatabase.getReference("Rating");
        rating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> topPhong = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Integer sum = 0;
                    Integer sl = 0;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Rating rating1 = dataSnapshot1.getValue(Rating.class);
                        sum = sum + Integer.valueOf(rating1.getRating() + "");
                        sl = sl + 1;
                    }
                    Integer trungBinh = sum / sl;
                    if (trungBinh > 3) {
                        topPhong.add(dataSnapshot.getKey());
                    }
                }
                ArrayList<String> getPost = new ArrayList<>();
                if (topPhong.size() < 5) {
                    getPost.addAll(topPhong);
                } else {
                    do {
                        int random = ThreadLocalRandom.current().nextInt(0, topPhong.size());
                        if (getPost.size() == 0) {
                            getPost.add(topPhong.get(random));
                            topPhong.remove(topPhong.get(random));
                        } else {
                            Boolean check = false;
                            for (String key : getPost) {
                                if (key.equals(topPhong.get(random))) {
                                    check = true;
                                }
                            }
                            if (!check) {
                                getPost.add(topPhong.get(random));
                                topPhong.remove(topPhong.get(random));
                            }
                        }
                    } while (getPost.size() < 5);
                }
                DatabaseReference dbPost = firebaseDatabase.getReference("Post");
                dbPost.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Post> listPostRatingHigh = new ArrayList<>();
                        for (String idPost : getPost) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Post post = dataSnapshot.getValue(Post.class);
                                if (post.getId().equals(idPost)) {
                                    listPostRatingHigh.add(post);
                                    break;
                                }
                            }
                        }
                        listPost.clear();
                        listPost.addAll(listPostRatingHigh);
                        myRecyclerViewAdapter.notifyDataSetChanged();
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

    public void check(Context context, String idPost, String idUser) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("HistoryTransaction").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean check = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HistoryTransaction historyTransaction = dataSnapshot.getValue(HistoryTransaction.class);
                    if (historyTransaction.getId_user().equals(idUser) && historyTransaction.getPost().equals(idPost)) {
                        check = true;
                    }
                }
                if (check == true) {
                    ((TenantPostDetail) context).gotoRepostActivity();
                } else {
                    ((TenantPostDetail) context).thongbao();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addNewPosttn(Context context, Post post, Uri[] uri) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post_Oghep");
        databaseReference.child(post.getId()).setValue(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        converImage.docAnhAddNewPostOghep(uri, context, post.getImage(), post.getImage1(), post.getImage2());
                    }
                });
    }
}
