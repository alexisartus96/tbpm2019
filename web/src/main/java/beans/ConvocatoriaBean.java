package beans;

import java.io.Serializable;

public class ConvocatoriaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String movilidad;
	private String convocatoria;
	private String numero;
	private Boolean recibirPostulaciones;

	public Boolean getRecibirPostulaciones() {
		return recibirPostulaciones;
	}

	public void setRecibirPostulaciones(Boolean recibirPostulaciones) {
		this.recibirPostulaciones = recibirPostulaciones;
	}

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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
