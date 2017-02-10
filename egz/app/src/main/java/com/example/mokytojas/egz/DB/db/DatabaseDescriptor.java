package com.example.mokytojas.egz.DB.db;

import java.util.List;


public class DatabaseDescriptor {

    private String name;
    private int version;
    private List<Class<?>> entityClasses;
    private MigrationAssistant migrationAssistant;
    
    public DatabaseDescriptor(
            String name,
            int version,
            List<Class<?>> entityClasses,
            MigrationAssistant migrationAssistant) {
        
        this.name = name;
        this.version = version;
        this.entityClasses = entityClasses;
        this.migrationAssistant = migrationAssistant;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public List<Class<?>> getEntityClasses() {
        return this.entityClasses;
    }
    
    public MigrationAssistant getMigrationAssistant() {
        return this.migrationAssistant;
    }
    
}
