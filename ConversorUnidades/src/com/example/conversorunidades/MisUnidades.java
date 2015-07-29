package com.example.conversorunidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import datos.AccionesDB;
import datos.Unidad;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class MisUnidades extends Activity{
	
	private ListView lv3;
	private TextView tvMisUnidades;
	private Button btnNuevaUnidad;
	private String tituloMenu;
	public static Activity ac;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.misunidades);
		tvMisUnidades = (TextView)findViewById(R.id.tvMisUnidades);
		btnNuevaUnidad = (Button)findViewById(R.id.btnNuevaUnidad);
		lv3 = (ListView)findViewById(R.id.lv3);
		Typeface font = Typeface.createFromAsset(getAssets(), "BEBAS___.TTF");
		tvMisUnidades.setTypeface(font);
		btnNuevaUnidad.setTypeface(font);
		Configuration config =getResources().getConfiguration();
		
		switch(AccionesDB.getDBLanguage(this)){
		case "es":
			Locale loc = new Locale("es", "US");
			config.locale = loc;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tvMisUnidades.setText(R.string.misUnidades_titulo);
			btnNuevaUnidad.setText(R.string.misUnidades_btn_nuevo);
			break;
		case "en":
			Locale loc1 = new Locale("en", "US");
			config.locale = loc1;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tvMisUnidades.setText(R.string.misUnidades_titulo);
			btnNuevaUnidad.setText(R.string.misUnidades_btn_nuevo);
			break;
		case "pt":
			Locale loc2 = new Locale("pt", "BR");
			config.locale = loc2;
			getBaseContext().getResources().updateConfiguration(
		            config, getBaseContext().getResources().getDisplayMetrics());
			tvMisUnidades.setText(R.string.misUnidades_titulo);
			btnNuevaUnidad.setText(R.string.misUnidades_btn_nuevo);
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
		
		ArrayList<Unidad> misUnidades1 = AccionesDB.getMisUnidades(this);
		ArrayList<String> listaMisUnidades=new ArrayList<String>();
		for(int i=0;i<misUnidades1.size();i++){
			
			listaMisUnidades.add(misUnidades1.get(i).getNombre_unidad());
			
		}
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for(int i=0;i<misUnidades1.size();i++){
			Map<String, String> datum = new HashMap<String, String>(2);
			datum.put("nombre", misUnidades1.get(i).getNombre_unidad());
		    datum.put("tipo", AccionesDB.getNombreTipoUnidad(misUnidades1.get(i).getId_tipo_unidad(),this));
		    data.add(datum);
		}
		
		
		SimpleAdapter adapter1 = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2,
                new String[] {"nombre", "tipo"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});
		
		lv3.setAdapter(adapter1);
		registerForContextMenu(lv3);
	}
	
	 @Override
	    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo)
	    {
	             
	        MenuInflater inflater = getMenuInflater();
	        AdapterView.AdapterContextMenuInfo info =
	                (AdapterView.AdapterContextMenuInfo)menuInfo;
	     String titulo = lv3.getAdapter().getItem(info.position).toString();
	     String [] t = titulo.split(Pattern.quote(","));
	     String t1 = t[0].replace("{nombre=", "");
	            menu.setHeaderTitle(t1);
	            tituloMenu = t1;
	        inflater.inflate(R.menu.menueliminar, menu);
	    }
	 
	 @Override
	    public boolean onContextItemSelected(MenuItem item) {
		 
		
	        switch (item.getItemId()) {
	        case R.id.eliminar:
	        	AccionesDB.deleteMiUnidad(tituloMenu,this);
	        	finish();
	        	 Intent i = new Intent(this, MisUnidades.class );
			 	 startActivity(i);
	        break;
	        case R.id.irAmodificar: 
	        	finish();
	            Intent i1 = new Intent(this, MiUnidad.class);
	            i1.putExtra("miUnidad", tituloMenu);
	          	startActivity(i1);
	        break;
	                      
	        }
	        return true;
	    }


	
	public void irAnuevaUnidad (View v){
		 finish();
		 Intent i = new Intent(this, NuevaUnidad.class );
	        startActivity(i);
	}

}
