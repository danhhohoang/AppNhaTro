package com.example.appnhatro.Activity;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText txtMatKhauMoi,txtNhapLaiMatKhauMoi;

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
//                HashMap<String,Object> hashMap = new HashMap<>();
//                hashMap.put("password",txtMatKhauMoi.getText().toString());
//                dao.update("KH01",hashMap).addOnSuccessListener(suc->{
//                    Toast.makeText(ReserPasswordChangeActivity.this,"Thay đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
//                });
                String pass = txtMatKhauMoi.getText().toString();
                String repass = txtNhapLaiMatKhauMoi.getText().toString();
                if (!repass.equals(pass)){
                    txtNhapLaiMatKhauMoi.setError("Mật khẩu nhập lại không giống mật khẩu trên");
                    return;
                }
                Handler handler = new Handler();
                final  Dialog dialog = new Dialog(ReserPasswordChangeActivity.this);
                openDialogNotifyNoButton(dialog,Gravity.CENTER,"Thay đổi mk thành công",R.layout.layout_dialog_notify_no_button);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
//                Log.d("TAG", data);
                databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            user User = dataSnapshot.getValue(user.class);
                            if(User.getEmail().equals(data)) {
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
        txtMatKhauMoi = findViewById(R.id.txtMatKhauMoi);
        txtNhapLaiMatKhauMoi = findViewById(R.id.txtNhapLaiMatKhauMoi);
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
