package com.example.notemanager.service;

import com.example.notemanager.exception.NoteNotFoundException;
import com.example.notemanager.model.Note;
import com.example.notemanager.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoteServiceTest {
    private NoteService noteService;
    private IdGenerator idGenerator;
    private Map<Long, Note> notes;

    @BeforeEach
    void setUp() {
        notes = new ConcurrentHashMap<>();
        idGenerator = mock(IdGenerator.class);
        noteService = new NoteService(notes, idGenerator);
    }

    @Test
    void listAllWhenEmptyAtStart() {
        List<Note> notes = noteService.listAll();
        assertNotNull(notes);
        assertTrue(notes.isEmpty(), "Expected no notes");
    }

    @Test
    void listAllWhenNotesExist() {
        Map<Long, Note> notesMock = new ConcurrentHashMap<>();
        NoteService noteServiceWithMock = new NoteService(notesMock, idGenerator);

        Note note1 = Note.builder().id(1L).title("title 1").content("content 1").build();
        Note note2 = Note.builder().id(2L).title("title 2").content("content 2").build();
        Note note3 = Note.builder().id(3L).title("title 3").content("content 3").build();

        notesMock.put(1L, note1);
        notesMock.put(2L, note2);
        notesMock.put(3L, note3);

        List<Note> result = noteServiceWithMock.listAll();

        assertNotNull(result);
        assertEquals(3, result.size(), "The list should contain all notes.");
        assertTrue(result.contains(note1), "The list should contain note1.");
        assertTrue(result.contains(note2), "The list should contain note2.");
        assertTrue(result.contains(note3), "The list should contain note3.");
    }

    @Test
    void createAddsNoteWithGeneratedId() {
        when(idGenerator.generateId()).thenReturn(1L);
        Note note = Note.builder().title("title").content("content").build();

        Note createdNote = noteService.create(note);

        assertNotNull(createdNote);
        assertEquals(1L, createdNote.getId());
        assertEquals("title", createdNote.getTitle());
        assertEquals("content", createdNote.getContent());

        List<Note> allNotes = noteService.listAll();

        assertEquals(1, allNotes.size());
        assertEquals(createdNote, allNotes.get(0));
    }

    @Test
    void getByIdReturnsNoteIfExists() {
        when(idGenerator.generateId()).thenReturn(1L);
        Note note = Note.builder().title("title").content("content").build();
        Note createdNote = noteService.create(note);

        Optional<Note> retrievedNote = noteService.getById(1L);

        assertTrue(retrievedNote.isPresent());
        assertEquals(createdNote, retrievedNote.get());
    }

    @Test
    void getByIdReturnsEmptyIfNotExists() {
        Optional<Note> retrievedNote = noteService.getById(999L);

        assertFalse(retrievedNote.isPresent());
    }

    @Test
    void updateUpdatesExistingNote() {
        when(idGenerator.generateId()).thenReturn(1L);
        Note note = Note.builder().title("initial title").content("initial content").build();
        Note createdNote = noteService.create(note);

        Note updatedNote = Note.builder()
                .id(createdNote.getId())
                .title("updated title")
                .content("updated content")
                .build();

        Note result = noteService.update(updatedNote);

        assertEquals("updated title", result.getTitle());
        assertEquals("updated content", result.getContent());

        Optional<Note> retrievedNote = noteService.getById(1L);
        assertTrue(retrievedNote.isPresent());
        assertEquals("updated title", retrievedNote.get().getTitle());
    }

    @Test
    void updateThrowsIfNoteDoesNotExist() {
        Note nonExistentNote = Note.builder().id(999L).title("nonexistent").content("no content").build();

        assertThrows(NoteNotFoundException.class, () -> {
            noteService.update(nonExistentNote);
        });
    }

    @Test
    void delete_RemovesExistingNote() {
        when(idGenerator.generateId()).thenReturn(1L);
        Note note = Note.builder().title("title").content("content").build();
        noteService.create(note);

        noteService.delete(1L);

        assertTrue(noteService.listAll().isEmpty());
        assertFalse(noteService.getById(1L).isPresent());
    }

    @Test
    void delete_ThrowsIfNoteDoesNotExist() {
        assertThrows(NoteNotFoundException.class, () -> {
            noteService.delete(999L);
        });
    }
}
