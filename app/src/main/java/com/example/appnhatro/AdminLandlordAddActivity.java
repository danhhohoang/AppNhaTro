package com.example.appnhatro;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.UserRoleModel;
import com.example.appnhatro.Models.user;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminLandlordAddActivity extends AppCompatActivity {
    CircleImageView crivAccount_ALA;
    EditText txtHoten_ALA,txtSdt_ALA,txtCMND_ALA,txtMatkhau_ALA,txtEmail_ALA;
    Button btnThemCT_ALA;
    StorageReference storageReference;
    private user users;
    private UserRoleModel userRoles;
    private ArrayList<user> arrUser;
    private ArrayList<UserRoleModel> arrURole;
    Dialog progressDialog;
    Uri imgUri = Uri.EMPTY;
    Date now;
    SimpleDateFormat formatter;
    String fileName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrUser = new ArrayList<>();
        arrURole = new ArrayList<>();
        formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CHINA);
        now = new Date();
        fileName = formatter.format(now);
        setContentView(R.layout.admin_landlord_add);
        setControl();
        getSizeUser();
        getSizeUser_Role();
        setEvent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.show();
        do {
            progressDialog.dismiss();
        } while(arrUser.size() >= 1);

    }
    public void setEvent(){
        crivAccount_ALA.setOnClickListener(click->{
            selectImage();
        });
        btnThemCT_ALA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLuuData();
            }
        });
    }
    private String getIdUser(){
        String str ="";
        if(arrUser.size() < 10 && arrUser.size() > 0){
            str = "KH0"+(arrUser.size() + 1);
        }else if(arrUser.size() > 9){
            str = "KH"+(arrUser.size());
        }
        return str;
    }
    private String getIdUserRole(){
        String str ="";
        str = "UR"+(arrURole.size() + 1);
        return str;
    }
    private void getSizeUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    user user1 = dataSnapshot.getValue(user.class);
                    arrUser.add(user1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminLandlordAddActivity.this, "Get list user faild", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getSizeUser_Role() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("User_Role/");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UserRoleModel userRole = dataSnapshot.getValue(UserRoleModel.class);
                    arrURole.add(userRole);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminLandlordAddActivity.this, "Get list user faild", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {
            imgUri = data.getData();
            crivAccount_ALA.setImageURI(imgUri);
        }
    }
    private void addUserRole(String idURole,String idUser){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("User_Role/"+idURole);
        userRoles = new UserRoleModel("1",idUser);
        userRef.setValue(userRoles, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(AdminLandlordAddActivity.this, "Update data successful", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void addLandlord(String fileName,String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/"+id);
        users = new user(id,
                txtHoten_ALA.getText().toString(),
                txtEmail_ALA.getText().toString(),
                txtSdt_ALA.getText().toString(),
                txtMatkhau_ALA.getText().toString(),
                txtCMND_ALA.getText().toString(),
                fileName);
        userRef.setValue(users, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(AdminLandlordAddActivity.this, "Update data successful", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void onClickLuuData() {
        if(Uri.EMPTY.equals(imgUri)){
            fileName = "";
            addLandlord(fileName,getIdUser());
            addUserRole(getIdUserRole(),getIdUser());
        }else {
            upImage();
            fileName = formatter.format(now);
            addLandlord(fileName,getIdUser());
            addUserRole(getIdUserRole(),getIdUser());
        }

    }
    private void upImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading image....");
        progressDialog.show();
        //update img
        storageReference = FirebaseStorage.getInstance().getReference("images/user/" + fileName );
        storageReference.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AdminLandlordAddActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(AdminLandlordAddActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    public void setControl(){
        crivAccount_ALA= findViewById(R.id.crivAccount_ALA);
        txtHoten_ALA = findViewById(R.id.txtHoten_ALA);
        txtCMND_ALA = findViewById(R.id.txtHoten_ALA);
        txtEmail_ALA = findViewById(R.id.txtEmail_ALA);
        txtMatkhau_ALA = findViewById(R.id.txtMatkhau_ALA);
        txtSdt_ALA = findViewById(R.id.txtSdt_ALA);
        btnThemCT_ALA = findViewById(R.id.btnThemCT_ALA);
    }
}
