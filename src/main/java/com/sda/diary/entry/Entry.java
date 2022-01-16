package com.sda.diary.entry;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Instant creationDate;
}
