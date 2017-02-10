package com.example.mokytojas.egz.DB.db;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public interface MigrationPolicy {
    
    public void migrate(
            SQLiteDatabase db,
            ConnectionSource connectionSource) throws SQLException;
    
    /**
     * Get version, migration policy migrates from.
     * Will always migrate to version: getVersion() + 1
     */
    public int getVersion();

}
