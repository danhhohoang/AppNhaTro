package com.example.appnhatro.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.Dangbaimodels;
import com.example.appnhatro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    Spinner spnStatus, spnSex, spnSl;
    RecyclerView recyclerView;
    TextView textView;
    Button UpLoading, UpData;
    EditText Diachi, SDT, DoTuoi, Den, gia, YeuCauKhac;
    RecyclerView picture;

    ArrayList<Uri> uri = new ArrayList<>();
    RecylerAdapter adapter;

    private static final int Read_Permission = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        setSpinner();


        textView = findViewById(R.id.totalphoto);
        UpLoading = findViewById(R.id.selectImagebtn);
        UpData = findViewById(R.id.uploadimagebtn);
        Diachi = findViewById(R.id.edtdiachi);
        SDT = findViewById(R.id.edtPhoneMunber);
        DoTuoi = findViewById(R.id.edtxdotuoi);
        Den = findViewById(R.id.edtxden);
        gia = findViewById(R.id.edtxgia);
        YeuCauKhac = findViewById(R.id.edtyeuccaukhac);
        picture = findViewById(R.id.Recylerview);


        adapter = new RecylerAdapter(uri);
        picture.setLayoutManager(new GridLayoutManager(PostActivity.this, 4));
        picture.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PostActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_Permission);
        }
        //Tải dữ liệu lên firebase
        UpData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dangbaimodels post = new Dangbaimodels("P_tenant_04", "Pt04", spnStatus.getSelectedItem().toString(),
                        Diachi.getText().toString(), SDT.getText().toString(),
                        spnSex.getSelectedItem().toString(),
                        DoTuoi.getText().toString(),
                        Den.getText().toString(),
                        spnSl.getSelectedItem().toString(),
                        gia.getText().toString(),
                        YeuCauKhac.getText().toString(), "hinh Iphone");
                addToFavorite(post);

            }
        });
        UpLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && requestCode == Activity.RESULT_OK) {
            if (data.getClipData() != null) {
                int x = data.getClipData().getItemCount();
                for (int i = 0; i < x; i++) {
                    uri.add(data.getClipData().getItemAt(i).getUri());
                }
                adapter.notifyDataSetChanged();
                textView.setText("Photos (" + uri.size() + ")");
            } else if (data.getData() != null) {
                String imageURL = data.getData().getPath();
                uri.add(Uri.parse(imageURL));
            }
        }
    }

    private void addToFavorite(Dangbaimodels post) {
        Log.d("text", post.getId());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post_Oghep");
        databaseReference.child(post.getId()).setValue(post);
    }

    private void setSpinner() {
        spnStatus = findViewById(R.id.spntinhtrang);
        spnSex = findViewById(R.id.spngioitinh);
        spnSl = findViewById(R.id.spnSl);


        ArrayAdapter spnStatusAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_status));

        ArrayAdapter spnSexAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_sex));

        ArrayAdapter spnSLAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_amount));

        spnStatus.setAdapter(spnStatusAdapter);
        spnSex.setAdapter(spnSexAdapter);
        spnSl.setAdapter(spnSLAdapter);

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
}
