package com.example.conversorunidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;




import java.util.Locale;

import datos.AccionesDB;
import datos.TipoUnidad;
import datos.Unidad;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	private ListView lv1;
	private TextView titulo;
	private TextView actualizando;
	private ProgressBar pbar;
	public static Activity ac;
	private MiTareaAsincrona tarea1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ac = this;
		super.onCreate(savedInstanceState);
		
		tarea1 = new MiTareaAsincrona();
		tarea1.execute();
		Configuration config =getResources().getConfiguration();
			
			  
	  			if(!config.locale.getLanguage().equals(AccionesDB.getDBLanguage(this))){
	  				switch(AccionesDB.getDBLanguage(this)){
	  				case "es":
	  					Locale loc = new Locale("es", "US");
	  					config.locale = loc;
	  					getBaseContext().getResources().updateConfiguration(
	  				            config, getBaseContext().getResources().getDisplayMetrics());
	  					titulo.setText(R.string.seleccione_tipo_unidad);
	  					actualizando.setText(R.string.main_actualizando);
	  					
	  					break;
	  				case "en":
	  					Locale loc1 = new Locale("en", "US");
	  					config.locale = loc1;
	  					getBaseContext().getResources().updateConfiguration(
	  				            config, getBaseContext().getResources().getDisplayMetrics());
	  					
	  					titulo.setText(R.string.seleccione_tipo_unidad); 
	  					actualizando.setText(R.string.main_actualizando);
	  					break;
	  				case "pt":
	  					Locale loc2 = new Locale("pt", "BR");
	  					config.locale = loc2;
	  					getBaseContext().getResources().updateConfiguration(
	  				            config, getBaseContext().getResources().getDisplayMetrics());
	  					titulo.setText(R.string.seleccione_tipo_unidad); 
	  					
	  					actualizando.setText(R.string.main_actualizando);
	  					break;
	  					
	  				}
	  			}
	  		
		ArrayList<TipoUnidad> listaTU=AccionesDB.consultaTipoUnidad(this);
		
		ArrayList<String> listaTUstring=new ArrayList<String>();
		
		for(int i=0;i<listaTU.size();i++){
			listaTUstring.add(listaTU.get(i).getNombre_tipo_unidad());
		}
		
		int size = listaTUstring.size();
		 String[] itemname ={
				 listaTUstring.get(size-size),
				 listaTUstring.get(size-size+1),
				 listaTUstring.get(size-size+2),
				 listaTUstring.get(size-size+3),
				 "Moneda"
				 };
				 
				 Integer[] imgid={
				 R.drawable.icon,
				 R.drawable.icon2,
				 R.drawable.icon4,
				 R.drawable.icon3,
				 R.drawable.icon5
				 };
		
		 CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
		lv1.setAdapter(adapter);
		lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
            	Intent i = new Intent(v.getContext(), ActivityConversion.class);
            	i.putExtra("tipo_unidad", lv1.getItemAtPosition(posicion).toString());
                startActivity(i);
            }
       });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.app_config) {
			Intent i = new Intent(this, Configuracion.class );
	        startActivity(i);
			return true;
		}
		if (id == R.id.app_acercaDe){
			Toast.makeText(this, "UConverter 1.0. Francisco García.", Toast.LENGTH_LONG).show();
		}
		if (id == R.id.app_salir){
			finish();
		}
		if (id == R.id.app_mis_unidades){
			Intent i = new Intent(this, MisUnidades.class );
		    startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
	
    public void altaTUarchivo(String archTipoUnidad) {
    	File tarjeta = Environment.getExternalStorageDirectory();
    	File file1 = new File(tarjeta.getAbsolutePath(), archTipoUnidad);
        ArrayList<TipoUnidad> listadoTipoUnidades = new ArrayList<TipoUnidad>();    	
        try{
    		FileInputStream fIn1 = new FileInputStream(file1);
            InputStreamReader archivo1 = new InputStreamReader(fIn1);
            BufferedReader br1 = new BufferedReader(archivo1);
            String linea1 = br1.readLine();
            ArrayList<String> listado1 = new ArrayList<String>();
            while (linea1 != null){
            	listado1.add(linea1);
            	linea1 = br1.readLine();
            }
            
            int id_tipo_unidad=1;
            for(int i=0; i<listado1.size();i++){
            	TipoUnidad tu=new TipoUnidad(id_tipo_unidad,listado1.get(i));
            	listadoTipoUnidades.add(tu);
            	id_tipo_unidad=id_tipo_unidad+1;
            }
            
            br1.close();
            archivo1.close();
            
        
    	}catch(IOException e){
    		Log.e("error", "errorIO");
    	}
    
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
        SQLiteDatabase db=admin.getWritableDatabase();
        db.execSQL("delete from tipo_unidad");
        ContentValues registro = new ContentValues();
        for(int i=0;i<listadoTipoUnidades.size();i++){
        	registro.put("id_tipo_unidad",listadoTipoUnidades.get(i).getId_tipo_unidad());
        	registro.put("nombre_tipo_unidad", listadoTipoUnidades.get(i).getNombre_tipo_unidad());
        	db.insert("tipo_unidad",  null, registro);
        }
       
      db.close();
       
       
  
    }
    public void altaUarchivo(String archUnidad) {
    	
    	File tarjeta = Environment.getExternalStorageDirectory();
        File file = new File(tarjeta.getAbsolutePath(), archUnidad);
        ArrayList<Unidad> listadoUnidades = new ArrayList<Unidad>();
        try{
    		FileInputStream fIn = new FileInputStream(file);
            InputStreamReader archivo = new InputStreamReader(fIn);
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            ArrayList<String> listado = new ArrayList<String>();
            while (linea != null) {
            	listado.add(linea);
            	linea=br.readLine();
            }
            int id_unidad=1;
            for(int i=0; i<listado.size();i++){
            	String [] partes = listado.get(i).split("-");
            	Unidad u=new Unidad(id_unidad,Integer.parseInt(partes[0]),partes[1],Double.parseDouble(partes[2]),Integer.parseInt(partes[3]),Integer.parseInt(partes[4]));
            	listadoUnidades.add(u);
            	id_unidad=id_unidad+1;
            }
            
            br.close();
            archivo.close();
        
    	}catch(IOException e){
    		Log.e("error", "errorIO");
    	}
    
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
        SQLiteDatabase db=admin.getWritableDatabase();
        db.execSQL("delete from unidad where miUnidad=1");
        ContentValues registro1 = new ContentValues();
        for(int i=0;i<listadoUnidades.size();i++){
        	registro1.put("id_unidad",listadoUnidades.get(i).getId_unidad());
        	registro1.put("nombre_unidad", listadoUnidades.get(i).getNombre_unidad());
        	registro1.put("unidad_padre", listadoUnidades.get(i).getUnidad_padre());
        	registro1.put("ref_unidad_padre", listadoUnidades.get(i).getRef_unidad_padre());
        	registro1.put("id_tipo_unidad", listadoUnidades.get(i).getId_tipo_unidad());
        	registro1.put("miUnidad", listadoUnidades.get(i).getMiUnidad());
        	db.insert("unidad", null, registro1);
        }
      db.close();
     }  
    

    
 
    
    public void lanzarActivity(View v, int posicion){
    	Intent i = new Intent(this, ActivityConversion.class);
    	i.putExtra("tipo_unidad", lv1.getItemAtPosition(posicion).toString());
        startActivity(i);
    }
    
    public boolean checkUpdatesTipoUnidad(){
     	String nomArchivo = getNombreTUenTabla()+getVersionTUenTabla()+".txt";
    	File tarjeta = Environment.getExternalStorageDirectory();
        File arch = new File(tarjeta.getAbsolutePath(),nomArchivo);
        if(arch.exists()){
        	  return false;
          }
          	  return true;
    }
    
    public boolean checkUpdatesUnidad(){
     	String nomArchivo = getNombreUenTabla()+getVersionUenTabla()+".txt";
    	File tarjeta = Environment.getExternalStorageDirectory();
        File arch = new File(tarjeta.getAbsolutePath(),nomArchivo);
        if(arch.exists()){
        	return false;
        }
          	return true;
    }
    
    public String getNombreTUenTabla(){
    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
   	 SQLiteDatabase bd=admin.getWritableDatabase();
   	 String nomArchivo="";
	  Cursor fila = bd.rawQuery("select * from archivotu", null);
     if (fila.moveToFirst()) {
         //Recorremos el cursor hasta que no haya más registros
         do {
              nomArchivo = fila.getString(0);
         } while(fila.moveToNext());
    }
     return nomArchivo;
    }
    
    public String getNombreUenTabla(){
    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
   	 SQLiteDatabase bd=admin.getWritableDatabase();
   	 String nomArchivo="";
	  Cursor fila = bd.rawQuery("select * from archivou", null);
     if (fila.moveToFirst()) {
         //Recorremos el cursor hasta que no haya más registros
         do {
              nomArchivo = fila.getString(0);
         } while(fila.moveToNext());
    }
     return nomArchivo;
    }
    
    public int getVersionTUenTabla(){
    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
   	 SQLiteDatabase bd=admin.getWritableDatabase();
   	 int verArchivo=0;
	  Cursor fila = bd.rawQuery("select * from archivotu", null);
     if (fila.moveToFirst()) {
         //Recorremos el cursor hasta que no haya más registros
         do {
              verArchivo = fila.getInt(1);
         } while(fila.moveToNext());
    }
     return verArchivo;
    }
    
    public int getVersionUenTabla(){
    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
   	 SQLiteDatabase bd=admin.getWritableDatabase();
   	 int verArchivo=0;
	  Cursor fila = bd.rawQuery("select * from archivou", null);
     if (fila.moveToFirst()) {
         //Recorremos el cursor hasta que no haya más registros
         do {
              verArchivo = fila.getInt(1);
         } while(fila.moveToNext());
    }
     return verArchivo;
    }
    
    public void actualizarVersionArchivoTU(String nomArchivo, int newVersion){
    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
   	 SQLiteDatabase bd=admin.getWritableDatabase();
   	 bd.execSQL("delete from archivotu");
   	 ContentValues registro = new ContentValues();
   	 registro.put("nombre_archivo",nomArchivo );
   	 registro.put("version", newVersion);
   	 bd.insert("archivotu", null, registro);
   	 bd.close();
    }
    
    public void actualizarVersionArchivoU(String nomArchivo, int newVersion){
    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
   	 SQLiteDatabase bd=admin.getWritableDatabase();
   	 bd.execSQL("delete from archivou");
   	 ContentValues registro = new ContentValues();
   	 registro.put("nombre_archivo",nomArchivo );
   	 registro.put("version", newVersion);
   	 bd.insert("archivou", null, registro);
   	 bd.close();
    }
    

    
    public String getDBColor(){
    	String color="";
    	AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
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
    

    private class MiTareaAsincrona extends AsyncTask<Void, Integer, Void> {
    	 
        @Override
        protected Void doInBackground(Void... params) {
        
  			
  			if(checkUpdatesTipoUnidad()){
  				int newVersion = getVersionTUenTabla()+1;
  				altaTUarchivo(getNombreTUenTabla()+newVersion+".txt");
  				actualizarVersionArchivoTU(getNombreTUenTabla(), newVersion);
  			}else{
  				altaTUarchivo(getNombreTUenTabla()+getVersionTUenTabla()+".txt");
  			}
  			if(checkUpdatesUnidad()){
  				int newVersion = getVersionUenTabla()+1;
  				altaUarchivo(getNombreUenTabla()+newVersion+".txt");
  				actualizarVersionArchivoU(getNombreUenTabla(), newVersion);
  			}else{
  				altaUarchivo(getNombreUenTabla()+getVersionUenTabla()+".txt");
  			}
  			
        			cancel(true);
                    
            
     
            return null;
        }
     
        @Override
        protected void onProgressUpdate(Integer... values) {
           
        }
     
        @Override
        protected void onPreExecute() {
        	setContentView(R.layout.activity_main);
        	
        	if(getDBColor().equals("Lima")){
        		
        		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#CDDC39"));
        		Configuracion.setTema("Lima");
        	}else if(getDBColor().equals("Naranja")){
        		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F57C00"));
        		Configuracion.setTema("Naranja");
        	}else if(getDBColor().equals("Celeste")){
        		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#03A9F4"));
        		Configuracion.setTema("Celeste");
        	}else{
        		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F57C00"));
        		Configuracion.setTema("Naranja");
        	}
        	pbar = (ProgressBar)findViewById(R.id.progressBar1);
    		pbar.setIndeterminate(true);
        	lv1=(ListView)findViewById(R.id.lv1);
			titulo=(TextView)findViewById(R.id.titulo);
			actualizando = (TextView)findViewById(R.id.actualizando);
			Typeface font = Typeface.createFromAsset(getAssets(), "BEBAS___.TTF");
			actualizando.setTypeface(font);
			titulo.setTypeface(font);
			titulo.setTextColor(Color.BLACK);
			lv1=(ListView)findViewById(R.id.lv1);
        }
     
       protected void onPostExecute() {
        actualizando.setVisibility(android.view.View.GONE);
        pbar.setVisibility(android.view.View.GONE);
        }
     
        @Override
        protected void onCancelled() {
        	actualizando.setVisibility(android.view.View.GONE);
            pbar.setVisibility(android.view.View.GONE);
           
        }
    }
   
}
