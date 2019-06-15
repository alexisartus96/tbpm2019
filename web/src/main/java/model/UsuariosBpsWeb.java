package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuarios_bps_web database table.
 * 
 */
@Entity
@Table(name="usuarios_bps_web")
@NamedQueries({
@NamedQuery(name="UsuariosBpsWeb.findAll", query="SELECT u FROM UsuariosBpsWeb u"),
@NamedQuery(name="Usuario.login", query="SELECT u FROM UsuariosBpsWeb u WHERE u.usuario =:usuario AND u.contrase�a =:contrase�a")
})
public class UsuariosBpsWeb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String usuario;

	private String contrase�a;

	public UsuariosBpsWeb() {
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrase�a() {
		return this.contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}

}