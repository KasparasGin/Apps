package edu.ktu.bloodconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DonorActivity extends AppCompatActivity {

    private TextView displayName;
    private TextView displayPhoneNumber;
    private TextView displayBloodType;
    private TextView displayAge;
    private TextView displayEmail;
    private TextView displayCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        displayName = (TextView)findViewById(R.id.display_name);
        displayPhoneNumber = (TextView)findViewById(R.id.display_phoneNumber);
        displayBloodType = (TextView)findViewById(R.id.display_bloodType);
        displayAge = (TextView)findViewById(R.id.display_age);
        displayEmail = (TextView)findViewById(R.id.display_email);
        displayCity = (TextView)findViewById(R.id.display_city);

        Donor donor = (Donor)getIntent().getSerializableExtra("Donor");

        displayName.setText(donor.getFirstName() + " " + donor.getLastName());
        displayPhoneNumber.setText(donor.getPhoneNumber());
        displayBloodType.setText(donor.getBloodType());
        displayAge.setText(String.valueOf(donor.getAge()));
        displayEmail.setText(donor.getEmail());
        displayCity.setText(donor.getCity());
    }
}
