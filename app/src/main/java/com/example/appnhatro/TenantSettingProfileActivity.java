package com.example.appnhatro;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class TenantSettingProfileActivity extends AppCompatActivity {
    EditText txtHoten_TSP, txtCMND_TSP, txtEmail_TSP, txtSDT_TSP;
    Button btnLuu_TSP, btnSelectImg_TSP;
    ImageView ivBack_TSP;
    CircleImageView ivImage_TSP;
    private user mListUser;
    StorageReference storageReference;
    Uri imgUri = Uri.EMPTY;
    String extensionFile = ".jpg";
    String id, avatar, fileName,namefile,b1,b2;
    int temp = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_setting_profile);
        setControl();
        getBundle();
        setEvent();
        TenantAccountActivity tenantAccountActivity = new TenantAccountActivity();
        tenantAccountActivity.setImage(ivImage_TSP, avatar);

    }

    private void setEvent() {
        onRead();
        btnSelectImg_TSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnLuu_TSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogNotifyYesNo(Gravity.CENTER, "Bạn có muốn lưu không ?",R.layout.layout_dialog_notify_yes_no);
            }
        });
        ivBack_TSP.setOnClickListener(click -> {
            finish();
        });
    }

    private void onRead() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/"+id);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListUser = snapshot.getValue(user.class);
                txtHoten_TSP.setText(mListUser.getName());
                txtCMND_TSP.setText(mListUser.getCitizenNumber());
                txtEmail_TSP.setText(mListUser.getEmail());
                txtSDT_TSP.setText(mListUser.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TenantSettingProfileActivity.this, "Get list user faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void upImage(String tenanh) {
        final Dialog dialog = new Dialog(this);
        openDialogNotifyNoButton(dialog,Gravity.CENTER,"Update Image....",R.layout.layout_dialog_notify_no_button);
        fileName = mListUser.getAvatar();
        String a1;
//        if (fileName.equals(mListUser.getId() +".jpg")){
//            a1 = mListUser.getId()+"1.jpg";
//        }else {
//            a1 = mListUser.getId() +".jpg";
//        }
        storageReference = FirebaseStorage.getInstance().getReference("images/user/" + tenanh );
        storageReference.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                            upData(tenanh, id);
                            openDialogNotifyFinish(Gravity.CENTER,"Cập nhật thông tin thành công",R.layout.layout_dialog_notify_finish);
//
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                            openDialogNotify(Gravity.CENTER,"Failed to Upload",R.layout.layout_dialog_notify);

                    }
                });


    }

    @Override
    public void onResume(){
        super.onResume();

    }
    private void openDialogNotify(int gravity, String noidung, int duongdanlayout) {
        final Dialog dialog = new Dialog(this);
        setContentNotify(dialog, gravity,Gravity.CENTER, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_Notify);
        Button btnLeft = dialog.findViewById(R.id.btnCenter_Notify);
        tvNoidung.setText(noidung);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void openDialogNotifyNoButton(final Dialog dialog,int gravity, String noidung, int duongdanlayout) {
        setContentNotify(dialog, gravity,Gravity.BOTTOM, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
    }
    private void openDialogNotifyFinish(int gravity, String noidung, int duongdanlayout) {
        Dialog dialog = new Dialog(this);
        setContentNotify(dialog, gravity,Gravity.CENTER, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyFinish);
        Button btnCenter = dialog.findViewById(R.id.btnCenter_NotifyFinish);
        tvNoidung.setText(noidung);
        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void openDialogNotifyYesNo(int gravity, String noidung,int duongdanlayout) {
        final Dialog dialog = new Dialog(this);
        setContentNotify(dialog, gravity,Gravity.CENTER, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyYesNo);
        Button btnLeft = dialog.findViewById(R.id.btnLeft_NotifyYesNo);
        Button btnRight = dialog.findViewById(R.id.btnRight_NotifyYesNo);
        tvNoidung.setText(noidung);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onClickLuuData();
            }
        });
        dialog.show();
    }


    private void upData(String fileName, String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/" + id);
        user users = new user(mListUser.getId(),
                txtHoten_TSP.getText().toString(),
                txtEmail_TSP.getText().toString(),
                txtSDT_TSP.getText().toString(),
                mListUser.getPassword(),
                txtCMND_TSP.getText().toString(),
                fileName);
        userRef.setValue(users);
    }

    private void onClickLuuData() {
        if (Uri.EMPTY.equals(imgUri)) {
            fileName = mListUser.getAvatar();
            upData(fileName, id);
            openDialogNotifyFinish(Gravity.CENTER,"Cập nhật thành công",R.layout.layout_dialog_notify_finish);
        } else {
            fileName = mListUser.getAvatar();
            if (fileName.equals(mListUser.getId() +".jpg")){
                String a1 = mListUser.getId()+"1.jpg";
                upImage(a1);

            }else {
                String a2 = mListUser.getId() +".jpg";
                upImage(a2);


            }

        }

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
            ivImage_TSP.setImageURI(imgUri);
        }
    }

    private void setControl() {
        txtHoten_TSP = findViewById(R.id.txtHoten_TSP);
        txtCMND_TSP = findViewById(R.id.txtCMND_TSP);
        txtSDT_TSP = findViewById(R.id.txtSDT_TSP);
        ivImage_TSP = findViewById(R.id.ivImage_TSP);
        txtEmail_TSP = findViewById(R.id.txtEmail_TSP);
        btnLuu_TSP = findViewById(R.id.btnLuu_TSP);
        ivBack_TSP = findViewById(R.id.ivBack_TSP);
        btnSelectImg_TSP = findViewById(R.id.btnSelectImg_TSP);
    }

    private void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(TenantSettingActivity.BUNDLE);
        id = bundle.getString(TenantSettingActivity.ID);
        avatar = bundle.getString(TenantSettingActivity.AVATAR);
    }
}
