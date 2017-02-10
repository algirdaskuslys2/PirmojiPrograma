package com.example.mokytojas.egz.DB.db;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MigrationAssistant {
    
    private List<MigrationPolicy> migrationPolicies;
    
    public MigrationAssistant() {
        this.migrationPolicies = new ArrayList<MigrationPolicy>();
    }
    
    /**
     * Adds migration policy.
     * Policies must be added in incremental ascending order (i.e. version 1, 2, ...) 
     * 
     * @param migrationPolicy
     */
    public void addMigrationPolicy(MigrationPolicy migrationPolicy) {
        int policyVersion = migrationPolicy.getVersion();
        int policiesSize = this.migrationPolicies.size(); 
        
        if (policiesSize > 0) {
            int lastPolicyVersion = this.migrationPolicies.get(policiesSize - 1).getVersion();
            
            if (policyVersion - lastPolicyVersion != 1) {
                throw new IllegalArgumentException("tried to add invalid migration policy; only incremental migration is supported");
            }
        }
        
        this.migrationPolicies.add(migrationPolicy);
    }
    
    public void migrate(
            SQLiteDatabase db, 
            ConnectionSource connectionSource, 
            int oldVersion, int newVersion) throws SQLException {
        
        if (oldVersion == newVersion) {
            return;
        }
        
        if (oldVersion > newVersion) {
            throw new IllegalArgumentException(
                    "oldVersion is greater then newVersion, "
                    + oldVersion + " > " + newVersion);
        }
        
        if (getMigrationPolicy(oldVersion) == null) {
            throw new IllegalArgumentException("Migration policy from oldVersion " + oldVersion + " not found");
        }
        
        if (getMigrationPolicy(newVersion - 1) == null) {
            throw new IllegalArgumentException("Migration policy to newVersion " + newVersion + " not found");
        }
        
        List<MigrationPolicy> migrationPlan = new ArrayList<MigrationPolicy>();
        for (int version = oldVersion; version < newVersion; ++version) {
            MigrationPolicy policy = getMigrationPolicy(version);
            if (policy == null) {
                int minAvailableVersion = this.migrationPolicies.get(0).getVersion();
                int maxAvailableVersion = this.migrationPolicies.get(this.migrationPolicies.size() - 1).getVersion();
                
                throw new IllegalStateException(
                        "Internal inconsistency - this should never happpen"
                        + " (oldVersion: " + oldVersion
                        + ", newVersion: " + newVersion
                        + ", min available version: " + minAvailableVersion
                        + ", max available version: " + maxAvailableVersion
                        + ", missing version: " + version + ")");
            }
            
            migrationPlan.add(policy);
        }
        
        doMigrate(db, connectionSource, migrationPlan);
    }
    
    private MigrationPolicy getMigrationPolicy(int version) {
        int size = this.migrationPolicies.size();
        if (size == 0) {
            return null;
        }
        
        int firstVersion = this.migrationPolicies.get(0).getVersion();
        int lastVersion = this.migrationPolicies.get(size - 1).getVersion();
        
        if (firstVersion > version || lastVersion < version) {
            return null;
        }
        
        MigrationPolicy policy = this.migrationPolicies.get(version - firstVersion);  
        if (policy.getVersion() != version) {
            throw new IllegalStateException("Invalid migration policy returned for version " + version);
        }
        
        return policy;
    }
    
    private void doMigrate(
            final SQLiteDatabase db,
            final ConnectionSource connectionSource,
            final List<MigrationPolicy> migrationPlan) throws SQLException {
        
        TransactionManager.callInTransaction(
                connectionSource,
                new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
        
                        for (MigrationPolicy policy : migrationPlan) {
                            policy.migrate(db, connectionSource);
                        }
                        
                        return null;
                    }
                });
    }
    
}
