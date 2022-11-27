package com.example.appnhatro.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Firebase.FireBaseTermAndService;
import com.example.appnhatro.Models.TermAndPolicy;
import com.example.appnhatro.R;

import java.util.ArrayList;

public class TenantViewTermAndPolicyActivity extends AppCompatActivity {
    EditText edtContent;
    Button btnBack;
    ArrayList<TermAndPolicy> termAndPolicyList = new ArrayList<>();
    FireBaseTermAndService fireBaseTermAndService = new FireBaseTermAndService();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_view_term_and_policy);
        setControl();

    }

    private void setControl() {
        edtContent = findViewById(R.id.tenant_view_term_and_policy_content);
        btnBack = findViewById(R.id.tenant_view_term_and_policy_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEvent();
        fireBaseTermAndService.getTermAndServiceForTenant(TenantViewTermAndPolicyActivity.this,termAndPolicyList);
    }
    public void doSomeThing(){
        edtContent.setText(termAndPolicyList.get(0).getContent());

    }

    private void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
