package com.example.pig98520.brushgo_doctor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private boolean back=false;
    private boolean isBack =false;
    private Calendar calendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN);
    private String backDate;
    private DatabaseReference dbRef;
    private Intent intent;

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

    private Button[] btn_interdental=new Button[30];
    private boolean[] condition_interdental=new boolean[30];
    private int[] btn_interdental_id={R.id.interdental_1,R.id.interdental_2,R.id.interdental_3,R.id.interdental_4,R.id.interdental_5,
            R.id.interdental_6,R.id.interdental_7,R.id.interdental_8,R.id.interdental_9,R.id.interdental_10,
            R.id.interdental_11,R.id.interdental_12,R.id.interdental_13,R.id.interdental_14,R.id.interdental_15,
            R.id.interdental_16,R.id.interdental_17,R.id.interdental_18,R.id.interdental_19,R.id.interdental_20,
            R.id.interdental_21,R.id.interdental_22,R.id.interdental_23,R.id.interdental_24,R.id.interdental_25,
            R.id.interdental_26,R.id.interdental_27,R.id.interdental_28,R.id.interdental_29,R.id.interdental_30};

    private Button[] btn_tooth=new Button[32];
    private int[] btn_tooth_id={R.id.tooth_1,R.id.tooth_2,R.id.tooth_3,R.id.tooth_4,R.id.tooth_5,R.id.tooth_6,R.id.tooth_7,R.id.tooth_8,
            R.id.tooth_9,R.id.tooth_10,R.id.tooth_11,R.id.tooth_12,R.id.tooth_13,R.id.tooth_14,R.id.tooth_15,R.id.tooth_16,
            R.id.tooth_17,R.id.tooth_18,R.id.tooth_19,R.id.tooth_20,R.id.tooth_21,R.id.tooth_22,R.id.tooth_23,R.id.tooth_24,
            R.id.tooth_25,R.id.tooth_26,R.id.tooth_27,R.id.tooth_28,R.id.tooth_29,R.id.tooth_30,R.id.tooth_31,R.id.tooth_32};
    private boolean[] hasTooth=new boolean[32];

    private GestureDetectorCompat gestureDetectorCompat;
    private Dialog customDialog;
    private TextView dialog_message;
    private EditText dialog_input;
    private Button dialog_confirm;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent=new Intent();
        intent.setClass(tooth_activity.this,main_activity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tooth_activity);
        processView();
        processControl();
        setTooth();
        setInterdental();
    }

    private void setTooth() {
        if(isBack){
            for(int i=0;i<btn_in.length;i++) {
                final int finalI = i;
                dbRef.child("tooth").child(uid).child(i + 1 + "").child("in").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().equals("g")) {
                            btn_in[finalI].setBackgroundResource(R.drawable.transparent_mark);
                            condition_in[finalI]=true;
                            btn_tooth[finalI].setBackgroundResource(R.drawable.transparent_mark);
                            hasTooth[finalI]=true;
                        }
                        else if(dataSnapshot.getValue().equals("b")){
                            btn_in[finalI].setBackgroundResource(R.drawable.red_mark);
                            condition_in[finalI]=false;
                            btn_tooth[finalI].setBackgroundResource(R.drawable.transparent_mark);
                            hasTooth[finalI]=true;
                        }
                        else if(dataSnapshot.getValue().equals(null)){
                            btn_tooth[finalI].setBackgroundResource(R.drawable.black_mark);
                            hasTooth[finalI]=false;
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
                            btn_out[finalI].setBackgroundResource(R.drawable.transparent_mark);
                            condition_out[finalI]=true;
                        }
                        else if(dataSnapshot.getValue().equals("b")){
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
        else{
            for(int i=0;i<btn_in.length;i++){
                updateData("in","g",i);
                updateData("out","g",i);
                condition_in[i]=true;
                condition_out[i]=true;
                hasTooth[i]=true;
            }
            isBack =true;
            setTooth();
        }
    }


    private void setInterdental() {
        if(isBack){
            for(int i=0;i<btn_interdental.length;i++){
                final int finalI = i;
                dbRef.child("interdental").child(uid).child(i+1+"").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            if(dataSnapshot.getValue().equals("g")){
                                btn_interdental[finalI].setBackgroundResource(R.drawable.transparent_mark);
                                condition_interdental[finalI]=true;
                            }
                            else{
                                btn_interdental[finalI].setBackgroundResource(R.drawable.red_mark);
                                condition_interdental[finalI]=false;
                            }
                        }
                        else{
                            for(int i=0;i<btn_interdental.length;i++){
                                dbRef.child("interdental").child(uid).child(i+1+"").setValue("g");
                            }
                            isBack =true;
                            setInterdental();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        else{
            for(int i=0;i<btn_interdental.length;i++){
                dbRef.child("interdental").child(uid).child(i+1+"").setValue("g");
            }
            isBack =true;
            setInterdental();
        }
    }
    private void processView() {
        calendar=Calendar.getInstance();
        dbRef= FirebaseDatabase.getInstance().getReference();
        bundle = this.getIntent().getExtras();
        uid = bundle.getString("uid");
        back= bundle.getBoolean("back");
        isBack =back;
        for(int i=0;i<btn_in.length;i++) {
            btn_in[i] = (Button) findViewById(btn_in_id[i]);
            btn_out[i] = (Button) findViewById(btn_out_id[i]);
            btn_tooth[i]=(Button)findViewById(btn_tooth_id[i]);
        }
        for(int i=0;i<btn_interdental.length;i++)
            btn_interdental[i]=(Button)findViewById(btn_interdental_id[i]);
        gestureDetectorCompat=new GestureDetectorCompat(this,new LearnGesture());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX()>e2.getX()){
                //由右向左滑
                pcrDialog();
            }
            else if(e1.getX()<e2.getX()){
                //由左向右滑
            }

            return true;
        }
    }

    private void pcrDialog() {
        customDialog =new Dialog(this,R.style.DialogCustom);
        customDialog.setContentView(R.layout.custom_dialog_text);
        customDialog.setCancelable(false);
        dialog_message = (TextView) customDialog.findViewById(R.id.message);
        if(back)
            dialog_message.setText("請輸入回診PCR分數:");
        else
            dialog_message.setText("請輸入初診PCR分數:");
        dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
        dialog_confirm.setText("確認");
        dialog_input=(EditText)customDialog.findViewById(R.id.input);
        customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
        customDialog.show();

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                if(back){
                    dbRef.child("profile").child(uid).child("second_pcr").setValue(dialog_input.getText().toString());
                    Toast.makeText(tooth_activity.this,"PCR已儲存",Toast.LENGTH_LONG).show();
                }
                else{
                    dbRef.child("profile").child(uid).child("first_pcr").setValue(dialog_input.getText().toString());
                    backTimedialog();
                }
            }
        });
    }


    private void processControl() {
        for (int i = 0; i < btn_out.length; i++) {
            final int finalI = i;
            btn_out[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(condition_out[finalI]==false)
                    {
                        condition_out[finalI]=true;
                        btn_out[finalI].setBackgroundResource(R.drawable.transparent_mark);
                        updateData("out","g",finalI);
                    }
                    else{
                        condition_out[finalI]=false;
                        btn_out[finalI].setBackgroundResource(R.drawable.red_mark);
                        updateData("out","b",finalI);
                    }
                }
            });
            btn_in[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(condition_in[finalI]==false)
                    {
                        condition_in[finalI]=true;
                        btn_in[finalI].setBackgroundResource(R.drawable.transparent_mark);
                        updateData("in","g",finalI);
                    }
                    else{
                        condition_in[finalI]=false;
                        btn_in[finalI].setBackgroundResource(R.drawable.red_mark);
                        updateData("in","b",finalI);
                    }
                }
            });
            btn_tooth[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hasTooth[finalI]==true){
                        hasTooth[finalI]=false;
                        btn_tooth[finalI].setBackgroundResource(R.drawable.black_mark);
                        updateData("in","null",finalI);
                        updateData("out","null",finalI);
                        btn_in[finalI].setClickable(false);
                        btn_out[finalI].setClickable(false);
                    }
                    else{
                        hasTooth[finalI]=false;
                        btn_tooth[finalI].setBackgroundResource(R.drawable.transparent_mark);
                        updateData("in","g",finalI);
                        updateData("out","g",finalI);
                        btn_in[finalI].setClickable(true);
                        btn_out[finalI].setClickable(true);
                    }
                }
            });
        }
        for(int i=0;i<btn_interdental.length;i++){
            final int finalI = i;
            btn_interdental[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(condition_interdental[finalI]==false){
                        condition_interdental[finalI]=true;
                        btn_interdental[finalI].setBackgroundResource(R.drawable.transparent_mark);
                        updateData("","g",finalI);
                    }
                    else{
                        condition_interdental[finalI]=false;
                        btn_interdental[finalI].setBackgroundResource(R.drawable.red_mark);
                        updateData("","b",finalI);
                    }
                }
            });
        }
    }

    private void updateData(String position, String condition, int i) {
        if(position.equals("in")||position.equals("out"))
            dbRef.child("tooth").child(uid).child(i+1+"").child(position).setValue(condition);
        else
            dbRef.child("interdental").child(uid).child(i+1+"").setValue(condition);
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

    /*
    滑動換頁:https://www.youtube.com/watch?v=Q5Ndr944U2o
    */
}