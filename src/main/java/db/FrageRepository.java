package db;

import model.Frage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class FrageRepository implements AutoCloseable{
    private static final FrageRepository INSTANCE = new FrageRepository();

    private FrageRepository() {
    }

    public static FrageRepository getInstance() {
        return INSTANCE;
    }


    public Optional<Frage> getFrageById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();
        try {
            return Optional.ofNullable(em.find(Frage.class, id));
        } finally {
            em.close();
        }
    }

    public List<Frage> findAll() {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        try {
            return em.createQuery("select a from Frage a").getResultList();
        } finally {
            em.close();
        }
    }

    public boolean persistFrage(Frage frage) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(frage);
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

    public boolean deleteFrage(Frage frage) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            frage = em.find(Frage.class, frage.getId());
            em.remove(frage);
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

    public Frage updateFrage(Frage frage) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (em.find(Frage.class, frage.getId()) != null) {
                frage = em.merge(frage);
            } else {
                frage = null;
            }
            tx.commit();
            return frage;
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
