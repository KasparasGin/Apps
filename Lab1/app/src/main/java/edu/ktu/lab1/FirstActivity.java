package edu.ktu.lab1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.ktu.lab1.SecondActivity;

public class FirstActivity extends AppCompatActivity {

    private Button myButton;
    private EditText titleField;
    private EditText descriptionField;
    private Button secondActivityButton;
    private Button imageActivityButton;
    private Context context = this;
    List<ListItem> students = new ArrayList<>();
    List<ListItem> subjects = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstactivitydesign);

        myButton = findViewById(R.id.addButton);
        secondActivityButton = findViewById(R.id.secondActivityButton);
        imageActivityButton = findViewById(R.id.imageActivityButton);

        myButton.setOnClickListener(myButtonClick);
        myButton.setOnLongClickListener(myButtonClickLong);

        secondActivityButton.setOnClickListener(startSecondActivity);
        secondActivityButton.setOnLongClickListener(startSecondActivityLong);

        imageActivityButton.setOnClickListener(startImageActivity);

        fillLists();
    }

    private void fillLists(){
        students.add(new ListItem("Jack", R.drawable.ic_3d_rotation_black_48dp, "Mathematics, Chemistry"));
        students.add(new ListItem("Jane", R.drawable.ic_announcement_black_48dp, "Physics, Informatics"));
        students.add(new ListItem("Bob", R.drawable.ic_alarm_black_48dp, "Mathematics, Informatics"));
        students.add(new ListItem("Clara", R.drawable.ic_account_box_black_48dp, "Geography, Chemistry"));
        students.add(new ListItem("Sam", R.drawable.ic_accessibility_black_48dp, "Mathematics, Physics"));

        subjects.add(new ListItem("Mathematics", R.drawable.ic_3d_rotation_black_48dp,
                "Mathematics is the study of topics such as quantity, structure, space and change"));
        subjects.add(new ListItem("Physics", R.drawable.ic_announcement_black_48dp,
                "Physics is the natural science that involves the study of matter and its motion through space and time along with related concepts such as energy for it"));
        subjects.add(new ListItem("Chemistry", R.drawable.ic_alarm_black_48dp,
                "Chemistry is a branch of physical science that studies the composition, structure, properties and change of matter"));
        subjects.add(new ListItem("Informatics", R.drawable.ic_account_box_black_48dp,
                "Informatics is the science of information and computer information systems"));
        subjects.add(new ListItem("Geography", R.drawable.ic_accessibility_black_48dp,
                "Geography is a field of science devoted to the study of the lands, the features, the inhabitants and the phenomena of Earth"));
    }

    private void addElement(String title, String description, List<ListItem> list){
        ListItem item = new ListItem(title, R.drawable.ic_person_add_black_24dp, description);

        list.add(item);
    }

    View.OnClickListener myButtonClick = new View.OnClickListener(){

        public void onClick(View w){
            titleField = findViewById(R.id.titleInput);
            descriptionField = findViewById(R.id.descriptionInput);

            addElement(titleField.getText().toString(), descriptionField.getText().toString(), students);
        }
    };

    View.OnLongClickListener myButtonClickLong = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View w){
            titleField = findViewById(R.id.titleInput);
            descriptionField = findViewById(R.id.descriptionInput);

            addElement(titleField.getText().toString(), descriptionField.getText().toString(), subjects);

            return true;
        }
    };

    public void runSecondActivity(boolean b) {
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("flag", b);
        intent.putExtra("students", (Serializable) students);
        intent.putExtra("subjects", (Serializable) subjects);

        context.startActivity(intent);
    }

    View.OnClickListener startSecondActivity = new View.OnClickListener(){
        @Override
        public void onClick(View w){
           runSecondActivity(true);
        }
    };

    View.OnLongClickListener startSecondActivityLong = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View w){
            runSecondActivity(false);
            return true;
        }
    };

    View.OnClickListener startImageActivity = new View.OnClickListener(){
        @Override
        public void onClick(View w){
            runImageActivity();
        }
    };

    public void runImageActivity() {
        context.startActivity(new Intent(context, ImageActivity.class));
    }
}
