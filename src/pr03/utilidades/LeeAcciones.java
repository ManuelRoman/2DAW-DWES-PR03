package pr03.utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import pr03.modelo.beans.BeanError;

public abstract class LeeAcciones {
	
	public static Properties getPropiedades(String archivoAcciones) {
		Properties propiedades = new Properties();
		FileInputStream entrada = null;
		try {
			entrada = new FileInputStream(archivoAcciones);
		} catch (FileNotFoundException fnfe) {
			new BeanError(5,"No se ha encontrado el archivo propiedades");
			fnfe.printStackTrace();
		}
		try {
			propiedades.load(entrada);
		} catch (IOException ioe) {
			new BeanError(6,"Error al leer del archivo propiedades", ioe);
			ioe.printStackTrace();
		}
		return propiedades;
	}

}
