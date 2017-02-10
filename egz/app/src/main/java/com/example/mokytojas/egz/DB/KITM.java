package com.example.mokytojas.egz.DB;

public final class KITM {

    private static KITM instance = null;

    private DB storage;

    public static synchronized void init(DB storage) {
        if (KITM.instance != null) {
            throw new RuntimeException("Already initialized");
        }

        KITM.instance = new KITM(storage);
    }

    private KITM(DB storage) {
        this.storage = storage;
    }

    public static DB getDB() {
        return KITM.instance.storage;
    }


}
