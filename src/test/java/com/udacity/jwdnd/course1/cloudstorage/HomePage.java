package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    //logout
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    //Notes
    @FindBy(id = "nav-notes-tab")
    private WebElement navNote;

    @FindBy(id = "add-note")
    private WebElement addNote;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note")
    private WebElement saveNote;

    @FindBy(id = "edit-note")
    private WebElement editNote;

    @FindBy(id = "delete-note")
    private WebElement deleteNote;

    @FindBy(id = "note-list")
    private List<WebElement> noteList;

    //Credentials
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentials;

    @FindBy(id = "add-credential")
    private WebElement addCredential;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "save-credential")
    private WebElement credentialSubmit;

    @FindBy(id = "edit-cred")
    private WebElement editCredential;

    @FindBy(id = "delete-credential")
    private WebElement deleteCredential;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 1);
        js = (JavascriptExecutor) driver;
    }

    //logout
    public void logout() {
        js.executeScript("arguments[0].click();", logoutButton);
    }

    //Notes
    public void navigateToNote() {
        js.executeScript("arguments[0].click();", navNote);
    }
    public void createNote(String title, String description) {
        this.navigateToNote();
        js.executeScript("arguments[0].click();", addNote);
        js.executeScript("arguments[0].value='" + title + "';", noteTitle);
        js.executeScript("arguments[0].value='" + description + "';", noteDescription);
        js.executeScript("arguments[0].click();", saveNote);
    }

    public void editNote(String newTitle, String newDescription) {
        this.navigateToNote();
        js.executeScript("arguments[0].value='" + newTitle + "';", noteTitle);
        js.executeScript("arguments[0].value='" + newDescription + "';", noteDescription);
        js.executeScript("arguments[0].click();", saveNote);
    }

    public void deleteNote() {
        WebElement noteTable = wait.until(webDriver -> webDriver.findElement(By.id("note-table")));
        js.executeScript("arguments[0].click();", deleteNote);
    }

    //Credentials
    public void navigateToCredentials() {
        js.executeScript("arguments[0].click();", navCredentials);
    }

    public void createCredentials(String url, String userName, String password) {
        this.navigateToCredentials();
        js.executeScript("arguments[0].click();", addCredential);
        js.executeScript("arguments[0].value='" + userName + "';", credentialUsername);
        js.executeScript("arguments[0].value='" + url + "';", credentialUrl);
        js.executeScript("arguments[0].value='" + password + "';", credentialPassword);
        js.executeScript("arguments[0].click();", credentialSubmit);
    }

    public void editCredential(String url, String userName, String password) {
        js.executeScript("arguments[0].value='" + userName + "';", credentialUsername);
        js.executeScript("arguments[0].value='" + url + "';", credentialUrl);
        js.executeScript("arguments[0].value='" + password + "';", credentialPassword);
        js.executeScript("arguments[0].click();", credentialSubmit);
    }

    public void deleteCredential() {
        WebElement deleteCredential = wait.until(webDriver -> webDriver.findElement(By.xpath("//a[@id='nav-credentials-tab']")));
        js.executeScript("arguments[0].click();", deleteCredential);
    }
}
