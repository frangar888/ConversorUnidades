package com.example.conversorunidades;

import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
	public static String DB_NAME="myDB";
	public static int versionDB=15;
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
        db.execSQL("create table if not exists archivotu(nombre_archivo text primary key, version integer)");
        db.execSQL("create table if not exists archivou(nombre_archivo text primary key, version integer)");
        db.execSQL("create table if not exists config(lenguage text primary key, color text)");
        ContentValues registro1 = new ContentValues();
        ContentValues registro2 = new ContentValues();
        ContentValues registro3= new ContentValues();
        registro1.put("nombre_archivo", "unidad");
        registro2.put("nombre_archivo", "tipo_unidad");
        registro1.put("version", 1);
        registro2.put("version",1);
        registro3.put("lenguage", Locale.getDefault().getLanguage());
        registro3.put("color", "naranja");
        db.insert("archivou", null, registro1);
        db.insert("archivotu", null, registro2);
    	db.insert("config", null, registro3);
    	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
    	if(versionNue > versionAnte){
        db.execSQL("drop table if exists tipo_unidad");
        db.execSQL("drop table if exists unidad");
        db.execSQL("drop table if exists archivotu");
        db.execSQL("drop table if exists archivou");
        db.execSQL("drop table if exists config");
        db.execSQL("create table tipo_unidad(id_tipo_unidad integer primary key, nombre_tipo_unidad text)");
        db.execSQL("create table unidad(id_unidad integer primary key, nombre_unidad text, unidad_padre integer, ref_unidad_padre real, id_tipo_unidad integer, miUnidad integer)" ); 
        db.execSQL("create table archivotu(nombre_archivo text primary key, version integer)");
        db.execSQL("create table archivou(nombre_archivo text primary key, version integer)");
        db.execSQL("create table config(lenguage text primary key, color text)");
        ContentValues registro1 = new ContentValues();
        ContentValues registro2 = new ContentValues();
        ContentValues registro3= new ContentValues();
        registro3.put("lenguage", Locale.getDefault().getLanguage());
        registro3.put("color", "naranja");
        registro1.put("nombre_archivo", "unidad");
        registro2.put("nombre_archivo", "tipo_unidad");
        registro1.put("version", 1);
        registro2.put("version",1);
        db.insert("archivou", null, registro1);
        db.insert("archivotu", null, registro2);		
        db.insert("config", null, registro3);
    	}
    }    
    
   
}
