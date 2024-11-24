package com.example.notemanager.repository;

import com.example.notemanager.model.Note;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConcurrentHashMapNoteRepository implements INoteRepository {
    private final Map<Long, Note> notes = new ConcurrentHashMap<>();

    @Override
    public List<Note> findAll() {
        return new ArrayList<>(notes.values());
    }

    @Override
    public Optional<Note> findById(Long id) {
        return Optional.ofNullable(notes.get(id));
    }

    @Override
    public void save(Note note) {
        notes.put(note.getId(), note);
    }

    @Override
    public void deleteById(Long id) {
        notes.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return notes.containsKey(id);
    }
}
