package com.example.appnhatro.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.example.appnhatro.tool.ConverImage;
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
import java.util.ArrayList;
public class PostActivity extends AppCompatActivity {
    private FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    private ConverImage converImage = new ConverImage();
    private Uri uri[]= new Uri[3];
    Spinner spnStatus;
    ImageView imgPhoTo, imgPhoTo1, imgPhoTo2, hinh, back;
    Button UpData, Huy;
    EditText Diachi, SDT, DoTuoi, Den, gia, YeuCauKhac;
    String idPost = "", iduser;
    SharedPreferences sharedPreferences;
    int stt = 1;
    EditText tieude, diachi, quan, dientich, Gia;
    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<TransactionModel> transactionModels = new ArrayList<>();
    //firebase
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Image");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        getinfo();
        setSpinner();
        setIntent();

        imgPhoTo = findViewById(R.id.imgAddNewPostTenant);
        imgPhoTo1 = findViewById(R.id.imgAddNewPostTenant1);
        imgPhoTo2 = findViewById(R.id.imgAddNewPostTenant2);
        UpData = findViewById(R.id.uploadimagebtn);
        Diachi = findViewById(R.id.edtdiachi);
        SDT = findViewById(R.id.edtPhoneMunber);
        DoTuoi = findViewById(R.id.edtxdotuoi);
        Den = findViewById(R.id.edtxden);
        gia = findViewById(R.id.edtxgia);
        YeuCauKhac = findViewById(R.id.edtyeuccaukhac);
        Huy = findViewById(R.id.bthuy);
        back = findViewById(R.id.btn_postback);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        iduser = sharedPreferences.getString("idUser", "");
        diachi = findViewById(R.id.edtPhoneMunber);
        tieude = findViewById(R.id.edtdiachi);
        quan = findViewById(R.id.edtxdotuoi);
        dientich = findViewById(R.id.edtxden);
        Gia = findViewById(R.id.edtxgia);

        //Tải dữ liệu lên firebase
        UpData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a = new AlertDialog.Builder(PostActivity.this);
                a.setTitle("Thông Báo");
                a.setMessage("Bạn có muốn đăng bài");
                a.setPositiveButton("Đăng bài", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Post post1 = new Post(idPost, iduser, YeuCauKhac.getText().toString(), SDT.getText().toString(), DoTuoi.getText().toString(),
                                gia.getText().toString(),Den.getText().toString(),
                                Diachi.getText().toString(), idPost + ".jpg", "Còn phòng",idPost + "_1.jpg",idPost + "_2.jpg");
                        addToFavorite(post1,uri);
                    }
                });
                a.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog al = a.create();
                al.show();
            }
        });
        imgPhoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);

            }
        });
        imgPhoTo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 2);
            }
        });
        imgPhoTo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 3);
            }
        });
        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(PostActivity.this);
                b.setTitle("Thông Báo");
                b.setMessage("Xác nhận hủy bài đăng?");
                b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && null != data && data.getData() != null) {
            uri[0] = data.getData();
            imgPhoTo.setImageURI(uri[0]);
        }
        if (requestCode == 2 && null != data && data.getData() != null) {
            uri[1] = data.getData();
            imgPhoTo1.setImageURI(uri[1]);
        }
        if (requestCode == 3 && null != data && data.getData() != null) {
            uri[2] = data.getData();
            imgPhoTo2.setImageURI(uri[2]);
        }
    }

    private void getinfo() {
        String idtt = getIntent().getStringExtra("it_id");
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post.getId().equals(idtt)) {
                        tieude.setText(post.getHouse_name());
                        diachi.setText(post.getAddress());
                        quan.setText(post.getAddress_district());
                        dientich.setText(post.getArea());
                        setAvatar(imgPhoTo, post.getImage());
                        setAvatar(imgPhoTo1, post.getImage1());
                        setAvatar(imgPhoTo2, post.getImage2());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static final void setAvatar(ImageView imageView, String avatar) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/post/" + avatar);
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
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addToFavorite(Post post,Uri[] uri) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post_Oghep");
        databaseReference.child(post.getId()).setValue(post, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                fireBaseThueTro.addNewPosttn(PostActivity.this, post, uri);
            }
        });
    }

    private void setSpinner() {
        spnStatus = findViewById(R.id.spntinhtrang);


        ArrayAdapter spnStatusAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_status));

        ArrayAdapter spnSexAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_sex));

        ArrayAdapter spnSLAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_amount));

        spnStatus.setAdapter(spnStatusAdapter);


        spnStatusAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnSexAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnSLAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);


        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PostActivity.this, spnStatus.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        fireBaseThueTro.IdPost(PostActivity.this);
    }
    private void setIntent() {
        Intent intent = this.getIntent();
    }
    public void setID(String id){
        idPost = id;
    }
}
