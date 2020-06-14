package ru.lamoda;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
    }

    By searchOpenSignForm = By.cssSelector("[class=\"link_blue login-form__register link\"]");

    By searchSubmitButton = By.xpath("//button[@class='button button_blue js-registration-button']");

    By searchForm = By.cssSelector("[class=\"popup__content-wrapper\"]");

    @FindBy(xpath = "//div[@class='register-form__inner']//input[@name='email']")
    private WebElement email;

    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/form[2]/div[2]/div[2]/div/input")
    private WebElement password;

    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/form[2]/div[2]/div[3]/div/input")
    private WebElement confirmPassword;

    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/form[2]/div[2]/div[4]/div/input")
    private WebElement name;

    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/form[2]/div[2]/div[1]/div/div[1]")
    private WebElement emailError;

    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/form[2]/div[2]/div[2]/div/div")
    private WebElement passwordError;

    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/form[2]/div[2]/div[3]/div/div")
    private WebElement confirmPasswordError;

    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/form[2]/div[2]/div[4]/div/div")
    private WebElement nameError;

    public void openSignUpForm() {
        driver.findElement(searchOpenSignForm).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchForm));

    }

    public void submitForm() {
        driver.findElement(searchSubmitButton).click();
    }

    public void waitCaptcha() {

    }

    public void setEmail(String parameter) {
        email.sendKeys(parameter);
    }

    public void setPassword(String parameter) {
        password.sendKeys(parameter);
    }

    public void setConfirmPassword(String parameter) {
        confirmPassword.sendKeys(parameter);
    }

    public void setName(String parameter) {
        name.sendKeys(parameter);
    }

    public String getEmailError() {
        return emailError.getText();
    }

    public String getPasswordError() {
        return passwordError.getText();
    }

    public String getConfirmPasswordError() {
        return confirmPasswordError.getText();
    }

    public String getNameError() {
        return nameError.getText();
    }
}
