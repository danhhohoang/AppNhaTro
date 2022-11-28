package com.example.appnhatro.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Firebase.FireBaseTenantList;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AdminTenantDetaiActivity extends AppCompatActivity {
    private String idUser;
    private ArrayList<user> userArrayList = new ArrayList<>();
    FireBaseTenantList fireBaseTenantList = new FireBaseTenantList();

    EditText edtID,
            edtName,
            edtPhone,
            edtCitizenNumber,
            edtEmail,
            edtPassword;
    TextView txtStatus;
    ImageView imgback,
            imgAvatar;
    Button btnLock,
            btnEdit,
            btnDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_tenant_details_layout);
        setControl();
        setEvent();
        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");


    }

    @Override
    protected void onResume() {
        super.onResume();
        fireBaseTenantList.getSingleUser(AdminTenantDetaiActivity.this, userArrayList, idUser);
//
    }

    private void setEvent() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //khóa Account
        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnLock.getText().toString().equalsIgnoreCase("KHOÁ"))
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(AdminTenantDetaiActivity.this).create();
                        alertDialog.setTitle("Yêu cầu xác nhận");
                        alertDialog.setMessage("Bạn có chắc muốn khóa tài khoản này chứ?");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fireBaseTenantList.lockUserAccount(AdminTenantDetaiActivity.this, idUser);
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                    }else if(btnLock.getText().toString().equalsIgnoreCase("MỞ KHÓA")){
                    AlertDialog alertDialog = new AlertDialog.Builder(AdminTenantDetaiActivity.this).create();
                    alertDialog.setTitle("Yêu cầu xác nhận");
                    alertDialog.setMessage("Bạn có chắc muốn mở khóa cho tài khoản này chứ?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fireBaseTenantList.unLockUserAccount(AdminTenantDetaiActivity.this, idUser);
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(AdminTenantDetaiActivity.this).create();
                    alertDialog.setTitle("Thông báo");
                    alertDialog.setMessage("Có lỗi đã xảy ra");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                           }
                    });
                    alertDialog.show();
                }
            }
        });
        //sửa account
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(AdminTenantDetaiActivity.this).create();
                    alertDialog.setTitle("Yêu cầu xác nhận");
                    alertDialog.setMessage("Bạn có chắc muốn thay đổi thông tin tài khoản này chứ?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            user _user = new user();

                            if (txtStatus.getText().equals("Trạng thái: đang hoạt động")) {
                                _user.setStatus("0");

                            } else if (txtStatus.getText().equals("Trạng thái: Bị khóa")) {
                                _user.setStatus("1");
                            } else {
                                _user.setStatus("2");
                            }
                            _user.setId(idUser);
                            _user.setAvatar(idUser);
                            _user.setName(edtName.getText().toString());
                            _user.setCitizenNumber(edtCitizenNumber.getText().toString());
                            _user.setId(edtID.getText().toString());
                            _user.setEmail(edtEmail.getText().toString());
                            _user.setPhone(edtPhone.getText().toString());
                            _user.setPhone(edtPhone.getText().toString());
                            _user.setPassword(edtPassword.getText().toString());
                            fireBaseTenantList.setInfoUser(AdminTenantDetaiActivity.this, _user);

                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(AdminTenantDetaiActivity.this).create();
                alertDialog.setTitle("Yêu cầu xác nhận");
                alertDialog.setMessage("Bạn có chắc muốn xóa tài khoản này chứ?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fireBaseTenantList.removeUser(AdminTenantDetaiActivity.this, idUser);
                        finish();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    public void setInfor(user _user) {
        edtID.setText(_user.getId());
        edtName.setText(_user.getName());
        edtPhone.setText(_user.getPhone());
        edtCitizenNumber.setText(_user.getCitizenNumber());
        edtEmail.setText(_user.getEmail());
        edtPassword.setText(_user.getPassword());
        if (_user.getStatus().equals("0")) {
            txtStatus.setText("Trạng thái: đang hoạt động");
            btnLock.setText("KHOÁ");
        } else if (_user.getStatus().equals("1")) {
            txtStatus.setText("Trạng thái: Bị khóa");
            btnLock.setText("MỞ KHÓA");
        } else {
            txtStatus.setText("Trạng thái: không xác định");
        }

        //lay hinh
        BitMap bitMap = new BitMap(_user.getAvatar(), null);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + bitMap.getTenHinh());

        try {
            final File file = File.createTempFile(bitMap.getTenHinh(), "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    imgAvatar.setImageBitmap(bitMap.getHinh());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setControl() {
        btnDelete = findViewById(R.id.admin_tenant_details_layout_delete);
        btnEdit = findViewById(R.id.admin_tenant_details_layout_edit);
        btnLock = findViewById(R.id.admin_tenant_details_layout_lock);
        edtID = findViewById(R.id.admin_tenant_details_layout_id);
        edtName = findViewById(R.id.admin_tenant_details_layout_name);
        edtPhone = findViewById(R.id.admin_tenant_details_layout_phone);
        edtCitizenNumber = findViewById(R.id.admin_tenant_details_layout_citizenNumber);
        edtEmail = findViewById(R.id.admin_tenant_details_layout_email);
        edtPassword = findViewById(R.id.admin_tenant_details_layout_password);
        imgAvatar = findViewById(R.id.admin_tenant_details_layout_avatar);
        imgback = findViewById(R.id.admin_tenant_details_layout_back);
        txtStatus = findViewById(R.id.admin_tenant_details_layout_status);
    }

    private boolean Validate() {

        boolean boolEdtName = !edtName.getText().toString().isEmpty();
        boolean boolEdtPhone = !edtPhone.getText().toString().isEmpty();
        boolean boolEdtCitizenNumber = !edtCitizenNumber.getText().toString().isEmpty();
        boolean boolEdtEmail = !edtEmail.getText().toString().isEmpty();
        boolean boolEdtPassword = !edtPassword.getText().toString().isEmpty();

        if (!boolEdtName) {
            edtName.setError("Thông tin không được bỏ trống");
        }
        if (!boolEdtPhone) {
            edtPhone.setError("Thông tin không được bỏ trống");
        }
        if (!boolEdtCitizenNumber) {
            edtCitizenNumber.setError("Thông tin không được bỏ trống");
        }
        if (!boolEdtEmail) {
            edtEmail.setError("Thông tin không được bỏ trống");
        }
        if (!boolEdtPassword) {
            edtPassword.setError("Thông tin không được bỏ trống");
        }
        return boolEdtName && boolEdtPhone && boolEdtCitizenNumber && boolEdtEmail && boolEdtPassword;
    }
}
