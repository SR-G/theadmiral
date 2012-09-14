package net.coljac.pirates.tools.conversion.wizkids;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.exception.GenericJDBCException;

public class HibernateUtil {
    private static final Logger log = Logger.getLogger(HibernateUtil.class);
    public static final SessionFactory sessionFactory;
    public static final ThreadGlobal<Transaction> currentTransaction = new ThreadGlobal<Transaction>();
    public static final ThreadGlobal<Session> session = new ThreadGlobal<Session>();

    static {
        try {
            final Configuration config = new Configuration();
            config.setProperty(Environment.SHOW_SQL, "true").setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider")

            .setProperty("hibernate.jdbc.batch_size", "0");

            config.configure();

            sessionFactory = config.buildSessionFactory();
        } catch (final Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void closeSession() throws HibernateException {
        final Session s = session.get();
        if ((s == null) || !s.isOpen()) {
            // System.out.println("Returning.");
            return;
        }
        final Transaction tx = currentTransaction.get();
        // noinspection ConstantConditions
        if ((tx != null) && tx.isActive()) {
            tx.commit();
        }

        s.close();
        session.set(null);
    }

    public static void commit() {
        final Session session = currentSession();
        final Transaction tx = currentTransaction.get();
        // noinspection ConstantConditions
        if ((tx != null) && tx.isActive()) {
            tx.commit();
        }
        currentTransaction.set(session.beginTransaction());
    }

    public static Query createQuery(final String queryString) {
        final Session session = currentSession();
        return session.createQuery(queryString);
    }

    public static Session currentSession() throws HibernateException {
        Session s = session.get();
        // Open a new Session, if this thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            // Store it in the ThreadLocal variable
            session.set(s);
        }
        if (currentTransaction.get() == null) {
            try {
                final Transaction tx = s.beginTransaction();
                currentTransaction.set(tx);
            } catch (final GenericJDBCException e) {

                log.error("Could not instantiate transaction: " + e.getMessage());
            }

        }
        return s;
    }

    public static void save(final Object o) {
        currentSession().save(o);
    }
}