package com.bussolalabs.zero.datamodel;

/**
 * Created by alessio on 04/05/16.
 */
public class ZeroEntityDataModel {

    private long id;
    private String stringField;
    private boolean booleanField;

    public boolean isBooleanField() {
        return booleanField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }
}
