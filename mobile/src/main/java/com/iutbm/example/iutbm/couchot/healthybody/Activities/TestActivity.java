package com.iutbm.example.iutbm.couchot.healthybody.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigamole.library.PulseView;
import com.iutbm.example.iutbm.couchot.healthybody.R;

public class TestActivity extends AppCompatActivity {

    ImageView imgRetour;
    PulseView pv;
    TextView dataComes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imgRetour=(ImageView)findViewById(R.id.retourbtn);
        pv=(PulseView)findViewById(R.id.pv);

        dataComes=(TextView)findViewById(R.id.dataComes);

        pv.startPulse();
        imgRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
