package smoke;

import com.carespeak.domain.entities.common.Day;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutoResponderTest extends SmokeBaseTest {

    @BeforeClass
    public void prepareClientData() {
        client = prepareClient();
        site.loginSteps().openSite().loginAs(user, password);
    }

    @Test(description = "Add Auto Responders and check auto response")
    public void addAutoResponder() {
        site.clientSteps()
                .addAutoResponder(client, "It's working day!", true, Day.getWorkingDays())
                .addAutoResponder(client, "It's WEEKEND, yay!!!", true, Day.getWeekEnds());
        //TODO: implement assertion
    }

    @AfterClass
    public void removeClient() {
        removeClient(client);
    }
}
