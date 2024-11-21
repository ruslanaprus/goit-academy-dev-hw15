package com.example.notemanager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Note {
    private Long id;
    private String title;
    private String content;
}
