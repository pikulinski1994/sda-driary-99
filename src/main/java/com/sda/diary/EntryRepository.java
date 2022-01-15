package com.sda.diary;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@RequiredArgsConstructor
public class EntryRepository {

    private final SessionFactory sessionFactory;

    public List<Entry> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Entry> entries = session.createQuery("FROM Entry", Entry.class).getResultList();
        transaction.commit();
        session.close();
        return entries;
    }

    public Entry save(Entry entry) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entry);
        transaction.commit();
        session.close();
        return entry;
    }
}
