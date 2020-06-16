package ru.lamoda;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import listeners.RetryAnalizer;
import org.junit.Assert;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;


public class Tests extends WebDriverSettings {

    @Test(priority=1)
    public void positiveCase() {

        ExtentTest test = extent.createTest("LamodaSignUpPositive");


        HomePage homePage = PageFactory.initElements(driver, HomePage.class);

        test.log(Status.INFO,"Open site");
        homePage.open();
        test.pass("Navigated to Lamoda");


        homePage.openLoginForm();
        test.pass("Navigated to login form");

        SignUpPage signUpPage = PageFactory.initElements(driver, SignUpPage.class);
        signUpPage.openSignUpForm();
        test.pass("Navigated to sign up form");

        signUpPage.setEmail("adasasdaqdadad@mail.ru");
        signUpPage.setPassword("fortest123");
        signUpPage.setConfirmPassword("fortest123");
        signUpPage.setName("Simon");

        signUpPage.submitForm();
        test.pass("Form submitted");



    }

    @Test
    public void negativeCase() {

        ExtentTest test = extent.createTest("LamodaSignUpNegative");

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);

        test.log(Status.INFO,"Open site");
        homePage.open();
        test.pass("Navigated to Lamoda");

        homePage.openLoginForm();
        test.pass("Navigated to login form");

        SignUpPage signUpPage = PageFactory.initElements(driver, SignUpPage.class);
        signUpPage.openSignUpForm();
        test.pass("Navigated to sign up form");


        signUpPage.submitForm();
        test.pass("Form submitted");

        String email = signUpPage.getEmailError();
        Assert.assertEquals(email,"Это поле необходимо заполнить.");

        String password = signUpPage.getPasswordError();
        Assert.assertEquals(password,"Это поле необходимо заполнить.");

        String confirmPassword = signUpPage.getConfirmPasswordError();
        Assert.assertEquals(confirmPassword,"Это поле необходимо заполнить.");

        String name = signUpPage.getNameError();
        Assert.assertEquals(name,"Это поле необходимо заполнить.");

        test.pass("Errors checked");


    }

    @Test(retryAnalyzer = listeners.RetryAnalizer.class)
    public void failTest() {
        int i = 1/0;
    }



}
