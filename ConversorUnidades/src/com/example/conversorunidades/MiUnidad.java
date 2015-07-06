package com.example.conversorunidades;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

import datos.AccionesDB;
import datos.TipoUnidad;
import datos.Unidad;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MiUnidad extends Activity {
	
	
	private EditText et3;
	private EditText et4;
	private Spinner sp3;
	private EditText etAb;
	private TextView tvRefMetro;
	private TextView tvAb;
	private TextView tvTu;
	private TextView tvNombre;
	private TextView tvMiUnidad;
	private Button btnAceptar;
	
	private int idU;
	private String nomU;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.miunidad);
		Typeface font = Typeface.createFromAsset(getAssets(), "BEBAS___.TTF");
		tvRefMetro = (TextView)findViewById(R.id.tvRefMetro);
		tvAb = (TextView)findViewById(R.id.tvAb);
		tvNombre = (TextView)findViewById(R.id.tvNombre);
		tvTu = (TextView)findViewById(R.id.tvTunidad);
		tvMiUnidad = (TextView)findViewById(R.id.tvMiUnidad);
		btnAceptar = (Button)findViewById(R.id.btnAceptar);
		et3 = (EditText)findViewById(R.id.et3);
		et4 = (EditText)findViewById(R.id.et4);
		sp3 = (Spinner)findViewById(R.id.sp3);
		etAb = (EditText)findViewById(R.id.etAb);
		tvRefMetro.setTypeface(font);
		tvAb.setTypeface(font);
		tvNombre.setTypeface(font);
		tvTu.setTypeface(font);
		tvMiUnidad.setTypeface(font);
		btnAceptar.setTypeface(font);
		Configuration config =getResources().getConfiguration();
		
		switch(AccionesDB.getDBLanguage(this)){
		case "es":
			Locale loc = new Locale("es", "US");
			config.locale = loc;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tvRefMetro.setText(R.string.nuevaUnidad_ref);
			tvAb.setText(R.string.nuevaUnidad_ab);
			tvNombre.setText(R.string.nuevaUnidad_nombre);
			tvTu.setText(R.string.nuevaUnidad_tipo);
			tvMiUnidad.setText(R.string.miUnidad_titulo);
			btnAceptar.setText(R.string.nuevaUnidad_btnAceptar);
			break;
		case "en":
			Locale loc1 = new Locale("en", "US");
			config.locale = loc1;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tvRefMetro.setText(R.string.nuevaUnidad_ref);
			tvAb.setText(R.string.nuevaUnidad_ab);
			tvNombre.setText(R.string.nuevaUnidad_nombre);
			tvTu.setText(R.string.nuevaUnidad_tipo);
			tvMiUnidad.setText(R.string.miUnidad_titulo);
			btnAceptar.setText(R.string.nuevaUnidad_btnAceptar);
			break;
		case "pt":
			Locale loc2 = new Locale("pt", "BR");
			config.locale = loc2;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tvRefMetro.setText(R.string.nuevaUnidad_ref);
			tvAb.setText(R.string.nuevaUnidad_ab);
			tvNombre.setText(R.string.nuevaUnidad_nombre);
			tvTu.setText(R.string.nuevaUnidad_tipo);
			tvMiUnidad.setText(R.string.miUnidad_titulo);
			btnAceptar.setText(R.string.nuevaUnidad_btnAceptar);
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
		
		Bundle bundle = getIntent().getExtras();
		String miUnidad = bundle.getString("miUnidad");
		ArrayList<TipoUnidad> listaTU=AccionesDB.consultaTipoUnidad(this);
		ArrayList<String> listaTUstring=new ArrayList<String>();
		for(int i=0;i<listaTU.size();i++){
			listaTUstring.add(listaTU.get(i).getNombre_tipo_unidad());
		}
		ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listaTUstring);
		 sp3.setAdapter(adapter);
		 	sp3.setOnItemSelectedListener(new OnItemSelectedListener() {
		 		public void onItemSelected(AdapterView<?> parent, View view, 
		 	            int pos, long id) {
		 			String tUnidad = "";
		        	if(sp3.getItemAtPosition(pos).equals("Longitud")){
		        	 tUnidad = "metro:";
		        	}
		        	if(sp3.getItemAtPosition(pos).equals("Masa")){
		            	 tUnidad = "gramo:";
		            	}
		        	if(sp3.getItemAtPosition(pos).equals("Volumen")){
		            	 tUnidad = "litro:";
		            	}
		        	if(sp3.getItemAtPosition(pos).equals("Temperatura")){
		            	 tUnidad = "\u00b0C:";
		            	}
		            tvRefMetro.setText("Ref. "+tUnidad );
		 	    }
		 		 public void onNothingSelected(AdapterView<?> parent) {
		 	        // Another interface callback
		 	    }
			});
		Unidad u = AccionesDB.getMiUnidad(miUnidad, this);
		nomU = miUnidad;
		idU = u.getId_unidad();
		String [] partes = miUnidad.split(Pattern.quote("("));
		String un = partes[0].replace("(", "");
		String ab = partes[1].replace(")", "");
		
		Double ref = u.getRef_unidad_padre();
		sp3.setSelection(u.getId_tipo_unidad()-1, false);
		et4.setText(ref.toString());
		et3.setText(un);
		etAb.setText(ab);
		
	}
	

	
	
	 
	 public void lanzarAyuda(View v){
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(getString(R.string.miUnidad_ayuda, (Object[])null));
	        builder.setMessage(getString(R.string.miUnidad_ejemplo, (Object[])null));
	       
	        builder.setPositiveButton("OK",null);
	        builder.create();
	        builder.show();
	 }
	 
	 public void modifMiUnidad(View v){
	 String tUnidad = sp3.getSelectedItem().toString();
	 if((et3.getText().toString().equals("") || et3.getText().toString().equals(null))||((et4.getText().toString().equals("") || et4.getText().toString().equals(null)))||((etAb.getText().toString().equals("") || etAb.getText().toString().equals(null)))){
		 String mensaje = getString(R.string.nuevaUnidad_camposOblig);	
		 Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
	 }else{
			 double refUnidadPadre =Double.parseDouble(et4.getText().toString());
			 String abUnidad = etAb.getText().toString();
			 String nombreUnidad = et3.getText().toString();
			 int idTunidad = AccionesDB.getIdTipoUnidad(tUnidad, this);
			 String nomUnidadAb = nombreUnidad+"("+abUnidad+")";
			 if(!nomUnidadAb.equals(nomU)){
				 if(AccionesDB.validarMiUnidad(nomUnidadAb, idTunidad, this)){
					 AlertDialog.Builder builder = new AlertDialog.Builder(this);
					 builder.setTitle(findViewById(R.string.miUnidad_error).toString());
					 builder.setMessage(findViewById(R.string.miUnidad_yaExiste).toString());
					 builder.setPositiveButton("OK",null);
					 builder.create();
					 builder.show();
				 	}else{
				 	 AccionesDB.updateUnidad1(nomUnidadAb, refUnidadPadre, idTunidad, idU, this);	
				 	 finish();
				 	}
			 }else{
				 AccionesDB.updateUnidad2(refUnidadPadre, idTunidad, idU, this);	
			     finish();
			     }
		 }
	 }
	 

	 


}
