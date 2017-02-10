package com.example.mokytojas.egz;

public class ModelClass {
    private Integer id;
    private String vardas;
    private String grupe;
    private String data;


    public ModelClass(Integer id, String vardas, String grupe, String data) {
        this.id = id;
        this.vardas = vardas;
        this.grupe = grupe;
        this.data = data;
    }

    public Integer getId() {
        return id;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
