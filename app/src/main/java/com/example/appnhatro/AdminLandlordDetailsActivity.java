package com.example.appnhatro;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.appnhatro.Models.user;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminLandlordDetailsActivity extends AppCompatActivity {
    EditText txt_id, txt_tenchutro, txt_email, txt_phone, txt_cmnd, txt_password;
    String [] email;
    CircleImageView civImage_ALD;
    Button btnKhoaTK_ALD, btnSuaTK_ALD;
    ImageButton ibtnBack_ALD;
    boolean passwordVisible;
    Date date;
    user userOld;
    int ttButton;
    String newrole;
    String title ="TÀI KHOẢN BẠN ĐÃ BỊ KHOÁ";
    String doly;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_landlord_details);
        date = new Date();
        date.getDate();
        setControl();
        getSetBundle();
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
        btnSuaTK_ALD.setOnClickListener(click ->{
            ttButton = 1;
            checkThaydoi(ttButton);
        });
        txt_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showHidePass(motionEvent,txt_password);
                return false;
            }
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
        userOld = (user) bundle.get("OBJECT");
        if(userOld != null){
            txt_id.setText(userOld.getId());
            txt_tenchutro.setText(userOld.getName());
            txt_email.setText(userOld.getEmail());
            txt_cmnd.setText(userOld.getCitizenNumber());
            txt_phone.setText(userOld.getPhone());
            txt_password.setText(userOld.getPassword());
            if (userOld.getStatus() != null) {
                if (userOld.getStatus().equals("0")) {
                    btnKhoaTK_ALD.setText("KHOÁ TÀI KHOẢN");
                } else if (userOld.getStatus().equals("1")) {
                    btnKhoaTK_ALD.setText("MỞ TÀI KHOẢN");
                }
            } else {
                btnKhoaTK_ALD.setText("KHOÁ TÀI KHOẢN");
            }
            TenantAccountActivity tenantAccountActivity = new TenantAccountActivity();
            tenantAccountActivity.setImage(civImage_ALD, userOld.getAvatar());
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

    private void openDialogNotifyYesNo1(int gravity, String noidung,int duongdanlayout,String status) {
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
                if(status.equals(userOld.getStatus())){
                    upData(txt_id.getText().toString(),status);
                }else{
                    if(status.equals("0")){
                        sendEmail("TÀI KHOẢN CỦA BẠN ĐÃ ĐƯỢC MỞ","Tài khoản đã được mở");
                        upData(txt_id.getText().toString(),status);
                    }else{
                        openDialogNotifyEdit(Gravity.CENTER,R.layout.layout_dialog_spn_lydo,0,status);
                    }
                }
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
                    openDialogNotifyEdit(Gravity.CENTER,R.layout.layout_dialog_spn_lydo,1,"");
                }else{
                    upDataStatus(userOld.getId(),"0");
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void upData(String id,String status) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");
        user users = new user(txt_id.getText().toString(),
                txt_tenchutro.getText().toString(),
                txt_email.getText().toString(),
                txt_phone.getText().toString(),
                txt_password.getText().toString(),
                txt_cmnd.getText().toString(),
                userOld.getAvatar(),
                status);
        userRef.child(id).child("id").setValue(users.getId());
        userRef.child(id).child("name").setValue(users.getName());
        userRef.child(id).child("email").setValue(users.getEmail());
        userRef.child(id).child("phone").setValue(users.getPhone());
        userRef.child(id).child("password").setValue(users.getPassword());
        userRef.child(id).child("citizenNumber").setValue(users.getCitizenNumber());
        userRef.child(id).child("avatar").setValue(users.getAvatar());
        userRef.child(id).child("status").setValue(status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                openDialogNotifyFinish(Gravity.CENTER,"Thay đổi thành công",R.layout.layout_dialog_notify_finish);
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
    private void upDataStatusNoSend(String id,String status) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");
        userRef.child(id).child("status").setValue(status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                openDialogNotifyFinish(Gravity.CENTER,"Thay đổi thành công",R.layout.layout_dialog_notify_finish);
            }
        });
    }
    private void upDataStatus(String id,String status) {
        sendEmail("TÀI KHOẢN CỦA BẠN ĐÃ ĐƯỢC MỞ","Tài khoản đã được mở");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");
        userRef.child(id).child("status").setValue(status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                openDialogNotifyFinish(Gravity.CENTER,"Thay đổi thành công",R.layout.layout_dialog_notify_finish);
            }
        });
    }
    private void upDataImage(String fileName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/" + userOld.getId());
        userRef.child("avatar").setValue(fileName);
    }
    private void upImage(String tenanh, Uri imgUri) {
        final Dialog dialog = new Dialog(this);
        openDialogNotifyNoButton(dialog,Gravity.CENTER,"Delete image....",R.layout.layout_dialog_notify_no_button);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("user/" + tenanh );
        storageReference.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                            upDataImage(tenanh);
                            openDialogNotifyFinish(Gravity.CENTER,"Xoá thành công",R.layout.layout_dialog_notify_finish);
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
    private void openDialogNotifyNoButton(final Dialog dialog,int gravity, String noidung, int duongdanlayout) {
        setContentNotify(dialog, gravity,Gravity.BOTTOM, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
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
    public void sendEmail(String title,String lydo){
        doly =lydo+". Vui lòng liên hệ với số 0394682138 để cần được hỗ trợ";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,new String []{userOld.getEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT,title);
        intent.putExtra(Intent.EXTRA_TEXT,doly);
        startActivity(intent);
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
            if(txt_tenchutro.getText().toString().equals(userOld.getName())&&
                    txt_email.getText().toString().equals(userOld.getEmail())&&
                    txt_phone.getText().toString().equals(userOld.getPhone())&&
                    txt_cmnd.getText().toString().equals(userOld.getCitizenNumber())&&
                    txt_password.getText().toString().equals(userOld.getPassword())){
                openDialogNotifyYesNo2(Gravity.CENTER, "Bạn có muốn thay đổi thông tin không ?",R.layout.layout_dialog_notify_yes_no);
            }else{
                if(userOld.getStatus().equals("0")){
                    openDialogNotifyYesNo1(Gravity.CENTER,"Bạn có muốn thay đổi thông tin không ?",R.layout.layout_dialog_notify_yes_no,"1");
                }else{
                    openDialogNotifyYesNo1(Gravity.CENTER,"Bạn có muốn thay đổi thông tin không ?",R.layout.layout_dialog_notify_yes_no,"0");
                }
            }
        }else{
            if(txt_tenchutro.getText().toString().equals(userOld.getName())&&
                    txt_email.getText().toString().equals(userOld.getEmail())&&
                    txt_phone.getText().toString().equals(userOld.getPhone())&&
                    txt_cmnd.getText().toString().equals(userOld.getCitizenNumber())&&
                    txt_password.getText().toString().equals(userOld.getPassword())){
                openDialogNotifyYesNo1(Gravity.CENTER,"Bạn có muốn thay đổi thông tin không ?",R.layout.layout_dialog_notify_yes_no,userOld.getStatus());
            }else{
                checkInputdata();
            }

        }
    }
    private void openDialogNotifyEdit(int gravity,int duongdanlayout,int truefalse ,String status) {
        email = new String[]{userOld.getEmail()};
        final Dialog dialog = new Dialog(this);
        setContentNotify(dialog, gravity, Gravity.CENTER, duongdanlayout);
        Button btnLeft = dialog.findViewById(R.id.btnLeft_SelectRole);
        Button btnRight = dialog.findViewById(R.id.btnRight_SelectRole);
        Spinner spnRole = dialog.findViewById(R.id.spnRole);

        String[] arr = getResources().getStringArray(R.array.spiner_lydo);
        spnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                newrole = arr[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newrole.equals(arr[0])){
                    openDialogNotify(Gravity.CENTER,"Vui lòng chọn trước khi lưu",R.layout.layout_dialog_notify);
                }else{
                    if(truefalse == 1){
                        dialog.dismiss();
                        sendEmail(title,newrole);
                        upDataStatusNoSend(userOld.getId(),"1");
                    }else{
                        dialog.dismiss();
                        sendEmail(title,newrole);
                        upData(txt_id.getText().toString(),status);
                    }
                }
            }
        });

        dialog.show();
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void checkInputdata() {
        Pattern specialChar = Pattern.compile("^.*[#?!@$%^&+*-]+.*$");
        Pattern specialCharPhone = Pattern.compile("^.*[#?!@$%^&*-]+.*$");
        Pattern specialCharEmail = Pattern.compile("^.*[#?!$%^&*-]+.*$");
        Pattern specialString = Pattern.compile("^.*[a-zA-Z]+.*$");
        Pattern specialStringNumber = Pattern.compile("^.*[0-9]+.*$");
        if (txt_tenchutro.getText().toString().replaceAll(" ", "").length() == 0) {
            txt_tenchutro.setError("Họ tên không được phép để trống");
        } else if (txt_cmnd.getText().toString().replaceAll(" ", "").length() == 0) {
            txt_cmnd.setError("CMND không được phép để trống");
        } else if (txt_email.getText().toString().replaceAll(" ", "").length() == 0) {
            txt_email.setError("Email không được phép để trống");
        } else if (txt_phone.getText().toString().replaceAll(" ", "").length() == 0) {
            txt_phone.setError("Phone Number không được phép để trống");
        } else if (specialStringNumber.matcher(txt_tenchutro.getText().toString()).find() || specialChar.matcher(txt_tenchutro.getText().toString()).find()) {
            txt_tenchutro.setError("Họ tên không được phép chứa số hoặc kí tự đặc biệt");
        } else if (specialChar.matcher(txt_cmnd.getText().toString()).find() && txt_cmnd.getText().toString().length() > 0) {
            txt_cmnd.setError("CMND không được phép chứa kí tự đặc biệt");
        }else if (specialCharEmail.matcher(txt_email.getText().toString()).find() && txt_email.getText().toString().length() > 0) {
            txt_email.setError("Email không được phép chứa kí tự đặc biệt");
        }else if (specialCharPhone.matcher(txt_phone.getText().toString()).find() && txt_phone.getText().toString().length() > 0) {
            txt_phone.setError("Phone Number không được phép chứa kí tự đặc biệt");
        } else if (specialString.matcher(txt_cmnd.getText().toString()).find() && txt_cmnd.getText().toString().length() > 0) {
            txt_cmnd.setError("CMND không được phép chứa chữ");
        }else if (specialString.matcher(txt_phone.getText().toString()).find() && txt_phone.getText().toString().length() > 0) {
            txt_phone.setError("Phone Number không được phép chứa chữ");
        } else if (!txt_email.getText().toString().contains("@")) {
            txt_email.setError("Email sai định dạng thiếu @");
        }else if (txt_cmnd.getText().toString().length() < 9) {
            txt_cmnd.setError("CMND ít nhất từ 9 - 12 kí tự ");
        } else if (txt_email.getText().toString().contains(" ")) {
            txt_email.setError("Email không được phép chứa khoảng trắng");
        }else if (txt_email.getText().toString().length() < 11) {
            txt_email.setError("Email tối thiếu là 11 kí tự ");
        } else if (txt_password.getText().toString().length() < 6) {
            txt_password.setError("Mật khẩu tối thiếu là 6 kí tự ");
        } else {
            openDialogNotifyYesNo1(Gravity.CENTER,"Bạn có muốn thay đổi thông tin không ?",R.layout.layout_dialog_notify_yes_no,userOld.getStatus());
        }
    }
}
