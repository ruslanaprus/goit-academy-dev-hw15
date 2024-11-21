package com.example.notemanager.config;

import com.example.notemanager.model.Note;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AppConfig {
    @Bean
    public Map<Long, Note> notesMap() {
        return new ConcurrentHashMap<>();
    }
}
