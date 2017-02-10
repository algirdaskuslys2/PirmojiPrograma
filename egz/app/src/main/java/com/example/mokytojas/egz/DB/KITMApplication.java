package com.example.mokytojas.egz.DB;

import android.app.Application;

import com.example.mokytojas.egz.DB.DB;

public class KITMApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        DB storage = new DB(this);
    }

}
