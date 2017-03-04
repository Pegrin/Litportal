package org.wtiger.inno.litportal.dbtools.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by olymp on 05.03.2017.
 */
public class SingletonEntityManagerFactory {
    private static EntityManagerFactory ourInstance;

    private SingletonEntityManagerFactory() {
    }

    public static EntityManagerFactory getInstance() {
        if (ourInstance != null) {
            return ourInstance;
        } else {
            init();
            return ourInstance;
        }
    }

    public synchronized static void init() {
        if (ourInstance == null) {
            ourInstance = Persistence.createEntityManagerFactory("LITPORTAL");
        }
    }
}
