package com.example.appnhatro;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Firebase.FirebaseUserSignUp;
import com.example.appnhatro.Models.USER_ROLE;
import com.example.appnhatro.Models.user;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSignUp extends AppCompatActivity {
    EditText name, email, password, phone, repass;
    TextView titleRule;
    Button signup;
    ImageView back;
    CircleImageView image;
    CheckBox rule;
    StorageReference storageReference;
    String getIdPost = "";
    FirebaseUserSignUp firebaseUserSignUp = new FirebaseUserSignUp();
    Uri imageUri = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.ic_user);
    DAOUser dao = new DAOUser();
    static ArrayList<user> arrUser = new ArrayList<>();
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceUR;
    DatabaseReference databaseReferenceCheck;
    String idAuto;

    String formatEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_signup);
        getSizeUser();
        openFolder();
        getID();


        name = findViewById(R.id.edt_suName);
        email = findViewById(R.id.edt_suEmail);
        password = findViewById(R.id.edt_suPass);
        repass = findViewById(R.id.edt_suRePass);
        phone = findViewById(R.id.edt_suSDT);
        signup = findViewById(R.id.btn_suSignUp);
        back = findViewById(R.id.btn_suBack);
        rule = findViewById(R.id.cb_suRule);
        titleRule = findViewById(R.id.txt_suTitleRule);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Trường tên không được bỏ trống");
                    return;
                }
                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Trường email không được bỏ trống");
                    return;
                }
                if (!email.getText().toString().matches(formatEmail)) {
                    email.setError("Trường email không đúng định dạng");
                    return;
                }
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    phone.setError("Trường sdt không được bỏ trống");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Trường mật khẩu không được bỏ trống");
                    return;
                }
                if (TextUtils.isEmpty(repass.getText().toString())) {
                    repass.setError("Trường nhập lại mật khẩu không được bỏ trống");
                    return;
                }
                if (!repass.getText().toString().equals(password.getText().toString())) {
                    repass.setError("Trường nhập lại mật khẩu không khớp với mật khẩu trên");
                    return;
                }
                if (!rule.isChecked()) {
                    titleRule.setError("Cần phải đồng ý với điều khoản sử dụng");
                    return;
                }
                Click();
            }
        });
    }

    public void Click(){
        Handler handler = new Handler();
        storageReference = FirebaseStorage.getInstance().getReference("images/user/"+idAuto);
        final  Dialog dialog = new Dialog(UserSignUp.this);
        openDialogNotifyNoButton(dialog,Gravity.CENTER,"Tạo tài khoản thành công",R.layout.layout_dialog_notify_no_button);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (dialog.isShowing()){
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            },2000);
                        }
                    }
                });
        databaseReference = FirebaseDatabase.getInstance().getReference("user/"+idAuto);
        user um = new user(idAuto,name.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString(),"test",idAuto);
        databaseReferenceUR = FirebaseDatabase.getInstance().getReference("User_Role/"+idAuto);
        USER_ROLE ur = new USER_ROLE("2",idAuto);
        databaseReferenceUR.setValue(ur);
        databaseReference.setValue(um, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                finish();
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

    public void openFolder() {
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
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    public void getID() {
        FirebaseDatabase firebaseDatabaseID = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceID = firebaseDatabaseID.getReference("HistoryTransaction");
        databaseReferenceID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsPost = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsPost.add(dataSnapshot.getKey());
                }
                String[] temp = dsPost.get(dsPost.size() - 1).split("HT");
                String id = "";
                if (Integer.parseInt(temp[1]) < 10) {
                    id = "HT0" + (Integer.parseInt(temp[1]) + 1);
                } else {
                    id = "HT" + (Integer.parseInt(temp[1]) + 1);
                }
                idAuto = id;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getSizeUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
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

    private void openDialogNotifyNoButton(final Dialog dialog, int gravity, String noidung, int duongdanlayout) {
        setContentNotify(dialog, gravity, Gravity.BOTTOM, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
    }

}
