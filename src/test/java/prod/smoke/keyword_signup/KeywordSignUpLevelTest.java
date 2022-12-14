package prod.smoke.keyword_signup;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.core.constant.Constants;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class KeywordSignUpLevelTest extends AbstractKeyWordSignUpLevelTest {

    private final String TO_ENDPOINT = PropertyFileReader.getVariableValue("twilioSmsSender");
    private static final String COMPLETED_MESSAGE = "You've successfully completed the registration.";
    private Client client;
    private String clientName;


    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("Keyword_Signup_Client-" + getFormattedDate("dd-MM-yy-H-mm-ss"));
        clientName = client.getName();
        Patient patient = new Patient();
        patient.setFirstName("AutoName"+getRandomString());
        patient.setLastName("Automator");
        patient.setSex(Sex.MALE);
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Simulate health alerts subscription confirmation (simulate patient's AGREE response)")
    public void simulateConfirmation_MHM_T115() {
        //Test data
        String programName_T115 = "AutoGenerated program-T115 "+getRandomString();
        String SIGN_UP_KEYWORD_T115 = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        //TODO: Add program and signup keyword
        site.programSteps().addNewProgram(clientName, programName_T115, ProgramAccess.PUBLIC).addKeywordForSignUp(SIGN_UP_KEYWORD_T115);

        //TODO: Simulate 'AGREE' to Signup of a new patient
        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, programName_T115, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD_T115);

        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(clientName, programName_T115)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER);

        Assert.assertEquals(signupResponse.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Add keyword for Sign Up")
    public void addKeywordForSignUp_MHM_T38() {
        //Test data
        String programName_T38 = "AutoGenerated program-T38 "+getRandomString();
        String SIGN_UP_KEYWORD_T38 = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        //TODO: Add program and signup keyword
        site.programSteps().addNewProgram(clientName, programName_T38, ProgramAccess.PUBLIC).addKeywordForSignUp(SIGN_UP_KEYWORD_T38);

        //TODO: Simulate Signup of a new patient
        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD_T38);

        String expectedMessage = String.format(Constants.MessageTemplate.CONFIRM_SUBSCRIPTION, programName_T38);

        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(clientName, programName_T38)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER);

        Assert.assertEquals(signupResponse.getMessage(), expectedMessage, "Received message is not the same as expected!");
    }

    @Test(description = "Successful sign up with questions")
    public void addSignupQuestions_MHM_T39() {
        //Test data
        String programName_T39 = "AutoGenerated program-T39 "+getRandomString();
        String SIGN_UP_KEYWORD_T39 = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        //TODO: Add program and signup keyword
        site.programSteps().addNewProgram(clientName, programName_T39, ProgramAccess.PUBLIC).addKeywordForSignUp(SIGN_UP_KEYWORD_T39);

        //TODO: Add questions
        site.programSteps()
                .goToProgramSettings(clientName, programName_T39)
                .addAccountCreationQuestion(false,
                        "Name and optional surname",
                        "Enter your name, please",
                        "Wrong name entered, try again")
                .addAccountCreationQuestion(false,
                        "E-Mail",
                        "Enter your email, or type SKIP to skip this question",
                        "Wrong email entered, try again");

        //TODO: Simulate sms from patient to client with SignUp, Agree, Patient name and Skip
        site.adminToolsSteps().initiateKeywordSignupSendAgreeNameAndSkip(clientName, programName_T39, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD_T39);

        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(clientName, programName_T39)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER);

        Assert.assertEquals(signupResponse.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Successful sign up with custom fields")
    public void addCustomField_MHM_T40() {
        //Test data
        String programName_T40 = "AutoGenerated program-T40 "+getRandomString();
        String SIGN_UP_KEYWORD_T40 = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        //TODO: Add program and signup keyword
        site.programSteps()
                .addNewProgram(clientName, programName_T40, ProgramAccess.PUBLIC).addKeywordForSignUp(SIGN_UP_KEYWORD_T40)
                .addCustomFields("Rx Therapy Start Date")
                .addAccountCreationQuestion(false,
                        "Event: Rx Therapy Start Date",
                        "Enter your Therapy Start Date, please",
                        "Wrong Start Date, try again");

        //TODO: Simulate E2E Signup of a new patient
        site.adminToolsSteps()
                .initiateKeywordSignupSendAndAgreeTherapyStartDate(clientName, programName_T40, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD_T40);

        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(clientName, programName_T40)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER);

        Assert.assertEquals(signupResponse.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Check if validation message is added using dynamic variables")
    public void addValidationMessage_MHM_T56() {
        //Test data
        String programName_T56 = "AutoGenerated program-T56 "+getRandomString();
        String SIGN_UP_KEYWORD_T56 = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        //TODO: Add program and signup keyword
        site.programSteps()
                .addNewProgram(clientName, programName_T56, ProgramAccess.PUBLIC).addKeywordForSignUp(SIGN_UP_KEYWORD_T56)
                .addAccountCreationQuestion(false,
                        "Name and optional surname",
                        "Enter your name, please",
                        "Wrong name entered, try again")
                .addAccountCreationQuestion(false,
                        "Birth Date",
                        "Enter your Birth Date, please",
                        "Wrong birth day data format, it should be like, mm/dd/yyyy")
                .addAccountCreationQuestion(false,
                        "Sex",
                        "Enter your Sex, please",
                        "Wrong Sex, try again")
                .addValidationMessage("Please validate the data you've shared with us by texting back Yes or No\n" +
                        "\n" +
                        "Name: ${p} \n" +
                        "Therapy Start Date: ${event:Rx Therapy Start Date}");

        //TODO: Simulate E2E Signup of a new patient
        site.adminToolsSteps()
                .initiateKeywordSignupSendAndAgree(clientName, programName_T56,FROM_PHONE_NUMBER,TO_ENDPOINT, SIGN_UP_KEYWORD_T56)
                .initiateSmsFirstLastNameDateSex(clientName, programName_T56, FROM_PHONE_NUMBER,TO_ENDPOINT, "F");

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER,TO_ENDPOINT, "No");
        site.programSteps().goToProgramSettings(clientName, programName_T56).getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER);

        site.adminToolsSteps().initiateSmsFirstLastNameDateSex(clientName, programName_T56, FROM_PHONE_NUMBER,TO_ENDPOINT, "M");

        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER,TO_ENDPOINT, "Yes");

        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(clientName, programName_T56)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER);

        Assert.assertEquals(signupResponse.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Check if completed message is added")
    public void addCompletedMessage_MHM_T43() {
        //Test data
        String programName_T43 = "AutoGenerated program-T43 "+getRandomString();
        String SIGN_UP_KEYWORD_T43 = getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        //TODO: Add program and signup keyword
        site.programSteps()
                .addNewProgram(clientName, programName_T43, ProgramAccess.PUBLIC).addKeywordForSignUp(SIGN_UP_KEYWORD_T43)
                .addAccountCreationQuestion(false,
                        "Name and optional surname",
                        "Enter your name, please",
                        "Wrong name entered, try again")
                .addAccountCreationQuestion(false,
                        "Birth Date",
                        "Enter your Birth Date, please",
                        "Wrong birth day data format, it should be like, mm/dd/yyyy")
                .addCompletedMessage(COMPLETED_MESSAGE);

        //TODO: Simulate sms from patient to client with SignUp, Agree, Patient name, Skip and Start therapy date
        site.adminToolsSteps().initiateSignUpAgreeFirstLastNameDate(clientName, programName_T43, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD_T43);

        MessageLogItem signupResponse = site.programSteps()
                .goToProgramSettings(clientName, programName_T43)
                .getLastMessageFromLogsForNumber(FROM_PHONE_NUMBER);

        Assert.assertEquals(signupResponse.getMessage(), COMPLETED_MESSAGE, "Received message is not the same as expected!");
    }

    @Test(description = "Move a new patient from one program to another under the same client during keyword signup")
    public void movePatientDuringKeywordSignup_MHM_T65() {
        //Test data
        String SIGN_UP_KEYWORD_T65 = getRandomString();
        String initProgram = "AutoGenerated newInit program-T65 "+getRandomString();
        String landingProgram = "AutoGenerated landing program-T65 "+getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        //TODO: Create initial and landing programs
        site.programSteps()
                .addNewProgram(clientName, initProgram, ProgramAccess.PUBLIC).addKeywordForSignUp(SIGN_UP_KEYWORD_T65)
                .addNewProgram(clientName, landingProgram, ProgramAccess.PUBLIC);

        //TODO: Add moving to another program question
        site.programSteps()
                .goToProgramSettings(clientName, initProgram)
                .addDestinationProgramQuestionKeywords("MOVING", landingProgram)
                .addAccountCreationQuestion(false,
                        "Destination Program",
                        "Enter MOVING if you'd like to be moved to another program. Or type SKIP if you wish to stay in this one.",
                        "Wrong response, try again");

        //TODO: Simulate sms from patient to SignUp and AGREE to newInitProgram
        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, initProgram, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD_T65);
        //TODO: Simulate 'move' patient from newInitProgram to landingProgram
        site.adminToolsSteps().simulateSMSToClient(FROM_PHONE_NUMBER, TO_ENDPOINT, "MOVING");

        boolean isPatientMoved = site.programSteps()
                .goToProgramSettings(clientName, landingProgram)
                .isInProgram(landingProgram, FROM_PHONE_NUMBER);

        Assert.assertTrue(isPatientMoved, "The patient was not moved!");
    }


    @Test(description = "Move patient to a specific program under the same client")
    public void movePatientToSpecificProgram_MHM_T141() {
        //Test data
        String SIGN_UP_KEYWORD_141 = getRandomString();
        String initProgram = "AutoGenerated newInit program-T141 "+getRandomString();
        String landingProgram = "AutoGenerated landing program-T141 "+getRandomString();
        String FROM_PHONE_NUMBER = getGeneratedPhoneNumber();

        //TODO: Create initial and landing programs
        site.programSteps()
                .addNewProgram(clientName, initProgram, ProgramAccess.PUBLIC).addKeywordForSignUp(SIGN_UP_KEYWORD_141)
                .addNewProgram(clientName, landingProgram, ProgramAccess.PUBLIC);
        //TODO: Simulate sms from patient to SignUp and AGREE to newInitProgram
        site.adminToolsSteps().initiateKeywordSignupSendAndAgree(clientName, initProgram, FROM_PHONE_NUMBER, TO_ENDPOINT, SIGN_UP_KEYWORD_141);

        site.programSteps()
                .goToProgramSettings(clientName, initProgram)
                .movePatientManually(clientName, landingProgram, FROM_PHONE_NUMBER);

        boolean isPatientMoved = site.programSteps()
                .goToProgramSettings(clientName, landingProgram)
                .isInProgram(landingProgram, FROM_PHONE_NUMBER);

        Assert.assertTrue(isPatientMoved, "The patient was not moved!");
    }


    @AfterClass(alwaysRun = true)
    public void cleanUpClientData() {
        removeClient(client);
    }
}
