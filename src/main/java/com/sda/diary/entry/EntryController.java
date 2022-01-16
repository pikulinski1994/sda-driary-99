package com.sda.diary.entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;
    private final ObjectMapper objectMapper;

    // GET: /entry (endpoint API)
    public String getAllEntries() {
        try {
            List<Entry> allEntries = entryService.getAllEntries();
            List<EntryDTO> allEntriesDTO = allEntries.stream()
                    .map(e -> mapToEntryDTO(e))
                    .collect(Collectors.toList());
            return objectMapper.writeValueAsString(allEntriesDTO);
        } catch (JsonProcessingException e) {
            return String.format("{\"message\": \"%s\"}", e.getMessage());
        }
    }

    // POST: /entry (endpoint API)
    public String createEntry(String data) {
        try {
            EntryDTO newEntry = objectMapper.readValue(data, EntryDTO.class);
            Entry entry = entryService.createEntry(newEntry.getTitle(), newEntry.getContent());
            EntryDTO entryDTO = mapToEntryDTO(entry);
            return objectMapper.writeValueAsString(entryDTO);
        } catch (IllegalArgumentException e) {
            return String.format("{\"message\": \"%s\"}", e.getMessage());
        } catch (JsonProcessingException e) {
            return String.format("{\"message\": \"%s\"}", e.getMessage());
        }
    }

    // Mapper
    private EntryDTO mapToEntryDTO(Entry entry) {
        EntryDTO entryDTO = new EntryDTO();
        entryDTO.setId(entry.getId());
        entryDTO.setTitle(entry.getTitle());
        entryDTO.setContent(entry.getContent());
        return entryDTO;
    }
}
