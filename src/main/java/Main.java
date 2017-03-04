import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.wtiger.inno.litportal.models.rows.PostsEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.UUID;

/**
 * Created by olymp on 04.03.2017.
 */
public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
//        final Session session = getSession();
//        try {
        System.out.println("querying all the managed entities...");
        EntityManager em = Persistence.createEntityManagerFactory("LITPORTAL").createEntityManager();
        PostsEntity post = em.find(PostsEntity.class,
                UUID.fromString("5d95b4a2-6f22-4c9f-aa7f-f2c2b4a0b27a"));
        System.out.println(post.getHead());
//            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                final String entityName = entityType.getName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    if (o instanceof UsersEntity){
//                        System.out.println("  " + ((UsersEntity)o).getLogin());
//                    }else {
//                        System.out.println("  " + o);
//                    }
//                }
//            }
//        } finally {
//            session.close();
//        }
    }
}