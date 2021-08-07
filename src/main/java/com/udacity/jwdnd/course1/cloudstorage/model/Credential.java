package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    Integer credentialId;
    String url;
    String userName;
    String key;
    String password;
    Integer userId;

    public Credential() {

    }

    public Credential(String url, String userName, String password, String key, Integer userId) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.key = key;
        this.userId = userId;
    }

    public Credential(Integer credentialId, String url, String userName, String password, String key) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.key = key;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
