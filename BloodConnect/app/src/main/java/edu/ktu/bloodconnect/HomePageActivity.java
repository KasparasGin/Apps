package edu.ktu.bloodconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class HomePageActivity extends AppCompatActivity {

    private Context context = this;
    private Button becomeDonorButton;
    private Button showDonorsButton;
    private EditText registerName;
    private EditText registerEmail;
    private Button registerButton;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        boolean isRegistered = sharedPreferences.getBoolean(Constants.IS_REGISTERED, false);


        if(isRegistered){
            loadHomePage();
        } else {
            loadRegistrationPage();
        }
    }

    private void loadHomePage(){
        setContentView(R.layout.home_page_activity);
        becomeDonorButton = (Button)findViewById(R.id.becomeDonorButton);
        becomeDonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BecomeDonorActivity.class);
                intent.putExtra("flag", true);

                context.startActivity(intent);
            }
        });

        showDonorsButton = (Button)findViewById(R.id.showDonorsButton);
        showDonorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowDonorsActivity.class);
                intent.putExtra("flag", true);

                context.startActivity(intent);
            }
        });
    }

    private void loadRegistrationPage(){
        setContentView(R.layout.activity_register);

        registerButton = (Button)findViewById(R.id.start_button);
        registerName = (EditText)findViewById(R.id.register_name);
        registerEmail = (EditText)findViewById(R.id.register_email);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRegistration();
                Intent intent = new Intent(context, HomePageActivity.class);
                intent.putExtra("flag", true);

                context.startActivity(intent);
            }
        });
    }

    private void saveRegistration(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Constants.EMAIL, registerEmail.getText().toString());
        editor.putString(Constants.NAME, registerName.getText().toString());

        editor.putBoolean(Constants.IS_REGISTERED, true);
        editor.apply();
    }
}
