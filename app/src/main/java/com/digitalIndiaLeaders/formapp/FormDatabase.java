package com.digitalIndiaLeaders.formapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FormDatabase extends SQLiteOpenHelper {
    FormDatabase(@Nullable Context context) {
        super(context,"FormDatabase.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email text primary key,name text,mobile_no text,gender text,address text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }
    //inserting in database
    void insert(String name, String email, String mobile_no, String gender, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("mobile_no",mobile_no);
        contentValues.put("gender",gender);
        contentValues.put("address",address);
        long val = db.insert("user",null,contentValues);
    }
    //checking if email exists;
    Boolean chkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    //Display data
    Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("Select name,email,mobile_no from user",null);
        return result;
    }
    //delete data
    Integer deleteData(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("user","email = ?",new String[] {email});
    }
}
