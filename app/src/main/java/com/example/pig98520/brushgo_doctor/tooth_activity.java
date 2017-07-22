package com.example.pig98520.brushgo_doctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by pig98520 on 2017/7/21.
 */

public class tooth_activity extends AppCompatActivity {
    private Bundle bundle;
    private String uid;
    private DatabaseReference dbRef;
    private int i=0;
    private Button tooth[]=new Button[28];
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
        processControl();
    }


    private void processView() {
        for(i=0;i<tooth.length;i++)
            tooth[i]=(Button)findViewById(id[i]);
        dbRef= FirebaseDatabase.getInstance().getReference();
        bundle = this.getIntent().getExtras();
        uid = bundle.getString("uid");
/*        Toast.makeText(tooth_activity.this,uid,Toast.LENGTH_LONG).show();*/
    }

    private void processControl() {
        for(i=0;i<tooth.length;i++)
        {
            tooth[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(tooth_activity.this,"Test",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}