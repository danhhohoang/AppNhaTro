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
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhatro.Adapters.TenantPostSearchAdapter;
import com.example.appnhatro.Adapters.TenantSearchAdapter;
import com.example.appnhatro.FakeModels.PostFake;
import com.example.appnhatro.Firebase.FireBaseTenantSearch;
import com.example.appnhatro.Models.Post;

import java.util.ArrayList;
import java.util.List;

public class TenantSearchActivity extends AppCompatActivity {


    ArrayList<Post> li_Post;
    FireBaseTenantSearch fireBaseTenantSearch = new FireBaseTenantSearch();
    TenantSearchAdapter tenantSearchAdapter;
    RecyclerView recyclerView;
    Spinner spnDistrict,
            spnArea,
            spnPrice;

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


        //
        tenantSearchAdapter = new TenantSearchAdapter(this,R.layout.item_tenant_search,li_Post);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //
        fireBaseTenantSearch.getPostsFormDB(li_Post,tenantSearchAdapter);
        tenantSearchAdapter.setOnItemClickListener(new TenantSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {

            }
        });

        //
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                fillterList(li_Post, query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                fillterList(newText);

                return false;
            }

        });


    }

    private void setEvent() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseTenantSearch.reTurnPostBySpinner(li_Post
                        ,tenantSearchAdapter
                        ,spnDistrict.getSelectedItem().toString()
                        ,spnDistrict.getSelectedItemPosition()
                        ,Integer.parseInt(spnPrice.getSelectedItem().toString())
                        ,Integer.parseInt(spnArea.getSelectedItem().toString())
                        );
            }
        });
    }

    private void setControl() {
        //item


        //
        recyclerView = findViewById(R.id.recycler_tenant_search);
        btnSearch = findViewById(R.id.btn_search);

        searchView = findViewById(R.id.search_bar);
        spnDistrict = findViewById(R.id.spn_district);
        spnArea = findViewById(R.id.spn_area);
        spnPrice = findViewById(R.id.spn_price);
    }

    private void SpinnerSet() {


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

                Toast.makeText(TenantSearchActivity.this, String.valueOf(spnDistrict.getSelectedItemPosition()), Toast.LENGTH_SHORT).show();
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

    private void fillterList(ArrayList<Post> li_Post, String text) {


        for (Post item : li_Post)
        {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                li_Post.add(item);
            }

        }
        if(li_Post.isEmpty()){
            Toast.makeText(this, "Không tìm thấy kết quả", Toast.LENGTH_LONG).show();
        }else {
            tenantSearchAdapter.setDataBySearchView(li_Post);
        }
    }
}