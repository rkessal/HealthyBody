package com.iutbm.example.iutbm.couchot.healthybody;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtGuide;
    LinearLayout Registre,Connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGuide=(TextView)findViewById(R.id.txtGuide);
        Registre=(LinearLayout)findViewById(R.id.Registre);
        Connexion=(LinearLayout)findViewById(R.id.Connection);
        final String GuideRegistre = getResources().getString(R.string.Registre);
        txtGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtGuide.getText().toString().equalsIgnoreCase(GuideRegistre)){
                    Registre.setVisibility(View.VISIBLE);
                    Connexion.setVisibility(View.INVISIBLE);
                    txtGuide.setText("J'ai un Compte ");
                }else {
                    Registre.setVisibility(View.INVISIBLE);
                    Connexion.setVisibility(View.VISIBLE);
                    txtGuide.setText(GuideRegistre);
                }
            }
        });


    }
}
