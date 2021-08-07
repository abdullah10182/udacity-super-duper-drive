package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentials(Integer userId) {
        return this.credentialMapper.getCredentials(userId);
    }

    public Integer createCredential(Credential credential, Integer userId) {
        String key = this.generateKey();
        Credential credentialToPost = new Credential(
                credential.getUrl(),
                credential.getUserName(),
                this.encryptPassword(credential.getPassword(), key),
                key,
                userId
        );

        return this.credentialMapper.createCredential(credentialToPost);
    }

    public void editCredential(Credential credential) {
        String key = this.generateKey();
        Credential credentialToPost = new Credential(
                credential.getCredentialId(),
                credential.getUrl(),
                credential.getUserName(),
                this.encryptPassword(credential.getPassword(), key),
                key
        );
        System.out.println("Test");
        this.credentialMapper.editCredential(credentialToPost);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }

    public String encryptPassword(String password, String encodedKey) {
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        //System.out.println(this.decryptPassword(encryptedPassword, encodedKey));
        System.out.println(encodedKey);
        System.out.println(encryptedPassword);
        return encryptedPassword;
    }

    public String decryptPassword(String password, String encodedKey) {
        String decryptedPassword = encryptionService.decryptValue(password, encodedKey);
        return decryptedPassword;
    }
}
