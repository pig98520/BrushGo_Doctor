package com.example.pig98520.brushgo_doctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by pig98520 on 2017/7/21.
 */

public class tooth_activity extends AppCompatActivity {
    private Bundle bundle;
    private String uid;
    private boolean back;
    private EditText pcr;
    private Button save;
    private DatabaseReference dbRef;
    private Button tooth[]=new Button[28];
    private Boolean status[]=new Boolean[28];
    private int id[]=new int[]
            {R.id.tooth_1,
            R.id.tooth_2,
            R.id.tooth_3,
            R.id.tooth_4,
            R.id.tooth_5,
            R.id.tooth_6,
            R.id.tooth_7,
            R.id.tooth_8,
            R.id.tooth_9,
            R.id.tooth_10,
            R.id.tooth_11,
            R.id.tooth_12,
            R.id.tooth_13,
            R.id.tooth_14,
            R.id.tooth_15,
            R.id.tooth_16,
            R.id.tooth_17,
            R.id.tooth_18,
            R.id.tooth_19,
            R.id.tooth_20,
            R.id.tooth_21,
            R.id.tooth_22,
            R.id.tooth_23,
            R.id.tooth_24,
            R.id.tooth_25,
            R.id.tooth_26,
            R.id.tooth_27,
            R.id.tooth_28};

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
                            tooth[finalJ].setBackgroundResource(R.drawable.tooth_dirty_24);
                            status[finalJ]=false;
                        }
                        else
                        {
                            tooth[finalJ].setBackgroundResource(R.drawable.tooth_clean_24);
                            status[finalJ]=true;
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
            tooth[i] = (Button) findViewById(id[i]);
        pcr=(EditText)findViewById(R.id.pcr_edt);
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
                        tooth[finalI].setBackgroundResource(R.drawable.tooth_clean_24);
                        status[finalI]=true;
                        dbRef.child("tooth").child(uid).child(finalI+1+"").setValue("g");
                    }
                    else {
                        tooth[finalI].setBackgroundResource(R.drawable.tooth_dirty_24);
                        status[finalI]=false;
                        dbRef.child("tooth").child(uid).child(finalI+1+"").setValue("b");
                    }
                }
            });
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!back)
                    dbRef.child("profile").child(uid).child("first_pcr").setValue(pcr.getText().toString().trim());
                else
                    dbRef.child("profile").child(uid).child("second_pcr").setValue(pcr.getText().toString().trim());

                pcr.setText("");
                Toast.makeText(tooth_activity.this,"PCR已儲存",Toast.LENGTH_LONG).show();
            }
        });
    }

}