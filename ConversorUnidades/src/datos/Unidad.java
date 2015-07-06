package datos;

public class Unidad {
	
	private int id_unidad;
	private int id_tipo_unidad;
	private String nombre_unidad;
	private double ref_unidad_padre;
	private int unidad_padre;
	private int miUnidad;
	
	public Unidad(int id_unidad, int id_tipo_unidad, String nombre_unidad, double ref_unidad_padre, int unidad_padre, int miUnidad){
		this.id_unidad=id_unidad;
		this.id_tipo_unidad=id_tipo_unidad;
		this.nombre_unidad=nombre_unidad;
		this.ref_unidad_padre=ref_unidad_padre;
		this.unidad_padre=unidad_padre;
		this.miUnidad = miUnidad;
	}
	
	public int getId_unidad() {
		return id_unidad;
	}
	public void setId_unidad(int id_unidad) {
		this.id_unidad = id_unidad;
	}
	public int getId_tipo_unidad() {
		return id_tipo_unidad;
	}
	public void setId_tipo_unidad(int id_tipo_unidad) {
		this.id_tipo_unidad = id_tipo_unidad;
	}
	public String getNombre_unidad() {
		return nombre_unidad;
	}
	public void setNombre_unidad(String nombre_unidad) {
		this.nombre_unidad = nombre_unidad;
	}
	public double getRef_unidad_padre() {
		return ref_unidad_padre;
	}
	public void setRef_unidad_padre(double ref_unidad_padre) {
		this.ref_unidad_padre = ref_unidad_padre;
	}
	public int getUnidad_padre() {
		return unidad_padre;
	}
	public void setUnidad_padre(int unidad_padre) {
		this.unidad_padre = unidad_padre;
	}

	public int getMiUnidad() {
		return miUnidad;
	}

	public void setMiUnidad(int miUnidad) {
		this.miUnidad = miUnidad;
	}
	
	
}
