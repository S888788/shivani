package com.elation.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.elation.myapplication.Modal.MobileNumberModal;

public class DatabaseHelperClass  extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelperClass.class.getSimpleName();

    public DatabaseHelperClass(Context context) {
        super(context, Constants2.DATABASE2.DB_NAME, null, Constants2.DATABASE2.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants2.DATABASE2.CREATE_TABLE_QUERY);

        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants2.DATABASE2.DROP_QUERY);
        this.onCreate(db);
    }

    public void addFlower2(MobileNumberModal flower) {

        Log.d(TAG, "Values Got " + flower.mobile_number);
        Log.d(TAG,"ID GOT "+flower.id);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants2.DATABASE2.CONTACT_IDS, flower.getId());
        values.put(Constants2.DATABASE2.MOBILENUMBERS, flower.getMobile_number());


        try {
            db.insert(Constants2.DATABASE2.TABLE_NAME, null, values);

        } catch (Exception e) {

        }
        db.close();
    }

    public Cursor fetchFlowers2() {
        SQLiteDatabase db = this.getReadableDatabase(); //here with rawquery method we receive our table's copy table.
        return db.rawQuery(Constants2.DATABASE2.GET_FLOWERS_QUERY, null);
    }



}