package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NotesController {

    private NoteService noteService;
    private UserService userService;

    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/home/create-note")
    private String createNote( @ModelAttribute("newNote") Note note, Authentication auth, RedirectAttributes redirectAttrs) {
        User currentUser = this.userService.getUser(auth.getName());

        if(note.getNoteId() == null) {
            try {
                this.noteService.createNote(note, currentUser.getUserid());
                redirectAttrs.addFlashAttribute("alertText", "Note added successfully");
                redirectAttrs.addFlashAttribute("alertColor", "alert-success");
            } catch (Exception err) {
                System.out.println(err.toString());
            }
        } else {
            try {
                this.noteService.editNote(note, currentUser.getUserid());
                redirectAttrs.addFlashAttribute("alertText", "Note edited successfully");
                redirectAttrs.addFlashAttribute("alertColor", "alert-success");
            } catch (Exception err) {
                System.out.println(err.toString());
            }
        }

        return "redirect:/home";
    }

    @RequestMapping("/home/delete-note/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, RedirectAttributes redirectAttrs) {
        this.noteService.deleteNote(noteId);
        redirectAttrs.addFlashAttribute("alertText", "Note deleted successfully");
        redirectAttrs.addFlashAttribute("alertColor", "alert-success");
        return "redirect:/home";
    }


}
