package com.example.appnhatro;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.UserRoleModel;
import com.example.appnhatro.Models.user;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
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

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class AdminLandlordAddActivity extends AppCompatActivity {
    CircleImageView crivAccount_ALA;
    EditText txtHoten_ALA, txtSdt_ALA, txtCMND_ALA, txtMatkhau_ALA, txtEmail_ALA;
    Button btnThemCT_ALA, btnBack_ALA;
    StorageReference storageReference;
    private user users;
    private UserRoleModel userRoles;
    private ArrayList<user> arrUser;
    private ArrayList<UserRoleModel> arrURole;
    boolean passwordVisible;
    private ActivityResultLauncher<Intent> launcher;
    Uri imgUri = Uri.EMPTY;
    Date now;
    SimpleDateFormat formatter;
    String fileName, IdAuto, IdURole;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrUser = new ArrayList<>();
        arrURole = new ArrayList<>();
        setContentView(R.layout.admin_landlord_add);
        setControl();
        setImage();
        getIDUser();
        getIDUserRole();
        setEvent();

    }

    public void setEvent() {
        crivAccount_ALA.setOnClickListener(click -> {
            selectImage();
        });
        btnThemCT_ALA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInputdata();
            }
        });
        btnBack_ALA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtMatkhau_ALA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showHidePass(motionEvent,txtMatkhau_ALA);
                return false;
            }
        });

    }

    public void getIDUser() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsUser = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsUser.add(dataSnapshot.getKey());
                }
                String[] temp = dsUser.get(dsUser.size() - 1).split("KH");
                String id = "";
                if (Integer.parseInt(temp[1]) < 10) {
                    id = "KH0" + (Integer.parseInt(temp[1]) + 1);
                } else {
                    id = "KH" + (Integer.parseInt(temp[1]) + 1);
                }
                IdAuto = id;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getIDUserRole() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("User_Role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsPost = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsPost.add(dataSnapshot.getKey());
                }
                String[] temp = dsPost.get(dsPost.size() - 1).split("KH");
                String id = "";
                if (Integer.parseInt(temp[1]) < 10) {
                    id = "KH" + (Integer.parseInt(temp[1]) + 1);
                } else {
                    id = "KH" + (Integer.parseInt(temp[1]) + 1);
                }
                IdURole = id;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void selectImage() {
        ImagePicker.Companion.with(AdminLandlordAddActivity.this)
                .crop()
                .cropOval()
                .maxResultSize(512, 512, true)
                .provider(ImageProvider.BOTH)
                .createIntentFromDialog((Function1) (new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        launcher.launch(it);
                    }
                }));
    }
    private void setImage(){
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
            if (result.getResultCode() == RESULT_OK) {
                imgUri = result.getData().getData();
                crivAccount_ALA.setImageURI(imgUri);
            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                openDialogNotify(Gravity.CENTER,"Image fail load",R.layout.layout_dialog_notify);
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
    private void addUserRole(String idURole, String idUser) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("User_Role/" + idURole);
        userRoles = new UserRoleModel("2", idUser);
        userRef.setValue(userRoles, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                openDialogNotifyFinish(Gravity.CENTER,"Thêm thành công tài khoản",R.layout.layout_dialog_notify_finish);
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
    private void addLandlord(String fileName, String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/" + id);
        users = new user(id,
                txtHoten_ALA.getText().toString(),
                txtEmail_ALA.getText().toString(),
                txtSdt_ALA.getText().toString(),
                txtMatkhau_ALA.getText().toString(),
                txtCMND_ALA.getText().toString(),
                fileName,
                "0");
        userRef.setValue(users, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                addUserRole(IdURole, id);
            }
        });
    }

    private void openDialogNotifyYesNo(int gravity, String noidung, int duongdanlayout) {
        final Dialog dialog = new Dialog(this);
        setContentNotify(dialog, gravity, Gravity.CENTER, duongdanlayout);
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

    private void onClickLuuData() {
        if (Uri.EMPTY.equals(imgUri)) {
            fileName = IdAuto + ".jpg";
            imgUri = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.ic_user);
            upImage(fileName,imgUri);
        } else {
            fileName = IdAuto + ".jpg";
            upImage(fileName,imgUri);

        }

    }

    private void upImage(String fileName,Uri imgUri) {
        //update img
        Dialog dialog = new Dialog(this);
        openDialogNotifyNoButton(dialog,Gravity.CENTER,"Uploaded Data...",R.layout.layout_dialog_notify_no_button);
        storageReference = FirebaseStorage.getInstance().getReference("images/user/" + fileName);
        storageReference.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        addLandlord(fileName, IdAuto);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        openDiglogNotify(Gravity.CENTER,"Failed to uploaded image",R.layout.layout_dialog_notify);
                    }
                });


    }

    public void setControl() {
        crivAccount_ALA = findViewById(R.id.crivAccount_ALA);
        txtHoten_ALA = findViewById(R.id.txtHoten_ALA);
        txtCMND_ALA = findViewById(R.id.txtCMND_ALA);
        txtEmail_ALA = findViewById(R.id.txtEmail_ALA);
        txtMatkhau_ALA = findViewById(R.id.txtMatkhau_ALA);
        txtSdt_ALA = findViewById(R.id.txtSdt_ALA);
        btnThemCT_ALA = (Button) findViewById(R.id.btnThemCT_ALA);
        btnBack_ALA = findViewById(R.id.btnBack_ALA);
    }
    private void openDialogNotifyNoButton(final Dialog dialog, int gravity, String noidung, int duongdanlayout) {
        setContentNotify(dialog, gravity, Gravity.BOTTOM, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
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
                finish();
            }
        });
        dialog.show();
    }
    private void openDiglogNotify(int gravity, String noidung, int duongdanlayout) {
        Dialog dialog = new Dialog(this);
        setContentNotify(dialog, gravity, Gravity.CENTER, duongdanlayout);
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


    @SuppressLint("UseCompatLoadingForDrawables")
    public void checkInputdata() {
        Pattern specialChar = Pattern.compile("^.*[#?!@$%^&+*-]+.*$");
        Pattern specialCharPhone = Pattern.compile("^.*[#?!@$%^&*-]+.*$");
        Pattern specialCharEmail = Pattern.compile("^.*[#?!$%^&*-]+.*$");
        Pattern specialString = Pattern.compile("^.*[a-zA-Z]+.*$");
        Pattern specialStringNumber = Pattern.compile("^.*[0-9]+.*$");
        if (txtHoten_ALA.getText().toString().replaceAll(" ", "").length() == 0) {
            txtHoten_ALA.setError("Họ tên không được phép để trống");
        } else if (txtCMND_ALA.getText().toString().replaceAll(" ", "").length() == 0) {
            txtCMND_ALA.setError("CMND không được phép để trống");
        } else if (txtEmail_ALA.getText().toString().replaceAll(" ", "").length() == 0) {
            txtEmail_ALA.setError("Email không được phép để trống");
        } else if (txtSdt_ALA.getText().toString().replaceAll(" ", "").length() == 0) {
            txtSdt_ALA.setError("Phone Number không được phép để trống");
        } else if (specialStringNumber.matcher(txtHoten_ALA.getText().toString()).find() || specialChar.matcher(txtHoten_ALA.getText().toString()).find()) {
            txtHoten_ALA.setError("Họ tên không được phép chứa số hoặc kí tự đặc biệt");
        } else if (specialChar.matcher(txtCMND_ALA.getText().toString()).find() && txtCMND_ALA.getText().toString().length() > 0) {
            txtCMND_ALA.setError("CMND không được phép chứa kí tự đặc biệt");
        }else if (specialCharEmail.matcher(txtEmail_ALA.getText().toString()).find() && txtEmail_ALA.getText().toString().length() > 0) {
            txtEmail_ALA.setError("Email không được phép chứa kí tự đặc biệt");
        }else if (specialCharPhone.matcher(txtSdt_ALA.getText().toString()).find() && txtSdt_ALA.getText().toString().length() > 0) {
            txtSdt_ALA.setError("Phone Number không được phép chứa kí tự đặc biệt");
        } else if (specialString.matcher(txtCMND_ALA.getText().toString()).find() && txtCMND_ALA.getText().toString().length() > 0) {
            txtCMND_ALA.setError("CMND không được phép chứa chữ");
        }else if (specialString.matcher(txtSdt_ALA.getText().toString()).find() && txtSdt_ALA.getText().toString().length() > 0) {
            txtSdt_ALA.setError("Phone Number không được phép chứa chữ");
        } else if (!txtEmail_ALA.getText().toString().contains("@")) {
            txtEmail_ALA.setError("Email sai định dạng thiếu @");
        }else if (txtCMND_ALA.getText().toString().length() < 9) {
            txtCMND_ALA.setError("CMND ít nhất từ 9 - 12 kí tự ");
        } else if (txtEmail_ALA.getText().toString().contains(" ")) {
            txtEmail_ALA.setError("Email không được phép chứa khoảng trắng");
        }else if (txtEmail_ALA.getText().toString().length() < 11) {
            txtEmail_ALA.setError("Email tối thiếu là 11 kí tự ");
        } else if (txtMatkhau_ALA.getText().toString().length() < 6) {
            txtMatkhau_ALA.setError("Mật khẩu tối thiếu là 6 kí tự ");
        } else {
            openDialogNotifyYesNo(Gravity.CENTER, "Bạn có muốn thêm landlord ?", R.layout.layout_dialog_notify_yes_no);
        }
    }
}
