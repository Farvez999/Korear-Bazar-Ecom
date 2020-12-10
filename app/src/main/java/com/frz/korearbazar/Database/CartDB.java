package com.frz.korearbazar.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.frz.korearbazar.model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class CartDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "addToCartDB";
    private static String TABLE_NAME = "cartTable";
    public static String ID = "id";
    public static String TITLE = "title";
    public static String IMAGE = "image";
    public static String PRICE = "price";
    public static String QUANTITY = "quantity";

    public CartDB(Context context){super(context,DATABASE_NAME,null,DB_VERSION);}

//    private static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + " (" +
//                    ID + " INTEGER PRIMARY KEY," +
//                    TITLE + " TEXT," +
//                    IMAGE + " TEXT," +
//                    PRICE + " TEXT," +
//                    QUANTITY + " TEXT)";
//
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("
            + ID+ " INTEGER PRIMARY KEY, "
            +TITLE+" TEXT, "
            +IMAGE+" TEXT, "
            +PRICE+" TEXT, "
            +QUANTITY+" TEXT)";
        //db.execSQL(CREATE_TABLE);


    //create a database table for my model class.this name is UserInfo ;
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    //Add user Information
    public long addInsert(CartModel prodModel){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        //values.put( KEY_ID,userInfo.getUserId());
        values.put( TITLE,prodModel.getTitle());
        values.put( IMAGE,prodModel.getImage());
        values.put( PRICE,prodModel.getPrice());
        values.put( QUANTITY,prodModel.getQuantity());
        long insertData = sqLiteDatabase.insert( TABLE_NAME,null,values );
        sqLiteDatabase.close();

        return insertData;
    }


    public List<CartModel> getAllData(){
        List<CartModel> userDataList=new ArrayList();
        String sql="Select * from "+ TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery( sql,null );
        if(cursor.moveToFirst()){
            do{
                CartModel prodModel=new CartModel();
                prodModel.setId(Integer.parseInt( cursor.getString(0 ) ));
                prodModel.setTitle( cursor.getString( 1 ) );
                prodModel.setImage( cursor.getString( 2) );
                prodModel.setPrice( cursor.getString( 3 ) );
                prodModel.setQuantity( cursor.getString( 4) );
                userDataList.add( prodModel );
            }while (cursor.moveToNext());
        }
        return  userDataList;
    }
    //update user Information in my database
    public int updateUserInfo(CartModel prodModel){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues(  );
        values.put( TITLE, prodModel.getTitle());
        values.put( IMAGE,prodModel.getImage());
        values.put( PRICE, prodModel.getPrice());
        values.put( QUANTITY,prodModel.getQuantity());
        return sqLiteDatabase.update( TABLE_NAME,values,ID +" = ?", new String[]{String.valueOf( prodModel.getId())});
    }
    //Delete user information from my SQLite Database
    public void deleteUser(CartModel info){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete( TABLE_NAME,ID + " = ?", new String[]{String.valueOf( info.getId())});
        sqLiteDatabase.close();

    }
    //count how many user contacts avaiable in my SQLite Database
    public int getCountUser(){
        String countQuery="Select * From " +TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery( countQuery,null );
        return cursor.getCount();
    }

    //retrive data from my SQLite Database
    public List<String> getData() {
        List<String> fileName = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery("select * from user",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id=cursor.getString( cursor.getColumnIndex( ID ) );
                String title = cursor.getString(cursor.getColumnIndex( TABLE_NAME ));
                String image = cursor.getString(cursor.getColumnIndex(IMAGE));
                String price = cursor.getString(cursor.getColumnIndex( PRICE ));
                String quantity = cursor.getString(cursor.getColumnIndex(QUANTITY));


                fileName.add("User id: " +id );
                fileName.add("User Title: "+title);
                fileName.add("User Image: "+image);
                fileName.add("User Price: "+price);
                fileName.add("User Quantity: "+quantity);
                cursor.moveToNext();
            }
            db.close();
        }
        return fileName;
    }
}
