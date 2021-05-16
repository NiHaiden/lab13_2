package db;

import model.Antwort;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class AntwortRepository implements AutoCloseable{
    private static final AntwortRepository INSTANCE = new AntwortRepository();

    private AntwortRepository() {
    }

    public static AntwortRepository getInstance() {
        return INSTANCE;
    }


    public Optional<Antwort> getAntwortById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();
        try {
            return Optional.ofNullable(em.find(Antwort.class, id));
        } finally {
            em.close();
        }
    }

    public List<Antwort> findAll() {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        try {
            return em.createQuery("select a from Antwort a").getResultList();
        } finally {
            em.close();
        }
    }

    public boolean persistAntwort(Antwort antwort) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(antwort);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public boolean deleteAntwort(Antwort antwort) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            antwort = em.find(Antwort.class, antwort.getAnt_id());
            em.remove(antwort);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public Antwort updateAntwort(Antwort antwort) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (em.find(Antwort.class, antwort.getAnt_id()) != null) {
                antwort = em.merge(antwort);
            } else {
                antwort = null;
            }
            tx.commit();
            return antwort;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void close() throws Exception {
        JPAUtil.close();
    }
}
