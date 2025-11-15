package Database.Hibernate;

import Database.Hibernate.Models.Animal;
import Database.Hibernate.Models.Places;
import Database.Hibernate.Models.Workman;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DBHibernateService {

    private static final SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();

    private Session openSession() {
        return sessionFactory.openSession();
    }

    public Animal getAnimalByName(String name) {
        try (Session session = openSession()) {
            return session.createQuery("FROM Animal WHERE name = :name", Animal.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
    }

    public Workman getWorkmanById(int id) {
        try (Session session = openSession()) {
            return session.get(Workman.class, id);
        }
    }

    public int getAnimalCount() {
        try (Session session = openSession()) {
            Long count = session.createQuery("SELECT COUNT(a) FROM Animal a", Long.class)
                    .getSingleResult();
            System.out.printf("Table public.animal has exact %s rows%n", count);
            return count.intValue();
        }
    }

    public int getZooCount() {
         try (Session session = openSession()) {
            Long count = session.createQuery("SELECT COUNT(z) FROM Zoo z", Long.class)
                    .getSingleResult();
            return count.intValue();
        }
    }

    public int getPlacesCount() {
        try (Session session = openSession()) {
            Long count = session.createQuery("SELECT COUNT(p) FROM Places p", Long.class)
                    .getSingleResult();
            return count.intValue();
        }
    }

    public void insertWorkman(Workman workman) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(workman);
            tx.commit();
        }
    }

    public void insertAnimal(Animal animal) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(animal);
            tx.commit();
        }
    }

    public void updateAnimal(Animal animal) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            // Метод merge используется для обновления объекта.
            // Если объект с таким ID уже существует в базе, его поля обновляются.
            // Если не существует, он будет сохранен как новый.
            session.merge(animal);
            tx.commit();
        }
    }

    public void deleteAnimalById(int id) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            Animal animal = session.get(Animal.class, id);
            if (animal != null) {
                session.remove(animal);
            }
            tx.commit();
            }
    }

    public void insertPlaces(Places places) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(places);
            tx.commit();
        }
    }

    public List<String> getZooNameData() {
        try (Session session = openSession()) {
            return session.createQuery("SELECT z.name FROM Zoo z", String.class)
                    .getResultList();
        }
    }

    public void deleteWorkmanById(int id) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            Workman workman = session.get(Workman.class, id);
            if (workman != null) {
                session.remove(workman);
            }
            tx.commit();
        }
    }
}
