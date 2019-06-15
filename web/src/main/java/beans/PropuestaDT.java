package beans;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class PropuestaDT {
	
	public PropuestaDT() {
	}

	private String identificacion;
	private LocalDate fecha_prevista;
	private Date fecha_prevista_date;
	private String bases_llamado;
	private Long monto_total;
	private Long alto;
	private Long medio;
	private Long bajo;
	private String descripcion;
	private String persona_bps;
	private String mail;
	private Boolean recibirSolicitud;
	
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public LocalDate getFecha_prevista() {
		return fecha_prevista;
	}
	
	public void setFecha_prevista(LocalDate fecha_prevista) {
		this.fecha_prevista = fecha_prevista;
	}
	
	public String getBases_llamado() {
		return bases_llamado;
	}
	public void setBases_llamado(String bases_llamado) {
		this.bases_llamado = bases_llamado;
	}
	public Long getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(Long monto_total) {
		this.monto_total = monto_total;
	}
	public Long getAlto() {
		return alto;
	}
	public void setAlto(Long alto) {
		this.alto = alto;
	}
	public Long getMedio() {
		return medio;
	}
	public void setMedio(Long medio) {
		this.medio = medio;
	}
	public Long getBajo() {
		return bajo;
	}
	public void setBajo(Long bajo) {
		this.bajo = bajo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPersona_bps() {
		return persona_bps;
	}
	public void setPersona_bps(String persona_bps) {
		this.persona_bps = persona_bps;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Date getFecha_prevista_date() {
		return fecha_prevista_date;
	}
	public void setFecha_prevista_date(Date fecha_prevista_date) {
		SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
		this.fecha_prevista = LocalDate.parse(df.format(fecha_prevista_date));
		this.fecha_prevista_date = fecha_prevista_date;
	}
	public Boolean getRecibirSolicitud() {
		return recibirSolicitud;
	}
	public void setRecibirSolicitud(Boolean recibirSolicitud) {
		this.recibirSolicitud = recibirSolicitud;
	}
}
