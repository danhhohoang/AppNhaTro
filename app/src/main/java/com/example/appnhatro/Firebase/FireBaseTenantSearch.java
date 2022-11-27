package com.example.appnhatro.Firebase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appnhatro.Activity.LandlordWallActivity;
import com.example.appnhatro.Adapters.TenantSearchAdapter;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.TenantSearchActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseTenantSearch {
    public FireBaseTenantSearch() {
    }

    public void reTurnLandlordInfoByID(Activity activity, ArrayList<user> userList, String landlordID) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user userLandlord = dataSnapshot.getValue(user.class);

                    if (userLandlord.getId().equals(landlordID)) {
                        userList.add(userLandlord);
                        break;
                    }

                }
                ((LandlordWallActivity) activity).loadLandlordInfo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void reTurnPostByLandlord(ArrayList<Post> postList, TenantSearchAdapter tenantSearchAdapter, String landLordID) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post.getUserID().equals(landLordID)) {
                        postList.add(post);
                    }

                    tenantSearchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void reTurnPostAndPostFindPeopleByFilter(
            ArrayList<Post> mergeList
            , TenantSearchAdapter tenantSearchAdapter
            , String spnDistrict
            , int spnDistrictPos
            , int spnTypePos
            , String searchText
            , int minPrice
            , int maxPrice
            , int minArea
            , int maxArea) {

        if (spnTypePos == 0) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
            DatabaseReference postReference = firebaseDatabase.getReference("Post");
            DatabaseReference Post_Oghep = firebaseDatabase2.getReference();
            postReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);
                        assert post != null;
                        if (spnDistrictPos == 0) {
                            if (Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.valueOf(post.getPrice()) >= minPrice && Integer.valueOf(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchText)) {
                                mergeList.add(post);
                            }

                        } else {
                            if (post.getAddress_district().equals(spnDistrict) && Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchText)) {
                                mergeList.add(post);
                            }
                        }
                    }
                    tenantSearchAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("ERROR", "Detail: " + error.getMessage());
                }
            });
            Post_Oghep.child("Post_Oghep").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);
                        if (spnDistrictPos == 0) {
                            assert post != null;
                            if (Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchText)) {
                                mergeList.add(post);
                            }
                        } else {
                            assert post != null;
                            if (post.getAddress_district().equals(spnDistrict) && Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchText)) {
                                mergeList.add(post);
                            }
                        }
                    }
                    tenantSearchAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("ERROR", "Detail: " + error.getMessage());
                }
            });
        } else if (spnTypePos == 1) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference postReference = firebaseDatabase.getReference("Post");
            postReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);
                        assert post != null;
                        if (spnDistrictPos == 0) {
                            if (Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.valueOf(post.getPrice()) >= minPrice && Integer.valueOf(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchText)) {
                                mergeList.add(post);
                            }

                        } else {
                            if (post.getAddress_district().equals(spnDistrict) && Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchText)) {
                                mergeList.add(post);
                            }
                        }
                    }
                    tenantSearchAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else if (spnTypePos == 2) {
            FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
            DatabaseReference Post_Oghep = firebaseDatabase2.getReference("Post_Oghep");
            Post_Oghep.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);
                        if (spnDistrictPos == 0) {
                            assert post != null;
                            if (Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchText)) {
                                mergeList.add(post);
                            }
                        } else {
                            assert post != null;
                            if (post.getAddress_district().equals(spnDistrict) && Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchText)) {
                                mergeList.add(post);
                            }
                        }
                    }
                    tenantSearchAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }

    public void getPostsAndPostFindPeopleFormDB(ArrayList<Post> mergeList, TenantSearchAdapter tenantSearchAdapter, Activity activity) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Post> allPost = new ArrayList<>();
                ArrayList<Post> allPostOGhep = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    allPost.add(post);
                }
                databaseReference.child("Post_Oghep").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotOG) {
                        for (DataSnapshot dataSnapshotOG : snapshotOG.getChildren()) {
                            Post post2 = dataSnapshotOG.getValue(Post.class);
                            allPostOGhep.add(post2);
                        }
                        mergeList.clear();
                        mergeList.addAll(allPost);
                        mergeList.addAll(allPostOGhep);
                        tenantSearchAdapter.notifyDataSetChanged();
                        Log.d("minh", "mergeList: "+mergeList.size());
                        Log.d("minh", "end get data: ");
                        ((TenantSearchActivity)activity).setData();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });



    }
}
