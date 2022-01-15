package com.sda.diary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Application {

    public static void main(String[] args) {
        // wstrzykiwanie zależności przez Spring
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        ObjectMapper objectMapper = new ObjectMapper();
        EntryRepository entryRepository = new EntryRepository(sessionFactory);
        EntryService entryService = new EntryService(entryRepository);
        EntryController entryController = new EntryController(entryService, objectMapper);

        String title = "my-super-title";
        String content = "asd";
        String data = String.format("{\"title\": \"%s\", \"content\": \"%s\"}", title, content);

        // To robi Spring - przechwytuje żądanie HHTP i wywołuje metode
        String response = entryController.createEntry(data);
        System.out.println(response);

        // HTTP GET: /entry
        String responseTwo = entryController.getAllEntries();
        System.out.println(responseTwo);
    }
}
