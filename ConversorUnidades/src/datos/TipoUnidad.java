package datos;

public class TipoUnidad {

	private int id_tipo_unidad;
	private String nombre_tipo_unidad;
	
	public TipoUnidad(int id_tipo_unidad, String nombre_tipo_unidad){
		this.id_tipo_unidad = id_tipo_unidad;
		this.nombre_tipo_unidad = nombre_tipo_unidad;
	}
	public int getId_tipo_unidad() {
		return id_tipo_unidad;
	}
	public void setId_tipo_unidad(int id_tipo_unidad) {
		this.id_tipo_unidad = id_tipo_unidad;
	}
	public String getNombre_tipo_unidad() {
		return nombre_tipo_unidad;
	}
	public void setNombre_tipo_unidad(String nombre_tipo_unidad) {
		this.nombre_tipo_unidad = nombre_tipo_unidad;
	}
	
	
}
