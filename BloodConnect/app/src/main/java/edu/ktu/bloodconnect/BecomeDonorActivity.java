package edu.ktu.bloodconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BecomeDonorActivity extends AppCompatActivity {

    private Button becomeDonorButton;
    private Context context = this;
    private EditText firstName;
    private EditText lastName;
    private EditText phone;
    private EditText bloodType;
    private EditText age;
    private EditText city;
    private EditText email;
    private Donor donor;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.become_donor_activity);

        firstName = (EditText)findViewById(R.id.firstNameInput);
        lastName = (EditText)findViewById(R.id.lastNameInput);
        phone = (EditText)findViewById(R.id.phoneNumberInput);
        bloodType = (EditText)findViewById(R.id.bloodTypeInput);
        age = (EditText) findViewById(R.id.ageInput);
        city = (EditText)findViewById(R.id.cityInput);
        email = (EditText)findViewById(R.id.emailInput);

        databaseReference = FirebaseDatabase.getInstance().getReference("donors");

        becomeDonorButton = (Button)findViewById(R.id.createButton);
        becomeDonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

        loadData();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);

        firstName.setText(sharedPreferences.getString(Constants.NAME, ""));
        email.setText(sharedPreferences.getString(Constants.EMAIL, ""));
    }

    private void uploadData(){

        if(firstName.getText().length() <= 0 || lastName.getText().length() <= 0  || phone.getText().length() <= 0
                || bloodType.getText().length() <= 0  || age.getText().length() <= 0
                || city.getText().length() <= 0  || email.getText().length() <= 0 ){
            Toast.makeText(getApplicationContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {

            donor = new Donor(firstName.getText().toString(), lastName.getText().toString(), phone.getText().toString(), bloodType.getText().toString(),
                    Integer.parseInt(age.getText().toString()), email.getText().toString(), city.getText().toString());

            databaseReference.child(email.getText().toString().replace(".", ",")).setValue(donor);

            Toast.makeText(getApplicationContext(), "You successfully became a donor!", Toast.LENGTH_SHORT).show();
        }
    }
}
