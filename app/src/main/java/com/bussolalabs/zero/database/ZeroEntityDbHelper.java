package com.bussolalabs.zero.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.bussolalabs.zero.datamodel.ZeroEntityDataModel;

/**
 * Created by alessio on 24/04/16.
 */
public class ZeroEntityDbHelper extends ZeroDbHelper {

    private final String TAG = ZeroEntityDbHelper.class.getSimpleName();

    public ZeroEntityDbHelper(Context context) {
        super(context);
    }

    public long insert(){

        ContentValues contentValues = ZeroContract.ZeroEntityEntry.getValues();
        Uri uri = mContext.getContentResolver().insert(ZeroContract.ZeroEntityEntry.CONTENT_URI, contentValues);

        long newRowId = ContentUris.parseId(uri);
        ZeroContract.ZeroEntityEntry.getValues().put(ZeroContract.ZeroEntityEntry._ID, newRowId);

        return newRowId;
    }

    public ZeroEntityDataModel retrieveById() {
        ZeroEntityDataModel zeroEntityDataModel = new ZeroEntityDataModel();

        String where = ZeroContract.ZeroEntityEntry._ID + " = ?";

        Cursor cursor = mContext.getContentResolver().query(
                ZeroContract.ZeroEntityEntry.CONTENT_URI,
                null,
                where,
                new String[] {""+ ZeroContract.ZeroEntityEntry.getValues().getAsLong(ZeroContract.ZeroEntityEntry._ID)},
                null
        );

        if (cursor.moveToFirst()) {
            return fillDM(cursor);
        }

        return zeroEntityDataModel;
    }

    public boolean update() {
        long rowId = ZeroContract.ZeroEntityEntry.getValues().getAsLong(ZeroContract.ZeroEntityEntry._ID);

        ContentValues values = new ContentValues();
        values.put(ZeroContract.ZeroEntityEntry.COLUMN_NAME_STRING_FIELD, ZeroContract.ZeroEntityEntry.getValues().getAsString(ZeroContract.ZeroEntityEntry.COLUMN_NAME_STRING_FIELD));
        values.put(ZeroContract.ZeroEntityEntry.COLUMN_NAME_BOOLEAN_FIELD, ZeroContract.ZeroEntityEntry.getValues().getAsInteger(ZeroContract.ZeroEntityEntry.COLUMN_NAME_BOOLEAN_FIELD));

        int updated = mContext.getContentResolver().update(
                ZeroContract.ZeroEntityEntry.CONTENT_URI,
                values,
                ZeroContract.ZeroEntityEntry._ID + " = ? ",
                new String[]{rowId + ""}
        );
        return updated == 1;
    }

    public void deleteAll() {
        mContext.getContentResolver().delete(
                ZeroContract.ZeroEntityEntry.CONTENT_URI,
                null,
                null
        );
    }


    private ZeroEntityDataModel fillDM(Cursor cursor) {
        ZeroEntityDataModel zeroEntityDataModel = new ZeroEntityDataModel();
        zeroEntityDataModel.setId(cursor.getLong(cursor.getColumnIndex(ZeroContract.ZeroEntityEntry._ID)));
        zeroEntityDataModel.setStringField(cursor.getString(cursor.getColumnIndex(ZeroContract.ZeroEntityEntry.COLUMN_NAME_STRING_FIELD)));
        zeroEntityDataModel.setBooleanField(cursor.getInt(cursor.getColumnIndex(ZeroContract.ZeroEntityEntry.COLUMN_NAME_BOOLEAN_FIELD)) == 1);
        return zeroEntityDataModel;
    }

    public void putInCache(ZeroEntityDataModel zeroEntityDataModel) {
        ZeroContract.ZeroEntityEntry.getValues().clear();
        ZeroContract.ZeroEntityEntry.getValues().put(ZeroContract.ZeroEntityEntry._ID, zeroEntityDataModel.getId());
        ZeroContract.ZeroEntityEntry.getValues().put(ZeroContract.ZeroEntityEntry.COLUMN_NAME_STRING_FIELD, zeroEntityDataModel.getStringField());
        ZeroContract.ZeroEntityEntry.getValues().put(ZeroContract.ZeroEntityEntry.COLUMN_NAME_BOOLEAN_FIELD , zeroEntityDataModel.isBooleanField());
    }
}
