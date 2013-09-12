package uqbar.celulares.domain;

import java.io.Serializable;
import java.util.List;

import org.uqbar.commons.utils.Observable;

/**
 * Application model que representa la búsqueda de {@link Celular}.
 * Contiene:
 * <ul>
 * 	<li>El estado de los atributos por los cuales buscar: numero y nombre</li>
 *  <li>El comportamiento para realizar la búsqueda (en realidad delega en otros objetos)</li>
 *  <li>El estado del resultado de la búsqueda, es decir que recuerda la lista de Celulares resultado</li>
 *  <li>El estado de la selección de un Celular para poder utilizar el comportamiento que sigue...</li>
 *  <li>Comportamiento para eliminar un Celular seleccionado.</li>
 * </ul> 
 * 
 * Este es un objeto transiente, que contiene estado de la ejecución para un usuario en particular
 * en una ejecución de la aplicación en particular.
 * 
 * @author npasserini
 */
@Observable
public class BuscadorCelular implements Serializable {
	private Integer numero;
	private String nombre;
	private List<Celular> resultados;
	private Celular celularSeleccionado;

	// ********************************************************
	// ** Acciones
	// ********************************************************

	public void search() {
		this.resultados = RepositorioCelulares.getInstance().search(this.numero, this.nombre);
	}

	public void clear() {
		this.nombre = "";
		this.numero = null;
	}

	public void eliminarCelularSeleccionado() {
		RepositorioCelulares.getInstance().delete(this.getCelularSeleccionado());
		this.search();
		this.celularSeleccionado = null;
	}

	// ********************************************************
	// ** Accessors
	// ********************************************************

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Celular getCelularSeleccionado() {
		return this.celularSeleccionado;
	}

	public void setCelularSeleccionado(Celular celularSeleccionado) {
		this.celularSeleccionado = celularSeleccionado;
	}

	public List<Celular> getResultados() {
		return this.resultados;
	}

	public void setResultados(List<Celular> resultados) {
		this.resultados = resultados;
	}
}
