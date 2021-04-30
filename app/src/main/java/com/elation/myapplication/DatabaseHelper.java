package com.elation.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.elation.myapplication.Modal.MobileNumberModal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY);

        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DATABASE.DROP_QUERY);
        this.onCreate(db);
    }
    //insert data

    public void addFlower(MobileNumberModal flower) {

        Log.d(TAG, "Values Got " + flower.mobile_number);
        Log.d(TAG,"ID GOT "+flower.id);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(Constants.DATABASE.CONTACT_ID, flower.getId());
        values.put("status","1");

        values.put(Constants.DATABASE.MOBILENUMBER, flower.getMobile_number());



        try {
            db.insert(Constants.DATABASE.TABLE_NAME,null , values);

        } catch (Exception e) {

        }
        db.close();
    }
    //select data
    public List<MobileNumberModal> fetchFlowers()
    {
        SQLiteDatabase db = this.getReadableDatabase();//here with rawquery method we receive our table's copy table.
        List<MobileNumberModal> mobileNumberModalList =new ArrayList<>();
        //generate the query to read from the databse
       Cursor cursor= db.rawQuery(Constants.DATABASE.GET_FLOWERS_QUERY, null);
       int cu=cursor.getCount();
        Log.d("cu", String.valueOf(cu));
       if(cursor.getCount()>0)
       {
           if(cursor.moveToFirst())
           {
               do{
                   MobileNumberModal mobileNumberModal=new MobileNumberModal();
                   mobileNumberModal.setId(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.CONTACT_ID)));
                   mobileNumberModal.setMobile_number(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.MOBILENUMBER)));
                 // mobileNumberModal.setStatus( cursor.getString(cursor.getColumnIndex("status")));
                   mobileNumberModalList.add(mobileNumberModal);
               }while (cursor.moveToNext());
           }
       }

       return  mobileNumberModalList;

    }
    //delete  number
    public void deleteNumbers(String id)
    {
        Log.d(TAG, "delete Got " +id);

        SQLiteDatabase db=this.getWritableDatabase();
                db.delete(Constants.DATABASE.TABLE_NAME,Constants.DATABASE.MOBILENUMBER+"=?",new String[]{id});
        db.close();
    }
    public int updateNmbers(int id, String MbNumber)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("status",id);

       int updatesss= db.update(Constants.DATABASE.TABLE_NAME,contentValues,Constants.DATABASE.MOBILENUMBER+"=?",new String[]{MbNumber} );
       // String strSQL = "UPDATE myTable SET Column1 = someValue WHERE columnId = "+ someValue;

        Log.d("update",String.valueOf(updatesss));

       // db.execSQL(strSQL);
       return updatesss;

    }
    public int updatestatus(int id)
    {


        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("status",id);

        int updatessssd= db.update(Constants.DATABASE.TABLE_NAME,contentValues,"",new String[]{} );


        Log.d("updatesss",String.valueOf(updatessssd));

        // db.execSQL(strSQL);
        return updatessssd;

    }



    }



