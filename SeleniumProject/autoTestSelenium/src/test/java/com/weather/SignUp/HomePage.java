package com.weather.SignUp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
    }

    public void open() {
        driver.get("https://www.lamoda.ru/men-home/");
    }

    public void getStarted() {
        driver.findElement(By.cssSelector("[class=\"link user-nav__link js-auth-button\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"link_blue login-form__register link\"]")));
    }
}
