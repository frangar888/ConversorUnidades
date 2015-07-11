package com.example.conversorunidades;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import datos.AccionesDB;
import datos.Unidad;
import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityConversion extends Activity {

	private EditText et1;
	private Spinner sp1;
	private ListView lv2;
	private Button btn1;
	private Button btn2;
	private Spinner spAux;
	private TextView tvAux;
	private Integer idTipoUnidad = 0;
	private String nombreTipoUnidad;
	private Double resConversion;
	private Double cotizacion;
	private ArrayList<String> results = new ArrayList<String>();
	@Override
	    protected void onSaveInstanceState(Bundle outState) {
		if(outState!=null){
	    	super.onSaveInstanceState(outState);
	    	if (!results.isEmpty()) {
	        	outState.putStringArrayList("resultados", results);
	        }
	    	if(resConversion!=null){
	    		outState.putDouble("resConversion", resConversion);
	    	}
		}
	    }//Fin de onSaveInstanceState()
    private void restoreMe(Bundle state){
    	
    	if (state!=null) {
    		if(nombreTipoUnidad.equals("Moneda")){
    			 resConversion = state.getDouble("resConversion");
    			tvAux.setText("Resultado:        "+resConversion.toString());
    		}else{
    	 results = state.getStringArrayList("resultados");
    	 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, state.getStringArrayList("resultados"));
    	 lv2.setAdapter(adapter);
    		}
    	}
    	
     }//Fin de restoreMe()
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityconversion);
		 
		    
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		et1 = (EditText)findViewById(R.id.et1);
		sp1 = (Spinner)findViewById(R.id.sp1);
		lv2 = (ListView)findViewById(R.id.lv2);
		spAux = (Spinner)findViewById(R.id.spAux);
		tvAux = (TextView)findViewById(R.id.tvAux);
	
		
		if(Configuracion.getTema().equals("Lima")){
    		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#CDDC39"));
    		
    	}else if(Configuracion.getTema().equals("Naranja")){
    		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F57C00"));
    		
    	}else if(Configuracion.getTema().equals("Celeste")){
    		
    		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#03A9F4"));
    		
    	}else{
    		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F57C00"));
    		
    	}
		Configuration config =getResources().getConfiguration();
		
				switch(AccionesDB.getDBLanguage(this)){
				case "es":
					Locale loc = new Locale("es", "US");
					config.locale = loc;
					getBaseContext().getResources().updateConfiguration(
				            config, getBaseContext().getResources().getDisplayMetrics());
					btn1.setText(R.string.activityConversion_btn_Convertir);
					btn2.setText(R.string.activityConversion_btn_Limpiar);
					et1.setHint(R.string.activityConversion_Hint);
					break;
				case "en":
					Locale loc1 = new Locale("en", "US");
					config.locale = loc1;
					getBaseContext().getResources().updateConfiguration(
				            config, getBaseContext().getResources().getDisplayMetrics());
					btn1.setText(R.string.activityConversion_btn_Convertir);
					btn2.setText(R.string.activityConversion_btn_Limpiar);
					et1.setHint(R.string.activityConversion_Hint);
					break;
				case "pt":
					Locale loc2 = new Locale("pt", "BR");
					config.locale = loc2;
					getBaseContext().getResources().updateConfiguration(
				            config, getBaseContext().getResources().getDisplayMetrics());
					btn1.setText(R.string.activityConversion_btn_Convertir);
					btn2.setText(R.string.activityConversion_btn_Limpiar);
					et1.setHint(R.string.activityConversion_Hint);
					break;
					
				}
			
	

		
		Bundle bundle = getIntent().getExtras();
		nombreTipoUnidad = bundle.getString("tipo_unidad");
		Typeface font = Typeface.createFromAsset(getAssets(), "BEBAS___.TTF");
		btn1.setTypeface(font);
		btn2.setTypeface(font);
		tvAux.setTypeface(font);
		ArrayList<Unidad> listaUnidades = new ArrayList<Unidad>();
		ArrayList<String> listaUstring = new ArrayList<String>();
		
		if(!nombreTipoUnidad.equals("Moneda")){
			spAux.setVisibility(View.GONE);
			tvAux.setVisibility(View.GONE);
		listaUnidades = AccionesDB.consultaUnidad(nombreTipoUnidad,this,idTipoUnidad);
		listaUstring=new ArrayList<String>();
		
		if(nombreTipoUnidad.equals("Temperatura")){
			String nombreUnidad="";
			for(int i=0;i<listaUnidades.size();i++){
				nombreUnidad = listaUnidades.get(i).getNombre_unidad();
				String letras = "";
				if(nombreUnidad.equals("Rankine") || nombreUnidad.equals("Reaumur")){
					letras = nombreUnidad.substring(0,2);
				}else{
					letras = nombreUnidad.substring(0,1);	
				}
				String nombreUnidadFinal = nombreUnidad+"(\u00b0"+letras+")";
				listaUstring.add(nombreUnidadFinal);
			}
		}else{
			for(int i=0;i<listaUnidades.size();i++){
				listaUstring.add(listaUnidades.get(i).getNombre_unidad());
			}
		}
		
		Collections.sort(listaUstring);
		}else{
			
			tvAux.setText("Resultado:         0.0");
			lv2.setVisibility(View.GONE);
			listaUstring=new ArrayList<String>();
			listaUstring.add("AFA-Afghani Afghanistan ");
			listaUstring.add("ALL-Lek albanés");
			listaUstring.add("DZD-Dinar argelino");
			listaUstring.add("ARS-Peso Argentino");
			listaUstring.add("AWG-Aruba Florin");
			listaUstring.add("AUD-Dolar Australiano");
			listaUstring.add("BSD-Dolar de las Bahamas");
			listaUstring.add("BHD-Dinar de Bahrein");
			listaUstring.add("BDT-Bangladesh Taka");
			listaUstring.add("BBD-Dolar de Barbados");
			listaUstring.add("BZD-Dolar de Belize");
			listaUstring.add("BMD-Dolar de Bermuda");
			listaUstring.add("BTN-Bhután Ngultrum");
			listaUstring.add("BOB-Peso Boliviano");
			listaUstring.add("BWP-Pula de Botswana");
			listaUstring.add("BRL-Real Brasilero");
			listaUstring.add("GBP-Libra Esterlina");
			listaUstring.add("BND-Dolar de Brunei");
			listaUstring.add("BIF-Franco Burundi");
			listaUstring.add("XOF-CFA Franc (BCEAO)");
			listaUstring.add("XAF-CFA Franc (BEAC)");
			listaUstring.add("KHR-Riel camboyano");
			listaUstring.add("CAD-Dolar Canadiense");
			listaUstring.add("CVE-Escudo de Cabo Verde");
		    listaUstring.add("KYD-Dolar de las Islas Caiman");
		    listaUstring.add("CLP-Peso Chileno");
		    listaUstring.add("CNY-Yuan chino");
		    listaUstring.add("COP-Peso Colombiano");
		    listaUstring.add("KMF-Franco Comoro");
		    listaUstring.add("CRC-Colon de Costa Rica");
		    listaUstring.add("HRK-Kuna croata");
		    listaUstring.add("CUP-Peso Cubano");
		    listaUstring.add("CYP-Libra chipriota");
		    listaUstring.add("CZK-Corona checa");
		    listaUstring.add("DKK-Corona danesa");
		    listaUstring.add("DJF-Franco Dijibouti");
		    listaUstring.add("DOP-Peso Dominicano");
		    listaUstring.add("XCD-Dólar del Caribe Oriental");
		    listaUstring.add("EGP-Libra Egipcia");
		    listaUstring.add("SVC-Colón de El Salvador");
		    listaUstring.add("EEK-Corona estonia");
		    listaUstring.add("ETB-Birr etíope");
		    listaUstring.add("EUR-Euro");
		    listaUstring.add("FKP-Libra malvinense");
		    listaUstring.add("GMD-Dalasi de Gambia");
		    listaUstring.add("GHC-Cedi de Ghana");
		    listaUstring.add("GIP-Libra de Gibraltar");
		    listaUstring.add("XAU-Onzas de oro");
		    listaUstring.add("GTQ-Quetzal de Guatemala");
		    listaUstring.add("GNF-Franco guineano");
		    listaUstring.add("GYD-Dólar de Guyana");
		    listaUstring.add("HTG-Gourde haitiano");
		    listaUstring.add("HNL-Lempira de Honduras");
		    listaUstring.add("HKD-Dólar de Hong Kong");
		    listaUstring.add("HUF-Florín húngaro");
		    listaUstring.add("ISK-Corona islandesa");
		    listaUstring.add("INR-Rupia india");
		    listaUstring.add("IDR-Rupia Indonesa");
		    listaUstring.add("IQD-Dinar iraquí");
		    listaUstring.add("ILS-Shekel israelí");
		    listaUstring.add("JMD-Dólar Jamaiquino");
		    listaUstring.add("JPY-Yen Japones");
		    listaUstring.add("JOD-Dinar Jordano");
		    listaUstring.add("KZT-Tenge de Kazajstán");
		    listaUstring.add("KES-Chelín keniano");
		    listaUstring.add("KRW-Won coreano");
		    listaUstring.add("KWD-Dinar kuwaití");
		    listaUstring.add("LAK-Lao Kip");
		    listaUstring.add("LVL-Latvian Lat");
		    listaUstring.add("LBP-Libra libanesa");
		    listaUstring.add("LSL-Loti de Lesotho");
		    listaUstring.add("LRD-Dólar liberiano");
		    listaUstring.add("LYD-Dinar libio");
		    listaUstring.add("LTL-Lita lituana");
		    listaUstring.add("MOP-Pataca de Macao");
		    listaUstring.add("MKD-Denar macedonio");
		    listaUstring.add("MGF-Franco malgache");
		    listaUstring.add("MWK-Kwacha Malawi");
		    listaUstring.add("MYR-Ringgit malayo");
		    listaUstring.add("MVR-Rupia de Maldivas");
		    listaUstring.add("MTL-Lira maltesa");
		    listaUstring.add("MRO-Ouguiya de Mauritania");
		    listaUstring.add("MUR-Rupia de Mauricio");
		    listaUstring.add("MXN-Peso Mexicano");
		    listaUstring.add("MDL-Leu moldavo");
		    listaUstring.add("MNT-Tugrik mongol");
		    listaUstring.add("MAD-Dirham marroquí");
		    listaUstring.add("MZM-Mozambique Metical");
		    listaUstring.add("MMK-Kyat de Myanmar");
		    listaUstring.add("NAD-Dólar de Namibia");
		    listaUstring.add("NPR-Rupia nepalí");
		    listaUstring.add("ANG-Guilder antillano");
		    listaUstring.add("NZD-Dólar de Nueva Zelanda");
		    listaUstring.add("NIO-Nicaragua Córdoba");
		    listaUstring.add("NGN-Naira Nigeriana");
		    listaUstring.add("KPW-Won de Corea del Norte");
		    listaUstring.add("NOK-Corona Noruega");
		    listaUstring.add("OMR-Rial omaní");
		    listaUstring.add("XPF-Franco Pacífico");
		    listaUstring.add("PKR-Rupia Pakistani");
		    listaUstring.add("XPD-Onzas de paladio");
		    listaUstring.add("PAB-Panamá Balboa");
		    listaUstring.add("PGK-Kina de Papúa Nueva Guinea");
		    listaUstring.add("PYG-Guaraní Paraguayo");
		    listaUstring.add("PEN-Nuevo Sol Peruano");
		    listaUstring.add("PHP-Peso Filipino");
		    listaUstring.add("XPT-Onzas de Platino");
		    listaUstring.add("PLN-Zloty polaco");
		    listaUstring.add("QAR-Rial Qatarí");
		    listaUstring.add("ROL-Leu rumano");
		    listaUstring.add("RUB-Rublo Ruso");
		    listaUstring.add("WST-Tala de Samoa");
		    listaUstring.add("STD-Sao Tome Dobra");
		    listaUstring.add("SAR-Riyal saudí");
		    listaUstring.add("SCR-Rupia de Seychelles");
		    listaUstring.add("SLL-Leona de Sierra Leona");
		    listaUstring.add("XAG-Onzas de Plata");
		    listaUstring.add("SGD-Dólar de Singapur");
		    listaUstring.add("SKK-Corona Eslovaca");
		    listaUstring.add("SIT-Tolar esloveno");
		    listaUstring.add("SBD-Dólar de las Islas Salomón");
		    listaUstring.add("SOS-Chelín somalí");
		    listaUstring.add("ZAR-Rand sudafricano");
		    listaUstring.add("LKR-Rupia de Sri Lanka");
		    listaUstring.add("SHP-Libra de Santa Helena");
		    listaUstring.add("SDD-Dinar Sudanes");
		    listaUstring.add("SRG-Guilder de Surinám");
		    listaUstring.add("SZL-Lilangeni de Suazilandia");
		    listaUstring.add("SEK-Corona Sueca");
		    listaUstring.add("TRY-Lira Turca");
		    listaUstring.add("CHF-Franco Suizo");
		    listaUstring.add("SYP-Libra Siria");
		    listaUstring.add("TWD-Dólar de Taiwan");
		    listaUstring.add("TZS-Shilling tanzano");
		    listaUstring.add("THB-Baht tailandés");
		    listaUstring.add("TOP-Tonga Pa'anga");
		    listaUstring.add("TTD-Dólar de Trinidad&amp;Tobago");
		    listaUstring.add("TND-Dinar tunecino");
		    listaUstring.add("TRL-Lira Turca");
			listaUstring.add("USD-Dólar estadounidense");
			listaUstring.add("AED-Dirham de EAU");
			listaUstring.add("UGX-Chelín ugandés");
			listaUstring.add("UAH-Hryvnia de Ucrania");
			listaUstring.add("UYU-Peso Uruguayo");
			listaUstring.add("VUV-Vanuatu Vatu");
			listaUstring.add("VEB-Bolivar Venezolano");
			listaUstring.add("VND-Dong de Vietnam");
			listaUstring.add("YER-Rial yemení");
			listaUstring.add("YUM-Dinar Yugoslavo");
			listaUstring.add("ZMK-Kwacha zambiano");
			listaUstring.add("ZWD-Dólar de Zimbabwe");
			Collections.sort(listaUstring);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaUstring);
        sp1.setAdapter(adapter);
        lv2.setAdapter(adapter);
        spAux.setAdapter(adapter);
		
        restoreMe(savedInstanceState); 
       
	}
	
	 
	   
	   public void calcular(View view){
		   if(!nombreTipoUnidad.equals("Moneda")){
		   results.clear();
		   String unidadSelec = sp1.getSelectedItem().toString();
		   double valor = 0;
		   if(et1.getText().toString().equals("") || et1.getText().toString().equals(null)){
			   Toast.makeText(this,getString(R.string.activityConversion_ingValor, (Object[])null), Toast.LENGTH_LONG).show();
		   }else{
		   valor =Double.parseDouble(et1.getText().toString());
		   //valorIng= et1.getText().toString();
		   ArrayList<Double> refsResultadosTodos = new ArrayList<Double>();
		   ArrayList<String> nombresTodos = new ArrayList<String>();
		  
		   AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, AdminSQLiteOpenHelper.DB_NAME, null, AdminSQLiteOpenHelper.versionDB);
		   SQLiteDatabase db =admin.getWritableDatabase();
		   String args [] = {unidadSelec};
		   double refUnidadSelec = 0;
		   Integer idTipoUnidadSelec = 0;
		   Cursor fila = db.rawQuery("select ref_unidad_padre, id_tipo_unidad from unidad where nombre_unidad= ?", args);
		   if(fila.moveToFirst()){
			   do{
				   refUnidadSelec = fila.getDouble(0);
				   idTipoUnidadSelec = fila.getInt(1);
			   }while(fila.moveToNext());
		   }
		   String args1 [] = {idTipoUnidadSelec.toString()};
		   Cursor fila1 = db.rawQuery("select ref_unidad_padre, nombre_unidad from unidad where id_tipo_unidad= ?", args1);
		   if(fila1.moveToFirst()){
			   do{
				   refsResultadosTodos.add(fila1.getDouble(0));
				   nombresTodos.add(fila1.getString(1));
			   }while(fila1.moveToNext());
		   }
		   Double r1=0.0;
		   Double resultadoFinal=0.0;
		   String resFinal="";
		   for(int i=0; i<refsResultadosTodos.size(); i++){
			  r1 = valor*refUnidadSelec;
			  
			  resultadoFinal = r1/refsResultadosTodos.get(i);
			  int resultadoFinalInt = resultadoFinal.intValue();
			  if(resultadoFinal == resultadoFinalInt){
				  results.add(nombresTodos.get(i)+" "+resultadoFinalInt);
				 
			  }else{
				  DecimalFormat formateador = new DecimalFormat("0.000000");
				  resFinal = formateador.format(resultadoFinal);
				  results.add(nombresTodos.get(i)+" "+resFinal);
				 
			  }
			  
			 
		   }
		  
		   Collections.sort(results);
		  
		   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results);
		   
		   lv2.setAdapter(adapter);
		   db.close(); 
		   }
	   }else{
		   if (et1.length() > 0) {
			   String from = sp1.getSelectedItem().toString().substring(0, 3);
			   String to = spAux.getSelectedItem().toString().substring(0, 3);
               getCotizacion(from, to);
           } else {
               Toast.makeText(this, R.string.activityConversion_ingValor, Toast.LENGTH_LONG).show();
           }
	   }
	   }
	   
	   public void limpiar(View view){
		   
		   ArrayList<Unidad> listaUnidades = AccionesDB.consultaUnidad(nombreTipoUnidad, this,idTipoUnidad);
			
			ArrayList<String> listaUstring=new ArrayList<String>();
			for(int i=0;i<listaUnidades.size();i++){
				listaUstring.add(listaUnidades.get(i).getNombre_unidad());
			}
			Collections.sort(listaUstring);
			results = listaUstring;
			resConversion = null;
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaUstring);
	        lv2.setAdapter(adapter);
	        et1.setText("");
	        tvAux.setText("Resultado:         0.0");
	   }
	   
	   public Handler handler = new Handler(new Handler.Callback() {

	        @Override
	        public boolean handleMessage(Message msg) {
	            switch (msg.what) {

	                case 0:
	                	Double valor = Double.parseDouble(et1.getText().toString());
	                	Double resultado = valor*cotizacion;
	                	resConversion = resultado;
	                    tvAux.setText("Resultado:        "+resConversion.toString());
	                    break;
	            }
	            return false;
	        }
	    });

	   private final void getCotizacion(final String fromCurr, final String toCurr) {
	        new Thread(new Runnable() {

	            @Override
	            public void run() {
	                SoapRequests ex = new SoapRequests();
	                cotizacion = ex.getConversionRate(fromCurr, toCurr);
	                handler.sendEmptyMessage(0);
	                }
	        }).start();
	    }
	
	  
	   
}
