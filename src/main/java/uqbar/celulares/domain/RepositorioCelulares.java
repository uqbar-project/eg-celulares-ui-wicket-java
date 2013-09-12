package uqbar.celulares.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

/**
 * 
 * @author npasserini
 */
@Observable
public class RepositorioCelulares implements Serializable {
	private static RepositorioCelulares instance;
	private List<Celular> data = new ArrayList<Celular>();

	public static synchronized RepositorioCelulares getInstance() {
		if (instance == null) {
			instance = new RepositorioCelulares();
		}
		return instance;
	}

	private RepositorioCelulares() {
		this.create(new Celular("Natalia", 1588022202, RepositorioModelos.getInstance().get("NOKIA 1100"), false));
		this.create(new Celular("Bernardo", 1566378124, RepositorioModelos.getInstance().get("Motorola M90"), true));
		this.create(new Celular("Adalberto", 1569575222, RepositorioModelos.getInstance().get("Samsung Galaxy SII"), true));
	}

	// ********************************************************
	// ** Altas y bajas
	// ********************************************************

	public void create(Celular celular) {
		celular.validar();
		this.validarClientesDuplicados(celular);

		celular.setId(this.data.size() + 1);
		this.data.add(celular);
	}

	public void delete(Celular celular) {
		this.data.remove(celular);
	}

	protected void validarClientesDuplicados(Celular celular) {
		if (!this.search(celular.getNumero()).isEmpty()) {
			throw new UserException("Ya existe un celular con el número: " + celular.getNumero());
		}
	}

	// ********************************************************
	// ** Búsquedas
	// ********************************************************

	public List<Celular> search(Integer numero) {
		return this.search(numero, null);
	}

	/**
	 * Busca los celulares que coincidan con los datos recibidos. Tanto número como nombre pueden ser nulos,
	 * en ese caso no se filtra por ese atributo.
	 * 
	 * Soporta búsquedas por substring, por ejemplo el celular (12345, "Juan Gonzalez") será contemplado por
	 * la búsqueda (23, "Gonza")
	 */
	public List<Celular> search(Integer numero, String nombre) {
		List<Celular> resultados = new ArrayList<Celular>();

		for (Celular celular : this.data) {
			if (match(numero, celular.getNumero()) && match(nombre, celular.getNombre())) {
				resultados.add(celular);
			}
		}

		return resultados;
	}

	protected boolean match(Object expectedValue, Object realValue) {
		return expectedValue == null
			|| realValue.toString().toLowerCase().contains(expectedValue.toString().toLowerCase());
	}
	
	/**
	 * Para el proyecto web - se mantiene la busqueda por Identificador
	 * 
	 * @param numero
	 * @param nombre
	 * @return
	 */
	public Celular searchById(int id) {
		for (Celular celular : this.data) {
			if (celular.getId().equals(id)) {
				return celular;
			}
		}
		return null;
	}

}
