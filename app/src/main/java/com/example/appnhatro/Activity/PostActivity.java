package com.example.appnhatro.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.R;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity{
    Spinner spnStatus, spnSex, spnSl;
    RecyclerView recyclerView;
    TextView textView;
    Button UpLoading;

    ArrayList<Uri> uri= new ArrayList<>();
    RecylerAdapter adapter;

    private static final int Read_Permission = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        setSpinner();

        textView = findViewById(R.id.totalphoto);
        recyclerView = findViewById(R.id.recylerview);
        UpLoading = findViewById(R.id.selectImagebtn);

        adapter = new RecylerAdapter(uri);
        recyclerView.setLayoutManager(new GridLayoutManager(PostActivity.this, 3));
        recyclerView.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PostActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_Permission);
        }
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
        if (requestCode == 1 && requestCode == Activity.RESULT_OK){
            if (data.getClipData() != null){
                int x = data.getClipData().getItemCount();
                for (int i = 0; i < x; i++){
                    uri.add(data.getClipData().getItemAt(i).getUri());
                }
                adapter.notifyDataSetChanged();
                textView.setText("Photos ("+uri.size()+")");
            }else if (data.getData() != null){
                String imageURL = data.getData().getPath();
                uri.add(Uri.parse(imageURL));
            }
        }
    }

    private void setSpinner() {
        spnStatus = findViewById(R.id.spntinhtrang);
        spnSex = findViewById(R.id.spngioitinh);
        spnSl = findViewById(R.id.spnSl);


        ArrayAdapter spnStatusAdapter = new ArrayAdapter<String>(this,  androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.string_status));

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
