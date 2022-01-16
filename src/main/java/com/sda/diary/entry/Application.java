package com.sda.diary.entry;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.diary.frontend.UserInterface;
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
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EntryRepositoryImpl entryRepositoryImpl = new EntryRepositoryImpl(sessionFactory);
        EntryService entryService = new EntryService(entryRepositoryImpl, objectMapper);
        EntryController entryController = new EntryController(entryService, objectMapper);

        // symulacja interfejsu
        UserInterface userInterface = new UserInterface(entryController);
        userInterface.run();
    }
}
