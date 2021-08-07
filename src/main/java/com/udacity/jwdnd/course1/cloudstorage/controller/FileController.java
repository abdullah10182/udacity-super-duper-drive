package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/home/upload-file")
    private String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication auth, Model model, RedirectAttributes redirectAttrs)  {
        User currentUser = this.userService.getUser(auth.getName());

        Boolean fileNameTaken = fileService.checkIfFileNameTaken(fileUpload.getOriginalFilename(), currentUser.getUserid());

        if(fileNameTaken) {
            redirectAttrs.addFlashAttribute("alertColor", "alert-danger");
            redirectAttrs.addFlashAttribute("alertText","File name " + fileUpload.getOriginalFilename() + " already taken.");
            return "redirect:/home";
        } else {
            try {
                this.fileService.uploadFile(fileUpload, currentUser.getUserid());
                redirectAttrs.addFlashAttribute("alertText", fileUpload.getOriginalFilename() + " uploaded successfully");
                redirectAttrs.addFlashAttribute("alertColor", "alert-success");
            } catch (Exception err) {
                redirectAttrs.addFlashAttribute("alertText", "Failed to upload " + fileUpload.getOriginalFilename());
                redirectAttrs.addFlashAttribute("alertColor", "alert-danger");
            }
        }

        model.addAttribute("files", fileService.getFiles(currentUser.getUserid()));
        return "redirect:/home";
    }

    @RequestMapping("/home/file-delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model, Authentication auth) {
        User currentUser = this.userService.getUser(auth.getName());
        File file = fileService.getFile(fileId);
        if(file == null) {
            return "redirect:/home";
        }
        fileService.deleteFile(fileId);
        model.addAttribute("files", fileService.getFiles(currentUser.getUserid()));
        model.addAttribute("alertText", "File " + file.getFileName() +" deleted");
        model.addAttribute("alertColor", "alert-danger");
        return "home";
    }

    @RequestMapping("/home/file-view/{fileId}")
    public ResponseEntity viewFile(@PathVariable("fileId") Integer fileId, Model model, Authentication auth) {
        File file = this.fileService.getFile(fileId);
        String contentType = file.getContentType();
        String fileName = file.getFileName();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(file.getFileData());
    }


}
