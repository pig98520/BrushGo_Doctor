package com.example.pig98520.brushgo_doctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class main_activity extends AppCompatActivity {
    private Spinner name_spin;
    private List<String> nameList = new ArrayList<String>();
    private ArrayAdapter<String> nameAdapter;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        processView();
        processControl();
    }

    private void processView() {
        name_spin= (Spinner) findViewById(R.id.name_spin);
    }

    private void processControl() {
        nameAdapter = new ArrayAdapter<String>(this, R.layout.spinner_style, nameList);
        nameAdapter.setDropDownViewResource(R.layout.spinner_style);
        name_spin.setAdapter(nameAdapter);

        dbRef = FirebaseDatabase.getInstance().getReference().child("profile");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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
    }
}
