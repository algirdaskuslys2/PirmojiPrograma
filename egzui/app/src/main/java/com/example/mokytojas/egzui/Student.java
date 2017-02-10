package com.example.mokytojas.egzui;

public class Student {
    private String vardas;
    private String grupe;

    public Student(String vardas, String grupe) {
        this.vardas = vardas;
        this.grupe = grupe;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getGrupe() {
        return grupe;
    }

    public void setGrupe(String grupe) {
        this.grupe = grupe;
    }
}
