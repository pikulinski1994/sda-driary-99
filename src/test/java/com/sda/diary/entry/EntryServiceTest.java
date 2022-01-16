package com.sda.diary.entry;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class EntryServiceTest {

    EntryService entryService;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EntryRepositoryMock entryRepository = new EntryRepositoryMock();
        entryService = new EntryService(entryRepository, objectMapper);
    }

    @Test
    void createEntry_whenAllArgumentsAreProper_createsNewEntry() {
        // when
        Entry entry = entryService.createEntry("title-1", "content-1");
        // then
        assertThat(entry.getTitle()).isEqualTo("title-1");
        assertThat(entry.getContent()).isEqualTo("content-1");
        assertThat(entry.getId()).isNotNull();
        assertThat(entry.getCreationDate()).isNotNull();
    }

    @Test
    void createEntry_whenTitleIsEmpty_throwsAnException() {
        // when
        Throwable throwable = catchThrowable(() -> entryService.createEntry("  ", "content-1"));
        // then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
