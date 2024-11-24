package com.example.notemanager.service;

import com.example.notemanager.model.Note;
import com.example.notemanager.repository.INoteRepository;
import com.example.notemanager.util.IdGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NoteService implements INoteService {
    private final INoteRepository noteRepository;
    private final IdGeneratorService idGeneratorService;

    @GetMapping
    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note getById(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Note not found"));
    }

    @Override
    public Note create(Note note) {
        long id = idGeneratorService.generateId();
        Note newNote = Note.builder()
                .id(id)
                .title(note.getTitle())
                .content(note.getContent())
                .build();
        noteRepository.save(newNote);
        return newNote;
    }

    @Override
    public Note update(Note note) {
        if(!noteRepository.existsById(note.getId())) {
            throw new NoSuchElementException("Note does not exist");
        }
        noteRepository.save(note);
        return note;
    }

    @Override
    public void delete(long id) {
        if(!noteRepository.existsById(id)) {
            throw new NoSuchElementException("Note does not exist");
        }
        noteRepository.deleteById(id);
    }
}
