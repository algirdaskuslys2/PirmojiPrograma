package com.example.mokytojas.egz.DB.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

public class DatabaseOpenHelper extends OrmLiteSqliteOpenHelper {

    private DatabaseDescriptor dbDescriptor;
    
    public DatabaseOpenHelper(Context context, DatabaseDescriptor dbDescriptor) {
        super(context, dbDescriptor.getName(), null, dbDescriptor.getVersion());
        
        this.dbDescriptor = dbDescriptor;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            final ConnectionSource dbConnectionSource = connectionSource;
            final List<Class<?>> dbEntityClasses = this.dbDescriptor.getEntityClasses();
            
            TransactionManager.callInTransaction(
                    connectionSource,
                    new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
            
                            for (Class<?> entityClass : dbEntityClasses) {
                                EntityHelper.createTable(dbConnectionSource, entityClass);
                            }
                            
                            return null;
                        }
                    });
        } catch (SQLException e) {
            throw new RuntimeException("Can't create database", e);
        }
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            if (this.dbDescriptor.getMigrationAssistant() == null) {
                throw new IllegalStateException("migration assistant is not provided, no migration path is available");
            }
            
            this.dbDescriptor.getMigrationAssistant().migrate(db, connectionSource, oldVersion, newVersion);
        } catch (SQLException e) {
            throw new RuntimeException(
                    "can't upgrade database from version(" + oldVersion
                    + ") to version(" + newVersion + ")", e);
        }
    }
    
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }
    
}
