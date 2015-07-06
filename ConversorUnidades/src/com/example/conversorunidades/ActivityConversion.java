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
import android.text.InputType;
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
	
	
	Integer idTipoUnidad = 0;
	String nombreTipoUnidad;
	double resultado;
	int seleccion1;
	int seleccion2;
	Double cotizacion;
	String valorIng="";
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
		if(!valorIng.equals("")){
			et1.setText(valorIng);
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
			
	
		//btn1.setBackgroundColor(Color.argb(255, 102, 153, 255));
		//btn2.setBackgroundColor(Color.argb(255, 224, 49, 81));
		
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
			
			tvAux.setText("Resultado: ");
			lv2.setVisibility(View.GONE);
			listaUstring=new ArrayList<String>();
			listaUstring.add("AFA-Afghanistan Afghani");
			listaUstring.add("ALL-Albanian Lek");
			listaUstring.add("DZD-Algerian Dinar");
			listaUstring.add("ARS-Argentine Peso");
			listaUstring.add("AWG-Aruba Florin");
			listaUstring.add("AUD-Australian Dollar");
			listaUstring.add("BSD-Bahamian Dollar");
			listaUstring.add("BHD-Bahraini Dinar");
			listaUstring.add("BDT-Bangladesh Taka");
			listaUstring.add("BBD-Barbados Dollar");
			listaUstring.add("BZD-Belize Dollar");
			listaUstring.add("BMD-Bermuda Dollar");
			listaUstring.add("BTN-Bhutan Ngultrum");
			listaUstring.add("BOB-Bolivian Boliviano");
			listaUstring.add("BWP-Botswana Pula");
			listaUstring.add("BRL-Brazilian Real");
			listaUstring.add("GBP-British Pound");
			listaUstring.add("BND-Brunei Dollar");
			listaUstring.add("BIF-Burundi Franc");
			listaUstring.add("XOF-CFA Franc (BCEAO)");
			listaUstring.add("XAF-CFA Franc (BEAC)");
			listaUstring.add("KHR-Cambodia Riel");
			listaUstring.add("CAD-Canadian Dollar");
			listaUstring.add("CVE-Cape Verde Escudo");
		    listaUstring.add("KYD-Cayman Islands Dollar");
		    listaUstring.add("CLP-Chilean Peso");
		    listaUstring.add("CNY-Chinese Yuan");
		    listaUstring.add("COP-Colombian Peso");
		    listaUstring.add("KMF-Comoros Franc");
		    listaUstring.add("CRC-Costa Rica Colon");
		    listaUstring.add("HRK-Croatian Kuna");
		    listaUstring.add("CUP-Cuban Peso");
		    listaUstring.add("CYP-Cyprus Pound");
		    listaUstring.add("CZK-Czech Koruna");
		    listaUstring.add("DKK-Danish Krone");
		    listaUstring.add("DJF-Dijibouti Franc");
		    listaUstring.add("DOP-Dominican Peso");
		    listaUstring.add("XCD-East Caribbean Dollar");
		    listaUstring.add("EGP-Egyptian Pound");
		    listaUstring.add("SVC-El Salvador Colon");
		    listaUstring.add("EEK-Estonian Kroon");
		    listaUstring.add("ETB-Ethiopian Birr");
		    listaUstring.add("EUR-Euro");
		    listaUstring.add("FKP-Falkland Islands Pound");
		    listaUstring.add("GMD-Gambian Dalasi");
		    listaUstring.add("GHC-Ghanian Cedi");
		    listaUstring.add("GIP-Gibraltar Pound");
		    listaUstring.add("XAU-Gold Ounces");
		    listaUstring.add("GTQ-Guatemala Quetzal");
		    listaUstring.add("GNF-Guinea Franc");
		    listaUstring.add("GYD-Guyana Dollar");
		    listaUstring.add("HTG-Haiti Gourde");
		    listaUstring.add("HNL-Honduras Lempira");
		    listaUstring.add("HKD-Hong Kong Dollar");
		    listaUstring.add("HUF-Hungarian Forint");
		    listaUstring.add("ISK-Iceland Krona");
		    listaUstring.add("INR-Indian Rupee");
		    listaUstring.add("IDR-Indonesian Rupiah");
		    listaUstring.add("IQD-Iraqi Dinar");
		    listaUstring.add("ILS-Israeli Shekel");
		    listaUstring.add("JMD-Jamaican Dollar");
		    listaUstring.add("JPY-Japanese Yen");
		    listaUstring.add("JOD-Jordanian Dinar");
		    listaUstring.add("KZT-Kazakhstan Tenge");
		    listaUstring.add("KES-Kenyan Shilling");
		    listaUstring.add("KRW-Korean Won");
		    listaUstring.add("KWD-Kuwaiti Dinar");
		    listaUstring.add("LAK-Lao Kip");
		    listaUstring.add("LVL-Latvian Lat");
		    listaUstring.add("LBP-Lebanese Pound");
		    listaUstring.add("LSL-Lesotho Loti");
		    listaUstring.add("LRD-Liberian Dollar");
		    listaUstring.add("LYD-Libyan Dinar");
		    listaUstring.add("LTL-Lithuanian Lita");
		    listaUstring.add("MOP-Macau Pataca");
		    listaUstring.add("MKD-Macedonian Denar");
		    listaUstring.add("MGF-Malagasy Franc");
		    listaUstring.add("MWK-Malawi Kwacha");
		    listaUstring.add("MYR-Malaysian Ringgit");
		    listaUstring.add("MVR-Maldives Rufiyaa");
		    listaUstring.add("MTL-Maltese Lira");
		    listaUstring.add("MRO-Mauritania Ougulya");
		    listaUstring.add("MUR-Mauritius Rupee");
		    listaUstring.add("MXN-Mexican Peso");
		    listaUstring.add("MDL-Moldovan Leu");
		    listaUstring.add("MNT-Mongolian Tugrik");
		    listaUstring.add("MAD-Moroccan Dirham");
		    listaUstring.add("MZM-Mozambique Metical");
		    listaUstring.add("MMK-Myanmar Kyat");
		    listaUstring.add("NAD-Namibian Dollar");
		    listaUstring.add("NPR-Nepalese Rupee");
		    listaUstring.add("ANG-Neth Antilles Guilder");
		    listaUstring.add("NZD-New Zealand Dollar");
		    listaUstring.add("NIO-Nicaragua Cordoba");
		    listaUstring.add("NGN-Nigerian Naira");
		    listaUstring.add("KPW-North Korean Won");
		    listaUstring.add("NOK-Norwegian Krone");
		    listaUstring.add("OMR-Omani Rial");
		    listaUstring.add("XPF-Pacific Franc");
		    listaUstring.add("PKR-Pakistani Rupee");
		    listaUstring.add("XPD-Palladium Ounces");
		    listaUstring.add("PAB-Panama Balboa");
		    listaUstring.add("PGK-Papua New Guinea Kina");
		    listaUstring.add("PYG-Paraguayan Guarani");
		    listaUstring.add("PEN-Peruvian Nuevo Sol");
		    listaUstring.add("PHP-Philippine Peso");
		    listaUstring.add("XPT-Platinum Ounces");
		    listaUstring.add("PLN-Polish Zloty");
		    listaUstring.add("QAR-Qatar Rial");
		    listaUstring.add("ROL-Romanian Leu");
		    listaUstring.add("RUB-Russian Rouble");
		    listaUstring.add("WST-Samoa Tala");
		    listaUstring.add("STD-Sao Tome Dobra");
		    listaUstring.add("SAR-Saudi Arabian Riyal");
		    listaUstring.add("SCR-Seychelles Rupee");
		    listaUstring.add("SLL-Sierra Leone Leone");
		    listaUstring.add("XAG-Silver Ounces");
		    listaUstring.add("SGD-Singapore Dollar");
		    listaUstring.add("SKK-Slovak Koruna");
		    listaUstring.add("SIT-Slovenian Tolar");
		    listaUstring.add("SBD-Solomon Islands Dollar");
		    listaUstring.add("SOS-Somali Shilling");
		    listaUstring.add("ZAR-South African Rand");
		    listaUstring.add("LKR-Sri Lanka Rupee");
		    listaUstring.add("SHP-St Helena Pound");
		    listaUstring.add("SDD-Sudanese Dinar");
		    listaUstring.add("SRG-Surinam Guilder");
		    listaUstring.add("SZL-Swaziland Lilageni");
		    listaUstring.add("SEK-Swedish Krona");
		    listaUstring.add("TRY-Turkey Lira");
		    listaUstring.add("CHF-Swiss Franc");
		    listaUstring.add("SYP-Syrian Pound");
		    listaUstring.add("TWD-Taiwan Dollar");
		    listaUstring.add("TZS-Tanzanian Shilling");
		    listaUstring.add("THB-Thai Baht");
		    listaUstring.add("TOP-Tonga Pa'anga");
		    listaUstring.add("TTD-Trinidad&amp;Tobago Dollar");
		    listaUstring.add("TND-Tunisian Dinar");
		    listaUstring.add("TRL-Turkish Lira");
			listaUstring.add("USD-U.S. Dollar");
			listaUstring.add("AED-UAE Dirham");
			listaUstring.add("UGX-Ugandan Shilling");
			listaUstring.add("UAH-Ukraine Hryvnia");
			listaUstring.add("UYU-Uruguayan New Peso");
			listaUstring.add("VUV-Vanuatu Vatu");
			listaUstring.add("VEB-Venezuelan Bolivar");
			listaUstring.add("VND-Vietnam Dong");
			listaUstring.add("YER-Yemen Riyal");
			listaUstring.add("YUM-Yugoslav Dinar");
			listaUstring.add("ZMK-Zambian Kwacha");
			listaUstring.add("ZWD-Zimbabwe Dollar");
			Collections.sort(listaUstring);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaUstring);
        sp1.setAdapter(adapter);
        lv2.setAdapter(adapter);
        spAux.setAdapter(adapter);
		
		
       
	}
	
	 
	   
	   public void calcular(View view){
		   if(!nombreTipoUnidad.equals("Moneda")){
		   String unidadSelec = sp1.getSelectedItem().toString();
		  // int unidadSelecIdList = sp1.getSelectedItemPosition();
		   double valor = 0;
		   //Toast.makeText(this, "\u00b0C", Toast.LENGTH_SHORT).show();
		   if(et1.getText().toString().equals("") || et1.getText().toString().equals(null)){
			   
			   Toast.makeText(this,getString(R.string.activityConversion_ingValor, (Object[])null), Toast.LENGTH_LONG).show();
			   
		   }else{
		   valor =Double.parseDouble(et1.getText().toString());
		   valorIng= et1.getText().toString();
		   ArrayList<Double> refsResultadosTodos = new ArrayList<Double>();
		   ArrayList<String> nombresTodos = new ArrayList<String>();
		   ArrayList<String> resultadosTodos = new ArrayList<String>();
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
				  resultadosTodos.add(nombresTodos.get(i)+" "+resultadoFinalInt);
			  }else{
				  DecimalFormat formateador = new DecimalFormat("0.000000");
				  resFinal = formateador.format(resultadoFinal);
				  resultadosTodos.add(nombresTodos.get(i)+" "+resFinal);
			  }
			  
			 
		   }
		   Collections.sort(resultadosTodos);
		   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, resultadosTodos);
		   
		   lv2.setAdapter(adapter);
		   db.close(); 
		   }
	   }else{
		   if (et1.length() > 0) {
			   String from = sp1.getSelectedItem().toString().substring(0, 3);
			   String to = spAux.getSelectedItem().toString().substring(0, 3);
			   //Toast.makeText(this, to, Toast.LENGTH_SHORT).show();
               getCelsius(from, to);
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
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaUstring);
	        lv2.setAdapter(adapter);
	        et1.setText("");
	        tvAux.setText("Resultado: ");
	   }
	   
	   public Handler handler = new Handler(new Handler.Callback() {

	        @Override
	        public boolean handleMessage(Message msg) {
	            switch (msg.what) {

	                case 0:
	                	Double valor = Double.parseDouble(et1.getText().toString());
	                	Double resultado = valor*cotizacion;
	                    tvAux.setText("Resultado:        "+resultado.toString());
	                    break;
	            }
	            return false;
	        }
	    });

	   private final void getCelsius(final String fromCurr, final String toCurr) {
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
