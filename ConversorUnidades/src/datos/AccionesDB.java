package datos;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.conversorunidades.AdminSQLiteOpenHelper;

public class AccionesDB {

	 public static String getDBLanguage(Context context){
	    	String lang="";
	    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
	                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
	   	 	SQLiteDatabase bd=admin.getWritableDatabase();
	   	 	Cursor fila = bd.rawQuery("select * from config", null);
	   	 	if (fila.moveToFirst()) {
	   	 		//Recorremos el cursor hasta que no haya más registros
	   	 		do {
	   	 			lang = fila.getString(0);
	   	 		} while(fila.moveToNext());
	   	 	}
	   	 	bd.close();
	   	 	return lang;
	    }
		
	  public static void updateConfig(String lang, String color, Context context){
			AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
	                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
	   	 SQLiteDatabase bd=admin.getWritableDatabase();
	   	 bd.execSQL("delete from config");
	   	 ContentValues registro = new ContentValues();
	   	 registro.put("lenguage",lang );
	   	 registro.put("color", color);
	   	 bd.insert("config", null, registro);
	   	 bd.close();
		}
	  
	    public static String getDBColor(Context context){
	    	String color="";
	    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
	                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
	   	 	SQLiteDatabase bd=admin.getWritableDatabase();
	   	 	Cursor fila = bd.rawQuery("select * from config", null);
	   	 	if (fila.moveToFirst()) {
	   	 		//Recorremos el cursor hasta que no haya más registros
	   	 		do {
	   	 			color = fila.getString(1);
	   	 		} while(fila.moveToNext());
	   	 	}
	   	 	bd.close();
	   	 	return color;
	    }
	    
	    public static ArrayList<TipoUnidad> consultaTipoUnidad(Context context) {
	    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
	                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
	        ArrayList<TipoUnidad> arrayTUnidades =new ArrayList<TipoUnidad>();
	        SQLiteDatabase bd=admin.getWritableDatabase();
	        Cursor fila = bd.rawQuery("select * from tipo_unidad", null);
	        if (fila.moveToFirst()) {
	            //Recorremos el cursor hasta que no haya más registros
	            do {
	                 TipoUnidad tu=new TipoUnidad(fila.getInt(0), fila.getString(1));
	                 arrayTUnidades.add(tu);
	            } while(fila.moveToNext());
	       }
	        bd.close();
	        return arrayTUnidades;
	    }
		  
	     public static ArrayList<Unidad> consultaUnidad(String nombre_tipo_unidad, Context context, Integer idTipoUnidad){
		    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
		                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
		    	ArrayList<Unidad> arrayUnidades= new ArrayList<Unidad>();
		    	SQLiteDatabase bd = admin.getWritableDatabase();
		    	String [] args ={nombre_tipo_unidad};
		    	 Cursor fila = bd.rawQuery("select id_tipo_unidad from tipo_unidad where nombre_tipo_unidad= ?", args);
		    	 
		    	 if (fila.moveToFirst()) {
		             //Recorremos el cursor hasta que no haya más registros
		             do {
		                  idTipoUnidad = fila.getInt(0);
		             } while(fila.moveToNext());
		        }
		         
		    	 String [] args1 = {idTipoUnidad.toString()};
		         Cursor fila1 = bd.rawQuery("select * from unidad where id_tipo_unidad= ?", args1);
		         if (fila1.moveToFirst()) {
		             //Recorremos el cursor hasta que no haya más registros
		             do {
		            	 Unidad u=new Unidad(fila1.getInt(0), fila1.getInt(4), fila1.getString(1), fila1.getDouble(3), fila1.getInt(2),fila1.getInt(5));
		                 arrayUnidades.add(u);
		             } while(fila1.moveToNext());
		        }
		         
		         bd.close();
		         return arrayUnidades;
		    }
	     
	 	public static void deleteMiUnidad(String nomUnidad, Context context){
			 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
		                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
			  SQLiteDatabase db=admin.getWritableDatabase();
			  db.execSQL("delete from unidad where nombre_unidad='"+nomUnidad+"'");
			  db.close();
		}
	 	
		public static ArrayList<Unidad> getMisUnidades(Context context){
			  AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
		                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
			  SQLiteDatabase db=admin.getWritableDatabase();
			  ArrayList<Unidad> misUnidades = new ArrayList<Unidad>();
			  Cursor fila = db.rawQuery("select * from unidad where miUnidad=0", null);
			  if (fila.moveToFirst()) {
				   
		             //Recorremos el cursor hasta que no haya más registros
		             do {
		            	 Unidad u=new Unidad(fila.getInt(0), fila.getInt(4), fila.getString(1), fila.getDouble(3), fila.getInt(2),fila.getInt(5));
		                 
		            	 misUnidades.add(u);
		             } while(fila.moveToNext());
		        }
		         
		         db.close();
		         return misUnidades;
		}
		
		public static String getNombreTipoUnidad(int idTunidad, Context context){
			 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
		                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
			  SQLiteDatabase db=admin.getWritableDatabase();
			  String nombre="";
			  Cursor fila = db.rawQuery("select nombre_tipo_unidad from tipo_unidad where id_tipo_unidad= "+idTunidad, null);
			  if (fila.moveToFirst()) {
				   
		             //Recorremos el cursor hasta que no haya más registros
		             do {
		            	nombre = fila.getString(0);
		             } while(fila.moveToNext());
		        }
			  return nombre;
		}
		
		 public static boolean validarMiUnidad(String nomMiUnidad, int idTipoUnidad, Context context){
			 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
		                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
			 SQLiteDatabase bd=admin.getWritableDatabase();
			 Cursor fila = bd.rawQuery("select * from unidad where nombre_unidad='"+nomMiUnidad+"' and id_tipo_unidad="+idTipoUnidad, null);
			 if (fila.moveToFirst()) {
				 return true;
			 	}
			 return false;
		 }
		 
		 public static int getIdTipoUnidad(String tUnidad, Context context){
			 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
		                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
			 SQLiteDatabase bd=admin.getWritableDatabase();
			 int idTunidad = 0;
			 String [] args = {tUnidad};
			 Cursor fila = bd.rawQuery("select id_tipo_unidad from tipo_unidad where nombre_tipo_unidad= ?", args);
			   if (fila.moveToFirst()) {
		             //Recorremos el cursor hasta que no haya más registros
		             do {
		            	 idTunidad = fila.getInt(0);
		             } while(fila.moveToNext());
		        }
			   return idTunidad;
		 }
		 
		 public static void updateUnidad1(String nomUnidadAb, double refUnidadPadre, int idTunidad, int idU, Context context){
			 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
		             AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
				 	 SQLiteDatabase bd=admin.getWritableDatabase();
				 	 bd.execSQL("UPDATE unidad SET nombre_unidad='"+nomUnidadAb+"',ref_unidad_padre="+refUnidadPadre+",id_tipo_unidad="+idTunidad+" WHERE id_unidad="+idU);
				 	 bd.close();	
				 	 
		 }
		 
		 public static void updateUnidad2(double refUnidadPadre, int idTunidad, int idU, Context context){
			 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
		                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
			 SQLiteDatabase bd=admin.getWritableDatabase();
			 bd.execSQL("UPDATE unidad SET ref_unidad_padre="+refUnidadPadre+",id_tipo_unidad="+idTunidad+" WHERE id_unidad="+idU);
		     bd.close();
		 }
		 
			public static Unidad getMiUnidad(String nombreUnidad, Context context){
				 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
			                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
				  SQLiteDatabase db=admin.getWritableDatabase();
				  Cursor fila = db.rawQuery("select * from unidad where nombre_unidad='"+nombreUnidad+"'", null);
				  Unidad u = null;
				  if (fila.moveToFirst()) {
					   
			             //Recorremos el cursor hasta que no haya más registros
			             do {
			            	 u=new Unidad(fila.getInt(0), fila.getInt(4), fila.getString(1), fila.getDouble(3), fila.getInt(2),fila.getInt(5));	 
			             } while(fila.moveToNext());
			        }
				  db.close();
				  return u;
				  
			}
			
			 public static int getSgteIdUnidad(Context context){
				 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
			                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
				 SQLiteDatabase bd=admin.getWritableDatabase();
				 int sgteId = 0;
				 Cursor fila = bd.rawQuery("select max(id_unidad) from unidad", null);
				 if (fila.moveToFirst()) {
		             //Recorremos el cursor hasta que no haya más registros
		             do {
		            	 sgteId = fila.getInt(0);
		             } while(fila.moveToNext());
		        }
				 sgteId = sgteId+1;
				 return sgteId;
			 }
			 
			 public static void addMiUnidad(double refUnidadPadre, String abUnidad, String nombreUnidad, int idTunidad, String nomUnidadAb, int idUnidad, Context context){
				 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
			                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
				 SQLiteDatabase bd=admin.getWritableDatabase();
				 ContentValues registro1 = new ContentValues();
			        	
			        	registro1.put("id_unidad",idUnidad);
			        	registro1.put("nombre_unidad", nomUnidadAb);
			        	registro1.put("unidad_padre", 1);
			        	registro1.put("ref_unidad_padre", refUnidadPadre);
			        	registro1.put("id_tipo_unidad", idTunidad);
			        	registro1.put("miUnidad", 0);
			        	
			        	bd.insert("unidad", null, registro1);
			        	bd.close();	
			 }
		   
}
