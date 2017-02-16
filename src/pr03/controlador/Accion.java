/**
 * Obliga a que todas las acciones de la aplicación empleen la misma nomenclatura de 
 * métodos y funcionalidad.
 * La arquitectura del sistema de administración de solicitudes permanece inalterable.
 */
package pr03.controlador;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;

import pr03.modelo.beans.*;

/**
 * Interface Accion que deberán implementar todas las clases de tipo Accion.
 * @author Eduardo A. Ponce
 * @version 2.0
 */
public interface Accion {
	/**
	 * Método que ejecuta la acción asociada a la petición
	 * @param request La petición encapsulada
	 * @param response La respuesta encapsulada
	 * @return true si se ha ejecutado con normalidad, false en caso contrario
	 * @throws ServletException Excepción de nivel Servlet
	 * @throws IOException Excepción de E/S 
	 */
  public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException;
  
  /**
   * Devuelve la vista que procesará el modelo respuesta
   * @return Nombre de la vista
   */
  public String getVista();
  
  /**
   * Devuelve un objeto que representa el modelo que deberá procesar la vista
   * @return El modelo a visualizar
   */
  public Object getModelo();
  
  /**
   * Establece el contexto del servlet (ámbito aplicación)
   * @param sc Objeto de tipo ServletContext
   */
  public void setSc(ServletContext sc);
  
  /**
   * Devuelve un objeto Exception que representa una excepción
   * @return Objeto Exception
   */
  public BeanError getError();
  
  /**
   * Establece el datasource con el que trabajará la acción
   * @param ds Objeto DataSource
   */
  public void setDS(DataSource ds);
  
  /**
   * Establece la sesión con la que trabajará la acción
   * @param sesion Objeto HttpSession
   */
  public void setSesion(HttpSession sesion);

}
