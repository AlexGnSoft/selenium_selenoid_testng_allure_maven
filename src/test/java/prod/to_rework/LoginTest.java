package prod.to_rework;

import org.testng.Assert;
import org.testng.annotations.Test;
import prod.base.BaseTest;

/**
 * Just basic simple login test for POC
 */
public class LoginTest extends BaseTest {

    @Test(description = "User should be able to log in")
    public void userLogInTest() {
        boolean isLoggedIn = site.loginSteps()
                .openSite()
                .loginAs(user, password)
                .isUserLoggedIn();
        Assert.assertTrue(isLoggedIn, "User not logged in!");
    }
}
