package com.example.appnhatro;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.MotionEvent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TenantPasswordChangeActivity extends AppCompatActivity {
    EditText txtMatkhaucu_TPC, txtMatkhaumoi1_TPC, txtMatkhaumoi2_TPC;
    ImageView ivBack_TPC;
    Button btnLuu_TPC;
    private user mUser;
    boolean passwordVisible;
    private String id, pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_password_change);
        setControl();
        getBundle();
        setEvent();
    }

    public void setEvent() {
        onReadPassWord();
        txtMatkhaucu_TPC.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return showHidePass(motionEvent, txtMatkhaucu_TPC);
            }
        });
        txtMatkhaumoi1_TPC.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return showHidePass(motionEvent, txtMatkhaumoi1_TPC);
            }
        });
        txtMatkhaumoi2_TPC.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return showHidePass(motionEvent, txtMatkhaumoi2_TPC);
            }
        });
        ivBack_TPC.setOnClickListener(click -> {
            finish();
        });
        btnLuu_TPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtMatkhaucu_TPC.getText().toString().length() == 0&&txtMatkhaumoi1_TPC.getText().toString().length() == 0 && txtMatkhaumoi2_TPC.getText().toString().length() == 0) {
                    openDiglogNotify(Gravity.CENTER, "Vui l??ng nh???p v??o", R.layout.layout_dialog_notify);
                } else if (!txtMatkhaumoi1_TPC.getText().toString().equals(txtMatkhaumoi2_TPC.getText().toString())) {
                    openDiglogNotify(Gravity.CENTER, "M???t m???u m???i kh??ng tr??ng kh???p", R.layout.layout_dialog_notify);
                } else if (txtMatkhaumoi1_TPC.getText().toString().length() == 0 && txtMatkhaumoi2_TPC.getText().toString().length() == 0) {
                    openDiglogNotify(Gravity.CENTER, "Vui l??ng nh???p m???t kh???u m???i", R.layout.layout_dialog_notify);
                }else if (!txtMatkhaucu_TPC.getText().toString().equals(pass)) {
                    openDiglogNotify(Gravity.CENTER, "M???t kh???u c?? sai", R.layout.layout_dialog_notify);
                }
                else {
                    openDiglogNotifyYesNo(Gravity.CENTER, "B???n c?? mu???n thay ?????i m???t kh???u kh??ng ?", R.layout.layout_dialog_notify_yes_no);

                }
            }
        });
    }

    public static final void setContentNotify(final Dialog dialog, int gravity,int truefalse,int duongdanlayout) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(duongdanlayout);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams winLayoutParams = window.getAttributes();
        winLayoutParams.gravity = gravity;
        window.setAttributes(winLayoutParams);

        if (truefalse == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
    }

    private void openDiglogNotify(int gravity, String noidung, int duongdanlayout) {
        Dialog dialog = new Dialog(this);
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
    private void openDiglogNotifyFinish(int gravity, String noidung, int duongdanlayout) {
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

    private void openDiglogNotifyYesNo(int gravity, String noidung,int duongdanlayout) {
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
                updatePassword();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void onReadPassWord() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/" + id);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser = snapshot.getValue(user.class);
                pass = mUser.getPassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TenantPasswordChangeActivity.this, "Get password fail", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updatePassword() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/" + id);
        user users = new user(mUser.getId(),
                mUser.getName(),
                mUser.getEmail(),
                mUser.getPhone(),
                txtMatkhaumoi1_TPC.getText().toString(),
                mUser.getCitizenNumber(),
                mUser.getAvatar(),
                mUser.getStatus());
        userRef.setValue(users, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                openDiglogNotifyFinish(Gravity.CENTER, "Thay ?????i m???t kh???u th??nh c??ng", R.layout.layout_dialog_notify_finish);
            }
        });

    }

    public boolean showHidePass(MotionEvent motionEvent, EditText edittext) {
        final int Right = 2;
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (motionEvent.getRawX() >= edittext.getRight() - edittext.getCompoundDrawables()[Right].getBounds().width()) {
                int selection = edittext.getSelectionEnd();
                if (passwordVisible) {
                    //set drawable image
                    edittext.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_hide, 0);
                    //for hide password
                    edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordVisible = false;
                } else {
                    edittext.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_open, 0);
                    edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordVisible = true;
                }
                edittext.setSelection(selection);
                return true;
            }
        }
        return false;
    }

    public void setControl() {
        txtMatkhaucu_TPC = findViewById(R.id.txtMatkhaucu_TPC);
        txtMatkhaumoi1_TPC = findViewById(R.id.txtMatkhaumoi1_TPC);
        txtMatkhaumoi2_TPC = findViewById(R.id.txtMatkhaumoi2_TPC);
        ivBack_TPC = findViewById(R.id.ivBack_TPC);
        btnLuu_TPC = findViewById(R.id.btnLuu_TPC);
    }

    public void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(TenantSettingActivity.BUNDLE);
        id = bundle.getString(TenantSettingActivity.ID);
    }
}
