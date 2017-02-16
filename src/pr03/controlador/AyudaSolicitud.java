package pr03.controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Clase que crea la acción según el párametro recibido
 */
public class AyudaSolicitud {

	/**
	 * La petición de la que se deducirá la acción a llevar a cabo
	 */
	private HttpServletRequest request;

	/**
	 * Constructor, que recibe la petición para ser procesada posteriormente
	 * @param request La petición
	 */
	public AyudaSolicitud(HttpServletRequest request) throws ServletException, IOException {
		this.request = request;
	}

	/**
	 * Método que crea la acción
	 * @return Accion
	 */
	public Accion getAccion() {
		String accion = (String) request.getParameter("accion");
		return FactoriaAcciones.creaAccion(accion);
	}

}
