package com.example.appnhatro;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.user;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminLandlordDetailsActivity extends AppCompatActivity {
    EditText txt_id, txt_tenchutro, txt_email, txt_phone, txt_cmnd, txt_password;
    String avt, id, name, email, phone, cmnd, pass, status;
    CircleImageView civImage_ALD;
    Button btnKhoaTK_ALD, btnSuaTK_ALD;
    ImageButton ibtnBack_ALD;
    user userOld;
    int ttButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_landlord_details);
        setControl();
        getSetBundle();
        TenantAccountActivity tenantAccountActivity = new TenantAccountActivity();
        tenantAccountActivity.setImage(civImage_ALD, avt);
        setEvent();
    }

    private void setEvent() {
        ibtnBack_ALD.setOnClickListener(click -> {
            finish();
        });
        btnKhoaTK_ALD.setOnClickListener(click ->{
            ttButton = 0;
            checkThaydoi(ttButton);
        });
    }

    private void setControl() {
        civImage_ALD = findViewById(R.id.civImage_ALD);
        txt_id = findViewById(R.id.txt_aldMachutro);
        txt_tenchutro = findViewById(R.id.txt_aldTenchutro);
        txt_email = findViewById(R.id.txt_aldEmail);
        txt_cmnd = findViewById(R.id.txt_aldCMND);
        txt_phone = findViewById(R.id.txt_aldSdt);
        txt_password = findViewById(R.id.txt_aldPassword);
        btnKhoaTK_ALD = findViewById(R.id.btnKhoaTK_ALD);
        btnSuaTK_ALD = findViewById(R.id.btnSuaTK_ALD);
        ibtnBack_ALD = findViewById(R.id.ibtnBack_ALD);
    }

    private void getSetBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(AdminLandlordListActivity.BUNDLE);
        id = bundle.getString(AdminLandlordListActivity.ID);
        avt = bundle.getString(AdminLandlordListActivity.AVATAR);
        name = bundle.getString(AdminLandlordListActivity.NAME);
        email = bundle.getString(AdminLandlordListActivity.EMAIL);
        phone = bundle.getString(AdminLandlordListActivity.PHONE);
        cmnd = bundle.getString(AdminLandlordListActivity.CMND);
        pass = bundle.getString(AdminLandlordListActivity.PASSWORD);
        status = bundle.getString(AdminLandlordListActivity.STATUS);

        userOld = new user(id, name, email, phone, pass, cmnd, avt, status);


        txt_id.setText(id);
        txt_tenchutro.setText(name);
        txt_email.setText(email);
        txt_cmnd.setText(cmnd);
        txt_phone.setText(phone);
        txt_password.setText(pass);
        if (status != null) {
            if (status.equals("0")) {
                btnKhoaTK_ALD.setText("KHOÁ TÀI KHOẢN");
            } else if (status.equals("1")) {
                btnKhoaTK_ALD.setText("MỞ TÀI KHOẢN");
            }
        } else {
            btnKhoaTK_ALD.setText("KHOÁ TÀI KHOẢN");
        }

    }
    private void openDialogNotifyFinish(int gravity, String noidung, int duongdanlayout) {
        Dialog dialog = new Dialog(this);
        setContentNotify(dialog, gravity, Gravity.CENTER, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyFinish);
        Button btnCenter = dialog.findViewById(R.id.btnCenter_NotifyFinish);
        tvNoidung.setText(noidung);
        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();}
        });
        dialog.show();
    }

    private void openDialogNotifyYesNo1(int gravity, String noidung,int duongdanlayout) {
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
                upData(txt_id.getText().toString());
            }
        });
        dialog.show();
    }
    private void openDialogNotifyYesNo2(int gravity, String noidung,int duongdanlayout) {
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
                if(userOld.getStatus().equals("0")){
                    upDataStatus(id,"1");
                }else{
                    upDataStatus(id,"0");
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void upData(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/" + id);
        user users = new user(txt_id.getText().toString(),
                txt_tenchutro.getText().toString(),
                txt_email.getText().toString(),
                txt_phone.getText().toString(),
                txt_password.getText().toString(),
                txt_cmnd.getText().toString(),
                avt,
                userOld.getStatus());
        userRef.setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                openDialogNotifyFinish(Gravity.CENTER,"Thay đổi thành công",R.layout.layout_dialog_notify_finish);
            }
        });
    }
    private void upDataStatus(String id,String status) {
        AdminLandlordListActivity.list.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/" + id);
        user users = new user(txt_id.getText().toString(),
                txt_tenchutro.getText().toString(),
                txt_email.getText().toString(),
                txt_phone.getText().toString(),
                txt_password.getText().toString(),
                txt_cmnd.getText().toString(),
                avt,
                status);
        userRef.setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                openDialogNotifyFinish(Gravity.CENTER,"Thay đổi thành công",R.layout.layout_dialog_notify_finish);
            }
        });
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
    public void checkThaydoi(int ttButton) {
        if (txt_tenchutro.getText().toString().length() == 0 &&
                txt_email.getText().toString().length() == 0 &&
                txt_phone.getText().toString().length() == 0 &&
                txt_cmnd.getText().toString().length() == 0 &&
                txt_password.getText().toString().length() == 0) {
            openDialogNotify(Gravity.CENTER,"Vui lòng nhập đầy đủ thông tin !",R.layout.layout_dialog_notify);
        }else{
            checkStatus(ttButton);
        }

    }
    public void checkStatus(int ttButton){
        if(ttButton ==  0){

            openDialogNotifyYesNo2(Gravity.CENTER,"Bạn có muốn thay đổi thông tin không ?",R.layout.layout_dialog_notify_yes_no);
        }else{
            openDialogNotifyYesNo1(Gravity.CENTER,"Bạn có muốn thay đổi thông tin không ?",R.layout.layout_dialog_notify_yes_no);
        }
    }
}
