package com.example.conversorunidades;

import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
	public static String DB_NAME="myDB";
	public static int versionDB=19;
	public AdminSQLiteOpenHelper(Context context, String nombre, CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	if(db.isReadOnly()){
    		db = getWritableDatabase();
    	}
    	db.execSQL("create table if not exists tipo_unidad(id_tipo_unidad integer primary key, nombre_tipo_unidad text)");
        db.execSQL("create table if not exists unidad(id_unidad integer primary key, nombre_unidad text, unidad_padre integer, ref_unidad_padre real, id_tipo_unidad integer, miUnidad integer)");	
        db.execSQL("create table if not exists config(lenguage text primary key, color text)");
        ContentValues registro3= new ContentValues();
        registro3.put("lenguage", Locale.getDefault().getLanguage());
        registro3.put("color", "naranja");
        
    	db.insert("config", null, registro3);
    	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
    	if(versionNue > versionAnte){
        db.execSQL("drop table if exists tipo_unidad");
        db.execSQL("drop table if exists unidad");
        db.execSQL("drop table if exists config");
        db.execSQL("create table tipo_unidad(id_tipo_unidad integer primary key, nombre_tipo_unidad text)");
        db.execSQL("create table unidad(id_unidad integer primary key, nombre_unidad text, unidad_padre integer, ref_unidad_padre real, id_tipo_unidad integer, miUnidad integer)" ); 
        db.execSQL("create table config(lenguage text primary key, color text)");
        ContentValues registro3= new ContentValues();
        registro3.put("lenguage", Locale.getDefault().getLanguage());
        registro3.put("color", "naranja");
        db.insert("config", null, registro3);
    	}
    }    
    
   
}
