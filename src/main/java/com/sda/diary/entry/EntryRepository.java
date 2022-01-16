package com.sda.diary.entry;

import java.util.List;

public interface EntryRepository {

    List<Entry> findAll();

    Entry save(Entry entry);
}
