package beans;

import org.primefaces.model.UploadedFile;

public class SolicitudDT {
	
	public SolicitudDT() {
		
	}

	private Long identificacion;
	private String nombre;
	private String apellido;
	private String informacion_familiar;
	private Long ingresos;
	private String mail_solicitante;
	private String nivel_educativo;
	private String integrantes_familia;
	private Long otros_ingresos;
	private Boolean solicitud_valida;

	private UploadedFile file;
	
	
	public Long getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(Long identificacion) {
		this.identificacion = identificacion;
	}
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
	public String getInformacion_familiar() {
		return informacion_familiar;
	}
	public void setInformacion_familiar(String informacion_familiar) {
		this.informacion_familiar = informacion_familiar;
	}
	public Long getIngresos() {
		return ingresos;
	}
	public void setIngresos(Long ingresos) {
		this.ingresos = ingresos;
	}
	public String getMail_solicitante() {
		return mail_solicitante;
	}
	public void setMail_solicitante(String mail_solicitante) {
		this.mail_solicitante = mail_solicitante;
	}
	public String getNivel_educativo() {
		return nivel_educativo;
	}
	public void setNivel_educativo(String nivel_educativo) {
		this.nivel_educativo = nivel_educativo;
	}
	public String getIntegrantes_familia() {
		return integrantes_familia;
	}
	public void setIntegrantes_familia(String integrantes_familia) {
		this.integrantes_familia = integrantes_familia;
	}
	public Long getOtros_ingresos() {
		return otros_ingresos;
	}
	public void setOtros_ingresos(Long otros_ingresos) {
		this.otros_ingresos = otros_ingresos;
	}
	public Boolean getSolicitud_valida() {
		return solicitud_valida;
	}
	public void setSolicitud_valida(Boolean solicitud_valida) {
		this.solicitud_valida = solicitud_valida;
	}

	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	
}
