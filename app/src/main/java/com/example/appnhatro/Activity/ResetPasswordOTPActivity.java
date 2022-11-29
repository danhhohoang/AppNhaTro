package com.example.appnhatro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ResetPasswordOTPActivity extends AppCompatActivity {
    public static final String TAG = ResetPasswordOTPActivity.class.getSimpleName();

    private EditText edt_roOTP;
    private Button btn_ipnOK;
    private TextView txt_roSendOTPAgain;
    private ImageView back;

    private FirebaseAuth mAuth;

    private String mPhoneNumber;
    private String mVerificationID;
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_resetpassword_otp);

        mAuth = FirebaseAuth.getInstance();

        getDataIntent();
        setTitleToolbar();
        init();
        btn_ipnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strOTP = edt_roOTP.getText().toString().trim();
                if (TextUtils.isEmpty(strOTP)){
                    edt_roOTP.setError("Không được bỏ trống trường OTP");
                    return;
                }
                onClickSendOTPCode(strOTP);
                edt_roOTP.getText().clear();
            }
        });

        txt_roSendOTPAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSendOTPCodeAgain();
            }
        });
    }


    private void setTitleToolbar(){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Enter OTP");
        }
    }

    private void init(){
        edt_roOTP = findViewById(R.id.edt_roOTP);
        btn_ipnOK = findViewById(R.id.btn_roOK);
        txt_roSendOTPAgain = findViewById(R.id.txt_roSendOTPAgain);
        back = findViewById(R.id.iv_roBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDataIntent(){
        mPhoneNumber = getIntent().getStringExtra("phone_nummber");
        mVerificationID = getIntent().getStringExtra("vertification_ID");
    }

    private void onClickSendOTPCodeAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setForceResendingToken(mForceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(ResetPasswordOTPActivity.this,"Vertification failed",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String vertificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(vertificationID, forceResendingToken);
                                mVerificationID = vertificationID;
                                mForceResendingToken = forceResendingToken;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void onClickSendOTPCode(String strOTP) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID, strOTP);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            goToResetPasswordChangeActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(ResetPasswordOTPActivity.this,"The verification code entered was invalid",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToResetPasswordChangeActivity(String phoneNumber) {
        Intent intent = new Intent(this,ReserPasswordChangeActivity.class);
        intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);
    }
}
