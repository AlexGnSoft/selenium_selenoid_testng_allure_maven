package smoke;

import com.carespeak.core.constant.Constants;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.Patient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class KeywordSignUpLevelTest extends SmokeBaseTest {

    //SIGN_UP_KEYWORD should contain only digits and letters without underscores and white spaces for valid test cases
    private static final String SIGN_UP_KEYWORD = "signup";
    private static final String FROM_PHONE_NUMBER = "+15554622669";
    private static final String FROM_PHONE_NUMBER_2 = "+15554622555";
    private static final String FROM_PHONE_NUMBER_3 = "+15554622556";
    private static final String FROM_PHONE_NUMBER_4 = "+15554622557";
    private static final String FROM_PHONE_NUMBER_5 = "+15554622558";
    private static final String FROM_PHONE_NUMBER_6 = "+15554622559";
    private static final String TO_ENDPOINT = "twilioSmsSender5 [TWILIO +17542272273]";
    private static final String COMPLETED_MESSAGE = "You've successfully completed the registration.";

    private Patient patient;
    private String newProgramName;

    @BeforeClass
    public void prepareClientData() {
        createNewClient();
        createProgram();
        newProgramName = createProgram(client);
        patient = new Patient();
        patient.setFirstName("AutoFred");
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
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER);

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

    @Test(description = "Successful sign up with custom fields", dependsOnMethods = "addSignupQuestions")
    public void addCustomField() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addCustomFields("Rx Therapy Start Date")
                .addAccountCreationQuestion(false,
                        "Event: Rx Therapy Start Date",
                        "Enter your Therapy Start Date, please",
                        "Wrong Start Date, try again");

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_3, TO_ENDPOINT, SIGN_UP_KEYWORD);
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_3);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_3, TO_ENDPOINT, "AGREE");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_3);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_3, TO_ENDPOINT, "AutoFred");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_3);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_3, TO_ENDPOINT, "SKIP");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_3);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_3, TO_ENDPOINT, "12/19/20");
        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_3);

        Assert.assertEquals(signupResponse.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Check if validation message is added using dynamic variables", dependsOnMethods = "addCustomField")
    public void addValidationMessage() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addValidationMessage("Please validate the data you've shared with us by texting back Yes or No\n" +
                        "\n" +
                        "Name: ${p} \n" +
                        "Therapy Start Date: ${event:Rx Therapy Start Date}");

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, SIGN_UP_KEYWORD);
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, "AGREE");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, "AutoFred");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, "SKIP");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, "12/19/20");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, "No");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, "AutoFred");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, "SKIP");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, "12/19/20");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_4, TO_ENDPOINT, "Yes");
        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_4);

        Assert.assertEquals(signupResponse.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Check if completed message is added", dependsOnMethods = "addValidationMessage")
    public void addCompletedMessage() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addCompletedMessage(COMPLETED_MESSAGE);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_5, TO_ENDPOINT, SIGN_UP_KEYWORD);
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_5);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_5, TO_ENDPOINT, "AGREE");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_5);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_5, TO_ENDPOINT, "AutoFred");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_5);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_5, TO_ENDPOINT, "SKIP");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_5);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_5, TO_ENDPOINT, "12/19/20");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_5);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_5, TO_ENDPOINT, "Yes");
        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_5);

        Assert.assertEquals(signupResponse.getMessage(), COMPLETED_MESSAGE, "Received message is not the same as expected!");
    }

    @Test(description = "Move patient to a specific program based on the keyword answer", dependsOnMethods = "addCompletedMessage")
    public void movePatientToSpecificProgram() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addDestinationProgramQuestionKeywords("MOVE", newProgramName)
                .addAccountCreationQuestion(false,
                        "Destination Program",
                        "Enter MOVE if you'd like to be moved to another program. Or type SKIP if you wish to stay in this one.",
                        "Wrong response, try again");

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_6, TO_ENDPOINT, SIGN_UP_KEYWORD);
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_6);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_6, TO_ENDPOINT, "AGREE");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_6);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_6, TO_ENDPOINT, "AutoFred");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_6);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_6, TO_ENDPOINT, "SKIP");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_6);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_6, TO_ENDPOINT, "12/19/20");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_6);

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_6, TO_ENDPOINT, "MOVE");

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER_6, TO_ENDPOINT, "Yes");
        site.programSteps().goToProgramSettings(client.getName(), programName).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER_6);

        boolean isPatientMoved = site.programSteps()
                .goToProgramSettings(client.getName(), newProgramName)
                .isInProgram(newProgramName, patient);

        Assert.assertTrue(isPatientMoved, "The patient was not moved!");
    }

    @AfterClass
    public void cleanUpClientData() {
        removeClient();
    }
}
