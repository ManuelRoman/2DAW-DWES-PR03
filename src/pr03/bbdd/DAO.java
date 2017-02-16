package pr03.bbdd;

import java.sql.Connection;
import java.sql.SQLException;

import pr03.modelo.beans.*;

/**
 * Interfaz de acceso a la base de datos
 */
public interface DAO {
	
	/**
	 * Proceso que crea la conexión con la base de datos.
	 * @return la conexión
	 * @throws SQLException
	 */
	public Connection getConexion() throws SQLException;
	
	/**
     * Proceso que cierra la conexión con la base de datos.
     * @throws SQLException
     */
	public void close() throws SQLException;
	
	/**
	 * Proceso que comprueba si existe un usuario(login), en la base de datos
	 * @param login
	 * @return devuelve true si existe 
	 * @throws SQLException
	 */
	public Boolean ifExistUsuario(String login) throws SQLException;
	
	/**
	 * Proceso que obtiene los datos del usuario de la base de datos
	 * @param login
	 * @param clave
	 * @return devuelve un objeto BeanUsuario
	 * @throws SQLException
	 * @throws BeanError
	 */
	public BeanUsuario obtenerUsuario(String login, String clave) throws SQLException, BeanError;
	
	/**
	 * Proceso que obtiene el número total de captchas existente en la base de datos
	 * @return int
	 * @throws SQLException
	 */
	public int GetNumCaptchas() throws SQLException;
	
	/**
	 * Proceso que obtiene los datos de un captcha
	 * @param numCaptcha
	 * @return BeanCaptcha
	 * @throws SQLException
	 */
	public BeanCaptcha getCaptcha(int numCaptcha) throws SQLException;

}
