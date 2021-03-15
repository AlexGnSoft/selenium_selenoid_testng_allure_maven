package prod.smoke.program_level;

import com.carespeak.core.constant.Constants;
import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Sex;
import com.carespeak.domain.entities.message.MessageLogItem;
import com.carespeak.domain.entities.program.Patient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ProgramOptInMessageTest extends AbstractProgramLevelTest {

    private final String filePath = getResourcesPath() + "\\picture.png";

    private Client client;
    private Patient patient;
    private String programName;

    @BeforeClass
    public void prepareClientData() {
        client = getTestClientByCode("ProgramLevelTestOptIn client");
        programName = getTestProgramByName("OptIn program", client);
        patient = new Patient();
        patient.setFirstName("Patient");
        patient.setSex(Sex.MALE);
        patient.setCellPhone(getGeneratedPhoneNumber());
        patient.setTimezone("Eastern Time (New York)");
    }

    @Test(description = "Add opt-in message")
    public void addOptInMessageWithConfirmation() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addOptInMessages(filePath, true)
                .addNewPatient(patient, client, programName);

        String expectedOptInMessage = String.format(Constants.MessageTemplate.CONFIRM_SUBSCRIPTION, programName);
        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), expectedOptInMessage, "Received message is not the same as expected!");
    }

    @Test(description = "Check image is attached opt-in message", dependsOnMethods = "addOptInMessageWithConfirmation")
    public void messageAttachment() {
        boolean isImageAttached = site.programSteps().isAttachedImageDisplayed();

        Assert.assertTrue(isImageAttached, "Image is not attached");
    }

    @Test(description = "Simulate patient's AGREE response", dependsOnMethods = "messageAttachment")
    public void simulateConfirmation() {
        site.programSteps().simulateResponse(patient.getFirstName(), "Agree");

        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's STOP response", dependsOnMethods = "simulateConfirmation")
    public void simulateStop() {
        site.programSteps().simulateResponse(patient.getFirstName(), "Stop");

        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.UNSUBSCRIBED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's START response", dependsOnMethods = "simulateStop")
    public void simulateStart() {
        site.programSteps().simulateResponse(patient.getFirstName(), "Start");

        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's HELP response", dependsOnMethods = "simulateStart")
    public void simulateHelp() {
        site.programSteps().simulateResponse(patient.getFirstName(), "Help");

        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.HELP, "Received message is not the same as expected!");
    }

    @Test(description = "Add opt-in message with 'Do NOT send opt in confirmation message' checkbox", dependsOnMethods = "simulateHelp")
    public void addOptInMessageWithoutConfirmation() {
        site.programSteps()
                .goToProgramSettings(client.getName(), programName)
                .addOptInMessages(filePath, false);

        site.programSteps().simulateResponse(patient.getFirstName(), "Agree");

        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), "Agree", "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's STOP response", dependsOnMethods = "addOptInMessageWithoutConfirmation")
    public void verifyStopMessages() {
        site.programSteps().simulateResponse(patient.getFirstName(), "Stop");

        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.UNSUBSCRIBED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's START response", dependsOnMethods = "verifyStopMessages")
    public void verifyStartMessage() {
        site.programSteps().simulateResponse(patient.getFirstName(), "Start");

        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.ACCOUNT_ACTIVATED, "Received message is not the same as expected!");
    }

    @Test(description = "Simulate patient's HELP response", dependsOnMethods = "verifyStartMessage")
    public void verifyHelpMessage() {
        site.programSteps().simulateResponse(patient.getFirstName(), "Help");

        MessageLogItem actualOptInMessage = site.programSteps()
                .getLastPatientMessageFromLogs(patient.getFirstName());

        Assert.assertEquals(actualOptInMessage.getMessage(), Constants.MessageTemplate.HELP, "Received message is not the same as expected!");
    }

    @AfterClass
    public void removeProgram() {
        site.programSteps().removeProgram(client, programName);
        List<String> programs = site.programSteps().getProgramsForClient(client);
        Assert.assertFalse(programs.contains(programName), "Program '" + programName + "' was not removed!");
    }
}
