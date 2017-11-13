package com.example.pig98520.brushgo_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
    private ConstraintLayout thisLayout;
    private Bundle bundle;
    private String uid;
    private boolean back=false;
    private Calendar calendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN);
    private String backDate;
    private DatabaseReference dbRef;
    private Intent intent;
    private Button btn_save;

    private Button[] btn_in=new Button[32];
    private boolean[] condition_in =new boolean[32];
    private int[] btn_in_id={R.id.tooth_in_1,R.id.tooth_in_2,R.id.tooth_in_3,R.id.tooth_in_4,
            R.id.tooth_in_5,R.id.tooth_in_6,R.id.tooth_in_7,R.id.tooth_in_8,
            R.id.tooth_in_9,R.id.tooth_in_10,R.id.tooth_in_11,R.id.tooth_in_12,
            R.id.tooth_in_13,R.id.tooth_in_14,R.id.tooth_in_15,R.id.tooth_in_16,
            R.id.tooth_in_17,R.id.tooth_in_18,R.id.tooth_in_19,R.id.tooth_in_20,
            R.id.tooth_in_21,R.id.tooth_in_22,R.id.tooth_in_23,R.id.tooth_in_24,
            R.id.tooth_in_25,R.id.tooth_in_26,R.id.tooth_in_27,R.id.tooth_in_28,
            R.id.tooth_in_29,R.id.tooth_in_30,R.id.tooth_in_31,R.id.tooth_in_32};

    private Button[] btn_out=new Button[32];
    private boolean[] condition_out =new boolean[32];
    private int[] btn_out_id={R.id.tooth_out_1,R.id.tooth_out_2,R.id.tooth_out_3,R.id.tooth_out_4,
            R.id.tooth_out_5,R.id.tooth_out_6,R.id.tooth_out_7,R.id.tooth_out_8,
            R.id.tooth_out_9,R.id.tooth_out_10,R.id.tooth_out_11,R.id.tooth_out_12,
            R.id.tooth_out_13,R.id.tooth_out_14,R.id.tooth_out_15,R.id.tooth_out_16,
            R.id.tooth_out_17,R.id.tooth_out_18,R.id.tooth_out_19,R.id.tooth_out_20,
            R.id.tooth_out_21,R.id.tooth_out_22,R.id.tooth_out_23,R.id.tooth_out_24,
            R.id.tooth_out_25,R.id.tooth_out_26,R.id.tooth_out_27,R.id.tooth_out_28,
            R.id.tooth_out_29,R.id.tooth_out_30,R.id.tooth_out_31,R.id.tooth_out_32};


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent=new Intent();
        intent.setClass(tooth_activity.this,main_activity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tooth_activity);
        processView();
        processControl();
        setTooth();
    }

    private void setTooth() {
        if(back){
            for(int i=0;i<btn_in.length;i++) {
                final int finalI = i;
                dbRef.child("tooth").child(uid).child(i + 1 + "").child("in").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().equals("g")) {
                            btn_in[finalI].setBackgroundResource(R.drawable.background_mark);
                            condition_in[finalI]=true;
                        }
                        else{
                            btn_in[finalI].setBackgroundResource(R.drawable.red_mark);
                            condition_in[finalI]=false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dbRef.child("tooth").child(uid).child(i + 1 + "").child("out").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().equals("g")) {
                            btn_out[finalI].setBackgroundResource(R.drawable.background_mark);
                            condition_out[finalI]=true;
                        }
                        else{
                            btn_out[finalI].setBackgroundResource(R.drawable.red_mark);
                            condition_out[finalI]=false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        else if(!back){
            for(int i=0;i<btn_in.length;i++){
                updateData("in","g",i);
                updateData("out","g",i);
                condition_in[i]=true;
                condition_out[i]=true;
            }
            setTooth();
        }
    }

    private void processView() {
        thisLayout =(ConstraintLayout)findViewById(R.id.constraintLayout);
        calendar=Calendar.getInstance();
        dbRef= FirebaseDatabase.getInstance().getReference();
        bundle = this.getIntent().getExtras();
        uid = bundle.getString("uid");
        back= bundle.getBoolean("back");
        for(int i=0;i<btn_in.length;i++) {
            btn_in[i] = (Button) findViewById(btn_in_id[i]);
            btn_out[i] = (Button) findViewById(btn_out_id[i]);
        }
        btn_save =(Button)findViewById(R.id.btn_save);
    }

    private void processControl() {
        for (int i = 0; i < btn_in.length; i++) {
            final int finalI = i;
            btn_in[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(condition_in[finalI]==false)
                    {
                     condition_in[finalI]=true;
                     btn_in[finalI].setBackgroundResource(R.drawable.background_mark);
                     updateData("in","g",finalI);
                    }
                    else{
                        condition_in[finalI]=false;
                        btn_in[finalI].setBackgroundResource(R.drawable.red_mark);
                        updateData("in","b",finalI);
                    }
                }
            });
        }
        for (int i = 0; i < btn_out.length; i++) {
            final int finalI = i;
            btn_out[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(condition_out[finalI]==false)
                    {
                        condition_out[finalI]=true;
                        btn_out[finalI].setBackgroundResource(R.drawable.background_mark);
                        updateData("out","g",finalI);
                    }
                    else{
                        condition_out[finalI]=false;
                        btn_out[finalI].setBackgroundResource(R.drawable.red_mark);
                        updateData("out","b",finalI);
                    }
                }
            });
        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tooth_activity.this,main_activity.class));
            }
        });
    }

    private void updateData(String position, String condition, int i) {
        dbRef.child("tooth").child(uid).child(i+1+"").child(position).setValue(condition);
    }

    /*private void backTimedialog() {
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
    };*/
}