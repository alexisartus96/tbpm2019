package persistencia;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuarios_agesic database table.
 * 
 */
@Entity
@Table(name="usuarios_agesic")
@NamedQuery(name="UsuariosAgesic.findAll", query="SELECT u FROM UsuariosAgesic u WHERE u.grupo=1")
public class UsuariosAgesic implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuariosAgesicPK id;
	
	private Long grupo;

	public UsuariosAgesic() {
	}

	public UsuariosAgesicPK getId() {
		return this.id;
	}

	public void setId(UsuariosAgesicPK id) {
		this.id = id;
	}

	public Long getGrupo() {
		return grupo;
	}

	public void setGrupo(Long grupo) {
		this.grupo = grupo;
	}

}