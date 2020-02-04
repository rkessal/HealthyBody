package com.iutbm.example.iutbm.couchot.healthybody;

import java.util.HashMap;

public class Medecin {

        String ID;
        String nom;
        String prenom;
        String email;
        String password;
        HashMap<String,Patient> ListePatient=new HashMap<>();
        public Medecin(){ }
        public Medecin(String ID, String nom, String prenom, String email, String password) {
            this.ID = ID;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.password = password;
        }

    public HashMap<String, Patient> getListePatient() {
        return ListePatient;
    }

    public void setListePatient(HashMap<String, Patient> listePatient) {
        ListePatient = listePatient;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
