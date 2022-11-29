package com.example.appnhatro.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;
import com.example.appnhatro.TenantPostList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class DetailPostUpdateList extends AppCompatActivity {
    EditText housename,title,address,address_district,area,price;
    Button cancel,update;
    ImageView image;
    Uri imageUri = Uri.EMPTY;
    StorageReference storageReference;
    String id,userID,image0,image1,image2,status;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_update_post);
        setControl();
        openFolder();
        setText();
    }

    private void setText() {
        String ex_housename = getIntent().getStringExtra("it_housename");
        String ex_title = getIntent().getStringExtra("it_title");
        String ex_address = getIntent().getStringExtra("it_address");
        String ex_address_district = getIntent().getStringExtra("it_address_district");
        String ex_area = getIntent().getStringExtra("it_area");
        String ex_price = getIntent().getStringExtra("it_price");
        image0 = getIntent().getStringExtra("it_image");
        image1 = getIntent().getStringExtra("it_image1");
        image2 = getIntent().getStringExtra("it_image2");
        id = getIntent().getStringExtra("it_id");
        status = getIntent().getStringExtra("it_status");
        userID = getIntent().getStringExtra("it_userID");

        housename.setText(ex_housename);
        title.setText(ex_title);
        address.setText(ex_address);
        address_district.setText(ex_address_district);
        area.setText(ex_area);
        price.setText(ex_price);
        setAvatarUser(image,image0);
    }

    private void setControl() {
        housename = findViewById(R.id.txt_dupHousename);
        title = findViewById(R.id.txt_dupTitle);
        address = findViewById(R.id.txt_dupAddress);
        address_district = findViewById(R.id.txt_dupAddressDistrict);
        area = findViewById(R.id.txt_dupArea);
        price = findViewById(R.id.txt_dupPrice);
        image = findViewById(R.id.iv_dupImage);
        cancel = findViewById(R.id.btn_dupCancel);
        update = findViewById(R.id.btn_dupUpdate);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(housename.getText().toString())){
                    housename.setError("Trường tên nhà không được bỏ trống");
                    return;
                }
                if (TextUtils.isEmpty(title.getText().toString())){
                    title.setError("Trường tiêu đề không được bỏ trống");
                    return;
                }
                if (TextUtils.isEmpty(address.getText().toString())){
                    address.setError("Trường địa chỉ không được bỏ trống");
                    return;
                }
                if (TextUtils.isEmpty(address_district.getText().toString())){
                    address_district.setError("Trường địa chỉ quận không được bỏ trống");
                    return;
                }
                if (TextUtils.isEmpty(price.getText().toString())){
                    price.setError("Trường giá không được bỏ trống");
                    return;
                }
                if (TextUtils.isEmpty(area.getText().toString())){
                    area.setError("Trường diện tích không được bỏ trống");
                    return;
                }
                setOnClick();
            }
        });

    }

    public static final void setAvatarUser(ImageView imageView, String avatar) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/"+avatar);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data!=null && data.getData() != null) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    public void openFolder(){
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
        startActivityForResult(intent,100);
    }


    private void setOnClick(){
        Handler handler = new Handler();
        final Dialog dialog = new Dialog(DetailPostUpdateList.this);
        openDialogNotifyNoButton(dialog, Gravity.CENTER,"Sửa bài đăng thành công",R.layout.layout_dialog_notify_no_button);
        storageReference = FirebaseStorage.getInstance().getReference("images/post/"+id+".jpg");
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
        String ad = housename.getText().toString();
        String cd = title.getText().toString();
        String cn = address.getText().toString();
        String des = address_district.getText().toString();
        String exp = area.getText().toString();
        String rk = price.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Post_Oghep/"+id);
        Post post = new Post(id,userID,cd,cn,des,rk,exp,ad,image0,status,image1,image2);
        databaseReference.setValue(post);
        if (dialog.isShowing()){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Intent intent = new Intent(DetailPostUpdateList.this, TenantPostList.class);
                    startActivity(intent);
                    finish();
                }
            },2000);
        }
    }

    public static final void setContentNotify(final Dialog dialog, int gravity, int truefalse, int duongdanlayout) {
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

    private void openDialogNotifyNoButton(final Dialog dialog, int gravity, String noidung, int duongdanlayout) {
        setContentNotify(dialog, gravity, Gravity.BOTTOM, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
    }
}
