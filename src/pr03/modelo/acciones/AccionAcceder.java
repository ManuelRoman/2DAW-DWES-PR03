package pr03.modelo.acciones;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import pr03.controlador.Accion;
import pr03.modelo.beans.*;
import pr03.bbdd.*;

/**
 * Encapsula el proceso de logueo
 */
public class AccionAcceder implements Accion {

	/**
	 * Información de la vista que se devolverá
	 */
	private String vista;
	
	/**
	 * Información de la vista si no se producen errores
	 */
	private final String vistaOK = "WEB-INF/menu.jsp";
	
	/**
	 * Información de la vista si hay algún error
	 */
	private final String vistaError = "WEB-INF/gesError.jsp";
	
	/**
	 * Información del modelo que se devolverá
	 */
	private BeanUsuario modelo;
	
	/**
	 * Información del error producido
	 */
	private BeanError error;
	
	/**
	 * Objeto que encapsula la información a nivel de la aplicación
	 */
	private ServletContext sc;
	
	/**
	 * Información de la base de datos
	 */
	private DataSource ds;	
	
	/**
	 * Información de la sesion
	 */
	private HttpSession sesion;
	
	/** 
	 * Ejecuta el proceso asociado a la acción.
	 * @param request Objeto que encapsula la petición.
	 * @param response Objeto que encapsula la respuesta.
	 * @return true o false en función de que no se hayan producido errores o lo contrario.
	 * @see fotogramas.controlador.Accion#ejecutar(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean resultado = true;
		String login = request.getParameter("login");
		String clave = request.getParameter("clave");
		String repuestaCaptcha = request.getParameter("respcaptcha");
		BeanCaptcha captchaSelec = (BeanCaptcha) sesion.getAttribute("captchaSelec");
		DAO beanDao = new BeanDAO(ds);
		BeanUsuario usuario = null;
		String errorCaptcha = "Se ha equivocado en el captcha";
		if (captchaSelec.getCadenaCaptcha().equals(repuestaCaptcha)){
			sesion.removeAttribute("errorCaptcha");
			try {
				beanDao.getConexion();
				if(beanDao.ifExistUsuario(login)){
					usuario=beanDao.obtenerUsuario(login, clave);
				} else{
					resultado = false;
					throw new BeanError(2, "El login no existe.");
				}
			} catch (BeanError be) {
				resultado = false;
				error = new BeanError(be.getCodError(), be.getMensError());
				be.printStackTrace();
			} catch (SQLException sqle) {
				resultado = false;
				error = new BeanError(sqle.getErrorCode(), sqle.getMessage(), sqle);
				sqle.printStackTrace();
			} finally {
				try {
					beanDao.close();
					} catch (SQLException sqle) {
						resultado = false;
						error = new BeanError(sqle.getErrorCode(), sqle.getMessage(), sqle);
						sqle.printStackTrace();
					}
			}
		} else {
			sesion.setAttribute("errorCaptcha", errorCaptcha);
			resultado = false;
		}
		if (resultado == true) {
			vista = vistaOK;
			sesion.setAttribute("usuario", usuario);
			this.modelo=usuario;
		} else{
			vista = vistaError;		
		}
		return resultado;
	}

	/**
	 * Devuelve la vista actual.
	 * @param La vista a devolver al usuario.
	 */
	@Override
	public String getVista() {
		return this.vista;
	}

	/**
	 * Devuelve el modelo con el que trabajará la vista.
	 * @return El modelo a procesar por la vista.
	 */
	@Override
	public Object getModelo() {
		return this.modelo;
	}

	/**
	 * Establece el contexto del servlet (nivel aplicación)
	 * @param sc Objeto ServletContext que encapsula el ámbito de aplicación.
	 */
	@Override
	public void setSc(ServletContext sc) {
		this.sc = sc;
	}

	/**
	 * Devuelve un objeto de error asociado al procesamiento de la acción.
	 * @return Objeto que encapsula una situación de error producida durante la
	 * ejecución de la acción.
	 */
	@Override
	public BeanError getError() {
		return this.error;
	}

	/**
	 * Establece el datasource con el que se trabajará
	 * @param ds Datasource
	 */
	@Override
	public void setDS(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Establece el objeto que encapsula la sesión actual.
	 * @param sesion La sesión a establecer en la acción.
	 */
	@Override
	public void setSesion(HttpSession sesion) {
		this.sesion = sesion;
	}
	
}
