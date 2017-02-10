package com.example.mokytojas.egz.DB.db;

import android.content.Context;

import com.example.mokytojas.egz.ModelDB;

import java.util.ArrayList;
import java.util.List;

public class KITMDbOpenHelper extends DatabaseOpenHelper {
    
    public KITMDbOpenHelper(Context context) {
        super(context, dbDescriptor());
    }

    private static DatabaseDescriptor dbDescriptor() {
        List<Class<?>> entityClasses = new ArrayList<Class<?>>();
        entityClasses.add(ModelDB.class);
        
        MigrationAssistant migrationAssistant = new MigrationAssistant();
        
        return new DatabaseDescriptor(
                "kitm.db",
                1,
                entityClasses,
                migrationAssistant);
    }
    
}
