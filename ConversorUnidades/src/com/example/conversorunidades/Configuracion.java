package com.example.conversorunidades;

import java.util.ArrayList;

import java.util.Locale;

import datos.AccionesDB;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;



public class Configuracion extends Activity {
	private MainActivity ma;
	private Spinner spinnerConfig;
	private Spinner spinnerTema;
	private TextView tituloConfig;
	private TextView configLenguage;
	private Button btnConfigGuardar;
	private TextView tvTema;
	private static String tema="";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracion);
		Typeface font = Typeface.createFromAsset(getAssets(), "BEBAS___.TTF");
		tvTema = (TextView)findViewById(R.id.tvTema);
		tituloConfig = (TextView)findViewById(R.id.tituloConfig);
		spinnerConfig =(Spinner) findViewById(R.id.spinnerConfig);
		spinnerTema =(Spinner) findViewById(R.id.spinnerTema);
		configLenguage = (TextView) findViewById(R.id.conifg_subtitulo1);
		btnConfigGuardar = (Button) findViewById(R.id.configBtnGuardar);
		tituloConfig.setTypeface(font);
		configLenguage.setTypeface(font);
		btnConfigGuardar.setTypeface(font);
		tvTema.setTypeface(font);
		Configuration config =getResources().getConfiguration();
		
		switch(AccionesDB.getDBLanguage(this)){
		case "es":
			Locale loc = new Locale("es", "US");
			config.locale = loc;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tvTema.setText(R.string.config_tema);
			tituloConfig.setText(R.string.config_titulo);
			configLenguage.setText(R.string.config_lenguage);
			btnConfigGuardar.setText(R.string.config_btnGuardar);
			break;
		case "en":
			Locale loc1 = new Locale("en", "US");
			config.locale = loc1;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tvTema.setText(R.string.config_tema);
			tituloConfig.setText(R.string.config_titulo);
			configLenguage.setText(R.string.config_lenguage);
			btnConfigGuardar.setText(R.string.config_btnGuardar);
			break;
		case "pt":
			Locale loc2 = new Locale("pt", "BR");
			config.locale = loc2;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tvTema.setText(R.string.config_tema);
			tituloConfig.setText(R.string.config_titulo);
			configLenguage.setText(R.string.config_lenguage);
			btnConfigGuardar.setText(R.string.config_btnGuardar);
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
		
		ArrayList<ItemData> lenguages=new ArrayList<>();
		lenguages.add(new ItemData(getString(R.string.espanol),R.drawable.spain));
		lenguages.add(new ItemData(getString(R.string.ingles),R.drawable.united_kingdom));
		lenguages.add(new ItemData(getString(R.string.portugues),R.drawable.brazil));
		ArrayList <ItemData> colores = new ArrayList<ItemData>();
		colores.add(new ItemData(getString(R.string.naranja),R.drawable.naranja));
		colores.add(new ItemData(getString(R.string.celeste),R.drawable.celeste));
		colores.add(new ItemData(getString(R.string.lima),R.drawable.lima));
		
		SpinnerAdapter adapter=new SpinnerAdapter(this,R.layout.spinner_layout,R.id.txt,lenguages);
		SpinnerAdapter adapter1=new SpinnerAdapter(this,R.layout.spinner_layout,R.id.txt,colores);
		
		spinnerConfig.setAdapter(adapter);
		spinnerTema.setAdapter(adapter1);
		
		switch(tema){
		case "Lima":
			spinnerTema.setSelection(2);
			break;
		case "Celeste":
			spinnerTema.setSelection(1);
			break;
		case "Naranja":
			spinnerTema.setSelection(0);
			break;
		default:
			spinnerTema.setSelection(0);	
		}
		
		
		switch (config.locale.getLanguage()) {
		case "es":
			spinnerConfig.setSelection(0);
			break;
		case "en":
			spinnerConfig.setSelection(1);
			break;
		case "pt":
			spinnerConfig.setSelection(2);
			break;
		default:
			spinnerConfig.setSelection(0);
			break;
		}
		
	}
	
	public void guardar(View v){
		ItemData id = (ItemData)spinnerConfig.getSelectedItem();
		ItemData id1 = (ItemData)spinnerTema.getSelectedItem();
		String seleccionIdioma = id.getText();
		String seleccionTema = id1.getText();
		Configuration config = new Configuration();
		if(seleccionTema.equals("Naranja")||seleccionTema.equals("Orange")||seleccionTema.equals("Laranja")){
			setTema("Naranja");
			
		}
		if(seleccionTema.equals("Celeste")||seleccionTema.equals("Light Blue")||seleccionTema.equals("Celestial")){
			setTema("Celeste");
			
		}
		if(seleccionTema.equals("Lima")||seleccionTema.equals("Lime")){
			setTema("Lima");
			
		}
		
		if(seleccionIdioma.equals("Español")||seleccionIdioma.equals("Espanhol")||seleccionIdioma.equals("Spanish")){
			Locale loc = new Locale("es", "US");
			config.locale = loc;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			AccionesDB.updateConfig("es",tema,this);
		}
		if(seleccionIdioma.equals("Ingles")||seleccionIdioma.equals("Inglês")||seleccionIdioma.equals("English")){
			Locale loc1 = new Locale("en", "US");
			config.locale = loc1;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			AccionesDB.updateConfig("en",tema,this);
		}
		if(seleccionIdioma.equals("Portugues")||seleccionIdioma.equals("Portuguese")){
			Locale loc2 = new Locale("pt", "BR");
			config.locale = loc2;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			AccionesDB.updateConfig("pt",tema,this);
		}
		
		
		
		
		
		
		ma.ac.finish();
		finish();
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i); 
		
		
		
        
	}
	
	public static String getTema() {
		return tema;
	}

	public static void setTema(String tema) {
		Configuracion.tema = tema;
	}
	
}
