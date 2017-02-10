package com.example.mokytojas.egz.DB.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class EntityHelper {

    public static void createTable(ConnectionSource connectionSource, Class<?> entityClass) throws SQLException {
        List<String> statements = TableUtils.getCreateTableStatements(connectionSource, entityClass);
        String foreignKeyStatemens = getForeignKeyStatements(connectionSource, entityClass);
        
        if (foreignKeyStatemens == null) {
            TableUtils.createTable(connectionSource, entityClass);
            return;
        }
        
        DatabaseConnection connection = connectionSource.getReadWriteConnection();
        
        try {
            for (String stat : statements) {
                if (stat.toLowerCase(Locale.US).contains("create table")) {
                    int endIndex = stat.lastIndexOf(")");
                    stat = stat.substring(0, endIndex) + foreignKeyStatemens + stat.substring(endIndex);
                }
                
                connection.executeStatement(stat, DatabaseConnection.DEFAULT_RESULT_FLAGS);
            }
        } finally {
            connectionSource.releaseConnection(connection);
        }
    }
    
    public static <D extends Dao<T, ?>, T> D getDao(
            ConnectionSource connectionSource,
            Class<T> entityClass) throws SQLException {
        
        Dao<T, ?> dao = DaoManager.createDao(connectionSource, entityClass);
        @SuppressWarnings("unchecked")
        D castDao = (D) dao;
        return castDao;
    }   
    
    public static void dropTable(ConnectionSource connectionSource, Class<?> entityClass) throws SQLException {
        TableUtils.dropTable(connectionSource, entityClass, false);
    }
    
    private static <T> TableInfo<T, Object> getTableInfo(ConnectionSource connectionSource, Class<T> entityClass) throws SQLException {
        BaseDaoImpl<T, Object> dao = getDao(connectionSource, entityClass);
        return new TableInfo<T, Object>(connectionSource, dao, entityClass);
    }
    
    private static String getForeignKeyStatements(ConnectionSource connectionSource, Class<?> entityClass) throws SQLException {
        StringBuilder foreignKeys = new StringBuilder();
        TableInfo<?, Object> tableInfo = getTableInfo(connectionSource, entityClass);
        
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            if (!fieldType.isForeign()) {
                continue;
            }
            
            // foreign parent type
            TableInfo<?, Object> parentTableInfo = getTableInfo(connectionSource, fieldType.getType());
            
            foreignKeys.append(", FOREIGN KEY ('");
            foreignKeys.append(fieldType.getColumnName());
            foreignKeys.append("') REFERENCES '");
            foreignKeys.append(parentTableInfo.getTableName());
            foreignKeys.append("'('");
            foreignKeys.append(parentTableInfo.getIdField().getColumnName());
            foreignKeys.append("')");
        }
        
        if (foreignKeys.length() == 0) {
            return null;
        }
        
        return foreignKeys.toString();
    } 
    
}
