package uqbar.celulares.ui.wicket.app;

import org.apache.wicket.protocol.http.WebApplication;

import uqbar.celulares.ui.wicket.BusquedaCelularesPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see uqbar.celulares.ui.wicket.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

	@Override
	public Class<BusquedaCelularesPage> getHomePage() {
		return BusquedaCelularesPage.class;
	}

}
