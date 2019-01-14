package edu.ktu.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private ListView myList;
    private ListAdapter adapter;
    private Button sortButton;
    private List<ListItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivitydesign);
        myList = findViewById(R.id.listView);
        sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(sortButtonClick);

        Intent intent = getIntent();

        if (intent.getBooleanExtra("flag", true)) {
            items = (List<ListItem>) intent.getExtras().getSerializable("students");
        } else items = (List<ListItem>) intent.getExtras().getSerializable("subjects");

        adapter = new ListAdapter(this, items);

        myList.setAdapter(adapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);

                myList.setAdapter(adapter);
            }
        });

    }

    View.OnClickListener sortButtonClick = new View.OnClickListener(){

        public void onClick(View w){
            Collections.sort(items, new Comparator<ListItem>() {
                @Override
                public int compare(ListItem o1, ListItem o2) {
                    if(o1.equals(o2)){
                        return 0;
                    } else {
                        return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
                    }
                }
            });

            myList.setAdapter(adapter);
        }
    };
}
