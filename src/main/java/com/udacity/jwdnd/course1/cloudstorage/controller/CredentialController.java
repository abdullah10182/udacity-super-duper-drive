package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/home/create-credential")
    private String createCredential(@ModelAttribute("newCredential") Credential credential, Authentication auth, RedirectAttributes redirectAttrs) {
        User currentUser = this.userService.getUser(auth.getName());

        if(credential.getCredentialId() == null) {
            try {
                this.credentialService.createCredential(credential, currentUser.getUserid());
                redirectAttrs.addFlashAttribute("alertText", "Credential added successfully");
                redirectAttrs.addFlashAttribute("alertColor", "alert-success");
            } catch (Exception err) {
                System.out.println(err.toString());
            }
        } else {
            try {
                this.credentialService.editCredential(credential);
                redirectAttrs.addFlashAttribute("alertText", "Credential edited successfully");
                redirectAttrs.addFlashAttribute("alertColor", "alert-success");
            } catch (Exception err) {
                System.out.println(err.toString());
            }
        }

        return "redirect:/home";
    }

    @RequestMapping("/home/delete-credential/{credentialId}")
    private String deleteCredential(@PathVariable("credentialId") Integer credentialId, RedirectAttributes redirectAttrs) {
        System.out.println(credentialId);
        credentialService.deleteCredential(credentialId);
        redirectAttrs.addFlashAttribute("alertText", "Credential deleted successfully");
        redirectAttrs.addFlashAttribute("alertColor", "alert-success");
        return "redirect:/home";
    }

}
