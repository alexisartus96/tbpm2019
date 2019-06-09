package beans;

import java.io.Serializable;
import java.util.Date;

public class DatosMovilidadBean implements Serializable{
	private static final long serialVersionUID = 4197095432907898861L;
	
	private String movilidad;
	private String convocatoria;
	private Date fechaPostulaciones;
	private String carrerasInvolucradas;
	private String duracionPrevista;
	private String bases;
	private String descripcion;
	private String nombreDGRC;
	private String mailDGRC;

	public String getMovilidad() {
		return movilidad;
	}

	public void setMovilidad(String movilidad) {
		this.movilidad = movilidad;
	}

	public String getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Date getFechaPostulaciones() {
		return fechaPostulaciones;
	}

	public void setFechaPostulaciones(Date fechaPostulaciones) {
		this.fechaPostulaciones = fechaPostulaciones;
	}

	public String getCarrerasInvolucradas() {
		return carrerasInvolucradas;
	}

	public void setCarrerasInvolucradas(String carrerasInvolucradas) {
		this.carrerasInvolucradas = carrerasInvolucradas;
	}

	public String getDuracionPrevista() {
		return duracionPrevista;
	}

	public void setDuracionPrevista(String duracion) {
		this.duracionPrevista = duracion;
	}

	public String getBases() {
		return bases;
	}

	public void setBases(String bases) {
		this.bases = bases;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreDGRC() {
		return nombreDGRC;
	}

	public void setNombreDGRC(String nombreDGRC) {
		this.nombreDGRC = nombreDGRC;
	}

	public String getMailDGRC() {
		return mailDGRC;
	}

	public void setMailDGRC(String mailDGRC) {
		this.mailDGRC = mailDGRC;
	}

}
