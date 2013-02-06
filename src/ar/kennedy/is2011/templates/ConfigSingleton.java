package ar.kennedy.is2011.templates;

import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateExceptionHandler;


public class ConfigSingleton {

	private static ConfigSingleton trxManagerSingleton;
	private static Configuration cfg;

	public static final String TEMPLATE_DIR = "templates";


	private ConfigSingleton(){
		init();
	}


	private void init(){
        // Initialize the FreeMarker configuration;
        // - Create a configuration instance
        cfg = new Configuration();
    
        // - Templates are stored in the WEB-INF/templates directory of the Web app.
        //cfg.setServletContextForTemplateLoading(getServletContext(), "templates");
    
        // - Set update delay to 0 for now, to ease debugging and testing.
        //   Higher value should be used in production environment.
        cfg.setTemplateUpdateDelay(0);

        // - Set an error handler that prints errors so they are readable with
        //   a HTML browser.
        cfg.setTemplateExceptionHandler(
                TemplateExceptionHandler.HTML_DEBUG_HANDLER);

        // - Use beans wrapper (recommmended for most applications)
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        // - Set the default charset of the template files

        cfg.setDefaultEncoding("ISO-8859-1");
        // - Set the charset of the output. This is actually just a hint, that
        //   templates may require for URL encoding and for generating META element
        //   that uses http-equiv="Content-type".

        cfg.setOutputEncoding("UTF-8");
        // - Set the default locale
        
        cfg.setLocale(Locale.US);
		
	}


	public static Configuration getConfiguration(){
		if (ConfigSingleton.trxManagerSingleton == null){
			synchronized (ConfigSingleton.class) { //1
				if (ConfigSingleton.trxManagerSingleton == null){ //2
					ConfigSingleton.trxManagerSingleton= new ConfigSingleton();
				}
			}
		}
		return ConfigSingleton.cfg;
	}
	//15 6003 6105 M—nica
}
