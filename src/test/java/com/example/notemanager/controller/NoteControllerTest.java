package com.example.notemanager.controller;

import com.example.notemanager.exception.ExceptionMessages;
import com.example.notemanager.model.Note;
import com.example.notemanager.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        Mockito.reset(noteService);
    }

    @Test
    void listAllWhenNotesExist() throws Exception {
        Note note1 = Note.builder().id(1L).title("title 1").content("content 1").build();
        Note note2 = Note.builder().id(2L).title("title 2").content("content 2").build();
        Note note3 = Note.builder().id(3L).title("title 3").content("content 3").build();

        when(noteService.listAll()).thenReturn(List.of(note1, note2, note3));

        mockMvc.perform(get("/note/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("note/list"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attribute("notes", List.of(note1, note2, note3)));
    }

    @Test
    void editValidIdReturnsEditView() throws Exception {
        Note note = Note.builder().id(1L).title("initial title").content("initial content").build();
        when(noteService.getById(1L)).thenReturn(note);

        mockMvc.perform(get("/note/edit")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("note/edit"))
                .andExpect(model().attributeExists("note"))
                .andExpect(model().attribute("note", note));

        verify(noteService, times(1)).getById(1L);
    }

    @Test
    void editInvalidIdReturnsErrorPage() throws Exception {
        mockMvc.perform(get("/note/edit")
        .param("id", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("note/error"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", ExceptionMessages.INVALID_NOTE_ID.getMessage()));

        verify(noteService, never()).getById(anyLong());
    }

    @Test
    void editByIdUpdatesNoteAndRedirectsToList() throws Exception {
        Note updatedNote = Note.builder().id(1L).title("Updated Title").content("Updated Content").build();

        when(noteService.update(any(Note.class))).thenReturn(updatedNote);

        mockMvc.perform(post("/note/edit")
                        .flashAttr("note", updatedNote))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/note/list"));

        verify(noteService, times(1)).update(any(Note.class));
    }

    @Test
    void deleteValidIdRedirectsToList() throws Exception {
        doNothing().when(noteService).delete(1L);

        mockMvc.perform(post("/note/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/note/list"));

        verify(noteService, times(1)).delete(1L);
    }

    @Test
    void deleteInvalidIdReturnsErrorPage() throws Exception {
        mockMvc.perform(post("/note/delete")
                        .param("id", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("note/error"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", ExceptionMessages.INVALID_NOTE_ID.getMessage()))
                .andReturn();

        verify(noteService, never()).delete(anyLong());
    }
}