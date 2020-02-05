package com.iutbm.example.iutbm.couchot.healthybody.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.iutbm.example.iutbm.couchot.healthybody.Patient;
import com.iutbm.example.iutbm.couchot.healthybody.R;
import com.iutbm.example.iutbm.couchot.healthybody.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailsPatientActivity extends AppCompatActivity {
    ImageView imgRetour,profilePicPatient;
    TextView txfullname,txtsize;
    ListView listeTest;
    String ID;
    HashMap<String, Test> ListeTest=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_patient);

        imgRetour=(ImageView)findViewById(R.id.retour);
        txfullname=(TextView)findViewById(R.id.fullname);
        listeTest=(ListView)findViewById(R.id.listeTest);
        profilePicPatient=(ImageView)findViewById(R.id.profilePicPatient);

        ID=getIntent().getStringExtra("id");
        txfullname.setText(getIntent().getStringExtra("full"));
        ListeTest=(HashMap<String, Test>)getIntent().getSerializableExtra("values");
        txtsize=(TextView)findViewById(R.id.txtsize);
        txtsize.setText(String.valueOf(ListeTest.size()));
    ArrayList<Test> ListeTeste=new ArrayList<>();
        for (Test t:ListeTest.values()
             ) {
            ListeTeste.add(t);

        }
    MyAdapter md=new MyAdapter(getApplicationContext(),R.layout.layout_test_patient,ListeTeste);
        listeTest.setAdapter(md);



        profilePicPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Test Activity
                Intent intent=new Intent(getApplicationContext(),TestActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });













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







    class MyAdapter extends ArrayAdapter<Test> {


        Context context;
        int mResource;
        ArrayList<Test> Tests;

        MyAdapter (Context c,int resource,ArrayList<Test> listeT) {
            super(c,resource,listeT);
            this.context = c;
            mResource=resource;
            this.Tests=listeT;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=LayoutInflater.from(context);

            convertView=layoutInflater.inflate(mResource,parent,false);
            TextView txtdateTestPatient=(TextView)convertView.findViewById(R.id.dateTestPatient);
            TextView Testp0=(TextView)convertView.findViewById(R.id.p0);
            TextView Testp1=(TextView)convertView.findViewById(R.id.p1);
            TextView Testp2=(TextView)convertView.findViewById(R.id.p2);
            TextView Testlr=(TextView)convertView.findViewById(R.id.lr);
            TextView Testld=(TextView)convertView.findViewById(R.id.ld);
            TextView TestFormeStatut=(TextView)convertView.findViewById(R.id.etatPatient);
            //fill elements

            txtdateTestPatient.setText(Tests.get(position).getDate());
            String p0,p1,p2;

            p0=Tests.get(position).getP0()+"";
            p1=Tests.get(position).getP1()+"";
            p2=Tests.get(position).getP2()+"";

            Testp0.setText(p0);
            Testp1.setText(p1);
            Testp2.setText(p2);

            Integer Lr=((Integer.valueOf(p0)+Integer.valueOf(p1)+Integer.valueOf(p2))-200)/10;
            Integer Ld = ((Integer.valueOf(p0)-70) + 2*(Integer.valueOf(p2)-Integer.valueOf(p0)) )/10;

            Testld.setText(String.valueOf(Ld));
            Testlr.setText(String.valueOf(Lr));

            if(Integer.valueOf(p0)>0){
                TestFormeStatut.setText("ça va");
                TestFormeStatut.setTextColor(getResources().getColor(R.color.colorGreen));

            }else {
                TestFormeStatut.setText("Faible");
                TestFormeStatut.setTextColor(getResources().getColor(R.color.colorRed));

            }
            return convertView;
        }
    }
}
