package pr03.controlador;

import pr03.modelo.acciones.*;

/**
 * Encapsula la creacción de acciones
 */
public abstract class FactoriaAcciones {

	/**
	 * Método estático que crea un objeto acción.
	 * @param accion
	 * @return
	 */
	public static Accion creaAccion(String accion) {
		Accion accionSeleccionada = new AccionSalir();
		if (accion != null) {
			switch (accion) {
			case "acceder":
				accionSeleccionada = new AccionAcceder();
				break;
			case "verdatos":
				accionSeleccionada = new AccionVerDatos();
				break;
			case "volver":
				accionSeleccionada = new AccionVolver();
				break;
			case "inicio":
				accionSeleccionada = new AccionInicio();
			}
		}
		return accionSeleccionada;
	}
}
