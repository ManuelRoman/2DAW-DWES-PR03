package pr03.controlador;

import java.io.IOException;

import javax.servlet.ServletContext;
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
	 * Objeto que encapsula la información a nivel de la aplicación
	 */
	private ServletContext sc;

	/**
	 * Constructor, que recibe la petición para ser procesada posteriormente
	 * @param request La petición
	 */
	public AyudaSolicitud(HttpServletRequest request, ServletContext sc) throws ServletException, IOException {
		this.request = request;
		this.sc = sc;
	}

	/**
	 * Método que crea la acción
	 * @return Accion
	 */
	public Accion getAccion() {
		String accion = (String) request.getParameter("accion");
		String rutaArchivo = request.getServletContext().getRealPath(request.getServletContext().getInitParameter("acciones"));
		return FactoriaAcciones.creaAccion(accion, rutaArchivo);
	}

}
