package com.example.mokytojas.egz.DB;

import android.content.Context;

import com.example.mokytojas.egz.DB.db.KITMDbOpenHelper;
import com.example.mokytojas.egz.ModelClass;
import com.example.mokytojas.egz.ModelDB;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB {
    
    private KITMDbOpenHelper databaseHelper;

    public DB(Context context) {
        this.databaseHelper = new KITMDbOpenHelper(context);
    }

    /**
     * Saves model to the storage.
     *
     * Creates new model if storageId is not set,
     * updates otherwise.
     * 
     * @param model to save. Will have storageId set on successful save.
     * 
     * @return stored model with storageId set.
     * 
     * @throws StorageIOException on failure to save model.
     */
    public void saveModel(ModelClass model) throws StorageIOException {
        try {
            Dao<ModelDB, String> modelDao = this.databaseHelper.getDao(ModelDB.class);

            ModelDB modelEntity = classToDB(model);
            modelDao.createOrUpdate(modelEntity);
            
        } catch (SQLException e) {
            throw new StorageIOException("Failed to save model", e);
        }
    }
    
    /**
     * Search models by name.
     * 
     * If given name is empty will return all models.
     * 
     * @param searchKey  key to compare models name with.
     *  
     * @return list of models which name contains searchKey. Ordered ascending by name.
     * 
     * @throws StorageIOException on search failure.
     */
    public List<ModelClass> searchModelByName(String searchKey)
            throws StorageIOException {
        
        if (searchKey == null) {
            throw new IllegalStateException("searchKey can not be null");
        }
        
        try {
            Dao<ModelDB, String> modelDao = this.databaseHelper.getDao(ModelDB.class);
            QueryBuilder<ModelDB, String> queryBuilder = modelDao.queryBuilder();
            
            queryBuilder.where().like("name", searchKey);
            queryBuilder.orderBy("name", true);
            
            List<ModelDB> entities = modelDao.query(queryBuilder.prepare());
            List<ModelClass> models = new ArrayList<ModelClass>(entities.size());

            for (ModelDB e : entities) {
                models.add(dbToClass(e));
            }

            return models;
        } catch (SQLException e) {
            throw new StorageIOException("ModelClass search with searchKey: '" + searchKey + "' failed", e);
        }
    }

    /**
     * Get all stored models.
     * 
     * @return list of stored models. Ordered ascending by name.
     *  
     * @throws StorageIOException on failure to get models.
     */
    public List<ModelClass> getAllModels() throws StorageIOException {
        try {
            Dao<ModelDB, String> modelDao = this.databaseHelper.getDao(ModelDB.class);
            QueryBuilder<ModelDB, String> queryBuilder = modelDao.queryBuilder();
            
            queryBuilder.orderBy("name", true);
            
            List<ModelDB> entities = modelDao.query(queryBuilder.prepare());
            List<ModelClass> models = new ArrayList<ModelClass>(entities.size());
            
            for (ModelDB e : entities) {
                models.add(dbToClass(e));
            }

            return models;
            
        } catch (SQLException e) {
            throw new StorageIOException("Failed to get models", e);
        }
    }
    
    /**
     * Get stored model count.
     * 
     * @return model count.
     * 
     * @throws StorageIOException on failure to get count.
     */
    public long getModelCount() throws StorageIOException {
        try {
            return this.databaseHelper.getDao(ModelDB.class).countOf();
        } catch (SQLException e) {
            throw new StorageIOException("Failed to get models' count", e);
        }
    }
    
    /**
     * Delete model by model storageId.
     * 
     * @param modelStorageId    model storageId to delete model with.
     * 
     * @throws StorageIOException on failure to delete model.
     */
    public void deleteModel(int modelStorageId) throws StorageIOException {
        try {
            Dao<ModelDB, Integer> modelDao = this.databaseHelper.getDao(ModelDB.class);
            modelDao.deleteById(modelStorageId);
        } catch (SQLException e) {
            throw new StorageIOException("Failed to delete model", e);
        }
    }

    public ModelDB getModelDB(int modelStorageId) throws StorageIOException {
        try {
            Dao<ModelDB, String> modelDao = this.databaseHelper.getDao(ModelDB.class);
            return modelDao.queryForId(String.valueOf(modelStorageId));
        } catch (SQLException e) {
            throw new StorageIOException("Failed to get model by id: " + modelStorageId, e);
        }
    }

    // db -> class
    private ModelClass dbToClass(ModelDB modelEntity) throws StorageIOException {

        ModelClass model = new ModelClass(
                modelEntity.getId(),
                modelEntity.getVardas(),
                modelEntity.getGrupe(),
                modelEntity.getData());

        return model;
    }

    // class -> db
    private ModelDB classToDB(ModelClass model) throws StorageIOException {
        ModelDB modelEntity = null;
        Integer modelStorageId = model.getId();
        
        if (modelStorageId != null) {
            modelEntity = getModelDB(modelStorageId);
            
            if (modelEntity == null) {
                throw new StorageIOException("Can not find model with storageId: " + modelStorageId);
            }
        } else {
            modelEntity = new ModelDB();
        }
        
        modelEntity.setVardas(model.getVardas());
        modelEntity.setGrupe(model.getGrupe());
        modelEntity.setData(model.getData());

        return modelEntity;
    }

}
