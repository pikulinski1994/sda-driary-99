package com.sda.diary.entry;

import lombok.Data;

@Data
public class EntryDTO {
    private Long id;
    private String title;
    private String content;
}
