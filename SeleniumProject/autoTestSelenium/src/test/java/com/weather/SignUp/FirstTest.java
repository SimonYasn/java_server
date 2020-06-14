package com.weather.SignUp;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;


public class FirstTest extends WebDriverSettings {


    @Test
    public void positiveCase() {

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.open();
        homePage.getStarted();

        SignUpPage signUpPage = PageFactory.initElements(driver, SignUpPage.class);
        signUpPage.openSignUpForm();

        signUpPage.setEmail("adasdad@mail.ru");
        signUpPage.setPassword("fortest123");
        signUpPage.setConfirmPassword("fortest123");
        signUpPage.setName("Simon");

        signUpPage.submitForm();
    }

    @Test
    public void negativeCase() {

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.open();
        homePage.getStarted();

        SignUpPage signUpPage = PageFactory.initElements(driver, SignUpPage.class);
        signUpPage.openSignUpForm();


        signUpPage.submitForm();

        String email = signUpPage.getEmailError();
        Assert.assertEquals(email,"Это поле необходимо заполнить.");

        String password = signUpPage.getPasswordError();
        Assert.assertEquals(password,"Это поле необходимо заполнить.");

        String confirmPassword = signUpPage.getConfirmPasswordError();
        Assert.assertEquals(confirmPassword,"Это поле необходимо заполнить.");

        String name = signUpPage.getNameError();
        Assert.assertEquals(name,"Это поле необходимо заполнить.");

    }



}
