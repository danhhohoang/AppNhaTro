package com.example.appnhatro.Activity;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.DAOUser;
import com.example.appnhatro.HomeTenantActivity;
import com.example.appnhatro.LoginActivity;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ReserPasswordChangeActivity extends AppCompatActivity {
    Button btnLuuMatKhauMoi;
    EditText txtMatKhauMoi,txtNhapLaiMatKhauMoi,txtEmail;
    ImageView back;
    boolean passwordVisible;
    String formatEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_resetpassword_change);

        String data = getIntent().getExtras().getString("email_check3","Null");
        DAOUser dao = new DAOUser();
        setTitleToolbar();
        init();
        btnLuuMatKhauMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = txtEmail.getText().toString();
                String pass = txtMatKhauMoi.getText().toString();
                String repass = txtNhapLaiMatKhauMoi.getText().toString();
                if (TextUtils.isEmpty(mail)){
                    txtEmail.setError("Tr?????ng email kh??ng ???????c tr???ng");
                    return;
                }
                if (!mail.matches(formatEmail)){
                    txtEmail.setError("Tr?????ng email kh??ng ????ng ?????nh d???ng");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    txtMatKhauMoi.setError("Tr?????ng m???t kh???u kh??ng ???????c ????? tr???ng");
                    return;
                }
                if (TextUtils.isEmpty(repass)){
                    txtNhapLaiMatKhauMoi.setError("Tr?????ng nh???p l???i m???t kh???u kh??ng ???????c ????? tr???ng");
                    return;
                }
                if (!repass.equals(pass)){
                    txtNhapLaiMatKhauMoi.setError("M???t kh???u nh???p l???i kh??ng gi???ng m???t kh???u tr??n");
                    return;
                }
                Handler handler = new Handler();
                final  Dialog dialog = new Dialog(ReserPasswordChangeActivity.this);
                openDialogNotifyNoButton(dialog,Gravity.CENTER,"Thay ?????i m???t kh???u th??nh c??ng",R.layout.layout_dialog_notify_no_button);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
//                Log.d("TAG", data);
                databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            user User = dataSnapshot.getValue(user.class);
                            if(User.getEmail().equals(mail)) {
                                dataSnapshot.getRef().child("password").setValue(pass);
                            }
                        }
                        if (dialog.isShowing()){
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Intent intent = new Intent(ReserPasswordChangeActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            },2000);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
    private void init(){
        btnLuuMatKhauMoi = findViewById(R.id.btnLuuMatKhauMoi);
        txtEmail = findViewById(R.id.txtEmail);
        txtMatKhauMoi = findViewById(R.id.txtMatKhauMoi);
        txtMatKhauMoi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=txtMatKhauMoi.getRight()-txtMatKhauMoi.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = txtMatKhauMoi.getSelectionEnd();
                        if (passwordVisible){
                            txtMatKhauMoi.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_hide_pass,0);
                            txtMatKhauMoi.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else {
                            txtMatKhauMoi.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_show_pass,0);
                            txtMatKhauMoi.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        txtMatKhauMoi.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        txtNhapLaiMatKhauMoi = findViewById(R.id.txtNhapLaiMatKhauMoi);
        txtNhapLaiMatKhauMoi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=txtNhapLaiMatKhauMoi.getRight()-txtNhapLaiMatKhauMoi.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = txtNhapLaiMatKhauMoi.getSelectionEnd();
                        if (passwordVisible){
                            txtNhapLaiMatKhauMoi.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_hide_pass,0);
                            txtNhapLaiMatKhauMoi.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else {
                            txtNhapLaiMatKhauMoi.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_show_pass,0);
                            txtNhapLaiMatKhauMoi.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        txtNhapLaiMatKhauMoi.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        back = findViewById(R.id.iv_rcBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Intent when success repassword
    private void setTitleToolbar(){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Success");
        }
    }

    private void openDialogNotifyNoButton(final Dialog dialog,int gravity, String noidung, int duongdanlayout) {
        setContentNotify(dialog, gravity,Gravity.BOTTOM, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
    }

    private void getDataIntent(){

    }
}
