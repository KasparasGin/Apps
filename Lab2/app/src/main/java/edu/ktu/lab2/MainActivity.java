package edu.ktu.lab2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RequestOperator.RequestOperatorListener {

    Button sendRequestButton;
    TextView title;
    TextView bodyText;
    private List<ModelPost> publication;
    private IndicatingView indicator;
    private CircleView indicator2;
    private int size = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivitydesign);

        sendRequestButton = (Button) findViewById(R.id.send_request);
        sendRequestButton.setOnClickListener(requestButtonClicked);


        title = (TextView)findViewById(R.id.title);
        bodyText = (TextView) findViewById(R.id.body_text);

        indicator = (IndicatingView)findViewById(R.id.generated_graphic);
        indicator2 = (CircleView)findViewById(R.id.circle_view);
    }

    View.OnClickListener requestButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setIndicatorStatus(IndicatingView.INPROGRESS);
            indicator2.setText("");
            indicator2.invalidate();
            sendRequest();

        }
    };

    public void setIndicatorStatus(final int status){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                indicator.setState(status);
                indicator.invalidate();
            }
        });
    }

    private void sendRequest(){
        RequestOperator ro = new RequestOperator();
        ro.setListener(this);
        ro.start();
    }

    public void updatePublication(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(publication != null){
                    indicator2.setText(Integer.toString(publication.size()));
                    indicator2.invalidate();
                } else {
                    title.setText("");
                    bodyText.setText("");
                }
            }
        });
    }

    @Override
    public void success(List<ModelPost> publication){
        this.publication = publication;
        this.size = publication.size();
        updatePublication();
        setIndicatorStatus(IndicatingView.SUCCESS);
    }

    @Override
    public void failed(int responseCode){
        this.publication = null;
        updatePublication();
        setIndicatorStatus(IndicatingView.FAILED);
    }

}
