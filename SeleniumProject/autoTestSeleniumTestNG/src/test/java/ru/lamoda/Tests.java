package ru.lamoda;

import org.junit.Assert;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;


public class Tests extends WebDriverSettings {




    @Test
    public void positiveCase() throws InterruptedException {

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.open();
        homePage.getStarted();

        SignUpPage signUpPage = PageFactory.initElements(driver, SignUpPage.class);
        signUpPage.openSignUpForm();

        signUpPage.setEmail("adasasdaqdadad@mail.ru");
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

        System.out.println();

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
