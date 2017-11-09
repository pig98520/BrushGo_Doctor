package com.example.pig98520.brushgo_doctor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by pig98520 on 2017/7/21.
 */

public class tooth_activity extends AppCompatActivity {
    private Bundle bundle;
    private String uid;
    private boolean back;
    private EditText pcr;
    private Calendar calendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN);
    private String backDate;
    private Button save;
    private DatabaseReference dbRef;
    private ImageView tooth[]=new ImageView[32];
    private Boolean status[]=new Boolean[232];
    private Intent intent;
    private int id[]=new int[]{
            R.id.imageView_1, R.id.imageView_2,R.id.imageView_3, R.id.imageView_4, R.id.imageView_5, R.id.imageView_6, R.id.imageView_7, R.id.imageView_8,
            R.id.imageView_9, R.id.imageView_10, R.id.imageView_11, R.id.imageView_12, R.id.imageView_13, R.id.imageView_14, R.id.imageView_15, R.id.imageView_16,
            R.id.imageView_17, R.id.imageView_18, R.id.imageView_19, R.id.imageView_20, R.id.imageView_21, R.id.imageView_22, R.id.imageView_23, R.id.imageView_24,
            R.id.imageView_25, R.id.imageView_26, R.id.imageView_27, R.id.imageView_28, R.id.imageView_29, R.id.imageView_30, R.id.imageView_31, R.id.imageView_32};

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent=new Intent();
        intent.setClass(tooth_activity.this,main_activity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tooth_activity);
        processView();
        setToothCondition();
        processControl();
    }

    private void setToothCondition() {
        if(!back)
        {
            for(int i=0;i<tooth.length;i++) {
                dbRef.child("tooth").child(uid).child(i + 1 + "").setValue("g");
                status[i]=true;
            }
        }
        else{
            for(int j=0;j<tooth.length;j++) {
                final int finalJ = j;
                dbRef.child("tooth").child(uid).child(j+1+"").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().toString().trim().equals("b"))
                        {
                            dbRef.child("image").child("tooth_dirty").child(finalJ%16+1+"").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Glide.with(tooth_activity.this)
                                            .load(Uri.parse(dataSnapshot.getValue().toString()))
                                            .dontAnimate()
                                            .into(tooth[finalJ]);
                                    status[finalJ]=false;
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            dbRef.child("image").child("tooth_clean").child(finalJ%16+1+"").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Glide.with(tooth_activity.this)
                                            .load(Uri.parse(dataSnapshot.getValue().toString()))
                                            .dontAnimate()
                                            .into(tooth[finalJ]);
                                    status[finalJ]=true;
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }


    private void processView() {
        for(int i=0;i<tooth.length;i++)
            tooth[i] = (ImageView) findViewById(id[i]);
        pcr=(EditText)findViewById(R.id.pcr_edt);
        calendar=Calendar.getInstance();
        save=(Button)findViewById(R.id.save_btn);
        dbRef= FirebaseDatabase.getInstance().getReference();
        bundle = this.getIntent().getExtras();
        uid = bundle.getString("uid");
        back=bundle.getBoolean("back");
    }

    private void processControl() {
        for(int i=0;i<tooth.length;i++)
        {
            final int finalI = i;
            tooth[finalI].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!status[finalI]){
                        status[finalI]=true;
                        dbRef.child("tooth").child(uid).child(finalI+1+"").setValue("g");
                    }
                    else {
                        status[finalI]=false;
                        dbRef.child("tooth").child(uid).child(finalI+1+"").setValue("b");
                    }
                }
            });
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pcr.getText().toString().trim().equals(""))
                    Toast.makeText(tooth_activity.this,"請輸入PCR",Toast.LENGTH_LONG).show();
                else {
                    if(!back) {
                        dbRef.child("profile").child(uid).child("first_pcr").setValue(pcr.getText().toString().trim());
                        backTimedialog();
                    }
                    else if(back) {
                        dbRef.child("profile").child(uid).child("second_pcr").setValue(pcr.getText().toString().trim());
                        Toast.makeText(tooth_activity.this,"PCR已儲存",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    private void backTimedialog() {
        DatePickerDialog dialog = new DatePickerDialog(tooth_activity.this,
                datepicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle("請選擇回診日期");
        dialog.show();
    }
    DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            backDate=sdf.format(calendar.getTime());
            dbRef.child("profile").child(uid).child("back_date").setValue(backDate);
            Toast.makeText(tooth_activity.this,"PCR及回診日期已儲存",Toast.LENGTH_LONG).show();
        }
    };
}