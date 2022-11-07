package com.example.appnhatro.Firebase;

import androidx.annotation.NonNull;

import com.example.appnhatro.Adapters.TenantSearchAdapter;
import com.example.appnhatro.Models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseTenantSearch {
    public FireBaseTenantSearch() {
    }

    //Doc toan bo "post" tu FireBase
    public void getPostsFormDB(ArrayList<Post> postList, TenantSearchAdapter tenantSearchAdapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    postList.add(post);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // FireBaseTenantSearch fireBaseTenantSearch;
    // fireBaseTenantSearch(List input, TenantSearchAdapter , value of spnDistrict, position of spnDistrict , position of spnPrice, position of spnArea)
    public void reTurnPostBySpinner(ArrayList<Post> postList, TenantSearchAdapter tenantSearchAdapter, String spnDistrict, int spnDistrictPos, int spnPrice, int spnArea) {
        int minPrice;
        int maxPrice;
        int minArea;
        int maxArea;

        //kiem tra position dau vao va set ket qua tuong ung
        switch (spnPrice) {
            case 0:
                minPrice = 0;
                maxPrice = 2000000000;
                break;
            case 1:
                minPrice = 0;
                maxPrice = 2000000;
                break;
            case 2:
                minPrice = 2000000;
                maxPrice = 4000000;
                break;
            case 3:
                minPrice = 4000000;
                maxPrice = 6000000;
                break;
            case 4:
                minPrice = 6000000;
                maxPrice = 8000000;
                break;
            case 5:
                minPrice = 8000000;
                maxPrice = 12000000;
                break;
            case 6:
                minPrice = 12000000;
                maxPrice = 2000000000;
                break;
            default:
                minPrice = 0;
                maxPrice = 2000000000;
                break;
        }

        switch (spnArea) {
            case 0:
                minArea = 0;
                maxArea = 10000;
                break;
            case 1:
                minArea = 0;
                maxArea = 20;
                break;
            case 2:
                minArea = 20;
                maxArea = 30;
                break;
            case 3:
                minArea = 30;
                maxArea = 40;
                break;
            case 4:
                minArea = 40;
                maxArea = 50;
                break;
            case 5:
                minArea = 50;
                maxArea = 10000;
                break;
            default:
                minArea = 0;
                maxArea = 10000;
                break;
        }


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);

                    if (spnDistrictPos == 0) {
                        assert post != null; //lenh may tu viet, khong hieu gi het
                        if (Integer.parseInt(post.getArea()) >= minArea
                                &&
                                Integer.parseInt(post.getArea()) <= maxArea
                                &&
                                Integer.parseInt(post.getPrice()) >= minPrice
                                &&
                                Integer.parseInt(post.getPrice()) <= maxPrice) {
                            postList.add(post);
                        }

                    } else {
                        assert post != null; //lenh may tu viet, khong hieu gi het
                        if (post.getAddress_district().equals(spnDistrict)
                                &&
                                Integer.parseInt(post.getArea()) >= minArea
                                &&
                                Integer.parseInt(post.getArea()) <= maxArea
                                &&
                                Integer.parseInt(post.getPrice()) >= minPrice
                                &&
                                Integer.parseInt(post.getPrice()) <= maxPrice) {
                            postList.add(post);
                        }
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
