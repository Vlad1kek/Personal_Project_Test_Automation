package test;

import io.qameta.allure.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import page.login.LoginPage;
import page.login.RegisterAccountPage;
import utils.run.BaseTest;

@Epic("Logging Out")
public class LoggingOutTest extends BaseTest {

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_03.001 Basic Logout Functionality")
    @Description("TC_03.001.01 Validate Successful Logout")
    @Test
    public void testSuccessfulLogout() {
        String loginPage = new HomePage(getDriver())
                .clickLogout()
                .getLoginBoxMsg();

        Allure.step("The user is redirected to the login page, and the session is terminated");
        Assert.assertEquals(loginPage, "Sign in to start your session",
                "If FAIL: User is NOT logout");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_03.001 Basic Logout Functionality")
    @Description("TC_03.001.02 Validate Redirect to Login for Authenticated Pages After Logout")
    @Test
    public void testRedirectLoginForAuthenticatedPagesAfterLogout() {
        String loginPage = new HomePage(getDriver())
                .clickLogout()
                .followTheURL("profile")
                .getLoginBoxMsg();

        Allure.step("The user is redirected to the login page.");
        Assert.assertEquals(loginPage, "Sign in to start your session",
                "If FAIL: User is NOT logout");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_03.002 Security of Logout Process")
    @Description("TC_03.002.01 Validate Clearing Session Cookie on Logout")
    @Test
    public void testClearingSessionCookieOnLogout() {
        String cookie = "laravel_token";

        boolean loginPage = new HomePage(getDriver())
                .clickLogout()
                .doesCookieExist(cookie);

        Allure.step(String.format("That all cookies related to the session (%S) have been cleared", cookie));
        Assert.assertTrue(loginPage,
               "If FAIL: Cookies are not cleared after logging out.");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_03.002 Security of Logout Process")
    @Description("TC_03.002.02 Validate Clearing Local Storage on Logout")
    @Test
    public void testClearingLocalStorageOnLogout() {
        String token = "auth_token";
        String session = "user_session";

        String authToken = new HomePage(getDriver())
                .clickLogout()
                .getLocalStorage(token);

        String userSession = new RegisterAccountPage(getDriver())
                .getLocalStorage(session);

        Allure.step("All keys and tokens associated with the session are deleted, such as:" + token);
        Assert.assertNull(authToken,
                "If FAIL: Authentication token NOT cleared after logout");

        Allure.step("All keys and tokens associated with the session are deleted, such as:" + userSession);
        Assert.assertNull(userSession,
                "If FAIL: User session NOT cleared after logout");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_03.002 Security of Logout Process")
    @Description("TC_03.002.03 Validate Clearing Session Storage on Logout")
    @Test
    public void testClearingSessionStorageOnLogout() {
        String token = "auth_token";
        String session = "user_session";

        String authToken = new HomePage(getDriver())
                .clickLogout()
                .getSessionStorage(token);

        String userSession = new RegisterAccountPage(getDriver())
                .getSessionStorage(session);

        Allure.step("All keys and tokens associated with the session are deleted, such as:" + token);
        Assert.assertNull(authToken,
                "If FAIL: Authentication token NOT cleared after logout");

        Allure.step("All keys and tokens associated with the session are deleted, such as:" + userSession);
        Assert.assertNull(userSession,
                "If FAIL: User session NOT cleared after logout");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_03.004 Logout and Browser Back Button")
    @Description("TC_03.004.01 Validate Logout and Browser Back Button")
    @Test
    public void testLogoutAndBrowserBackButton() {
        String loginPage = new HomePage(getDriver())
                .clickLogout()
                .clickBackBrowserButton(new HomePage(getDriver()))
                .refreshPage(new LoginPage(getDriver()))
                .getLoginBoxMsg();

        Allure.step("The user is redirected to the login page");
        Assert.assertEquals(loginPage, "Sign in to start your session",
                "If FAIL: User is NOT logout");
    }
}
