package pr03.modelo.beans;

/**
 * Encapsula el concepto de captcha
 */
public class BeanCaptcha {

	// Propiedades
	private String archivo;

	private String cadenaCaptcha;

	public BeanCaptcha() {
		this.archivo = "";
		this.cadenaCaptcha = "";
	}

	public BeanCaptcha(String archivo, String cadenaCaptcha) {
		this.archivo = archivo;
		this.cadenaCaptcha = cadenaCaptcha;
	}

	// Setters y getters
	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getCadenaCaptcha() {
		return cadenaCaptcha;
	}

	public void setCadenaCaptcha(String cadenaCaptcha) {
		this.cadenaCaptcha = cadenaCaptcha;
	}
}
