package com.example.notemanager.repository;

import com.example.notemanager.model.Note;

import java.util.List;
import java.util.Optional;

public interface INoteRepository {
    List<Note> findAll();
    Optional<Note> findById(Long id);
    void save(Note note);
    void deleteById(Long id);
    boolean existsById(Long id);
}
