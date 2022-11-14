package com.example.appnhatro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Activity.RepportActivity;
import com.example.appnhatro.Firebase.FirebaseUserSignUp;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserSignUp extends AppCompatActivity {
    EditText name,email,password,phone;
    Button signup;
    ImageView image;
    StorageReference storageReference;
    String getIdPost = "";
    FirebaseUserSignUp firebaseUserSignUp = new FirebaseUserSignUp();
    Uri imageUri = Uri.EMPTY;
    DAOUser dao = new DAOUser();
    static ArrayList<user> arrUser = new ArrayList<>();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_signup);
        getSizeUser();
        openFolder();

        name = findViewById(R.id.edt_suName);
        email = findViewById(R.id.edt_suEmail);
        password = findViewById(R.id.edt_suPass);
        phone = findViewById(R.id.edt_suSDT);
        signup = findViewById(R.id.btn_suSignUp);

        signup.setOnClickListener(v-> {
            Click();
        });
    }

    public void Click(){
        storageReference = FirebaseStorage.getInstance().getReference("images/user/"+getIdUser());
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("test", "onSuccess: " + imageUri.getPath());
                    }
                });
        databaseReference = FirebaseDatabase.getInstance().getReference("user/"+getIdUser());
        user um = new user(getIdUser(),name.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString(),"test",getIdUser());
        databaseReference.setValue(um, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(UserSignUp.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserSignUp.this,TenantPostFavourite.class);
                startActivity(intent);
            }
        });
//        dao.add(um).addOnSuccessListener(suc->{
//            Toast.makeText(this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this,TenantPostFavourite.class);
//            startActivity(intent);
//        }).addOnFailureListener(er->{
//            Toast.makeText(this,"Đăng ký không thành công",Toast.LENGTH_SHORT).show();
//        });
    }

    public void openFolder(){
        image = findViewById(R.id.iv_suImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data!=null && data.getData() != null) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    public static final String getIdUser(){
        String str ="";
        if(arrUser.size() < 10 && arrUser.size() > 0){
            str = "KH0"+(arrUser.size() + 1);
        }else if(arrUser.size() > 9){
            str = "KH"+(arrUser.size());
        }
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
                Toast.makeText(UserSignUp.this, "Get list user faild", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


}
