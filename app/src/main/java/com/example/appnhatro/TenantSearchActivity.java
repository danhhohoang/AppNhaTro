package com.example.appnhatro;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.TenantSearchAdapter;
import com.example.appnhatro.Firebase.FireBaseTenantSearch;
import com.example.appnhatro.Models.Post;

import java.util.ArrayList;


public class TenantSearchActivity extends AppCompatActivity {

    ArrayList<Post> li_Post = new ArrayList<>();
    ArrayList<Post> listTemp = new ArrayList<>();

    RecyclerView recyclerView;
    ImageButton back;
    FireBaseTenantSearch fireBaseTenantSearch = new FireBaseTenantSearch();

    TenantSearchAdapter tenantSearchAdapter;

    Spinner spnDistrict,
            spnArea,
            spnPrice;

    TextView textViewEmpty;
    Button btnSearch;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_search_layout);

        //mapping
        setControl();
        SpinnerSet();
        setEvent();


        //Call data form Firebase to recyclerview


        tenantSearchAdapter = new TenantSearchAdapter(this
                , R.layout.item_tenant_search
                , li_Post);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        fireBaseTenantSearch.getPostsFormDB(li_Post, tenantSearchAdapter);
        back = findViewById(R.id.imb_back);

        recyclerView.setAdapter(tenantSearchAdapter);
        //


        tenantSearchAdapter.setOnItemClickListener(new TenantSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                //mo man hinh chi tiet phong tro
            }
        });

        //
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    li_Post.clear();

                    reTurnPost();
                } else {
                    li_Post.clear();
                    reTurnPost();
                    recyclerView.setAdapter(tenantSearchAdapter);
                }

                return true;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void reTurnPost(){
        fireBaseTenantSearch.reTurnPostByFilter(li_Post
                , tenantSearchAdapter
                , spnDistrict.getSelectedItem().toString()
                , spnDistrict.getSelectedItemPosition()
                , spnPrice.getSelectedItemPosition()
                , spnArea.getSelectedItemPosition()
                , searchView.getQuery().toString().toLowerCase()
        );

    }

    public void setEvent() {

        //Event cho nut tim kiem
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                li_Post.clear();

                reTurnPost();
                //recyclerView.setAdapter(cloneAdapter);

                //--Đây là đoạn code để kiểm tra kết quả tìm kiếm, nếu kết quả tìm kiếm là null,
                //--hiện đoạn text thông báo
//                if(li_Post.isEmpty()){
//                    recyclerView.setVisibility(View.GONE);
//                    textViewEmpty.setVisibility(View.VISIBLE);
//                }else{
//                    recyclerView.setVisibility(View.VISIBLE);
//                    textViewEmpty.setVisibility(View.GONE);
//                }

            }
        });

    }

    public void setControl() {
        //item

        // Searchview
        searchView = findViewById(R.id.search_bar);

        //Spinner
        spnDistrict = findViewById(R.id.spn_district);
        spnArea = findViewById(R.id.spn_area);
        spnPrice = findViewById(R.id.spn_price);

        //ETC
        textViewEmpty = findViewById(R.id.tvDoNotData);
        recyclerView = findViewById(R.id.recycler_tenant_search);
        btnSearch = findViewById(R.id.btn_search);
        //btnResetFilter = findViewById(R.id.imb_resetFilter);
    }

    public void SpinnerSet() {

        ArrayAdapter districAdapter = new ArrayAdapter<String>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_district));
        ArrayAdapter areaAdapter = new ArrayAdapter<String>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_area));
        ArrayAdapter priceAdapter = new ArrayAdapter<>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_price));

        districAdapter.setDropDownViewResource(R.layout.text_spinner);
        areaAdapter.setDropDownViewResource(R.layout.text_spinner);
        priceAdapter.setDropDownViewResource(R.layout.text_spinner);

        spnDistrict.setAdapter(districAdapter);
        spnArea.setAdapter(areaAdapter);
        spnPrice.setAdapter(priceAdapter);


        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

}