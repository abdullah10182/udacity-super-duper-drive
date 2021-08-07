package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	private String fName ="John";
	private String lName ="Doe";
	private String uName ="john1";
	private String password ="password";
	private String noteTitle = "TestNote3";
	private String noteDescription = "This is a test note :)";
	private String url = "google.com";


	@LocalServerPort
	private int port;
	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() throws InterruptedException {
		if (this.driver != null) {
			Thread.sleep(5000);
			driver.quit();
		}
	}

	@Test
	public void homePageNotAccessibleWithoutAuth() {
		driver.get("http://localhost:" + this.port + "/home");
		String URL = driver.getCurrentUrl();
		Assertions.assertEquals(URL, "http://localhost:" + this.port + "/login" );
	}


	@Test
	public void onboardingFlow() throws InterruptedException {
		this.signUp();
		Thread.sleep(1000);
		this.login();
		Thread.sleep(1000);
		this.logout();
		this.homePageNotAccessibleWithoutAuth();
	}

	public void signUp() {
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.setFirstName(this.fName);
		signupPage.setLastName(this.lName);
		signupPage.setUserName(this.uName);
		signupPage.setPassword(this.password);
		signupPage.signUp();
	}

	@Test
	public void login() {
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName(this.uName);
		loginPage.setPassword(this.password);
		loginPage.login();
	}

	public void logout() {
		HomePage homePage = new HomePage(driver);
		homePage.logout();
	}

	//Notes
	@Test
	public void createNote() {
		this.login();
		HomePage homePage = new HomePage(driver);
		homePage.createNote(this.noteTitle, this.noteDescription);
		List<WebElement> elements = driver.findElements(By.cssSelector(".note-list-item"));
		WebElement lastNoteAdded = elements.get(elements.size()-1);
		WebElement noteTitle = lastNoteAdded.findElement(By.cssSelector("td.note-title-row"));
		WebElement noteDescription = lastNoteAdded.findElement(By.cssSelector("td.note-description-row"));
		Assertions.assertEquals(noteTitle.getAttribute("innerHTML"),this.noteTitle);
		Assertions.assertEquals(noteDescription.getAttribute("innerHTML"),this.noteDescription);
	}

	@Test
	public void editNote() throws InterruptedException {
		String newTitle = "Title Edit1";
		String newDescription = "Description Edit";
		this.login();
		HomePage homePage = new HomePage(driver);
		homePage.navigateToNote();
		List<WebElement> elements = driver.findElements(By.cssSelector(".note-list-item"));
		WebElement noteToEdit = elements.get(0);
		Thread.sleep(400);
		noteToEdit.findElement(By.cssSelector(".edit-note")).click();
		homePage.editNote(newTitle, newDescription);

		List<WebElement> elementsEdited= driver.findElements(By.cssSelector(".note-list-item"));
		WebElement noteEdited = elementsEdited.get(0);
		WebElement noteTitle = noteEdited.findElement(By.cssSelector("td.note-title-row"));
		WebElement noteDescription = noteEdited.findElement(By.cssSelector("td.note-description-row"));
		Assertions.assertEquals(noteTitle.getAttribute("innerHTML"), newTitle);
		Assertions.assertEquals(noteDescription.getAttribute("innerHTML"), newDescription);
	}

	@Test public void deleteNote() throws InterruptedException {
		this.login();
		HomePage homePage = new HomePage(driver);
		homePage.navigateToNote();
		List<WebElement> elements = driver.findElements(By.cssSelector(".note-list-item"));
		WebElement noteToEdit = elements.get(0);
		Thread.sleep(400);
		noteToEdit.findElement(By.cssSelector(".delete-note")).click();
		List<WebElement> elementsDeleted = driver.findElements(By.cssSelector(".note-list-item"));
		Assertions.assertEquals(elementsDeleted.size(), 0);
	}

	//Credentials
	@Test
	public void createCredential() {
		this.login();
		HomePage homePage = new HomePage(driver);
		homePage.createCredentials(this.url, this.uName, this.password);
		List<WebElement> elements = driver.findElements(By.cssSelector(".credential-list-items"));
		WebElement lastCredAdded = elements.get(elements.size()-1);
		WebElement urlElement = lastCredAdded.findElement(By.cssSelector("td.credential-url-row"));
		WebElement usernameElement = lastCredAdded.findElement(By.cssSelector("td.credential-username-row"));
		Assertions.assertEquals(urlElement.getAttribute("innerHTML"),this.url);
		Assertions.assertEquals(usernameElement.getAttribute("innerHTML"),this.uName);
	}

	@Test
	public void editCredential() throws InterruptedException {
		String newUrl = "Title Edit1";
		String newUsername = "Description Edit";
		String newPass = "12345";
		this.login();
		HomePage homePage = new HomePage(driver);
		homePage.navigateToCredentials();
		List<WebElement> elements = driver.findElements(By.cssSelector(".credential-list-items"));
		WebElement credToEdit = elements.get(0);
		Thread.sleep(400);
		credToEdit.findElement(By.cssSelector(".edit-credential")).click();
		homePage.editCredential(newUrl, newUsername, newPass);

		List<WebElement> elementsEdited= driver.findElements(By.cssSelector(".credential-list-items"));
		WebElement credEdited = elementsEdited.get(0);
		WebElement credUrl = credEdited.findElement(By.cssSelector("td.credential-url-row"));
		WebElement credUsername = credEdited.findElement(By.cssSelector("td.credential-username-row"));
		Assertions.assertEquals(credUrl.getAttribute("innerHTML"), newUrl);
		Assertions.assertEquals(credUsername.getAttribute("innerHTML"), newUsername);
	}

	@Test public void deleteCredential() throws InterruptedException {
		this.login();
		HomePage homePage = new HomePage(driver);
		homePage.navigateToCredentials();
		List<WebElement> elements = driver.findElements(By.cssSelector(".credential-list-items"));
		WebElement credToDelete = elements.get(0);
		Thread.sleep(400);
		credToDelete.findElement(By.cssSelector(".delete-credential")).click();
		List<WebElement> elementsDeleted = driver.findElements(By.cssSelector(".credential-list-items"));
		Assertions.assertEquals(elementsDeleted.size(), 0);
	}


}
