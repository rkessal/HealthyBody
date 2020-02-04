package com.iutbm.example.iutbm.couchot.healthybody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iutbm.example.iutbm.couchot.healthybody.Activities.ListePatientsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity   {

    //Firebase
    //la base de données
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,db;
    Button btnConnecte,btnRegistre;
    TextView txtGuide;
    EditText txtEmailRegistre,txtPasswordRegistre,txtPrenomRegistre,txtNomRegistre,txtEmailConnecte,txtPasswordConnecte;
    LinearLayout Registre,Connexion;
    HashMap<String,Medecin> listVerified=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //shared Prefres
     final    SharedPreferences pref = getApplicationContext().getSharedPreferences("conf", 0); // 0 - for private mode
        //Initialisation base de données
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Medecins");
        db=firebaseDatabase.getReference();
        /// Intialisation des compoosants
        txtGuide=(TextView)findViewById(R.id.txtGuide);
        Registre=(LinearLayout)findViewById(R.id.Registre);
        Connexion=(LinearLayout)findViewById(R.id.Connection);
            //btn
        btnConnecte=(Button)findViewById(R.id.btnConnect);
        btnRegistre=(Button)findViewById(R.id.btnRegistreAccount);
            //Edit Text
        txtEmailRegistre=(EditText)findViewById(R.id.txtEmailRegistre);
        txtPasswordRegistre=(EditText)findViewById(R.id.txtPasswordRegistre);
        txtPrenomRegistre=(EditText)findViewById(R.id.txtPrenomRegistre);
        txtNomRegistre=(EditText)findViewById(R.id.txtNomRegistre);
        //Login
        txtEmailConnecte=(EditText)findViewById(R.id.txtEmailConnecte);
        txtPasswordConnecte=(EditText)findViewById(R.id.txtPasswordConnecte);

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


        ReadNewsEvents(new SynchronisationMedecins() {
                           @Override
                           public void onCallBack(HashMap<String, Medecin> UserList) {
                               listVerified=UserList;
                           }
                       });


                btnConnecte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean trouve=true;
                       if(listVerified.size()<0){
                           Toast.makeText(getApplicationContext(),"Faible Connection",Toast.LENGTH_SHORT).show();
                       }else
                       {
                           for (Medecin medInListe:listVerified.values()) {
                               if(txtEmailConnecte.getText().toString().toLowerCase().equalsIgnoreCase(medInListe.email.toString().toLowerCase()) && txtPasswordConnecte.getText().toString().toLowerCase().equalsIgnoreCase(medInListe.password.toString().toLowerCase())){
                                  //Redirection Home Interface
                                  SharedPreferences.Editor editor = pref.edit();
                                     editor.putString("uid",medInListe.getID());
                                  editor.commit();
                                   Intent intent = new Intent(getBaseContext(), ListePatientsActivity.class);
                                    startActivity(intent);
                                    finish();
                                   trouve=false;
                                   break;
                               }
                           }
                           if(trouve){
                               Toast.makeText(getApplicationContext(),"compte n'existe pas",Toast.LENGTH_SHORT).show();;
                           }
                       }
                    }
                });


        btnRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText=txtEmailRegistre.getText().toString();
                String passText= txtPasswordRegistre.getText().toString();
                String prenomText=txtPrenomRegistre.getText().toString();
                String nomText=txtNomRegistre.getText().toString();
                if(emptyString(passText) || emptyString(prenomText) || emptyString(nomText) || isValidEmailAddress(emailText) ){

                    String uid = databaseReference.push().getKey();
                    Medecin med=new Medecin(uid,nomText,prenomText,emailText,passText);
                    listVerified.put(uid,med);
                    databaseReference.setValue(listVerified);
                    Toast.makeText(getApplicationContext(),"Redirection",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Tous les champs sont obligatoires. ",Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    public Boolean emptyString(String e){
       return e.isEmpty();
    }

    public void Enregistrer(){

    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }



    private void ReadNewsEvents(final SynchronisationMedecins Sync){

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            HashMap<String,Medecin> ListeMedecin=new HashMap<>();
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

}
