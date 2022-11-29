package com.example.appnhatro.Activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class VertifyPhoneNumberActivity extends AppCompatActivity {

    private static final String TAG = VertifyPhoneNumberActivity.class.getName();
    private EditText edt_ipnPhone;
    private Button btn_ipnOK;
    private FirebaseAuth mAuth;
    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_phone_number);

        setTitleToolbar();
        init();

        mAuth = FirebaseAuth.getInstance();

        btn_ipnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPhoneNumber = edt_ipnPhone.getText().toString().trim();
                if (TextUtils.isEmpty(strPhoneNumber)){
                    edt_ipnPhone.setError("Không được bỏ trống trường sdt");
                    return;
                }
                onClickVerifyPhoneNumber("+84" + strPhoneNumber);
                edt_ipnPhone.getText().clear();
            }
        });
    }

    private void setTitleToolbar(){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("VerifyPhone");
        }
    }

    private void init(){
        edt_ipnPhone = findViewById(R.id.edt_ipnPhone);
        btn_ipnOK = findViewById(R.id.btn_ipnOK);
        back = findViewById(R.id.iv_pnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void onClickVerifyPhoneNumber(String strPhoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VertifyPhoneNumberActivity.this,"Vertification failed",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String vertificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(vertificationID, forceResendingToken);
                                goToResetPasswordOTPActivity(strPhoneNumber,vertificationID);

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
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
                                Toast.makeText(VertifyPhoneNumberActivity.this,"The verification code entered was invalid",Toast.LENGTH_SHORT).show();
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

    private void goToResetPasswordOTPActivity(String strPhoneNumber, String vertificationID) {
        Intent intent = new Intent(this,ResetPasswordOTPActivity.class);
        intent.putExtra("phone_number",strPhoneNumber);
        intent.putExtra("vertification_ID",vertificationID);
        startActivity(intent);
    }
}
