package persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the solicitud database table.
 * 
 */
@Embeddable
public class SolicitudPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long ci;

	@Column(name="id_proceso")
	private long idProceso;

	public SolicitudPK() {
	}
	public long getCi() {
		return this.ci;
	}
	public void setCi(long ci) {
		this.ci = ci;
	}
	public long getIdProceso() {
		return this.idProceso;
	}
	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}
	
	public void setCi(Integer ci) {
		this.ci= new Long(ci.toString());
	}

	public void setIdProceso(Integer idProceso) {
		this.idProceso= new Long(idProceso.toString());
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SolicitudPK)) {
			return false;
		}
		SolicitudPK castOther = (SolicitudPK)other;
		return 
			(this.ci == castOther.ci)
			&& (this.idProceso == castOther.idProceso);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.ci ^ (this.ci >>> 32)));
		hash = hash * prime + ((int) (this.idProceso ^ (this.idProceso >>> 32)));
		
		return hash;
	}
}