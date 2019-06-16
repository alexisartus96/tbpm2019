package persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the solicitud database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Solicitud.findAll", query="SELECT s FROM Solicitud s"),
@NamedQuery(name="Solicitud.byId", query="SELECT s FROM Solicitud s WHERE s.id.ci =:ci AND s.id.idProceso =:idProceso"),
@NamedQuery(name="Solicitud.byProcesoId", query="SELECT s FROM Solicitud s WHERE s.id.idProceso =:idProceso"),
@NamedQuery(name="Solicitud.ValidadasByProcesoId", query="SELECT s FROM Solicitud s WHERE s.id.idProceso =:idProceso and s.validado=true ORDER by s.prioridad ASC"),
@NamedQuery(name="Solicitud.ByProcesoIdOrderPrioridad", query="SELECT s FROM Solicitud s WHERE s.id.idProceso =:idProceso ORDER by s.prioridad ASC"),
@NamedQuery(name="Solicitud.ConfirmadasByProcesoId", query="SELECT s FROM Solicitud s WHERE s.id.idProceso =:idProceso and s.confirmado=true ORDER by s.prioridad ASC")
})
public class Solicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SolicitudPK id;

	private String apellido;

	@Column(name="datos_familiares")
	private String datosFamiliares;

	private long ingresos;

	@Column(name="integrantes_familia")
	private long integrantesFamilia;

	@Column(name="mail_solicitante")
	private String mailSolicitante;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_entrevista")
	private Date fechaEntrevista;

	public Date getFechaEntrevista() {
		return fechaEntrevista;
	}

	public void setFechaEntrevista(Date fechaEntrevista) {
		this.fechaEntrevista = fechaEntrevista;
	}

	@Column(name="nivel_educativo")
	private String nivelEducativo;

	private String url_doc;
	
	private String nombre;

	@Column(name="otros_ingresos")
	private long otrosIngresos;

	private long prioridad;

	@Column(name="rango_subsidio")
	private String rangoSubsidio;

	private Boolean validado;
	
	private Boolean confirmado;

	public Solicitud() {
	}

	public SolicitudPK getId() {
		return this.id;
	}

	public void setId(SolicitudPK id) {
		this.id = id;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDatosFamiliares() {
		return this.datosFamiliares;
	}

	public void setDatosFamiliares(String datosFamiliares) {
		this.datosFamiliares = datosFamiliares;
	}

	public long getIngresos() {
		return this.ingresos;
	}

	public void setIngresos(long ingresos) {
		this.ingresos = ingresos;
	}

	public long getIntegrantesFamilia() {
		return this.integrantesFamilia;
	}

	public void setIntegrantesFamilia(long integrantesFamilia) {
		this.integrantesFamilia = integrantesFamilia;
	}

	public String getMailSolicitante() {
		return this.mailSolicitante;
	}

	public void setMailSolicitante(String mailSolicitante) {
		this.mailSolicitante = mailSolicitante;
	}

	public String getNivelEducativo() {
		return this.nivelEducativo;
	}

	public void setNivelEducativo(String nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getOtrosIngresos() {
		return this.otrosIngresos;
	}

	public void setOtrosIngresos(long otrosIngresos) {
		this.otrosIngresos = otrosIngresos;
	}

	public long getPrioridad() {
		return this.prioridad;
	}

	public void setPrioridad(long prioridad) {
		this.prioridad = prioridad;
	}
	
	public void setPrioridad(Integer i){
		prioridad= new Long(i.toString());
	}

	public String getRangoSubsidio() {
		return this.rangoSubsidio;
	}

	public void setRangoSubsidio(String rangoSubsidio) {
		this.rangoSubsidio = rangoSubsidio;
	}

	public Boolean getValidado() {
		return this.validado;
	}

	public void setValidado(Boolean validado) {
		this.validado = validado;
	}
	
	public void setIngresos(Integer i) {
		ingresos= new Long(i.toString());
	}
	
	public void setIntegrantesFamilia(Integer i) {
		integrantesFamilia= new Long(i.toString());
	}
	
	public void setOtrosIngresos(Integer i) {
		otrosIngresos= new Long(i.toString());
	}

	public Boolean getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}

	public String getUrl_doc() {
		return url_doc;
	}

	public void setUrl_doc(String url_doc) {
		this.url_doc = url_doc;
	}
	

}