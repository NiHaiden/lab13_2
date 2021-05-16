package db;

import model.Mitarbeiter;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class MitarbeiterRepository implements AutoCloseable{
    private static final MitarbeiterRepository INSTANCE = new MitarbeiterRepository();

    private MitarbeiterRepository() {
    }

    public static MitarbeiterRepository getInstance() {
        return INSTANCE;
    }


    public Optional<Mitarbeiter> getMitarbeiterById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();
        try {
            return Optional.ofNullable(em.find(Mitarbeiter.class, id));
        } finally {
            em.close();
        }
    }

    public List<Mitarbeiter> findAll() {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        try {
            return em.createQuery("select m from Mitarbeiter m").getResultList();
        } finally {
            em.close();
        }
    }

    public boolean persistMitarbeiter(Mitarbeiter mitarbeiter) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(mitarbeiter);
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

    public boolean deleteMitarbeiter(Mitarbeiter mitarbeiter) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            mitarbeiter = em.find(Mitarbeiter.class, mitarbeiter.getMit_nr());
            em.remove(mitarbeiter);
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

    public Mitarbeiter updateMitarbeiter(Mitarbeiter mitarbeiter) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit1").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (em.find(Mitarbeiter.class, mitarbeiter.getMit_nr()) != null) {
                mitarbeiter = em.merge(mitarbeiter);
            } else {
                mitarbeiter = null;
            }
            tx.commit();
            return mitarbeiter;
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
