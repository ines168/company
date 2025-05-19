package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.Company;
import org.example.model.Contract;
import org.example.model.Person;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("company");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Company c1 = new Company();
        c1.setName("Algebra");
        em.persist(c1);

        Person p1 = new Person();
        p1.setName("Ana Anić");
        em.persist(p1);

        Contract u1 = new Contract();
        u1.getCompanySet().add(c1);
        u1.getPersonSet().add(p1);
        em.persist(u1);

        getContracts(em);

        Person p2 = new Person();
        p2.setName("Ivo Ivić");
        em.persist(p2);

        u1.getPersonSet().add(p2);
        em.persist(u1);

        getContracts(em);

        tx.commit();
        em.close();
        emf.close();
    }

    public static void getContracts(EntityManager em) {
        List<Contract> contracts = em.createQuery("select c from Contract c", Contract.class).getResultList();
        for (Contract c : contracts) {
            System.out.println("Ugovor: ");
            for (Person person : c.getPersonSet()) {
                System.out.println("\nPersons: " + person.getName());
            }
            for (Company company : c.getCompanySet()) {
                System.out.println("\nCompany: " + company.getName());

            }
        }
    }
}