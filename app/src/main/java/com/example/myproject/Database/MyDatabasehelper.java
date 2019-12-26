package com.example.myproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myproject.models.Request;

import java.util.ArrayList;

public class MyDatabasehelper extends SQLiteOpenHelper {

    private Context context;
    private static String DATABSE_NAME = "requests" ;
    private static int DATABASE_VERSION = 1;
    private SQLiteDatabase db = getWritableDatabase();


    public MyDatabasehelper (Context context){
        super(context,DATABSE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Request.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+Request.TABLE_NAME);
        onCreate(db);
    }

    public boolean addRequest(Request request){
        ContentValues cv = new ContentValues();
        cv.put(Request.COL_NAME,request.getName());
        cv.put(Request.COL_DATE,request.getDate());
        if (request.isFinished()){
            cv.put(Request.COL_IS_FINISHED,1);
        }else if (!request.isFinished()){
            cv.put(Request.COL_IS_FINISHED,0);
        }
        return db.insert(Request.TABLE_NAME,null,cv)>0;
    }

    public ArrayList<Request> getAllRequests(){
        ArrayList<Request> requests = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+Request.TABLE_NAME+" order by "+Request.COL_DATE+" asc",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            boolean b = false;
            if (cursor.getInt(2)==1){
                b=true;
            }else if (cursor.getInt(2)==0){
                b=false;
            }
           // requests.add(new Request(cursor.getString(0),cursor.getLong(1),b));
            cursor.moveToNext();
        }
        cursor.close();
        return requests;
    }

    public boolean deleteRequest(Request request){
        return db.delete(Request.TABLE_NAME,Request.COL_NAME+" = '"+request.getName()+"'",null)>0;
    }
}
