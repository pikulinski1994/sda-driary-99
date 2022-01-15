package com.sda.diary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;
    private final ObjectMapper objectMapper;

    // GET: /entry
    public String getAllEntries() {
        try {
            List<Entry> allEntries = entryService.getAllEntries();
            return objectMapper.writeValueAsString(allEntries);
        } catch (JsonProcessingException e) {
            return String.format("{\"message\": \"%s\"}", e.getMessage());
        }
    }

    // POST: /entry
    public String createEntry(String data) {
        try {
            Entry newEntry = objectMapper.readValue(data, Entry.class);
            Entry entry = entryService.createEntry(newEntry);
            return objectMapper.writeValueAsString(entry);
        } catch (IllegalArgumentException e) {
            return String.format("{\"message\": \"%s\"}", e.getMessage());
        } catch (JsonProcessingException e) {
            return String.format("{\"message\": \"%s\"}", e.getMessage());
        }
    }
}
