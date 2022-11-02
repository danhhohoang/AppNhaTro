package com.example.appnhatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appnhatro.Adapters.TenantPostSearchAdapter;
import com.example.appnhatro.FakeModels.PostFake;

import java.util.ArrayList;
import java.util.List;

public class TenantSearchActivity extends AppCompatActivity {

    RecyclerView rv_Post;
    ArrayList<PostFake> li_Post;
    TenantPostSearchAdapter adp_Post;
    Spinner spnDistric;
    Spinner spnArea;
    Spinner spnPrice;
    Button btnSearch;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_search_layout);


        //mapping
        searchView = findViewById(R.id.search_bar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillterList(newText);

                return true;
            }
        });

        btnSearch = findViewById(R.id.btn_search);
        rv_Post = findViewById(R.id.recycler_tenant_search);
        li_Post = new ArrayList<>();
        //
        PostFake post1 = new PostFake("căn hộ chung cư cao cấp ở quận 9 - giá bình dân cực hạt dẻ thích hợp cho sinh viên thuê nhưng là sinh viên RMIT", "s1.01 Vinhomes Grandpark, Nguyễn Xiển, Long Thạnh Mỹ, Thành Phố Thủ Đức, Hồ Chí Minh, Việt Nam",
                "string", "string", 45, 12000000, "Cho Thuê");
        PostFake post2 = new PostFake("Tieu de 2", "Dia chi 1",
                "string", "string", 20, 2, "Status2");
        PostFake post3 = new PostFake("Tieu de 3", "Dia chi 1",
                "string", "string", 30, 3, "Status3");
        PostFake post4 = new PostFake("Tieu de 4", "Dia chi 1",
                "string", "string", 40, 4, "Status4");
        PostFake post5 = new PostFake("Tieu de 5", "Dia chi 1",
                "string", "string", 50, 5, "Status5");
        PostFake post6 = new PostFake("Tieu de 6", "Dia chi 1",
                "string", "string", 60, 6, "Status6");
        PostFake post7 = new PostFake("Tieu de 7", "Dia chi 1",
                "string", "string", 70, 7, "Status7");

        li_Post.add(post1);
        li_Post.add(post2);
        li_Post.add(post3);
        li_Post.add(post4);
        li_Post.add(post5);
        li_Post.add(post6);
        li_Post.add(post7);
        //

        adp_Post = new TenantPostSearchAdapter(li_Post);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_Post.setLayoutManager(linearLayoutManager);
        rv_Post.setAdapter(adp_Post);

        SpinnerSet();
    }

    private void SpinnerSet() {
        spnDistric = findViewById(R.id.spn_district);
        spnArea = findViewById(R.id.spn_area);
        spnPrice = findViewById(R.id.spn_price);

        ArrayAdapter districAdapter = new ArrayAdapter<String>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_district));
        ArrayAdapter areaAdapter = new ArrayAdapter<String>(this,R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_area));
        ArrayAdapter priceAdapter = new ArrayAdapter<>(this,R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_price));

        districAdapter.setDropDownViewResource(R.layout.text_spinner);
        areaAdapter.setDropDownViewResource(R.layout.text_spinner);
        priceAdapter.setDropDownViewResource(R.layout.text_spinner);

        spnDistric.setAdapter(districAdapter);
        spnArea.setAdapter(areaAdapter);
        spnPrice.setAdapter(priceAdapter);


        spnDistric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(TenantSearchActivity.this, String.valueOf(spnDistric.getSelectedItemPosition()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TenantSearchActivity.this, spnArea.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TenantSearchActivity.this, spnPrice.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fillterList(String text) {
        List<PostFake> fillterList = new ArrayList<>();

        for (PostFake item : li_Post)
        {
            if (item.getPostTitle().toLowerCase().contains(text.toLowerCase())) {
                fillterList.add(item);
            }

        }
        if(fillterList.isEmpty()){
            Toast.makeText(this, "Không tìm thấy kết quả", Toast.LENGTH_LONG).show();
        }else {
            adp_Post.setFillterList(fillterList);
        }
    }
}