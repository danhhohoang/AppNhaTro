package com.example.appnhatro;

import android.os.Bundle;
import android.util.Log;
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
    ArrayList<Post> li_Merge = new ArrayList<>();
    ArrayList<Post> li_appearance = new ArrayList<>();
    RecyclerView recyclerView;
    ImageButton back,
            btnResetFilter;
    FireBaseTenantSearch fireBaseTenantSearch = new FireBaseTenantSearch();
    TenantSearchAdapter tenantSearchAdapter;
    Spinner spnDistrict,
            spnArea,
            spnPrice,
            spnType;
    TextView textViewEmpty;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_search_layout);
        setControl();
        SpinnerSet();
        setEvent();
        tenantSearchAdapter = new TenantSearchAdapter(this
                , R.layout.item_tenant_search
                , li_Merge);
        fireBaseTenantSearch.getPostsAndPostFindPeopleFormDB(li_Merge, tenantSearchAdapter, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
    public void reTurnPost() {
        //Che bien
        int minPrice;
        int maxPrice;
        int minArea;
        int maxArea;

        switch (spnPrice.getSelectedItemPosition()) {
            case 0:
                minPrice = 0;
                maxPrice = 2000000000;
                break;
            case 1:
                minPrice = 0;
                maxPrice = 1000000;
                break;
            case 2:
                minPrice = 1000000;
                maxPrice = 2000000;
                break;
            case 3:
                minPrice = 2000000;
                maxPrice = 4000000;
                break;
            case 4:
                minPrice = 4000000;
                maxPrice = 6000000;
                break;
            case 5:
                minPrice = 6000000;
                maxPrice = 8000000;
                break;
            case 6:
                minPrice = 8000000;
                maxPrice = 12000000;
                break;
            case 7:
                minPrice = 12000000;
                maxPrice = 18000000;
                break;
            case 8:
                minPrice = 18000000;
                maxPrice = 2000000000;
                break;
            default:
                minPrice = 0;
                maxPrice = 2000000000;
                break;
        }
        switch (spnArea.getSelectedItemPosition()) {
            case 0:
                minArea = 0;
                maxArea = 10000;
                break;
            case 1:
                minArea = 0;
                maxArea = 20;
                break;
            case 2:
                minArea = 20;
                maxArea = 30;
                break;
            case 3:
                minArea = 30;
                maxArea = 40;
                break;
            case 4:
                minArea = 40;
                maxArea = 50;
                break;
            case 5:
                minArea = 50;
                maxArea = 10000;
                break;
            default:
                minArea = 0;
                maxArea = 10000;
                break;
        }

        String DistrictName = spnDistrict.getSelectedItem().toString();
        int DistrictPos = spnDistrict.getSelectedItemPosition();
        int spnTypePos = spnType.getSelectedItemPosition();
        String searchString = searchView.getQuery().toString().toLowerCase();

        li_appearance.clear();
        for (Post post : li_Merge) {
            //Trường hợp 1: người dùng chọn cả 2 loại phòng
            if (spnTypePos == 0) {
                //Trường hợp 1.1: người dùng không chọn quận
                if (DistrictPos == 0) {
                    if (Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchString)) {
                        li_appearance.add(post);
                    }
                }
                //Trường hợp 1.2: người dùng chọn quận
                else {
                    if (post.getAddress_district().equalsIgnoreCase(DistrictName) && Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchString)) {
                        li_appearance.add(post);
                    }
                }
            }
            //Trường hợp 2: người dùng chọn loại phòng là "chỉ thuê mới"
            if (spnTypePos == 1) {
                boolean kiemTraLoaiPhong = post.getId().contains("tenant");
                //Trường hợp 2.1: người dùng không chọn quận
                if (DistrictPos == 0) {

                    if (!kiemTraLoaiPhong && Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchString)) {
                        li_appearance.add(post);
                    }
                }
                //Trường hợp 2.2: người dùng chọn quận
                else {
                    if (!kiemTraLoaiPhong
                            && post.getAddress_district().equalsIgnoreCase(DistrictName)
                            && Integer.parseInt(post.getArea()) >= minArea
                            && Integer.parseInt(post.getArea()) <= maxArea
                            && Integer.parseInt(post.getPrice()) >= minPrice
                            && Integer.parseInt(post.getPrice()) <= maxPrice
                            && post.getHouse_name().toLowerCase().contains(searchString)) {
                        li_appearance.add(post);
                    }
                }
            }
            //Trường hợp 2: người dùng chọn loại phòng là "chỉ ở ghép"
            if (spnTypePos == 2) {
                //Trường hợp 2.1: người dùng không chọn quận
                boolean kiemTraLoaiPhong = post.getId().contains("tenant");
                if (DistrictPos == 0) {

                    if (kiemTraLoaiPhong && Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchString)) {
                        li_appearance.add(post);
                    }
                }
                //Trường hợp 2.2: người dùng chọn quận
                else {
                    if (kiemTraLoaiPhong && post.getAddress_district().equalsIgnoreCase(DistrictName) && Integer.parseInt(post.getArea()) >= minArea && Integer.parseInt(post.getArea()) <= maxArea && Integer.parseInt(post.getPrice()) >= minPrice && Integer.parseInt(post.getPrice()) <= maxPrice && post.getHouse_name().toLowerCase().contains(searchString)) {
                        li_appearance.add(post);
                    }
                }
            }
        }
        tenantSearchAdapter = new TenantSearchAdapter(this
                , R.layout.item_tenant_search
                , li_appearance);
        recyclerView.setAdapter(tenantSearchAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(tenantSearchAdapter);

    }

    public void setData(){
        tenantSearchAdapter = new TenantSearchAdapter(this
                , R.layout.item_tenant_search
                , li_Merge);
        recyclerView.setAdapter(tenantSearchAdapter);
    }

    public void setEvent() {
        //Event cua rieng spinner se duoc dat tai SpinnerSet
        btnResetFilter.setOnClickListener(v -> {
            ClearFilter();
        });

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    reTurnPost();
                    recyclerView.setAdapter(tenantSearchAdapter);
                } else {
                    reTurnPost();
                    recyclerView.setAdapter(tenantSearchAdapter);
                }

                return true;
            }
        });
        back.setOnClickListener(view -> finish());

    }

    public void setControl() {
        //button
        btnResetFilter = findViewById(R.id.imb_resetFilter);
        back = findViewById(R.id.imb_back);

        // Searchview
        searchView = findViewById(R.id.search_bar);

        //Spinner
        spnDistrict = findViewById(R.id.spn_district);
        spnArea = findViewById(R.id.spn_area);
        spnPrice = findViewById(R.id.spn_price);
        spnType = findViewById(R.id.spn_type);
        //ETC
        textViewEmpty = findViewById(R.id.tvDoNotData);
        recyclerView = findViewById(R.id.recycler_tenant_search);


        //btnResetFilter = findViewById(R.id.imb_resetFilter);
    }

    public void SpinnerSet() {
        ArrayAdapter<String> districAdapter = new ArrayAdapter<>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_district));
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_area));
        ArrayAdapter<String> priceAdapter = new ArrayAdapter<>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_price));
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_Kieu_Thue_phong));
        districAdapter.setDropDownViewResource(R.layout.text_spinner);
        areaAdapter.setDropDownViewResource(R.layout.text_spinner);
        priceAdapter.setDropDownViewResource(R.layout.text_spinner);
        typeAdapter.setDropDownViewResource(R.layout.text_spinner);

        spnDistrict.setAdapter(districAdapter);
        spnArea.setAdapter(areaAdapter);
        spnPrice.setAdapter(priceAdapter);
        spnType.setAdapter(typeAdapter);

        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reTurnPost();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reTurnPost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reTurnPost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reTurnPost();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void ClearFilter(){
        spnArea.setSelection(0);
        spnPrice.setSelection(0);
        spnDistrict.setSelection(0);
        spnType.setSelection(0);
        searchView.setQuery("", false);
        tenantSearchAdapter = new TenantSearchAdapter(this
                , R.layout.item_tenant_search
                , li_Merge);
        recyclerView.setAdapter(tenantSearchAdapter);
    }

}