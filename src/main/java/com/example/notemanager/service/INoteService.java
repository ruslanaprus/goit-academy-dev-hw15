package com.example.notemanager.service;

import com.example.notemanager.model.Note;

import java.util.List;

public interface INoteService {
    List<Note> listAll();
    Note getById(long id);
    Note create(Note note);
    Note update(Note note);
    void delete(long id);
}
