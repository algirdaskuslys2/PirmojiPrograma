package com.example.mokytojas.egz;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ModelDB {

    @DatabaseField(canBeNull = false, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String vardas;

    @DatabaseField(canBeNull = false)
    private String grupe;
    
    @DatabaseField(canBeNull = false)
    private String data;
    
    public ModelDB() {
        // required by ORMLite
    }
    
    public ModelDB(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public String getVardas() {
        return this.vardas;
    }
    
    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getGrupe() {
        return this.grupe;
    }
    
    public void setGrupe(String grupe) {
        this.grupe = grupe;
    }

    public String getData() {
        return this.data;
    }
    
    public void setData(String data) {
        this.data = data;
    }

}
