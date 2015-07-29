package com.example.conversorunidades;


import java.util.ArrayList;
import java.util.Locale;

import datos.AccionesDB;
import datos.TipoUnidad;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaUnidad extends Activity {

	private Spinner sp2;
	private TextView tv6;
	private TextView nUtitulo;
	private TextView nUtU;
	private TextView nUnom;
	private TextView nUab;
	private Button btnNuevaUnidad;
	private EditText et2;
	private EditText et3;
	private EditText et5;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevaunidad);
		sp2 = (Spinner)findViewById(R.id.sp2);
		tv6 = (TextView)findViewById(R.id.tv6);
		nUtitulo = (TextView)findViewById(R.id.nUtitulo);
		nUab = (TextView)findViewById(R.id.nUab);
		nUnom = (TextView)findViewById(R.id.nUnom);
		nUtU = (TextView)findViewById(R.id.nUtU);
		btnNuevaUnidad = (Button)findViewById(R.id.btnNuevaUnidad);
		et2 = (EditText)findViewById(R.id.et2);
		et3 = (EditText)findViewById(R.id.et3);
		et5 = (EditText)findViewById(R.id.et5);
		Typeface font = Typeface.createFromAsset(getAssets(), "BEBAS___.TTF");
		tv6.setTypeface(font);
		nUtitulo.setTypeface(font);
		nUab.setTypeface(font);
		nUnom.setTypeface(font);
		btnNuevaUnidad.setTypeface(font);
		nUtU.setTypeface(font);
		Configuration config =getResources().getConfiguration();
		switch(AccionesDB.getDBLanguage(this)){
		case "es":
			Locale loc = new Locale("es", "US");
			config.locale = loc;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tv6.setText(R.string.nuevaUnidad_ref);
			nUtitulo.setText(R.string.nuevaUnidad_titulo);
			nUab.setText(R.string.nuevaUnidad_ab);
			nUnom.setText(R.string.nuevaUnidad_nombre);
			nUtU.setText(R.string.nuevaUnidad_tipo);
			btnNuevaUnidad.setText(R.string.nuevaUnidad_btnAceptar);
			
			break;
		case "en":
			Locale loc1 = new Locale("en", "US");
			config.locale = loc1;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tv6.setText(R.string.nuevaUnidad_ref);
			nUtitulo.setText(R.string.nuevaUnidad_titulo);
			nUab.setText(R.string.nuevaUnidad_ab);
			nUnom.setText(R.string.nuevaUnidad_nombre);
			nUtU.setText(R.string.nuevaUnidad_tipo);
			btnNuevaUnidad.setText(R.string.nuevaUnidad_btnAceptar);
			break;
		case "pt":
			Locale loc2 = new Locale("pt", "BR");
			config.locale = loc2;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tv6.setText(R.string.nuevaUnidad_ref);
			nUtitulo.setText(R.string.nuevaUnidad_titulo);
			nUab.setText(R.string.nuevaUnidad_ab);
			nUnom.setText(R.string.nuevaUnidad_nombre);
			nUtU.setText(R.string.nuevaUnidad_tipo);
			btnNuevaUnidad.setText(R.string.nuevaUnidad_btnAceptar);
			break;
			
		}
		if(Configuracion.getTema().equals("Lima")){
    		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#CDDC39"));
    		
    	}else if(Configuracion.getTema().equals("Naranja")){
    		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F57C00"));
    		
    	}else if(Configuracion.getTema().equals("Celeste")){
    		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#03A9F4"));
    		
    	}else{
    		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F57C00"));
    		
    	}

		ArrayList<TipoUnidad> listaTU=AccionesDB.consultaTipoUnidad(this);
		ArrayList<String> listaTUstring=new ArrayList<String>();
		for(int i=0;i<listaTU.size();i++){
			listaTUstring.add(listaTU.get(i).getNombre_tipo_unidad());
		}
		ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listaTUstring);
		sp2.setAdapter(adapter);
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {
		 	public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
		 		String tUnidad = "";
		        if(sp2.getItemAtPosition(pos).equals("Longitud")){
		        	 tUnidad = getString(R.string.metro);
		        }
		        if(sp2.getItemAtPosition(pos).equals("Masa")){
		            	 tUnidad = getString(R.string.gramo);
		        }
		        if(sp2.getItemAtPosition(pos).equals("Volumen")){
		            	 tUnidad =getString(R.string.litro);
		        }
		        if(sp2.getItemAtPosition(pos).equals("Temperatura")){
		            	 tUnidad = "\u00b0C:";
		        }
		        tv6.setText("Ref. "+tUnidad );
		 	 }
		 	public void onNothingSelected(AdapterView<?> parent) {
		 	       
		 	    }
			});
		}
	
	

	 
	 public void lanzarAyuda(View v){
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(getString(R.string.miUnidad_ayuda, (Object[])null));
	        builder.setMessage(getString(R.string.miUnidad_ejemplo, (Object[])null));
	        builder.setPositiveButton("OK",null);
	        builder.create();
	        builder.show();
	 }
	 
	 public void altaMiUnidad(View v){
		 String tUnidad = sp2.getSelectedItem().toString();
		 
		 if((et2.getText().toString().equals("") || et2.getText().toString().equals(null))||((et3.getText().toString().equals("") || et3.getText().toString().equals(null)))||((et5.getText().toString().equals("") || et5.getText().toString().equals(null)))){
			String mensaje = getString(R.string.nuevaUnidad_camposOblig);
			 Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show();
		 }else{
			 double refUnidadPadre =Double.parseDouble(et3.getText().toString());
			 String abUnidad = et5.getText().toString();
			 String nombreUnidad = et2.getText().toString();
			 int idTunidad = AccionesDB.getIdTipoUnidad(tUnidad,this);
			 String nomUnidadAb = nombreUnidad+"("+abUnidad+")";
		if(AccionesDB.validarMiUnidad(nomUnidadAb, idTunidad,this)){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(getString(R.string.miUnidad_error, (Object[])null));
	        builder.setMessage(getString(R.string.miUnidad_yaExiste,(Object[]) null));
	        builder.setPositiveButton("OK",null);
	        builder.create();
	        builder.show();
		}else{
			 
			 int idUnidad = AccionesDB.getSgteIdUnidad(this);
			 AccionesDB.addMiUnidad(refUnidadPadre, abUnidad, nombreUnidad, idTunidad, nomUnidadAb, idUnidad, this);
		     finish();
		     Intent i = new Intent(this, MisUnidades.class );
		        startActivity(i);
		        
			}
		 }
		 
		
	 }
	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	    
	   if (keyCode == KeyEvent.KEYCODE_BACK) {
	    
		   finish();
		   Intent i = new Intent(this, MisUnidades.class );
		     startActivity(i);
	     // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
	     return true;
	   }
	 //para las demas cosas, se reenvia el evento al listener habitual
	   return super.onKeyDown(keyCode, event);
	 } 
}
