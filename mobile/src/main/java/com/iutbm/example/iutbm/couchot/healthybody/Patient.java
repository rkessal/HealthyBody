package com.iutbm.example.iutbm.couchot.healthybody;

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