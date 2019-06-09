package web.beans;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;

public class DatosPostulacionBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String apellido;
	private String ci;
	private String carrera;
	private Integer cantCreditos;
	private String universidad;
	private UploadedFile cv;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public Integer getCantCreditos() {
		return cantCreditos;
	}

	public void setCantCreditos(Integer cantCreditos) {
		this.cantCreditos = cantCreditos;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public UploadedFile getCv() {
		return cv;
	}

	public void setCv(UploadedFile cv) {
		this.cv = cv;
	}

}
