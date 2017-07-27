package com.example.pig98520.brushgo_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class main_activity extends AppCompatActivity {
    private Spinner name_spin;
    private String name;
    private String uid;
    private String nowDate;
    private TextView install_date;
    private TextView back_date;
    private Button confirm;
    private List<String> nameList = new ArrayList<String>();
    private ArrayAdapter<String> nameAdapter;
    private DatabaseReference dbRef;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        processView();
        processControl();
    }

    private void processView() {
        name_spin= (Spinner) findViewById(R.id.name_spin);
        nameAdapter = new ArrayAdapter<String>(this, R.layout.spinner_style, nameList);
        nameAdapter.setDropDownViewResource(R.layout.spinner_style);
        name_spin.setAdapter(nameAdapter);
        confirm=(Button)findViewById(R.id.confirm_btn);
        install_date=(TextView)findViewById(R.id.txt_installDate);
        back_date=(TextView)findViewById(R.id.txt_backDate);
        intent = new Intent();
        bundle= new Bundle();
    }

    private void processControl() {
        dbRef = FirebaseDatabase.getInstance().getReference().child("profile");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    DB_profile data = snapshot.getValue(DB_profile.class);
                    nameList.add(data.getName());
                }
                nameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        name_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name=name_spin.getSelectedItem().toString();
                Query query = dbRef.orderByChild("name").equalTo(name);
                query.addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            uid=dataSnapshot.getKey().toString();
/*                            Toast.makeText(main_activity.this,uid,Toast.LENGTH_LONG).show();*/
                            checkDate();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("uid", uid);
                intent.setClass(main_activity.this, tooth_activity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void checkDate() {
        dbRef.child(uid).child("install_date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                install_date.setText("上次看診日期:"+dataSnapshot.getValue().toString().trim());

                if(dataSnapshot.getValue().toString().trim().equals(nowDate))
                    bundle.putBoolean("back",false);
                else {
                    bundle.putBoolean("back", true);
                    dbRef.child(uid).child("back_date").setValue(nowDate);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbRef.child(uid).child("back_date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    back_date.setText("預計回診日期:"+dataSnapshot.getValue().toString().trim());
                }
                else
                    back_date.setText("預計回診日期:");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
