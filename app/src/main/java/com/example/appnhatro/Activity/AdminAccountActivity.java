package com.example.appnhatro.Activity;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.LandlordPasswordChangeActivity;
import com.example.appnhatro.LandlordSettingProfileActivity;
import com.example.appnhatro.LoginActivity;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.example.appnhatro.TenantContactActivity;
import com.example.appnhatro.TenantPostFavourite;
import com.example.appnhatro.TenantPostList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminAccountActivity extends AppCompatActivity {
    Button btnDangxuat_AA, btnThaydoimk_AA;
    private user mUsers;
    TextView tvHoten_AA, tvIdaccount_AA;
    ImageButton ivbtnBack_AA;
    public static final String NAME = "NAME";
    public static final String ID = "ID";
    public static final String BUNDLE = "BUNDLE";
    public static final String PASS = "PASS";
    CircleImageView ivAccount_AA;
    String id, pass;
    public static final String AVATAR = "AVATAR";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_account);
        getPutExtra();
        setControl();
        setEvent();
        onRead();
    }

    private void onRead() {
        final Dialog dialog = new Dialog(this);
        openDialogNotifyNoButton(dialog,Gravity.CENTER,"Loading data...",R.layout.layout_dialog_notify_no_button);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/"+id);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers = snapshot.getValue(user.class);
                setImage(ivAccount_AA, mUsers.getAvatar());
                do {
                    dialog.dismiss();
                    tvHoten_AA.setText(mUsers.getName());
                    tvIdaccount_AA.setText(mUsers.getId());

                } while (ivAccount_AA.getDrawable().toString().equals(mUsers.getAvatar()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminAccountActivity.this, "Get list user faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    public final void setImage(CircleImageView imageView, String avatar) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + avatar);
        try {
            final File file = File.createTempFile("ảnh", ".jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Notify", "Load Image Fail");

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setEvent() {
        btnThaydoimk_AA.setOnClickListener(click -> {
            Intent intent = new Intent(AdminAccountActivity.this, AdminPasswordChangeActivity.class);
            intent.putExtra(BUNDLE, byBundle());
            startActivity(intent);
        });
        btnDangxuat_AA.setOnClickListener(click -> {
            Intent intent = new Intent(AdminAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        ivbtnBack_AA.setOnClickListener(click->{
            finish();
        });
    }

    private void setControl() {
        btnDangxuat_AA = findViewById(R.id.btnDangxuat_AA);
        btnThaydoimk_AA = findViewById(R.id.btnThaydoimk_AA);
        ivbtnBack_AA = findViewById(R.id.ivbtnBack_AA);
        tvHoten_AA = findViewById(R.id.tvHoten_AA);
        tvIdaccount_AA = findViewById(R.id.tvIdaccount_AA);
        ivAccount_AA = findViewById(R.id.ivAccount_AA);
    }
    private void openDialogNotifyNoButton(final Dialog dialog, int gravity, String noidung, int duongdanlayout) {
        setContentNotify(dialog, gravity, Gravity.BOTTOM, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
    }
    public Bundle byBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(NAME, mUsers.getName());
        bundle.putString(ID, mUsers.getId());
        bundle.putString(PASS, mUsers.getPassword());
        bundle.putString(AVATAR, mUsers.getAvatar());
        return bundle;
    }
    private void getPutExtra(){
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
    }
}
