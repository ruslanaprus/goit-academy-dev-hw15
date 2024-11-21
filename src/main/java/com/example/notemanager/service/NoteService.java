package com.example.notemanager.service;

import com.example.notemanager.exception.NoteNotFoundException;
import com.example.notemanager.model.Note;
import com.example.notemanager.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final Map<Long, Note> notes;
    private final IdGenerator idGenerator;

    public List<Note> listAll() {
        return new ArrayList<>(notes.values());
    }

    public Optional<Note> getById(long id) {
        return Optional.ofNullable(notes.get(id));
    }

    public Note create(Note note) {
        long id = idGenerator.generateId();
        Note newNote = Note.builder()
                .id(id)
                .title(note.getTitle())
                .content(note.getContent())
                .build();
        notes.put(id, newNote);
        return newNote;
    }

    public Note update(Note note) {
        return Optional.ofNullable(notes.get(note.getId()))
                .map(existingNote -> {
                    Note updatedNote = Note.builder()
                            .id(note.getId())
                            .title(note.getTitle())
                            .content(note.getContent())
                            .build();
                    notes.put(note.getId(), updatedNote);
                    return updatedNote;
                })
                .orElseThrow(() -> new NoteNotFoundException("Note with id " + note.getId() + " not found"));
    }

    public void delete(long id) {
        if (notes.remove(id) == null) {
            throw new NoteNotFoundException("Note with id " + id + " not found");
        }
    }
}
