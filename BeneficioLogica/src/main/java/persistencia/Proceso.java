package persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the procesos database table.
 * 
 */
@Entity
@Table(name="procesos")
@NamedQueries({
@NamedQuery(name="Proceso.findAll", query="SELECT p FROM Proceso p"),
@NamedQuery(name="Proceso.byId", query="SELECT p FROM Proceso p WHERE p.idProceso =:idProceso")
})
public class Proceso implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long alto;

	private Long bajo;

	@Column(name="bases_llamado")
	private String basesLlamado;

	@Column(name="contacto_bps")
	private String contactoBps;

	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_prevista")
	private Date fechaPrevista;
	
	@Id
	@Column(name="id_proceso")
	private Long idProceso;

	@Column(name="mail_bps")
	private String mailBps;

	private Long medio;

	@Column(name="monto_total")
	private Long montoTotal;

	public Proceso() {
	}

	public Long getAlto() {
		return this.alto;
	}

	public void setAlto(Long alto) {
		this.alto = alto;
	}

	public Long getBajo() {
		return this.bajo;
	}

	public void setBajo(Long bajo) {
		this.bajo = bajo;
	}

	public String getBasesLlamado() {
		return this.basesLlamado;
	}

	public void setBasesLlamado(String basesLlamado) {
		this.basesLlamado = basesLlamado;
	}

	public String getContactoBps() {
		return this.contactoBps;
	}

	public void setContactoBps(String contactoBps) {
		this.contactoBps = contactoBps;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaPrevista() {
		return this.fechaPrevista;
	}

	public void setFechaPrevista(Date fechaPrevista) {
		this.fechaPrevista = fechaPrevista;
	}

	public Long getIdProceso() {
		return this.idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public String getMailBps() {
		return this.mailBps;
	}

	public void setMailBps(String mailBps) {
		this.mailBps = mailBps;
	}

	public Long getMedio() {
		return this.medio;
	}

	public void setMedio(Long medio) {
		this.medio = medio;
	}

	public Long getMontoTotal() {
		return this.montoTotal;
	}

	public void setMontoTotal(Long montoTotal) {
		this.montoTotal = montoTotal;
	}

	public void setIdProceso(Integer integer) {
		idProceso= new Long(integer.toString());
	}

	public void setAlto(Integer integer) {
		alto = new Long(integer.toString());
		
	}

	public void setMedio(Integer integer) {
		medio= new Long(integer);
	}

	public void setBajo(Integer integer) {
		bajo= new Long(integer.toString());
	}

	public void setMontoTotal(Integer integer) {
		montoTotal= new Long(integer.toString());
	}

}