package com.sda.diary;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;

    List<Entry> getAllEntries() {
        List<Entry> entries = entryRepository.findAll();
        return entries;
    }

    Entry createEntry(Entry entry) {
        String title = entry.getTitle();
        String content = entry.getContent();
        if (title == null || content == null || title.isBlank() || content.isBlank()) {
            throw new IllegalArgumentException("Validacja danych nie powiodła się");
        }
        Entry savedEntry = entryRepository.save(entry);
        return savedEntry;
    }
}
