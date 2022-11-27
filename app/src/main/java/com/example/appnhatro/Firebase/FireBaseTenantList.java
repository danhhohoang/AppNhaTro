package com.example.appnhatro.Firebase;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.appnhatro.Activity.AdminTenantDetaiActivity;
import com.example.appnhatro.Adapters.TenantListAdapter;
import com.example.appnhatro.Models.USER_ROLE;
import com.example.appnhatro.Models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class FireBaseTenantList {

    public void getAllUser(Activity activity, ArrayList<user> userArrayList, TenantListAdapter adapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userFirebase = firebaseDatabase.getReference("user");
        DatabaseReference roleFirebase = firebaseDatabase.getReference("User_Role");
        roleFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshotRole : snapshot.getChildren()) {
                    USER_ROLE user_role = dataSnapshotRole.getValue(USER_ROLE.class);
                    if (user_role.getId_role().equals("1")) {
                        userFirebase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshotUser : snapshot.getChildren()) {
                                    user _user = dataSnapshotUser.getValue(user.class);
                                    if (_user.getId().equals(user_role.getId_user())) {
                                        userArrayList.add(_user);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                adapter.notifyDataSetChanged();
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

    public void getSingleUser(Activity activity, ArrayList<user> userArrayList, String maUser) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userDatabaseReference = firebaseDatabase.getReference("user");
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user _user = dataSnapshot.getValue(user.class);
                    if (_user.getId().equals(maUser)) {
                        userArrayList.add(_user);
                        ((AdminTenantDetaiActivity) activity).setInfor(_user);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void lockUserAccount(Activity activity, String userID) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userDatabaseReference = firebaseDatabase.getReference("user");
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user snapshotUser = dataSnapshot.getValue(user.class);
                    if (snapshotUser.getId().equals(userID)) {
                        userDatabaseReference.child(snapshotUser.getId()).child("status").setValue("1");
                        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                        alertDialog.setTitle("Thông báo");
                        alertDialog.setMessage("Tài khoản đã bị khoá");
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.finish();
                            }
                        });
                        alertDialog.show();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void unLockUserAccount(Activity activity, String userID) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userDatabaseReference = firebaseDatabase.getReference("user");
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user snapshotUser = dataSnapshot.getValue(user.class);
                    if (snapshotUser.getId().equals(userID)) {
                        userDatabaseReference.child(snapshotUser.getId()).child("status").setValue("0");
                        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                        alertDialog.setTitle("Thông báo");
                        alertDialog.setMessage("Tài khoản đã được mở khóa, người dùng này có thể truy cập dịch vụ như bình thường");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void setInfoUser(Activity activity, user _user) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userDatabaseReference = firebaseDatabase.getReference("user");
        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user snapshotUser = dataSnapshot.getValue(user.class);
                    if (snapshotUser.getId().equals(_user.getId())) {
                        userDatabaseReference.child(snapshotUser.getId()).child("avatar").setValue(_user.getAvatar());
                        userDatabaseReference.child(snapshotUser.getId()).child("citizenNumber").setValue(_user.getCitizenNumber());
                        userDatabaseReference.child(snapshotUser.getId()).child("email").setValue(_user.getEmail());
                        userDatabaseReference.child(snapshotUser.getId()).child("id").setValue(_user.getId());
                        userDatabaseReference.child(snapshotUser.getId()).child("name").setValue(_user.getName());
                        userDatabaseReference.child(snapshotUser.getId()).child("password").setValue(_user.getPassword());
                        userDatabaseReference.child(snapshotUser.getId()).child("phone").setValue(_user.getPhone());
                        userDatabaseReference.child(snapshotUser.getId()).child("status").setValue(_user.getStatus());

                        ((AdminTenantDetaiActivity) activity).setInfor(snapshotUser);
                        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                        alertDialog.setTitle("Sửa thành công");
                        alertDialog.setMessage("Thông tin tài khoản đã được cập nhật");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void removeUser(Activity activity, String userID) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userDatabaseReference = firebaseDatabase.getReference("user");
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user snapshotUser = dataSnapshot.getValue(user.class);
                    if (snapshotUser.getId().equals(userID)) {
                        userDatabaseReference.child(snapshotUser.getId()).removeValue();
                        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                        alertDialog.setTitle("Thông báo");
                        alertDialog.setMessage("Tài khoản đã bị xóa");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void reTurnUserByFilter(String newText, ArrayList<user> userArrayList, TenantListAdapter adapter) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userDatabaseReference = firebaseDatabase.getReference("user");
        DatabaseReference roleFirebase = firebaseDatabase.getReference("User_Role");
        roleFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    USER_ROLE user_role = dataSnapshot.getValue(USER_ROLE.class);
                    if (user_role.getId_role().equalsIgnoreCase("1")) {
                        userDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshotUser) {
                                for (DataSnapshot dataSnapshotUser : snapshotUser.getChildren()) {
                                    user _user = dataSnapshotUser.getValue(user.class);
                                    if (_user.getId().equals(user_role.getId_user())) {

                                        if (snapshotUser != null) {
                                            if (_user.getId().toLowerCase().contains(newText.toLowerCase(Locale.ROOT)) || _user.getName().toLowerCase().contains(newText.toLowerCase())) {
                                                userArrayList.add(_user);
                                                adapter.notifyDataSetChanged();
                                            }
                                        } else {

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

