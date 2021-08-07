package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    Integer noteId;
    String noteTitle;
    String noteDescription;
    Integer userId;

    public Note() {
    }

    public Note(String noteTitle, String noteDescription, Integer userid) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userid;
    }

    public Note(Integer noteId, String noteTitle, String noteDescription, Integer userid) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userid;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}