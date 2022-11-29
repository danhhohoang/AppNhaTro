package com.example.appnhatro.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appnhatro.Firebase.FireBaseTermAndService;
import com.example.appnhatro.Models.TermAndPolicy;
import com.example.appnhatro.R;

import java.util.ArrayList;

public class TermAndSerciveActivity extends AppCompatActivity {

    ArrayList<TermAndPolicy> termAndPolicyList = new ArrayList<>();
    FireBaseTermAndService fireBaseTermAndService = new FireBaseTermAndService();
    EditText etTermAndSerVice;
    Button btnSubmit,
            btnCancel;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_sercive);
        setControl();
        setEvent();
        lockButton();
        lockEditText();

        fireBaseTermAndService.getTermAndServiceFormDB(this, termAndPolicyList);


    }

    private void setEvent() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etTermAndSerVice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unLockButton();
            }
        });


        etTermAndSerVice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    unLockButton();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TermAndSerciveActivity.this)
                        .setTitle("Xác nhận hủy")
                        .setMessage("Bạn chắc chắn muốn HỦY thay đổi chứ?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //chay lenh neu submit
                                fireBaseTermAndService.getTermAndServiceFormDB(TermAndSerciveActivity.this, termAndPolicyList);

                            }
                        })// A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermAndPolicy termAndPolicy = new TermAndPolicy(etTermAndSerVice.getText().toString());

                new AlertDialog.Builder(TermAndSerciveActivity.this)
                        .setTitle("Xác nhận sửa")
                        .setMessage("Bạn chắc chắn muốn cập nhật lại Điều khoản dịch vụ chứ?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //chay lenh neu submit
                                fireBaseTermAndService.setTermAndServiceToDB(termAndPolicy);
                                Toast.makeText(TermAndSerciveActivity.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });
    }

    private void setControl() {
        etTermAndSerVice = findViewById(R.id.et_Term_and_Service);
        btnSubmit = findViewById(R.id.btn_TermAndSerVice_Submit);
        btnCancel = findViewById(R.id.btn_TermAndSerVice_Cancel);
        imgBack = findViewById(R.id.img_TermAndSerVice_back);
    }


    public void doSomeThing() {
        etTermAndSerVice.setText(termAndPolicyList.get(0).getContent());
        unLockEditText();
    }

    public void unLockButton() {
        btnSubmit.setBackgroundResource(R.drawable.shape_button_term_and_service);
        btnCancel.setBackgroundResource(R.drawable.button_luu_tenant_border);
        btnSubmit.setClickable(true);
        btnCancel.setClickable(true);
    }

    public void lockButton() {
        btnSubmit.setBackgroundResource(R.drawable.shape_locked_button);
        btnCancel.setBackgroundResource(R.drawable.shape_locked_button);
        btnSubmit.setClickable(false);
        btnCancel.setClickable(false);
    }

    private void lockEditText() {


        etTermAndSerVice.setEnabled(false);
        etTermAndSerVice.setClickable(false);
    }

    private void unLockEditText() {

        etTermAndSerVice.setEnabled(true);
        etTermAndSerVice.setClickable(true);
    }
}