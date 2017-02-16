package pr03.bbdd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import pr03.modelo.beans.BeanCaptcha;
import pr03.modelo.beans.BeanError;
import pr03.modelo.beans.BeanUsuario;

/**
 * Encapsula la comunicación con la base de datos
 */
public class BeanDAO implements DAO {

	/**
	 * Información de la base de datos
	 */
	private DataSource dataSource;

	/**
	 * Representa la conexión con la base de datos
	 */
	private Connection conexion = null;

	/**
	 * Constructor que recibe el DataSource
	 * 
	 * @param datasource
	 */
	public BeanDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Proceso que crea la conexión con la base de datos.
	 * 
	 * @return la conexión
	 * @throws SQLException
	 */
	@Override
	public Connection getConexion() throws SQLException {
		if (conexion == null) {
			this.conexion = dataSource.getConnection();
		}
		return conexion;
	}

	/**
	 * Proceso que cierra la conexión con la base de datos.
	 * 
	 * @throws SQLException
	 */
	@Override
	public void close() throws SQLException {
		if (conexion != null) {
			conexion.close();
		}
		conexion = null;

	}

	/**
	 * Proceso que comprueba si existe un usuario(login), en la base de datos
	 * 
	 * @param login
	 * @return devuelve true si existe
	 * @throws SQLException
	 */
	@Override
	public Boolean ifExistUsuario(String login) throws SQLException {
		Boolean exist = false;
		boolean conexionNula = false;
		if (conexion == null) {
			getConexion();
			conexionNula = true;
		}
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery("select login from usuarios where login = '" + login + "'");
		// Se comprueba si existe el login
		if (rs.next()) {
			exist = true;
		}
		if (st != null) {
			st = null;
			if (conexionNula) {
				close();
			}
		}
		return exist;
	}

	/**
	 * Proceso que obtiene los datos del usuario de la base de datos, si no
	 * existe el login o la clave es errónea lanza una excepción
	 * 
	 * @param login
	 * @param clave
	 * @return devuelve un objeto BeanUsuario
	 * @throws SQLException
	 * @throws BeanError
	 */
	@Override
	public BeanUsuario obtenerUsuario(String login, String clave) throws SQLException, BeanError {
		boolean conexionNula = false;
		if (conexion == null) {
			getConexion();
			conexionNula = true;
		}
		BeanUsuario usuario = null;
		Statement st = null;
		try {
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery("select login,clave,nombre from usuarios where login = '" + login + "'");
			if (rs.next()) {
				if (!rs.getString("clave").equals(clave)) {
					throw new BeanError(1, "La clave no coincide.");
				} else {
					usuario = new BeanUsuario(rs.getString("login"), rs.getString("clave"), rs.getString("nombre"));
				}
			} else {
				throw new BeanError(2, "El login no existe.");
			}
		} finally {
			if (st != null) {
				st = null;
				if (conexionNula) {
					close();
				}
			}

		}
		return usuario;
	}

	/**
	 * Proceso que obtiene los datos de un captcha
	 * @param numCaptcha
	 * @return BeanCaptcha
	 * @throws SQLException
	 */
	@Override
	public BeanCaptcha getCaptcha(int numCaptcha) throws SQLException {
		boolean conexionNula = false;
		if (conexion == null) {
			getConexion();
			conexionNula = true;
		}
		BeanCaptcha captcha = null;
		Statement st = null;
		try {
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery("select archivo,cadena from captchas where id = '" + numCaptcha + "'");
			if (rs.next()) {
				captcha = new BeanCaptcha(rs.getString("archivo"), rs.getString("cadena"));
			}
		} finally {
			if (st != null) {
				st = null;
				if (conexionNula) {
					close();
				}
			}

		}
		return captcha;
	}

	/**
	 * Proceso que obtiene el número total de captchas existente en la base de datos
	 * @return int
	 * @throws SQLException
	 */
	@Override
	public int GetNumCaptchas() throws SQLException {
		boolean conexionNula = false;
		if (conexion == null) {
			getConexion();
			conexionNula = true;
		}
		int numCaptchas = 0;
		Statement st = null;
		try {
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM captchas");
			if (rs.next()) {
				numCaptchas = rs.getInt(1);
			}
		} finally {
			if (st != null) {
				st = null;
				if (conexionNula) {
					close();
				}
			}
		}
		return numCaptchas;
	}

}
