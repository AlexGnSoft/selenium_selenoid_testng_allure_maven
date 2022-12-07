package prod.smoke.program_level;

import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.core.constant.Constants;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.patient.Patient;
import com.carespeak.domain.entities.program.ProgramAccess;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProgramOptInMessageTest extends AbstractProgramLevelTest {

    private final String filePath = getResourcesPath() + "picture.png";
    private final String endPoint = PropertyFileReader.getVariableValue("twilioSmsSender");

    private Client client;
    private String clientName;
    private Patient patient;


    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("ProgramLevelTestOptIn client");
        clientName = client.getName();
        patient = new Patient();
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Add opt-in message")
    public void addOptInMessageWithConfirmation_MHM_T165() {
        String programName = "OptIn program " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addOptInMessagesWithoutAttachment(true)
                .addNewPatient(patient, client, programName);

        String expectedOptInMessage = String.format(Constants.MessageTemplate.CONFIRM_SUBSCRIPTION, programName);

        MessageLogItem actualOptInMessage = site.programSteps().getLastPatientMessage(patient);

        Assert.assertEquals(actualOptInMessage.getMessage(), expectedOptInMessage, "Received message is not the same as expected!");
    }

    @Test(description = "Add opt-in message with 'Do NOT send opt in confirmation message' checkbox")
    public void addOptInMessageWithoutConfirmation_MHM_T32() {
        String programName = "OptIn program " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addOptInMessagesWithoutAttachment(false)
                .addNewPatient(patient, client, programName);

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "AGREE");

        Assert.assertEquals(actualOptInMessage.getMessage(), "AGREE", "Received message is not the same as expected!");
    }

    @Test(description = "Check image is attached opt-in message")
    public void messageAttachment_MHM_T164() {
        boolean isImageAttached = false;
        String programName = "OptIn program " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addOptInMessagesWithAttachment(filePath, true)
                .addNewPatient(patient, client, programName);

        isImageAttached = site.programSteps().isAttachedImageDisplayed(patient.getFirstName());

        Assert.assertTrue(isImageAttached, "Image is not attached");
    }

    @Test(description = "Simulate patient's AGREE response")
    public void simulateConfirmation_MHM_T44() {
        //TODO: Page refresh to make 'Programs' button visible
        site.programSteps().pageRefresh();
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatient(patient, client, programName);

        MessageLogItem actualOptInMessage = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "AGREE");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's STOP response")
    public void simulateStop_MHM_T46() {
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatient(patient, client, programName);

        site.programSteps().simulateResponseAndGetLastPatientMessage(patient, "AGREE");

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "STOP");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.UNSUBSCRIBED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's START response")
    public void simulateStart_MHM_T166() {
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatient(patient, client, programName);

        site.programSteps().simulateResponseAndGetLastPatientMessage(patient, "AGREE");
        site.programSteps().simulateResponseAndGetLastPatientMessage(patient, "STOP");


        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "START");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's HELP response")
    public void simulateHelp_MHM_T47() {
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addNewPatient(patient, client, programName);

        site.programSteps().simulateResponseAndGetLastPatientMessage(patient, "AGREE");

        MessageLogItem actualOptInMessage  = site.programSteps()
                .simulateResponseAndGetLastPatientMessage(patient, "HELP");

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.HELP, "Received message is not the same as expected!");
    }

    @Test(description = "Message Logs - Simulate response functionality")
    public void simulateResponseFunctionality_MHM_T126() {
        String programName = "OptIn program  " + getFormattedDate("dd-MM-yy-H-mm");
        String signUpKeyWord = getRandomString();
        String phoneNumber = getGeneratedPhoneNumber();
        patient.setFirstName("Patient " + getRandomString());
        patient.setCellPhone(getGeneratedPhoneNumber());

        site.programSteps()
                .addNewProgram(clientName, programName, ProgramAccess.PUBLIC)
                .addKeywordForSignUp(signUpKeyWord)
                .addAccountCreationQuestion(false,
                        "Name and optional surname",
                        "Enter your name, please",
                        "Wrong name entered, try again");

        site.adminToolsSteps().simulateSMSToClient(phoneNumber, endPoint, signUpKeyWord);
        site.programSteps().goToProgramSettings(clientName, programName);


        site.programSteps().simulateResponse(patient.getFirstName(), "AGREE");
        //MessageLogItem actualOptInMessage  = site.programSteps().simulateResponseAndGetLastPatientMessage(patient, "AGREE");
        //System.out.println("Message is " + actualOptInMessage);

        //Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @AfterClass(alwaysRun = true)
    public void removeClient() {
        site.adminToolsSteps()
                .removeClient(client);
    }
}
