package datos;

import android.app.Application;
import android.content.Context;

public class MiAplicacion extends Application {

	private static Context context;
	
	public void onCreate(){
		super.onCreate();
		MiAplicacion.context = getApplicationContext();
	}
	
	public static Context getAppContext(){
		return MiAplicacion.context;
	}
}
