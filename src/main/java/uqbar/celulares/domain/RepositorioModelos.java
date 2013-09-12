package uqbar.celulares.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

@SuppressWarnings("serial")
@Observable
public class RepositorioModelos implements Serializable {
	private static RepositorioModelos instance;
	private List<ModeloCelular> data = new ArrayList<ModeloCelular>();

	public static RepositorioModelos getInstance() {
		if (instance == null) {
			instance = new RepositorioModelos();
		}
		return instance;
	}

	private RepositorioModelos() {
		this.create(new ModeloCelular("NOKIA 1100", 150, true));
		this.create(new ModeloCelular("Motorola M90", 350, true));
		this.create(new ModeloCelular("Samsung Galaxy SII", 440));
	}

	public void create(ModeloCelular modelo) {
		this.data.add(modelo);
	}

	public ModeloCelular get(String descripcion) {
		for (ModeloCelular modelo : this.data) {
			if (modelo.getDescripcion().equals(descripcion)) {
				return modelo;
			}
		}

		throw new UserException("No se encontró el modelo de celular: " + descripcion);
	}

	public List<ModeloCelular> getModelos() {
		return this.data;
	}
	
}
