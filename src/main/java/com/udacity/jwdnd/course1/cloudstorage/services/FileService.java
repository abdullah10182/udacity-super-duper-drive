package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles(Integer userId) {
        return this.fileMapper.getFiles(userId);
    }

    public Integer uploadFile(MultipartFile uploadedFile, Integer userId) throws IOException {
        File file = new File(
            uploadedFile.getOriginalFilename(),
            uploadedFile.getContentType(),
            uploadedFile.getSize(),
            userId,
            uploadedFile.getBytes()
        );
        return this.fileMapper.uploadFile(file);
    }

    public Boolean checkIfFileNameTaken(String fileName, Integer userId) {
        List<File> files = fileMapper.getFiles(userId);
        Boolean fileNameTaken = false;
        for (File file : files) {
            //System.out.println(file.getFileName());
            if(fileName.equals(file.getFileName())) return true;
        }
        return false;
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }
}
