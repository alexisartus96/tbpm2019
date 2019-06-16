package dt;

import java.io.Serializable;

public class ActividadDT implements Serializable {
	private static final long serialVersionUID = 6495184856847888851L;

	private Object nombre;
	private Object fechaComienzo;
	private Object fechaFin;
	private Object responsable;

	public Object getNombre() {
		return nombre;
	}

	public void setNombre(Object nombre) {
		this.nombre = nombre;
	}

	public Object getFechaComienzo() {
		return fechaComienzo;
	}

	public void setFechaComienzo(Object fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}

	public Object getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Object fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Object getResponsable() {
		return responsable;
	}

	public void setResponsable(Object responsable) {
		this.responsable = responsable;
	}

}
