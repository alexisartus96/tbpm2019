package persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the usuarios_agesic database table.
 * 
 */
@Embeddable
public class UsuariosAgesicPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String usr;

	private String pswr;

	public UsuariosAgesicPK() {
	}
	public String getUsr() {
		return this.usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getPswr() {
		return this.pswr;
	}
	public void setPswr(String pswr) {
		this.pswr = pswr;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsuariosAgesicPK)) {
			return false;
		}
		UsuariosAgesicPK castOther = (UsuariosAgesicPK)other;
		return 
			this.usr.equals(castOther.usr)
			&& this.pswr.equals(castOther.pswr);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.usr.hashCode();
		hash = hash * prime + this.pswr.hashCode();
		
		return hash;
	}
}