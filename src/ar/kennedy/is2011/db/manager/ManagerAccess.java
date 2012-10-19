package ar.kennedy.is2011.db.manager;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * @author ml - mgo
 * En el modo de persistencia JPA, la aplicación interactúa a través de un 
 * Entity Manager. Véase:
 * https://developers.google.com/appengine/docs/java/datastore/jpa/overview-dn2
 * 
 * El objeto em es un singleton
 * 
 */
public final class ManagerAccess {
    //nótese que "transactions-optional" refiere al persistent-unit-name, establecido en META-INF/persistence.xml
	private static final EntityManager em = 
			Persistence.createEntityManagerFactory("transactions-optional").createEntityManager();

    private ManagerAccess() {}

    public static EntityManager get() {
    	return em;
    }
    
}