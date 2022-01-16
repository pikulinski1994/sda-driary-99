package com.sda.diary.entry;

import java.util.Collections;
import java.util.List;

public class EntryRepositoryMock implements EntryRepository{

    @Override
    public List<Entry> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Entry save(Entry entry) {
        entry.setId(1L);
        return entry;
    }
}
