package com.example.appnhatro.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.DatLichModels;
import com.example.appnhatro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    Button TimeButton, DateButton, Book, Huy;
    EditText Name, Phone, notes;
    String it_login,  getIdBooking = "";
    int hour, minute;
    private DatePickerDialog datePickerDialog;
    private FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    String iduser;
    SharedPreferences sharedPreferences;
    String idLandlor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout);

        TimeButton = findViewById(R.id.timeButton);
        DateButton = findViewById(R.id.dayButton);
        DateButton.setText(getTodayDate());
        Name = findViewById(R.id.edtname);
        Phone = findViewById(R.id.edtphone);
        notes = findViewById(R.id.edtnotes);
        Book = findViewById(R.id.btnBook);
        it_login = getIntent().getStringExtra("it_ID");
        Huy = findViewById(R.id.btnHuy);
        initDatePicker();
        setIntent();
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        iduser = sharedPreferences.getString("idUser", "");
        idLandlor = getIntent().getStringExtra("idpost");
        //Tải dữ liệu lên firebase
        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a = new AlertDialog.Builder(BookingActivity.this);
                a.setTitle("Thông báo");
                a.setMessage("Bạn có muốn đăng kí lịch xem phòng");
                a.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatLichModels post = new DatLichModels(iduser, Name.getText().toString(), Phone.getText().toString(),
                                TimeButton.getText().toString(), DateButton.getText().toString(),
                                notes.getText().toString(), getIdBooking, idLandlor);
                        addToFavorite(post);
                        finish();
                    }
                });
                a.setNegativeButton("Không gửi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog al = a.create();
                al.show();
            }
        });
        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(BookingActivity.this);
                b.setTitle("Thông Báo");
                b.setMessage("Xác nhận hủy Đặt lịch xem phòng");
                b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                androidx.appcompat.app.AlertDialog al = b.create();
                al.show();
            }
        });
    }

    //ket noi database
    private void addToFavorite(DatLichModels post) {
        Log.d("text", post.getId());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("booking");
        databaseReference.child(post.getId()).setValue(post);
    }

    //gọi bảng TimePickerDialog
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                TimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    //gọi bảng DateTimePickerDialog
    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                DateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    //format
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    //custom daytime
    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
    private void setIntent() {
        Intent intent = this.getIntent();
    }

    @Override
    protected void onResume(){
        super.onResume();
        fireBaseThueTro.IdBooking(BookingActivity.this);
    }
    public void IdBooking(String id){
        getIdBooking = id;
    }
}
