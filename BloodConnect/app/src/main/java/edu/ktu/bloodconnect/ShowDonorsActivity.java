package edu.ktu.bloodconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowDonorsActivity extends AppCompatActivity {

    private ListView donorList;
    private ListAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_donors_activity);

        donorList = (ListView) findViewById(R.id.donor_list);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("donors");

        getDonorList();
    }

    private void getDonorList(){

        final ArrayList<Donor> donors = new ArrayList<>();
        Query donorsQuery = databaseReference.orderByChild("bloodType");

        donorsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                        Donor donor = singleSnapshot.getValue(Donor.class);
                        donors.add(donor);
                    }
                }
                adapter = new ListAdapter(ShowDonorsActivity.this, donors);

                donorList.setAdapter(adapter);

                donorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), DonorActivity.class);
                        intent.putExtra("Donor", donors.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
