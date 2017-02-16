package pr03.controlador;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import pr03.modelo.beans.BeanError;

/**
 * Servlet que encapsula el control de la aplicación.
 */
@SuppressWarnings("serial")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	/*
	 * Es recomendable establecer como propiedades del servlet aquellos objetos
	 * que encapsulan fuentes de datos.
	 */
	/**
	 * Cualquier fuente de datos que requiera acceso desde la aplicación deberá
	 * contemplarse como propiedad del servlet. En este caso, la fuente de datos
	 * es la base de datos bdfotogramas.
	 */
	private DataSource dataSource;
	/**
	 * Este objeto es precisamente el objeto que encapsula toda la información a
	 * nivel de aplicación. Se corresponde con el objeto application generado
	 * por el contenedor web al generar los servlets de las páginas JSP.
	 */
	private ServletContext sc;

	/**
	 * Inicializa el servlet, y le proporciona un objeto, ServletConfig con
	 * información de nivel de aplicación sobre el contexto de datos que rodea
	 * al servlet en el contenedor web.
	 * @see Servlet#init(ServletConfig)
	 */

	public void init(ServletConfig config) throws ServletException {
		// Imprescindible llamar a super.init(config) para tener acceso a la
		// configuración del servlet a nivel de contenedor web.
		super.init(config);
		// En este punto se procedería a obtener las referencias a las fuentes
		// de datos de la aplicación.
		try {
			InitialContext initCtx = new InitialContext();
			sc = config.getServletContext();
			String urlDataSource = (String) sc.getInitParameter("URLDataSource");
			setDataSource((DataSource) initCtx.lookup(urlDataSource));
			if (getDataSource() == null)
				System.out.println("DataSource es null.");
			// El datasource se almacena a nivel de aplicación.
			sc.setAttribute("dataSource", getDataSource());
			sc.setAttribute("appOperativa", "true");
		} catch (NamingException ne) {
			// Si no se pudiera recuperar el recurso JNDI asociado al datasource,
			// se envía un mensaje de error hacia la salida de log del servidor
			// de aplicaciones.
			System.out.println("Error: Método init(). tipo NamingException.");
			sc.setAttribute("appOperativa", "false");
		}
	}

	/**
	 * Lo último que se debe hacer antes de que se elimine la instancia del servlet.
	 * @see Servlet#destroy()
	 */

	public void destroy() {
		// Elimina el datasource del ámbito de aplicación, liberando todos los
		// recursos que tuviera asignados.
		sc.removeAttribute("dataSource");
		sc.removeAttribute("appOperativa");
		// Elimina el ámbito de aplicación.
		sc = null;
	}

	/**
	 * Procesa las peticiones que vienen por la vía GET.
	 * @param request La petición.
	 * @param response La respuesta.
	 * @throws javax.servlet.ServletException Error al ejecutar doPost()
	 * @throws java.io.IOException Error de E/S proviniente de doPost()
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Se reenvía hacia el método doPost(), ya que tanto las peticiones GET como
		// las POST se procesarán igual, y de esta manera, se evita código redundante.
		doPost(request, response);
	}

	/**
	 * Procesa las peticiones que vienen por la vía POST.
	 * @param request La petición.
	 * @param response La respuesta.
	 * @throws javax.servlet.ServletException Puede ser lanzada por forward().
	 * @throws java.io.IOException Puede ser lanzada por forward().
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (sc.getAttribute("appOperativa").equals("true")) {
			// Se obtiene el objeto de ámbito sesión
			HttpSession sesion = request.getSession();
			// Obtener un objeto de ayuda para la solicitud
			AyudaSolicitud ayudaSol = new AyudaSolicitud(request);
			// Crear un objeto de acción partiendo de los parámetros asociados a
			// la solicitud
			Accion accion = ayudaSol.getAccion();
			// Se proporciona el contexto del servlet (ámbito aplicación) a la acción
			accion.setSc(sc);
			// Se proporciona el DataSource asociado al servlet a la acción
			accion.setDS(dataSource);
			// Se proporciona el ámbito de sesión
			accion.setSesion(sesion);
			// Se procesa la solicitud (lógica de empresa)
			if (accion.ejecutar(request, response)) {
				// Si es correcto, obtener el componente relativo a la vista
				String vista = accion.getVista();
				// Añadir en la petición el modelo a visualizar
				request.setAttribute("modelo", accion.getModelo());
				// Enviar la respuesta a la solicitud
				RequestDispatcher rd = request.getRequestDispatcher(vista);
				rd.forward(request, response);
			} else {
				// Si la ejecución ha generado un error, procesarlo mediante el gestor centralizado de errores
				gesError(accion.getVista(), accion.getError(), request, response);
			}
		} else {
			gesError("gesError.jsp", new BeanError(1, "Aplicación no operativa"), request, response);
		}

	}

	/**
	 * Reenvía el proceso hacia una vista de gestión de errores.
	 * @param vistaError Página que gestionará el error.
	 * @param excepcion Objeto encapsulador de la excepción.
	 * @param request La petición.
	 * @param response La respuesta.
	 * @throws javax.servlet.ServletException Puede ser generada por forward().
	 * @throws java.io.IOException Puede ser generada por forward().
	 */
	private void gesError(String vistaError, BeanError excepcion, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(vistaError);
		request.setAttribute("error", excepcion);
		rd.forward(request, response);
	}

	/**
	 * Establece la fuente de datos para la aplicación.
	 * @param dsBdfotogramas Acceso a la base de datos bdfotogramas.
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;;
	}

	/**
	 * Obtiene la referencia a la fuente de datos de la aplicación.
	 * @return La fuente de datos asociada a la base de datos bdfotogramas.
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

}
