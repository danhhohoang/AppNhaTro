package com.example.appnhatro;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    EditText name, email, password, phone, cmnd, repass;
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
    int checkEmail = 0;

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
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkEmail();
            }
        });
        password = findViewById(R.id.edt_suPass);
        repass = findViewById(R.id.edt_suRePass);
        phone = findViewById(R.id.edt_suSDT);
        signup = findViewById(R.id.btn_suSignUp);
        back = findViewById(R.id.btn_suBack);
        rule = findViewById(R.id.cb_suRule);
        cmnd = findViewById(R.id.edt_suCMND);
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
                    name.setError("Tr?????ng t??n kh??ng ???????c b??? tr???ng");
                    return;
                }
                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Tr?????ng email kh??ng ???????c b??? tr???ng");
                    return;
                }
                if (!email.getText().toString().matches(formatEmail)) {
                    email.setError("Tr?????ng email kh??ng ????ng ?????nh d???ng");
                    return;
                }
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    phone.setError("Tr?????ng sdt kh??ng ???????c b??? tr???ng");
                    return;
                }
                if (TextUtils.isEmpty(cmnd.getText().toString())) {
                    cmnd.setError("Tr?????ng cmnd kh??ng ???????c b??? tr???ng");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Tr?????ng m???t kh???u kh??ng ???????c b??? tr???ng");
                    return;
                }
                if (TextUtils.isEmpty(repass.getText().toString())) {
                    repass.setError("Tr?????ng nh???p l???i m???t kh???u kh??ng ???????c b??? tr???ng");
                    return;
                }
                if (!repass.getText().toString().equals(password.getText().toString())) {
                    repass.setError("Tr?????ng nh???p l???i m???t kh???u kh??ng kh???p v???i m???t kh???u tr??n");
                    return;
                }
                if (!rule.isChecked()) {
                    titleRule.setError("C???n ph???i ?????ng ?? v???i ??i???u kho???n s??? d???ng");
                    return;
                }
                if (checkEmail == 1){
                    final  Dialog dialog = new Dialog(UserSignUp.this);
                    openDialogNotifyNoButton(dialog,Gravity.CENTER,"Email ???? c?? ng?????i s??? d???ng",R.layout.layout_dialog_notify_no_button);
                    checkEmail = 0;
                    email.getText().clear();
                    return;
                }
                Click();
            }
        });
    }

    public void Click(){
        Handler handler = new Handler();
        storageReference = FirebaseStorage.getInstance().getReference("images/user/"+idAuto+".jpg");
        final  Dialog dialog = new Dialog(UserSignUp.this);
        openDialogNotifyNoButton(dialog,Gravity.CENTER,"T???o t??i kho???n th??nh c??ng",R.layout.layout_dialog_notify_no_button);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
        databaseReference = FirebaseDatabase.getInstance().getReference("user/"+idAuto);
        user um = new user(idAuto,name.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString(),cmnd.getText().toString(),idAuto,"0");
        databaseReferenceUR = FirebaseDatabase.getInstance().getReference("User_Role/"+idAuto);
        USER_ROLE ur = new USER_ROLE("1",idAuto);
        databaseReferenceUR.setValue(ur);
        databaseReference.setValue(um, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (dialog.isShowing()){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            finish();
                        }
                    },2000);
                }
            }
        });
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
        DatabaseReference databaseReferenceID = firebaseDatabaseID.getReference("user");
        databaseReferenceID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsPost = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsPost.add(dataSnapshot.getKey());
                }
                String[] temp = dsPost.get(dsPost.size() - 1).split("KH");
                String id = "";
                if (Integer.parseInt(temp[1]) < 9) {
                    id = "KH0" + (Integer.parseInt(temp[1]) + 1);
                } else {
                    id = "KH" + (Integer.parseInt(temp[1]) + 1);
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
        setContentNotify(dialog, gravity, Gravity.CENTER, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
    }

    public void checkEmail(){
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    user _user = dataSnapshot.getValue(user.class);
                    if (_user.getEmail().equals(email.getText().toString())){
                        checkEmail = 1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
