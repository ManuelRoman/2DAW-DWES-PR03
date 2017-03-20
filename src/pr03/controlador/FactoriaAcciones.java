package pr03.controlador;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import pr03.modelo.acciones.*;
import pr03.utilidades.*;

/**
 * Encapsula la creacción de acciones
 */
public abstract class FactoriaAcciones {
	
	private static HashMap<String, Accion> listaAcciones = null;

	/**
	 * Método estático que crea un objeto acción.
	 * @param accion
	 * @return
	 */
	public static Accion creaAccion(String accion, String archivoAcciones) {
		System.out.println("Acceso a creaAccion, acción: " + accion);
		if (listaAcciones == null) {
			System.out.println("Accede al archivo propiedades");
			Properties propiedades = LeeAcciones.getPropiedades(archivoAcciones);
			listaAcciones = new HashMap<String, Accion>();
			Enumeration e = propiedades.keys();
			while (e.hasMoreElements()) {
				String clave = (String) e.nextElement();
				String valorAccion = (String) propiedades.get(clave);
				Class clase = null;
				try {
					clase = Class.forName(valorAccion);
					Accion valor = (Accion) clase.newInstance();
					listaAcciones.put(clave, valor);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		Accion accionSeleccionada = listaAcciones.get(accion);
		return accionSeleccionada;
	}
}
