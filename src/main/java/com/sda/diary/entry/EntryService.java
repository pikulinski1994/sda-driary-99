package com.sda.diary.entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final ObjectMapper objectMapper;

    List<Entry> getAllEntries() {
        List<Entry> entries = entryRepository.findAll();
        return entries;
    }

    Entry createEntry(String title, String content) {
        if (title == null || content == null || title.isBlank() || content.isBlank()) {
            throw new IllegalArgumentException("Validacja danych nie powiodła się");
        }

        Entry entry = new Entry();
        entry.setTitle(title);
        entry.setContent(content);

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://worldclockapi.com/api/json/utc/now"))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            TimeDTO timeDTO = objectMapper.readValue(responseBody, TimeDTO.class);
            String currentDateTime = timeDTO.getCurrentDateTime();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");
            LocalDateTime localDateTime = LocalDateTime.parse(currentDateTime, dateTimeFormatter);
            Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
            entry.setCreationDate(instant);
        } catch (Exception e) {
            // status code 500
            throw new IllegalArgumentException("Komunikacja z serwerm zewnętrsznym nie powiodła się");
        }

        Entry savedEntry = entryRepository.save(entry);
        return savedEntry;
    }
}
