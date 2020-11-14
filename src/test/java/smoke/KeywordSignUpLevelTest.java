package smoke;

import com.carespeak.core.constant.Constants;
import com.carespeak.domain.entities.message.MessageLogItem;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class KeywordSignUpLevelTest extends SmokeBaseTest {

    //SIGN_UP_KEYWORD should contain only digits and letters without underscores and white spaces for valid test cases
    private static final String SIGN_UP_KEYWORD = "signup";
    private static final String FROM_PHONE_NUMBER = "+15554622669";
    private static final String FROM_PHONE_NUMBER_2 = "+15554622555";
    private static final String TO_ENDPOINT = "twilioSmsSender5 [TWILIO +17542272273]";

    @BeforeClass
    public void prepareClientData() {
        createNewClient();
        createProgram();
    }

    @Test(description = "Add keyword for Sign Up")
    public void addKeywordForSignUp() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addKeywordForSignUp(SIGN_UP_KEYWORD);
        site.adminToolsSteps()
                .simulateSMSToClient(FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD);

        String expectedMessage = String.format(Constants.MessageTemplate.CONFIRM_SUBSCRIPTION, programName);
        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_2);

        Assert.assertEquals(signupResponse.getMessage(), expectedMessage, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate health alerts subscription confirmation (simulate patient's AGREE response)", dependsOnMethods = "addKeywordForSignUp")
    public void simulateConfirmation() {
        site.adminToolsSteps()
                .simulateSMSToClient(FROM_PHONE_NUMBER, TO_ENDPOINT, "AGREE");

        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER);

        Assert.assertEquals(signupResponse.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Successful sign up with questions", dependsOnMethods = "simulateConfirmation")
    public void addSignupQuestions() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addAccountCreationQuestion(true,
                        "Name and optional surname",
                        "Enter your name, please",
                        "Wrong name entered, try again")
                .addAccountCreationQuestion(false,
                        "E-Mail",
                        "Enter your email, or type SKIP to skip this question",
                        "Wrong email entered, try again");

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_2, TO_ENDPOINT, SIGN_UP_KEYWORD);
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_2);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_2, TO_ENDPOINT, "AGREE");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_2);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_2, TO_ENDPOINT, "AutoFred");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_2);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_2, TO_ENDPOINT, "SKIP");
        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_2);

        Assert.assertEquals(signupResponse.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @AfterClass
    public void cleanUpClientData() {
        removeClient();
    }
}
