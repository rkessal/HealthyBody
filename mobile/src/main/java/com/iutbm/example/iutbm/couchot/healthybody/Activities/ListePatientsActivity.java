package com.iutbm.example.iutbm.couchot.healthybody.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iutbm.example.iutbm.couchot.healthybody.Medecin;
import com.iutbm.example.iutbm.couchot.healthybody.Patient;
import com.iutbm.example.iutbm.couchot.healthybody.R;
import com.iutbm.example.iutbm.couchot.healthybody.SynchronisationMedecins;
import com.iutbm.example.iutbm.couchot.healthybody.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ListePatientsActivity extends AppCompatActivity {

    //Firebase
    //la base de données
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,db;
    //

    TextView btnClose;
    FloatingActionButton btnAdd;
    LinearLayout lrAddPatient,MainShow;
    Button btnValidePatient;
    EditText txtNom,txtPrenom,txtFiltre;
    ListView lv;
    HashMap<String,Medecin> listVerified=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_patients);
        //shared Prefres
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("conf", 0); // 0 - for private mode
        lv=(ListView)findViewById(R.id.listPatient);
        btnAdd=(FloatingActionButton)findViewById(R.id.addClick);
        lrAddPatient=(LinearLayout)findViewById(R.id.showAdd);
        MainShow=(LinearLayout)findViewById(R.id.MainLR);
        btnClose=(TextView)findViewById(R.id.btnClose);
        btnValidePatient=(Button)findViewById(R.id.patientAdd);
        txtNom=(EditText)findViewById(R.id.txtNom);
        txtPrenom=(EditText)findViewById(R.id.txtPrenom);
        //Initialisation base de données
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Medecins");
        db=firebaseDatabase.getReference();
        txtFiltre=(EditText)findViewById(R.id.txtFiltre);

        ReadNewsEvents(new SynchronisationMedecins() {
            @Override
            public void onCallBack(HashMap<String, Medecin> UserList) {
                listVerified=UserList;
                ArrayList<Patient> patientArrayList=new ArrayList<>();
                for (Patient p:listVerified.get(pref.getString("uid","")).getListePatient().values()){
                    if(txtFiltre.getText().toString().equalsIgnoreCase("")){
                        patientArrayList.add(p);
                    }else {
                        if(p.getPrenom().toLowerCase().contains(txtFiltre.getText().toString().toLowerCase())||p.getNom().toLowerCase().contains(txtFiltre.getText().toString().toLowerCase())){
                            patientArrayList.add(p);
                        }
                    }
                }
                 MyAdapter md=new MyAdapter(getApplicationContext(),R.layout.layout_contact_list,patientArrayList);
                lv.setAdapter(md);



            }
        });

        txtFiltre.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(!s.equals("") ) {

                    ArrayList<Patient> patientArrayList=new ArrayList<>();
                    for (Patient p:listVerified.get(pref.getString("uid","")).getListePatient().values()){
                            if(p.getPrenom().toLowerCase().contains(s.toString().toLowerCase())||p.getNom().toLowerCase().contains(s.toString().toLowerCase())){
                                patientArrayList.add(p);
                            }
                    }
                    MyAdapter md=new MyAdapter(getApplicationContext(),R.layout.layout_contact_list,patientArrayList);
                    lv.setAdapter(md);

                }else {
                    ArrayList<Patient> patientArrayList=new ArrayList<>();
                    for (Patient p:listVerified.get(pref.getString("uid","")).getListePatient().values()){
                                patientArrayList.add(p);
                    }
                    MyAdapter md=new MyAdapter(getApplicationContext(),R.layout.layout_contact_list,patientArrayList);
                    lv.setAdapter(md);
                }
            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lrAddPatient.setVisibility(View.GONE);
                MainShow.setVisibility(View.VISIBLE);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lrAddPatient.getVisibility()==View.GONE){
                    lrAddPatient.setVisibility(View.VISIBLE);
                    MainShow.setVisibility(View.GONE);
                }else {
                    lrAddPatient.setVisibility(View.GONE);
                    MainShow.setVisibility(View.VISIBLE);
                }
            }
        });


        btnValidePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNom.getText().toString().equalsIgnoreCase("") || txtPrenom.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Remplir tous les champs",Toast.LENGTH_SHORT).show();
                }else {
                    String uid = databaseReference.push().getKey();
                    Patient p=new Patient(uid,txtNom.getText().toString(),txtPrenom.getText().toString());
                    listVerified.get(pref.getString("uid","")).getListePatient().put(uid,p);
                    databaseReference.setValue(listVerified);
                    lrAddPatient.setVisibility(View.GONE);
                    MainShow.setVisibility(View.VISIBLE);
                }
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient p=(Patient) lv.getItemAtPosition(position);

                Intent intent=new Intent(getApplicationContext(),DetailsPatientActivity.class);
                intent.putExtra("id",p.getID());
                intent.putExtra("full",p.getNom()+" "+p.getPrenom());
                intent.putExtra("values",p.getListeTest());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });



    }





    private void ReadNewsEvents(final SynchronisationMedecins Sync){

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Medecin> ListeMedecin=new HashMap<>();
                for(DataSnapshot dataMedList : dataSnapshot.getChildren()){
                    for (DataSnapshot data: dataMedList.getChildren() ) {
                        Medecin med=data.getValue(Medecin.class);
                        ListeMedecin.put(med.getID(),med);
                    }
                }
                Sync.onCallBack(ListeMedecin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class MyAdapter extends ArrayAdapter<Patient>{


        Context context;
        int mResource;
        ArrayList<Patient> patients;

        MyAdapter (Context c,int resource,ArrayList<Patient> listeP) {
            super(c,resource,listeP);
            this.context = c;
            mResource=resource;
            this.patients=listeP;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=LayoutInflater.from(context);

            convertView=layoutInflater.inflate(mResource,parent,false);
            TextView txtNomPrenom=(TextView)convertView.findViewById(R.id.nomPrenomPatient);
            TextView  dateDuTest=(TextView)convertView.findViewById(R.id.dernierTest);
            TextView statutDuTest=(TextView)convertView.findViewById(R.id.GenResult);
            //Fill les element

            txtNomPrenom.setText(patients.get(position).getNom()+" "+patients.get(position).getPrenom());

            ArrayList<Test> listP=new ArrayList<>();
            for (Test i:patients.get(position).getListeTest().values()
                 ) {
                listP.add(i);
            }
            if(listP.size()>0){
                dateDuTest.setText(listP.get(listP.size()-1).getDate());
                if(listP.get(listP.size()-1).getP0()>0){
                    statutDuTest.setText("En forme");
                    statutDuTest.setTextColor(getResources().getColor(R.color.colorGreen));
                }else {
                    statutDuTest.setText("Pas mal");
                    statutDuTest.setTextColor(getResources().getColor(R.color.colorRed));
                }
            }else {
                dateDuTest.setText("");
                statutDuTest.setText("");

            }



            return convertView;
        }
    }

}
