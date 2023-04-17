package etu1930.framework.model;

import etu1930.framework.annotation.Url;

public class Emp {
    String nom;
    int age;

    public Emp() {}

    public Emp(String nom, int age) {
        setAge(age);
        setNom(nom);
    }

    @Url("/prenom")
    public void getPrenom () {
        System.out.println("Haga");
    }

    @Url("/nom")
    public String getNom() {
        return nom;
    }
    public int getAge() {
        return age;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setAge(int age) {
        this.age = age;
    }

}
