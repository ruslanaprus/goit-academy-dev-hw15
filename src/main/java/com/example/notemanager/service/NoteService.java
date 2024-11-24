package com.example.notemanager.service;

import com.example.notemanager.exception.ExceptionMessages;
import com.example.notemanager.exception.NoteServiceException;
import com.example.notemanager.model.Note;
import com.example.notemanager.repository.INoteRepository;
import com.example.notemanager.util.IdGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
                .orElseThrow(() -> new NoteServiceException(ExceptionMessages.NOTE_NOT_FOUND.getMessage()));
    }

    @Override
    public Note create(Note note) {
        if(note.getTitle() == null || note.getTitle().isEmpty()) {
            throw new NoteServiceException(ExceptionMessages.INVALID_NOTE_DATA.getMessage());
        }

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
            throw new NoteServiceException(ExceptionMessages.NOTE_NOT_FOUND.getMessage());
        }
        noteRepository.save(note);
        return note;
    }

    @Override
    public void delete(long id) {
        if(!noteRepository.existsById(id)) {
            throw new NoteServiceException(ExceptionMessages.NOTE_NOT_FOUND.getMessage());
        }
        noteRepository.deleteById(id);
    }
}
