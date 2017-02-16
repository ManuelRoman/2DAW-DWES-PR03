package pr03.modelo.beans;

/**
 * Encapsula el concepto de usuario
 */
public class BeanUsuario {

	//Propiedades
	private String login;

	private String clave;

	private String nombre;

	public BeanUsuario() {
		this.login = "";
		this.clave = "";
		this.nombre = "";
	}

	public BeanUsuario(String login, String clave, String nombre) {
		this.login = login;
		this.clave = clave;
		this.nombre = nombre;
	}

	//Getters y setters
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
