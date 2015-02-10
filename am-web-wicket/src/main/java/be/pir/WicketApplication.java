package be.pir;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.javaee.injection.JavaEEComponentInjector;

import be.pir.naming.SuffixStandardJndiNamingStrategy;
/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see be.pir.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		getComponentInstantiationListeners().add(
				new JavaEEComponentInjector(this, new SuffixStandardJndiNamingStrategy("Impl")));
	}
}
