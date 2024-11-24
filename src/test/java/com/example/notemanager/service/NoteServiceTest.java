package com.example.notemanager.service;

import com.example.notemanager.exception.ExceptionMessages;
import com.example.notemanager.exception.NoteServiceException;
import com.example.notemanager.model.Note;
import com.example.notemanager.repository.INoteRepository;
import com.example.notemanager.util.IdGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {
    private NoteService noteService;
    private INoteRepository noteRepository;
    private IdGeneratorService idGeneratorService;

    @BeforeEach
    void setUp() {
        noteRepository = mock(INoteRepository.class);
        idGeneratorService = mock(IdGeneratorService.class);
        noteService = new NoteService(noteRepository, idGeneratorService);
    }

    @Test
    void listAllWhenEmptyAtStart() {
        List<Note> notes = noteService.listAll();
        assertNotNull(notes);
        assertTrue(notes.isEmpty(), "Expected no notes");
    }

    @Test
    void listAllWhenNotesExist() {
        Note note1 = Note.builder().id(1L).title("title 1").content("content 1").build();
        Note note2 = Note.builder().id(2L).title("title 2").content("content 2").build();
        Note note3 = Note.builder().id(3L).title("title 3").content("content 3").build();

        when(noteRepository.findAll()).thenReturn(List.of(note1, note2, note3));

        List<Note> result = noteService.listAll();

        assertNotNull(result);
        assertEquals(3, result.size(), "The list should contain all notes.");
        assertTrue(result.contains(note1), "The list should contain note1.");
        assertTrue(result.contains(note2), "The list should contain note2.");
        assertTrue(result.contains(note3), "The list should contain note3.");
    }

    @Test
    void createSavesNoteWithGeneratedId() {
        when(idGeneratorService.generateId()).thenReturn(1L);
        ArgumentCaptor<Note> noteCaptor = ArgumentCaptor.forClass(Note.class);

        Note inputNote = Note.builder().title("title").content("content").build();
        Note expectedNote = Note.builder().id(1L).title("title").content("content").build();

        Note result = noteService.create(inputNote);

        verify(noteRepository).save(noteCaptor.capture());
        Note capturedNote = noteCaptor.getValue();

        assertEquals(expectedNote.getId(), capturedNote.getId());
        assertEquals(expectedNote.getTitle(), capturedNote.getTitle());
        assertEquals(expectedNote.getContent(), capturedNote.getContent());
        assertEquals(expectedNote, result);
    }

    @Test
    void getByIdReturnsNoteIfExists() {
        Note note = Note.builder().id(1l).title("title").content("content").build();
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        Note result = noteService.getById(1L);

        assertNotNull(result);
        assertEquals(note, result);
    }

    @Test
    void getByIdReturnsEmptyIfNotExists() {
        when(noteRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoteServiceException.class, () -> noteService.getById(999L));
        assertEquals(ExceptionMessages.NOTE_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void updateUpdatesExistingNote() {
        Note existingNote = Note.builder().id(1L).title("old title").content("old content").build();
        Note updatedNote = Note.builder().id(1L).title("new title").content("new content").build();

        when(noteRepository.existsById(1L)).thenReturn(true);

        Note result = noteService.update(updatedNote);

        verify(noteRepository).save(updatedNote);
        assertEquals(updatedNote, result);
    }

    @Test
    void updateThrowsIfNoteDoesNotExist() {
        Note nonExistentNote = Note.builder().id(999L).title("nonexistent").content("no content").build();

        when(noteRepository.existsById(999L)).thenReturn(false);

        Exception exception = assertThrows(NoteServiceException.class, () -> noteService.update(nonExistentNote));
        assertEquals(ExceptionMessages.NOTE_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void deleteRemovesExistingNote() {
        when(noteRepository.existsById(1L)).thenReturn(true);

        noteService.delete(1L);

        verify(noteRepository).deleteById(1L);
    }

    @Test
    void deleteThrowsIfNoteDoesNotExist() {
        when(noteRepository.existsById(999L)).thenReturn(false);

        Exception exception = assertThrows(NoteServiceException.class, () -> noteService.delete(999L));
        assertEquals(ExceptionMessages.NOTE_NOT_FOUND.getMessage(), exception.getMessage());
    }
}
