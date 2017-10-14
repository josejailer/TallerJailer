package com.example.jailer.tallerjailer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jailer on 8/10/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "BDList";

    public static final String TABLE_NAME = "Usuarios";
    public static final String Table_Column_ID = "id";
    public static final String Table_Column_1_Name = "nombre";
    public static final String Table_Column_2_Email = "email";
    public static final String Table_Column_3_Password = "password";


        public static final String TABLE2_NAME = "Tarea";
        public static final String COLUMN_ID = "idT";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_REALIZADA = "realizada";

    public SQLiteHelper(Context context) {
        //String name, SQLiteDatabase.CursorFactory factory, int version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" PRIMARY KEY, "
                +Table_Column_1_Name+" VARCHAR, "
                +Table_Column_2_Email+" VARCHAR, "
                +Table_Column_3_Password+" VARCHAR)";
        database.execSQL(CREATE_TABLE);

        String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE2_NAME + " (" +COLUMN_ID +" VARCHAR ,"
                +COLUMN_NAME + " VARCHAR , "
                +COLUMN_FECHA+ " VARCHAR , "
                +COLUMN_REALIZADA + " VARCHAR )";

        database.execSQL(SQL_CREATE_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

         db.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
         onCreate(db);
    }

}

























































/*
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "
                +TABLE_NAME+" ("+Table_Column_ID
                +" INTEGER PRIMARY KEY , "
                +Table_Column_1_Name+" VARCHAR, "
                +Table_Column_2_Email+" VARCHAR, "
                +Table_Column_3_Password+" VARCHAR)";
                db.execSQL(CREATE_TABLE);
                 this.db=db;
    }
    public void insertUsuarios(Usuarios u){
        db=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query="select *from usuarios";
        Cursor cursor=db.rawQuery(query,null);
        int count=cursor.getCount();
        values.put(Table_Column_ID,count);
        values.put(Table_Column_1_Name,u.getNombre());
        values.put(Table_Column_2_Email,u.getEmail());
        values.put(Table_Column_3_Password,u.getPassword());

         db.insert(TABLE_NAME,null,values);

    }
    public  String searchPass(String email){
        db=this.getReadableDatabase();
        String query="select email,password from"+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        String a,b="";
        if (cursor.moveToFirst()){
            do{
                a=cursor.getString(0);
                if (a.equals(email)){
                    b = cursor.getString(1);
                    break;
                }
             }
             while (cursor.moveToNext());
        }
        return b;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
*/