package db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory EMF = null;

    public static EntityManagerFactory getEMF(String persistenceUnit) {
        if(EMF == null) {
            EMF = Persistence.createEntityManagerFactory(persistenceUnit);
        }
        return EMF;
    }

    public static void close() {
        if (EMF.isOpen()) {
            EMF.close();
            EMF = null;
        }
    }

}
