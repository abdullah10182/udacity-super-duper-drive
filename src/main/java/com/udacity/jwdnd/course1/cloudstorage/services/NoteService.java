package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes(Integer userId) {
        return this.noteMapper.getNotes(userId);
    }

    public Integer createNote(Note note, Integer userId) {
        Note noteToPost = new Note(
            note.getNoteTitle(),
            note.getNoteDescription(),
            userId
        );
        return this.noteMapper.createNote(noteToPost);
    }

    public void editNote(Note note, Integer userId) {
        Note noteToPost = new Note(
                note.getNoteId(),
                note.getNoteTitle(),
                note.getNoteDescription(),
                userId
        );
        this.noteMapper.editNote(noteToPost);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

}
