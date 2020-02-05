package com.iutbm.example.iutbm.couchot.healthybody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Patient {
    String ID;
    String nom;
    String prenom;
    HashMap<String,Test> ListeTest=new HashMap<>();
    public Patient(String ID, String nom, String prenom) {
        this.ID = ID;
        this.nom = nom;
        this.prenom = prenom;
    }

    public HashMap<String, Test> getListeTest() {
        return ListeTest;
    }

    public void setListeTest(HashMap<String, Test> listeTest) {
        ListeTest = listeTest;
    }

    public Patient() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = format.format( new Date() );
        ListeTest.put("id",new Test("ID",0,0,0,dateString));
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}