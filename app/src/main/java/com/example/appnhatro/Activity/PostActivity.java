package com.example.appnhatro.Activity;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
public class PostActivity extends AppCompatActivity {
    private FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    private ConverImage converImage = new ConverImage();
    Spinner spnStatus, spnSex, spnSl;
    ImageView imgPhoTo;
    Button UpData, Huy;
    EditText Diachi, SDT, DoTuoi, Den, gia, YeuCauKhac;
    Uri uri;
    String idPost = "", iduser;
    int stt = 1;
    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<TransactionModel> transactionModels = new ArrayList<>();
    //firebase
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Image");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        setSpinner();
        setIntent();


        imgPhoTo = findViewById(R.id.imageView);
        UpData = findViewById(R.id.uploadimagebtn);
        Diachi = findViewById(R.id.edtdiachi);
        SDT = findViewById(R.id.edtPhoneMunber);
        DoTuoi = findViewById(R.id.edtxdotuoi);
        Den = findViewById(R.id.edtxden);
        gia = findViewById(R.id.edtxgia);
        YeuCauKhac = findViewById(R.id.edtyeuccaukhac);
        Huy = findViewById(R.id.bthuy);


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
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference2 = firebaseDatabase.getReference("HistoryTransaction");
                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
                                    if (transactionModel.getId_user().equals("KH02")){
                                        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                    Post post = dataSnapshot.getValue(Post.class);
                                                    if (post.getId().equals(transactionModel.getPost())){
                                                        posts.add(post);
                                                    }
                                                    if (posts.size() >= 1){
                                                        final Dialog dialog = new Dialog(PostActivity.this);
                                                        opendialog(dialog, Gravity.CENTER, "Load image....",R.layout.layout_dialog_notify_no_button);
                                                        Post post1 = new Post(idPost, "KH01", YeuCauKhac.getText().toString(), SDT.getText().toString(), DoTuoi.getText().toString(),
                                                                gia.getText().toString(),Den.getText().toString(),
                                                                Diachi.getText().toString(), idPost + ".jpg", spnStatus.getSelectedItem().toString());
                                                        addToFavorite(post1);
                                                        String str = idPost+".jpg";
                                                        storageReference = FirebaseStorage.getInstance().getReference("images/post/" + str);
                                                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                if (dialog.isShowing()){
                                                                    dialog.dismiss();
                                                                    openDialogNotifyFinish(Gravity.CENTER, "Đăng bài thành công", R.layout.layout_dialog_notify_finish);
                                                                }
                                                            }
                                                        });
                                                        finish();

                                                    }else if (posts.size() == 0){

                                                        AlertDialog.Builder c = new AlertDialog.Builder(PostActivity.this);
                                                        c.setTitle("Thông Báo");
                                                        c.setMessage("Bạn chưa thuê phòng nên chưa được đăng bài tìm người ở ghép");
                                                        c.setPositiveButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                finish();
                                                                dialogInterface.cancel();
                                                            }
                                                        });
                                                    }
                                                }

                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


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
                Intent y = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);

            }
        });
        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(PostActivity.this);
                b.setTitle("Thông Báo");
                b.setMessage("Xác nhận hủy Report?");
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
    }

    public void ThongBaoThanhCong(){
        AlertDialog.Builder b = new AlertDialog.Builder( PostActivity.this);
        b.setTitle("Thông báo");
        b.setMessage("Bạn đã đăng bài thành công");
        b.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode == RESULT_OK && null != data){
            uri  =  data.getData();
            imgPhoTo.setImageURI(uri);

        }
    }
    private void openDialogNotifyFinish(int gravity, String noidung, int duongdanlayout) {
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

    private void opendialog(final Dialog dialog, int garavity, String noidung, int Duongdan){
        setContentNotify(dialog, garavity, Gravity.BOTTOM, Duongdan);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
    }

    private void addToFavorite(Post post) {
        Log.d("text", post.getId());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post_Oghep");
        databaseReference.child(post.getId()).setValue(post, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                ThongBaoThanhCong();
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
