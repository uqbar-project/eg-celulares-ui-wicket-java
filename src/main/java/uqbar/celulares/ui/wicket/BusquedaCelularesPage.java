package uqbar.celulares.ui.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.uqbar.edu.paiu.examples.celulares.dao.RepositorioCelulares;
import org.uqbar.edu.paiu.examples.celulares.domain.Celular;
import org.uqbar.edu.paiu.examples.celulares.domain.ModeloCelular;
import org.uqbar.edu.paiu.examples.celulares.ui.arena.BuscadorCelular;

/**
 * Homepage
 */
public class BusquedaCelularesPage extends WebPage {

	private static final long serialVersionUID = 1L;

	private final BuscadorCelular buscador;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public BusquedaCelularesPage(final PageParameters parameters) {
		this.buscador = new BuscadorCelular();
		Form<BuscadorCelular> buscarForm = new Form<BuscadorCelular>("buscarCelularesForm", new CompoundPropertyModel<BuscadorCelular>(this.buscador));
		this.generarCamposBusqueda(buscarForm);
		this.generarAcciones(buscarForm);
		this.generarGrillaResultados(buscarForm);
		this.add(buscarForm);
		// Al abrir el formulario disparo la búsqueda
		this.buscarCelulares();
	}

	public void buscarCelulares() {
		this.buscador.search();
	}

	private void generarCamposBusqueda(Form<BuscadorCelular> parent) {
		parent.add(new TextField<String>("numero"));
		parent.add(new TextField<String>("nombre"));
	}

	private void generarAcciones(Form<BuscadorCelular> parent) {
		parent.add(new Button("buscar") {
			@Override
			public void onSubmit() {
				BusquedaCelularesPage.this.buscador.search();
			}

		});
		parent.add(new Button("limpiar") {
			@Override
			public void onSubmit() {
				BusquedaCelularesPage.this.buscador.clear();
			}

		});
		parent.add(new Button("nuevo") {
			@Override
			public void onSubmit() {
				BusquedaCelularesPage.this.actualizar(new Celular());
			}

		});
	}

	private void generarGrillaResultados(Form<BuscadorCelular> parent) {
		// Resultados
		parent.add(new PropertyListView<Celular>("resultados") {

			@Override
			protected void populateItem(final ListItem<Celular> item) {
				item.add(new Label("nombre"));
				item.add(new Label("numero"));
				ModeloCelular modeloCelular = item.getModelObject().getModeloCelular();
				if (modeloCelular != null) {
					item.add(new Label("modeloCelular", new Model<String>(modeloCelular.getDescripcion())));
				} else {
					item.add(new Label("modeloCelular", ""));
				}
				CheckBox checkResumen = new CheckBox("recibeResumenCuenta");
				// Ojo, no poner en HTML disabled=true porque no lo refresca al model después
				checkResumen.setEnabled(false);
				//
				item.add(checkResumen);
				item.add(new Button("editar") {
					@Override
					public void onSubmit() {
						Celular celular = item.getModelObject();
						BusquedaCelularesPage.this.actualizar(celular);
					}
				});
				item.add(new Button("eliminar") {
					@Override
					public void onSubmit() {
						RepositorioCelulares.getInstance().delete(item.getModelObject());
						BusquedaCelularesPage.this.buscador.search();
					}
				});
			}

		});
	}

	protected void actualizar(Celular celular) {
		EditarCelularPage editar = new EditarCelularPage(celular, this);
		this.setResponsePage(editar);
	}

}
