package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<Note> getNotes(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle , notedescription, userid ) VALUES ( #{noteTitle}, #{noteDescription}, #{userId} ) ")
    @Options(useGeneratedKeys = true, keyColumn = "noteid", keyProperty = "noteId")
    Integer createNote(Note note);

    @Insert("UPDATE NOTES SET notetitle= #{noteTitle} , notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    void editNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId}")
    void deleteNote(Integer noteId);
}
